package org.springframework.ai.openai.samples.helloworld.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.openai.samples.helloworld.dto.GeoSearchResultDto;
import org.springframework.ai.chat.client.ChatClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class WikiGeoSearchService {


    private static final Logger logger = LoggerFactory.getLogger(WikiGeoSearchService.class);
    private static final String WIKIPEDIA_API_URL = "https://en.wikipedia.org/w/api.php";
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final ChatClient chatClient;

    public WikiGeoSearchService(ChatClient chatClient) {
        this.chatClient = chatClient;
        this.webClient = WebClient.builder()
            .baseUrl(WIKIPEDIA_API_URL)
            .defaultHeader("User-Agent", "SpringAIWithReact/1.0 (tthogho1@gmail.com)")
            .build();
        this.objectMapper = new ObjectMapper();
    }

    private String callGeoSearchApi(String latitude, String longitude, String gslimit, String gsradius) throws Exception {
        String limit = (gslimit == null || gslimit.isEmpty()) ? "10" : gslimit;
        String radius = (gsradius == null || gsradius.isEmpty()) ? "10000" : gsradius;
        String url = WIKIPEDIA_API_URL + "?format=json&list=geosearch&gscoord="
            + latitude + "|" + longitude
            + "&gslimit=" + limit + "&gsradius=" + radius + "&action=query";
        logger.info("[WikiGeoSearchService] Wikipedia API request URL: {}", url);
        String response = webClient.get()
            .uri(url.replace(WIKIPEDIA_API_URL, ""))
            .retrieve()
            .bodyToMono(String.class)
            .block();
        logger.info("[WikiGeoSearchService] Wikipedia API response:\n{}", response);
        return response;
    }

    public String searchNearbyPlaces(String latitude, String longitude) {
        return searchNearbyPlaces(latitude, longitude, null, null);
    }

    public String searchNearbyPlaces(String latitude, String longitude, String gslimit, String gsradius) {
        try {
            String response = callGeoSearchApi(latitude, longitude, gslimit, gsradius);
            JsonNode root = objectMapper.readTree(response);
            JsonNode places = root.path("query").path("geosearch");
            StringBuilder result = new StringBuilder();
            if (places.isArray()) {
                for (JsonNode place : places) {
                    String title = place.path("title").asText();
                    result.append(title).append("\n");
                }
            }
            return result.length() > 0 ? result.toString() : "No places found";
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    public List<GeoSearchResultDto> searchNearbyPlacesDto(String latitude, String longitude, String gslimit, String gsradius) {
        try {
            String response = callGeoSearchApi(latitude, longitude, gslimit, gsradius);
            JsonNode root = objectMapper.readTree(response);
            JsonNode places = root.path("query").path("geosearch");
            List<GeoSearchResultDto> resultList = new ArrayList<>();
            if (places.isArray()) {
                for (JsonNode place : places) {
                    GeoSearchResultDto dto = objectMapper.treeToValue(place, GeoSearchResultDto.class);
                    resultList.add(dto);
                }
            }
            return resultList;
        } catch (Exception e) {
            logger.error("Error occurred: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public String searchNearbyPlacesStrings(String latitude, String longitude, String gslimit, String gsradius) {
        try {
            List<GeoSearchResultDto> resultList = searchNearbyPlacesDto(latitude, longitude, gslimit, gsradius);
            List<String> extracts = fetchExtractsForGeoSearchResults(resultList);
            return summarizeAllExtractsWithOpenAI(extracts);
        } catch (Exception e) {
            logger.error("Error occurred: {}", e.getMessage());
            return "";
        }
    }

    private List<String> fetchExtractsForGeoSearchResults(List<GeoSearchResultDto> results) {
        List<String> extracts = new ArrayList<>();
        for (GeoSearchResultDto dto : results) {
            try {
                String url = WIKIPEDIA_API_URL + "?action=query&pageids=" + dto.getPageId() + "&prop=extracts&exintro&format=json";
                logger.info("[WikiGeoSearchService] Wikipedia extract request URL: {}", url);
                String response = webClient.get()
                        .uri(url.replace(WIKIPEDIA_API_URL, ""))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
                logger.info("[WikiGeoSearchService] Wikipedia extract response:\n{}", response);
                JsonNode root = objectMapper.readTree(response);
                JsonNode pages = root.path("query").path("pages");
                JsonNode page = pages.path(String.valueOf(dto.getPageId()));
                String extract = page.path("extract").asText("");
                extracts.add(extract);
            } catch (Exception e) {
                logger.error("Error fetching extract for pageid {}: {}", dto.getPageId(), e.getMessage());
                extracts.add("");
            }
        }
        return extracts;
    }

    public String summarizeAllExtractsWithOpenAI(List<String> extracts) {
        StringBuilder sb = new StringBuilder();
        for (String extract : extracts) {
            if (extract != null && !extract.isEmpty()) {
                sb.append(extract).append("\n\n");
            }
        }
        String prompt = "以下の複数のWikipedia概要文をまとめて英語で要約してください。\n" + sb.toString();
        try {
            String summary = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            return summary;
        } catch (Exception e) {
            logger.error("Error summarizing all extracts: {}", e.getMessage());
            return "";
        }
    }
}

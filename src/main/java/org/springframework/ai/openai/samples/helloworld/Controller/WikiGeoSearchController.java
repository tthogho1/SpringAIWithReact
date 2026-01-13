package org.springframework.ai.openai.samples.helloworld.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.samples.helloworld.Service.WikiGeoSearchService;
import org.springframework.ai.openai.samples.helloworld.dto.GeoSearchResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
public class WikiGeoSearchController {

    private static final Logger logger = LoggerFactory.getLogger(WikiGeoSearchController.class);
    @Autowired
    private WikiGeoSearchService wikiGeoSearchService;

    @PostMapping("/wikigeosearch")
    public String searchByLocation(@RequestBody Map<String, String> request) {
        String latitude = request.get("latitude");
        String longitude = request.get("longitude");
        String gslimit = request.get("gslimit");
        String gsradius = request.get("gsradius");
        if (latitude == null || longitude == null) {
            return "Error: latitude and longitude are required";
        }
        try {
            Double.parseDouble(latitude);
            Double.parseDouble(longitude);
        } catch (NumberFormatException e) {
            logger.error("Invalid latitude or longitude: {}", e.getMessage());
            return "Error: latitude and longitude must be numeric";
        }
        try {
            return wikiGeoSearchService.searchNearbyPlaces(latitude, longitude, gslimit, gsradius);
        } catch (Exception e) {
            logger.error("Exception in searchByLocation: {}", e.getMessage(), e);
            return "Error occurred: " + e.getMessage();
        }
    }

    @PostMapping("/wikigeosearch/dto")
    public List<GeoSearchResultDto> searchByLocationDto(@RequestBody Map<String, String> request) {
        String latitude = request.get("latitude");
        String longitude = request.get("longitude");
        String gslimit = request.get("gslimit");
        String gsradius = request.get("gsradius");
        if (latitude == null || longitude == null) {
            return List.of();
        }
        try {
            Double.parseDouble(latitude);
            Double.parseDouble(longitude);
        } catch (NumberFormatException e) {
            return List.of();
        }
        return wikiGeoSearchService.searchNearbyPlacesDto(latitude, longitude, gslimit, gsradius);
    }

    @PostMapping("/wikigeosearch/extracts")
    public String searchByLocationExtracts(@RequestBody Map<String, String> request) {
        String latitude = request.get("latitude");
        String longitude = request.get("longitude");
        String gslimit = request.get("gslimit");
        String gsradius = request.get("gsradius");
        if (latitude == null || longitude == null) {
            return "";
        }
        try {
            Double.parseDouble(latitude);
            Double.parseDouble(longitude);
        } catch (NumberFormatException e) {
              
            return "";
        }
        return wikiGeoSearchService.searchNearbyPlacesStrings(latitude, longitude, gslimit, gsradius);
    }
}

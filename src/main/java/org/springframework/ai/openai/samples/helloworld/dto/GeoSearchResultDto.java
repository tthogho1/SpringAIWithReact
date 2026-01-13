package org.springframework.ai.openai.samples.helloworld.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoSearchResultDto {
    @JsonProperty("pageid")
    private long pageId;
    @JsonProperty("ns")
    private int ns;
    @JsonProperty("title")
    private String title;
    @JsonProperty("lat")
    private double lat;
    @JsonProperty("lon")
    private double lon;
    @JsonProperty("dist")
    private double dist;
    @JsonProperty("primary")
    private String primary;

    // getters and setters
    public long getPageId() { return pageId; }
    public void setPageId(long pageId) { this.pageId = pageId; }
    public int getNs() { return ns; }
    public void setNs(int ns) { this.ns = ns; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }
    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }
    public double getDist() { return dist; }
    public void setDist(double dist) { this.dist = dist; }
    public String getPrimary() { return primary; }
    public void setPrimary(String primary) { this.primary = primary; }
}

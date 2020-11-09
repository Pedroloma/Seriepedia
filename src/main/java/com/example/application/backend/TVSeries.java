package com.example.application.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TVSeries {
    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "overview")
    private String overview;
    @JsonProperty(value = "first_air_date")
    private LocalDate firstAirDate;
    @JsonProperty(value = "last_air_date")
    private LocalDate lastAirDate;
    @JsonProperty(value = "number_of_episodes")
    private short numberOfEpisodes;
    @JsonProperty(value = "number_of_seasons")
    private short numberOfSeasons;

    private List<Season> seasons;

    public TVSeries() {
        super();
        seasons = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public LocalDate getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(LocalDate firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public LocalDate getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(LocalDate lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public short getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(short numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public short getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(short numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public String toString() {
        return "TVSeries{" +
                "overview='" + overview + '\'' +
                ", firstAirDate=" + firstAirDate +
                ", lastAirDate=" + lastAirDate +
                ", numberOfEpisodes=" + numberOfEpisodes +
                ", numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                '}';
    }
}

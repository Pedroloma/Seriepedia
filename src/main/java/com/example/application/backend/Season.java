package com.example.application.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Season {
    private int id;

    @JsonProperty(value = "season_number")
    private short seasonNumber;

    @JsonProperty(value = "air_date")
    private LocalDate airDate;

    private List<Episode> episodes;

    public Season() {
        super();
        episodes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(short seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public LocalDate getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDate airDate) {
        this.airDate = airDate;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return "id: " + id + " - " + "air date: " + airDate.toString();
    }

}

package com.example.application.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {


    @JsonProperty(value = "episode_number")
    private short episodeNumber;

    private String name;

    @JsonProperty(value = "air_date")
    private LocalDate airDate;

    public Episode() {
        super();
    }

    public short getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(short episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDate airDate) {
        this.airDate = airDate;
    }

    @Override
    public String toString() {
        return "Episode number: " + episodeNumber + " - " + "name: " + name;
    }
}

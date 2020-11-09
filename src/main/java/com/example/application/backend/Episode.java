package com.example.application.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {


    @JsonProperty(value = "episode_number")
    private short episodeNumber;

    private String name;

    public Episode() {
        super();
    }

    short getEpisodeNumber() {
        return episodeNumber;
    }

    void setEpisodeNumber(short episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Episode number: " + episodeNumber + " - " +"name: " + name;
    }
}

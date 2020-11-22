package com.example.application.backend.service;

import com.example.application.backend.entity.Season;
import com.example.application.backend.entity.TVSeries;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Service
public class SeasonService extends AbstractService {

    public Season callSeasonService(int tvSeriesId, int seasonNumber) {
        TVSeries tvSeries = new TVSeries();
        RestOperations restTemplate = new RestTemplate();
        Season season = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + tvSeriesId + "/season/" + seasonNumber
                + "?api_key=" + this.apiKey + "&language=en-US", Season.class);
        tvSeries.getSeasons().add(season);
        log.info("{}", season);
        return season;
    }


}

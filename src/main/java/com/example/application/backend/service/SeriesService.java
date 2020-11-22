package com.example.application.backend.service;

import com.example.application.backend.entity.TVSeries;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Service
public class SeriesService extends AbstractService {

    public TVSeries callSeriesService(int seriesId) {
        RestOperations restTemplate = new RestTemplate();
        TVSeries tvSeries = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + seriesId + "?api_key=" + this.apiKey + "&language=en-US", TVSeries.class);
        log.info(tvSeries != null ? tvSeries.toString() : null);
        return tvSeries;
    }

}

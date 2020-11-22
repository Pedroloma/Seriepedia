package com.example.application.backend.service;

import com.example.application.backend.entity.Search;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Service
public class SearchService extends AbstractService {

    public Search callSearchService(String searchString) {
        RestOperations restTemplate = new RestTemplate();
        Search search = restTemplate.getForObject("https://api.themoviedb.org/3/search/tv?api_key=" + this.apiKey + "&language=en-US&page=1&query=" + searchString + "&include_adult=false", Search.class);
        log.info(search != null ? search.toString() : null);
        return search;
    }

}

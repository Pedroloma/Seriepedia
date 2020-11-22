package com.example.application.backend.service;

import com.example.application.views.search.searchView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractService {

    protected static final Logger log = LoggerFactory.getLogger(searchView.class);

    @Value("${api.key}")
    protected String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}

package com.example.application.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {

    @JsonProperty(value = "page")
    private int page;
    @JsonProperty(value = "total_results")
    private int totalResults;
    @JsonProperty(value = "total_pages")
    private int totalPages;
    @JsonProperty(value = "results")
    private List<Result> results;

    public Search() {
        super();
        results = new ArrayList<>();
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Search{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", results=" + results +
                '}';
    }
}

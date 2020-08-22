package com.xtm.wikifootball.wiki.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties({"searchinfo"})
public class SearchQueryResult {

    @JsonProperty("search")
    private List<PageInfo> result;

    public List<PageInfo> getResult() {
        return result;
    }

    public void setResult(List<PageInfo> result) {
        this.result = result;
    }
}

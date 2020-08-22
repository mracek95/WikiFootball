package com.xtm.wikifootball.wiki.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties({"continue", "batchcomplete"})
public class SearchResult {

    @JsonProperty("query")
    private SearchQueryResult searchQueryResult;

    public SearchQueryResult getSearchQueryResult() {
        return searchQueryResult;
    }

    public void setSearchQueryResult(SearchQueryResult searchQueryResult) {
        this.searchQueryResult = searchQueryResult;
    }

    public List<PageInfo> getSearch() {
        return this.searchQueryResult.getResult();
    }
}

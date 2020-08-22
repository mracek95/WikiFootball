package com.xtm.wikifootball.wiki.connector;

import com.xtm.wikifootball.rest.RestTemplate;
import com.xtm.wikifootball.wiki.model.PageInfo;
import com.xtm.wikifootball.wiki.model.SearchResult;

import java.util.HashMap;
import java.util.Map;

public class WikiConnectorImpl implements WikiConnector {

    private static final String baseWikiApiUrl = "https://en.wikipedia.org/w/api.php";
    private static final String baseWikiUrl = "https://en.wikipedia.org/wiki/";

    private final RestTemplate restTemplate;

    public WikiConnectorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SearchResult findPages(String searchValue) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("action", "query");
        queryParams.put("list", "search");
        queryParams.put("format", "json");
        queryParams.put("srlimit", "10");
        queryParams.put("srsearch", searchValue);

        return restTemplate.get(baseWikiApiUrl, SearchResult.class, queryParams);
    }

    public String getFullUrlForPage(PageInfo pageInfo) {
        return baseWikiUrl + pageInfo.getTitle().replace(" ", "_");
    }
}

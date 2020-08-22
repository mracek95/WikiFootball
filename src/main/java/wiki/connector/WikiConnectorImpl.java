package wiki.connector;

import rest.RestTemplate;
import wiki.model.PageInfo;
import wiki.model.SearchResult;

import java.util.HashMap;
import java.util.Map;

public class WikiConnectorImpl implements WikiConnector {

    private final static String baseWikiApiUrl = "https://en.wikipedia.org/w/api.php";
    private final static String baseWikiUrl = "https://en.wikipedia.org/wiki/";

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

        return this.restTemplate.get(baseWikiApiUrl, SearchResult.class, queryParams);
    }

    public String getFullUrlForPage(PageInfo pageInfo) {
        return baseWikiUrl + pageInfo.getTitle().replaceAll(" ", "_");
    }
}

package wiki.service;

import javassist.NotFoundException;
import wiki.connector.WikiConnector;
import wiki.model.PageInfo;
import wiki.model.SearchResult;

import java.util.*;

public class WikiFootballService {

    private final WikiConnector wikiConnector;
    private final List<String> keyWords;

    public WikiFootballService(WikiConnector wikiConnector, List<String> keyWords) {
        this.wikiConnector = wikiConnector;
        this.keyWords = keyWords;
    }

    public String getWikiPageForFootballTeam(String footballTeam) throws NotFoundException {
        List<PageInfo> pageInfoList = searchWikiPages(footballTeam);
        if (pageInfoList.isEmpty()) {
            throw new NotFoundException("No wiki results for: " + footballTeam);
        }

        PageInfo bestPage = findBestMatch(pageInfoList);
        return getPageUrl(bestPage);
    }

    private List<PageInfo> searchWikiPages(String searchValue) {
        SearchResult result = wikiConnector.findPages(searchValue);
        return result.getSearch();
    }

    private PageInfo findBestMatch(List<PageInfo> pageInfoList) {
        Map<PageInfo, Integer> matchesMap = new LinkedHashMap<>();

        pageInfoList.forEach(pageInfo -> {
            Integer numberOfMatches = getNumberOfMatches(pageInfo.getSnippet());
            matchesMap.put(pageInfo, numberOfMatches);
        });

        return getMaxFromMap(matchesMap);
    }

    private Integer getNumberOfMatches(String text) {
        return keyWords.stream()
                .filter(keyWord -> text.toLowerCase().contains(keyWord.toLowerCase()))
                .toArray()
                .length;
    }

    private PageInfo getMaxFromMap(Map<PageInfo, Integer> matchesMap) {
        Map.Entry<PageInfo, Integer> bestEntry = null;
        for (Map.Entry<PageInfo, Integer> entry : matchesMap.entrySet()) {
            if (bestEntry == null) {
                bestEntry = entry;
            }
            if (entry.getValue() > bestEntry.getValue()) {
                bestEntry = entry;
            }
        }
        return bestEntry.getKey();
    }

    private String getPageUrl(PageInfo pageInfo) {
        return wikiConnector.getFullUrlForPage(pageInfo);
    }
}

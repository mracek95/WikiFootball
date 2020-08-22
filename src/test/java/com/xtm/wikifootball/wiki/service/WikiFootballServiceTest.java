package com.xtm.wikifootball.wiki.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.xtm.wikifootball.rest.RestTemplate;
import com.xtm.wikifootball.TestUtils;
import com.xtm.wikifootball.wiki.connector.WikiConnector;
import com.xtm.wikifootball.wiki.connector.WikiConnectorImpl;
import com.xtm.wikifootball.wiki.model.SearchQueryResult;
import com.xtm.wikifootball.wiki.model.SearchResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class WikiFootballServiceTest {

    private static final List<String> KEY_WORDS = Arrays.asList("F.C", "FC", "football club", "football", "club","league", "champions", "professional", "team", "match", "game", "sport");

    private ObjectMapper objectMapper = new ObjectMapper();
    private WikiConnectorImpl wikiConnectorImpl = new WikiConnectorImpl(mock(RestTemplate.class));
    private WikiConnector wikiConnectorMock = spy(wikiConnectorImpl);
    private WikiFootballService wikiFootballService;

    private SearchResult liverpoolSearchResult;
    private SearchResult emptySearchResult;
    private String liverpoolName = "Liverpool";
    private String notFoundName = "Not found name";

    @Before
    public void init() throws IOException {
        this.liverpoolSearchResult = getLiverpoolSearchResult();
        this.emptySearchResult = getEmptySearchResult();

        when(wikiConnectorMock.findPages("Not found name")).thenReturn(emptySearchResult);
        when(wikiConnectorMock.findPages(liverpoolName)).thenReturn(liverpoolSearchResult);

        this.wikiFootballService = new WikiFootballService(wikiConnectorMock, KEY_WORDS);
    }

    @Test
    public void shouldReturnFootballTeamUrl() throws NotFoundException {
        String result = wikiFootballService.getWikiPageForFootballTeam(liverpoolName);
        Assert.assertEquals("https://en.wikipedia.org/wiki/Liverpool_F.C.", result);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenNoPageFound() throws NotFoundException {
        wikiFootballService.getWikiPageForFootballTeam(notFoundName);
    }

    private SearchResult getLiverpoolSearchResult() throws IOException {
        String liverpoolResult = TestUtils.getLiverpoolResults();
        return objectMapper.readValue(liverpoolResult, SearchResult.class);
    }

    private SearchResult getEmptySearchResult() {
        SearchResult emptySearchResult = new SearchResult();
        SearchQueryResult searchQueryResult = new SearchQueryResult();

        searchQueryResult.setResult(Collections.emptyList());
        emptySearchResult.setSearchQueryResult(searchQueryResult);

        return emptySearchResult;
    }
}

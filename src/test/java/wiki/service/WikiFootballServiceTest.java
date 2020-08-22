package wiki.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rest.RestTemplate;
import wiki.TestUtils;
import wiki.connector.WikiConnector;
import wiki.connector.WikiConnectorImpl;
import wiki.model.SearchQueryResult;
import wiki.model.SearchResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class WikiFootballServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private List<String> keyWords = Arrays.asList("F.C", "FC", "football club", "football", "club","league", "champions", "professional", "team", "match", "game", "sport");
    private WikiConnectorImpl wikiConnectorImpl = new WikiConnectorImpl(mock(RestTemplate.class));
    private WikiConnector wikiConnectorMock = spy(wikiConnectorImpl);

    private SearchResult liverpoolSearchResult;
    private SearchResult emptySearchResult;

    @Before
    public void init() throws IOException, URISyntaxException {
        this.liverpoolSearchResult = getLiverpoolSearchResult();
        this.emptySearchResult = getEmptySearchResult();
        when(wikiConnectorMock.findPages("Empty")).thenReturn(getEmptySearchResult());
    }

    @Test
    public void shouldReturnFootballTeamUrl() throws NotFoundException {
        // given
        String liverpoolName = "Liverpool";
        when(wikiConnectorMock.findPages(liverpoolName)).thenReturn(liverpoolSearchResult);
        WikiFootballService wikiFootballService = new WikiFootballService(wikiConnectorMock, keyWords);

        // when
        String result = wikiFootballService.getWikiPageForFootballTeam(liverpoolName);

        // then
        Assert.assertEquals("https://en.wikipedia.org/wiki/Liverpool_F.C.", result);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenNoPageFound() throws NotFoundException {
        // given
        String notFoundName = "Not found name";
        when(wikiConnectorMock.findPages(notFoundName)).thenReturn(emptySearchResult);
        WikiFootballService wikiFootballService = new WikiFootballService(wikiConnectorMock, keyWords);

        // when
        wikiFootballService.getWikiPageForFootballTeam(notFoundName);
    }

    private SearchResult getLiverpoolSearchResult() throws IOException, URISyntaxException {
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

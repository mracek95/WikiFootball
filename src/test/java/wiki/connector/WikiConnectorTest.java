package wiki.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rest.RestTemplate;
import wiki.TestUtils;
import wiki.model.PageInfo;
import wiki.model.SearchResult;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

public class WikiConnectorTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private RestTemplate restTemplateMock = mock(RestTemplate.class);

    private WikiConnector wikiConnector;
    SearchResult liverpoolSearchResult;

    @Before
    public void init() throws IOException, URISyntaxException {
        liverpoolSearchResult = getLiverpoolSearchResult();
        when(restTemplateMock.get(any(), any(), any())).thenReturn(liverpoolSearchResult);
        wikiConnector = new WikiConnectorImpl(restTemplateMock);
    }

    @Test
    public void shouldReturnUrlForPage() {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTitle("Liverpool F.C.");

        String result = wikiConnector.getFullUrlForPage(pageInfo);

        Assert.assertEquals("https://en.wikipedia.org/wiki/Liverpool_F.C.", result);
    }

    @Test
    public void shouldReturnSearchResult() {
        SearchResult result = wikiConnector.findPages("Liverpool");

        Assert.assertEquals(liverpoolSearchResult, result);
    }

    private SearchResult getLiverpoolSearchResult() throws IOException, URISyntaxException {
        String liverpoolResult = TestUtils.getLiverpoolResults();
        return objectMapper.readValue(liverpoolResult, SearchResult.class);
    }
}

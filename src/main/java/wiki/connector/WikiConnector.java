package wiki.connector;

import wiki.model.PageInfo;
import wiki.model.SearchResult;

public interface WikiConnector {

    SearchResult findPages(String searchValue);

    String getFullUrlForPage(PageInfo pageInfo);
}

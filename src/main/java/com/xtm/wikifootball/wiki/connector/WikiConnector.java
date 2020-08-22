package com.xtm.wikifootball.wiki.connector;

import com.xtm.wikifootball.wiki.model.PageInfo;
import com.xtm.wikifootball.wiki.model.SearchResult;

public interface WikiConnector {

    SearchResult findPages(String searchValue);

    String getFullUrlForPage(PageInfo pageInfo);
}

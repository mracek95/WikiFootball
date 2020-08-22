package com.xtm.wikifootball.rest;

import java.util.Map;

public interface RestTemplate {

    <T> T get(String url, Class<T> resultType);

    <T> T get(String url, Class<T> resultType, Map<String, String> queryParams);
}

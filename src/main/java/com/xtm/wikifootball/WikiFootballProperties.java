package com.xtm.wikifootball;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

class WikiFootballProperties {

    private static final String PROPERTIES_FILE_NAME = "app.properties";

    private final Properties properties;

    protected WikiFootballProperties() {
        Properties properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.properties = properties;
    }

    protected List<String> getKeyWords() {
        String keyWords = (String) properties.get("keyWords");
        return Arrays.stream(keyWords.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}

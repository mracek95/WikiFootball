package com.xtm.wikifootball;

import javassist.NotFoundException;
import com.xtm.wikifootball.rest.RestTemplate;
import com.xtm.wikifootball.rest.RestTemplateImpl;
import com.xtm.wikifootball.wiki.connector.WikiConnector;
import com.xtm.wikifootball.wiki.connector.WikiConnectorImpl;
import com.xtm.wikifootball.wiki.service.WikiFootballService;

import java.util.List;

public class WikiFootball {

    public static void main(String[] args) {
        WikiFootballProperties wikiFootballProperties = new WikiFootballProperties();
        validateArguments(args);

        List<String> keyWords = wikiFootballProperties.getKeyWords();

        RestTemplate restTemplate = new RestTemplateImpl();
        WikiConnector wikiConnector = new WikiConnectorImpl(restTemplate);
        WikiFootballService wikiService = new WikiFootballService(wikiConnector, keyWords);

        try {
            String searchValue = args[0];
            String result = wikiService.getWikiPageForFootballTeam(searchValue);
            System.out.println(result);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void validateArguments(String args[]) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Provide one string argument!");
        }
    }
}
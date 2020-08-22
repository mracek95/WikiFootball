import javassist.NotFoundException;
import rest.RestTemplate;
import rest.RestTemplateImpl;
import wiki.connector.WikiConnector;
import wiki.connector.WikiConnectorImpl;
import wiki.service.WikiFootballService;

import java.util.List;

public class WikiFootball {

    public static void main(String[] args) {
        WikiFootballProperties wikiFootballProperties = new WikiFootballProperties();
        wikiFootballProperties.validateArguments(args);

        String searchValue = args[0];
        List<String> keyWords = wikiFootballProperties.getKeyWords();

        RestTemplate restTemplate = new RestTemplateImpl();
        WikiConnector wikiConnector = new WikiConnectorImpl(restTemplate);
        WikiFootballService wikiService = new WikiFootballService(wikiConnector, keyWords);

        try {
            String result = wikiService.getWikiPageForFootballTeam(searchValue);
            System.out.println(result);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
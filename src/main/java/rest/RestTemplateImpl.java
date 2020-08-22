package rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Map;

public class RestTemplateImpl implements RestTemplate {

    private Client client;

    public RestTemplateImpl() {
        this.client = ClientBuilder.newClient();
    }

    @Override
    public <T> T get(String url, Class<T> resultType) {
        return get(url, resultType, Collections.emptyMap());
    }

    @Override
    public <T> T get(String url, Class<T> resultType, Map<String, String> queryParams) {
        return createWebTargetWithParams(url, queryParams)
                .request(MediaType.APPLICATION_JSON)
                .get(resultType);
    }

    private WebTarget createWebTargetWithParams(String url, Map<String, String> queryParams) {
        WebTarget webTargetWithQueryParams = client.target(urlify(url));

        for (Map.Entry<String, String> mapEntry: queryParams.entrySet()) {
            webTargetWithQueryParams = webTargetWithQueryParams.queryParam(mapEntry.getKey(), urlify(mapEntry.getValue()));
        }

        return webTargetWithQueryParams;
    }

    private String urlify(String str) {
        return str.replaceAll(" ", "%20");
    }

}

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class WikiFootballProperties {

    private final static String PROPERTIES_FILE_NAME = "app.properties";

    private final Properties properties;

    public WikiFootballProperties() {
        Properties properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.properties = properties;
    }

    public List<String> getKeyWords() {
        String keyWords = (String) properties.get("keyWords");
        return Arrays.stream(keyWords.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public void validateArguments(String args[]) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Provide one string argument!");
        }
    }
}

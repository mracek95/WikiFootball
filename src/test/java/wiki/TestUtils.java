package wiki;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class TestUtils {

    private final static String RESULTS_FILE_NAME = "liverpoolResult.json";

    public static String getLiverpoolResults() {
        InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream(RESULTS_FILE_NAME);
        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
    }
}

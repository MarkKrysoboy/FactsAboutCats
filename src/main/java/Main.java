import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static final String PATH_SERVER_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/" +
            "master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("User")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build()) {
            HttpGet request = new HttpGet(PATH_SERVER_URL);
            request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

            CloseableHttpResponse response = httpClient.execute(request);
//            Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

            String answer = new String(response.getEntity().getContent().readAllBytes());
//            System.out.println(answer);


            List<FactAboutCat> factsAboutCats = mapper.readValue(answer,
                    new TypeReference<>() {
                    }
            );
            factsAboutCats.stream().filter(value -> value.getUpvotes() != null && Integer.parseInt(value.getUpvotes()) > 0).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

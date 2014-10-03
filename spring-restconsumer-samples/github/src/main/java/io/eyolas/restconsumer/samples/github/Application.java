package io.eyolas.restconsumer.samples.github;

import io.eyolas.restconsumer.samples.github.gitub.Contributor;
import io.eyolas.restconsumer.samples.github.gitub.GitHub;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eyolas.restconsumer.rest.RestBuilder;
import io.eyolas.restconsumer.rest.RestEntityManager;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author eyolas
 */
public class Application {

    private static final String API_URL = "https://api.github.com";

    

    public static void main(String[] args) throws Exception {
        RestEntityManager re = new RestEntityManager(new RestTemplate(), API_URL);
        re.setObjectMapper(new ObjectMapper());

        RestBuilder builder = new RestBuilder();
        GitHub gitHub = builder.create(GitHub.class, re);
//        List<Contributor> c = gitHub.contributors("eyolas", "spring-rest-consumer");
//        for (Contributor contributor : c) {
//            System.out.println("c : " + contributor);
//        }
        
        Contributor c = gitHub.getContributorInfo("eyolas");
        System.out.println(c);
    }
}

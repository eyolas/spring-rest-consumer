/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.eyolas.restconsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.eyolas.restconsumer.annotation.GET;
import io.eyolas.restconsumer.annotation.Path;
import io.eyolas.restconsumer.rest.RestBuilder;
import io.eyolas.restconsumer.rest.RestEntityManager;
import java.util.List;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author dev
 */
public class NewMain {
    
    public interface GitHub {

    @GET("/repos/{owner}/{repo}/contributors")
    List<Object> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo
    );
    
    @GET("/users/{username}")
    Object getContributorInfo(@Path("username") String username);
}


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RestEntityManager re = new RestEntityManager(new RestTemplate(), "https://api.github.com");
        re.setObjectMapper(new ObjectMapper());

        RestBuilder builder = new RestBuilder();
        GitHub gitHub = builder.create(GitHub.class, re);
//        List<Contributor> c = gitHub.contributors("eyolas", "spring-rest-consumer");
//        for (Contributor contributor : c) {
//            System.out.println("c : " + contributor);
//        }
        
        Object c = gitHub.getContributorInfo("eyolas");
        System.out.println(c);
    }
    
}

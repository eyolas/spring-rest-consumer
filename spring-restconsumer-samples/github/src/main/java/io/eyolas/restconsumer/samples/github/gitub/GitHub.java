package io.eyolas.restconsumer.samples.github.gitub;

import io.eyolas.restconsumer.annotation.GET;
import io.eyolas.restconsumer.annotation.Path;
import java.util.List;

/**
 *
 * @author eyolas
 */
public interface GitHub {

    @GET("/repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo
    );
    
    @GET("/users/{username}")
    Contributor getContributorInfo(@Path("username") String username);
}

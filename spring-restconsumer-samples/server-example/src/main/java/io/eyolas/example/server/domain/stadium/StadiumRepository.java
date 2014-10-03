package io.eyolas.example.server.domain.stadium;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import io.eyolas.example.server.domain.city.City;
import io.eyolas.example.server.domain.stadium.Stadium;

/**
 *
 * @author eyolas
 */
@RepositoryRestResource(collectionResourceRel = "stadiums", path = "stadiums")
public interface StadiumRepository extends JpaRepository<Stadium, Long>{
    List<Stadium> findByNameContainingIgnoreCaseAndCity(@Param("name") String name, @Param("city") City city);
}

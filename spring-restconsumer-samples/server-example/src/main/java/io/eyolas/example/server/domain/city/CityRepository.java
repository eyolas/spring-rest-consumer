package io.eyolas.example.server.domain.city;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import io.eyolas.example.server.domain.city.City;

/**
 *
 * @author eyolas
 */
@RepositoryRestResource(collectionResourceRel = "cities", path = "cities")
public interface CityRepository extends JpaRepository<City, Long>{
    List<City> findByNameContainingIgnoreCase(@Param("name") String name);
}

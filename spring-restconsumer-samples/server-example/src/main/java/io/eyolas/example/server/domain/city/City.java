package io.eyolas.example.server.domain.city;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author eyolas
 */
@Data
@Entity
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String state;

    @Column
    private String country;

    @Column(nullable = false)
    private String map;

    public City() {
    }

    public City(String name, String state, String country, String map) {
        this.name = name;
        this.state = state;
        this.country = country;
        this.map = map;
    }

}

package io.eyolas.example.server.domain.stadium;

import io.eyolas.example.server.domain.city.City;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

/**
 *
 * @author eyolas
 */
@Data
@Entity
public class Stadium implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private City city;

    @Column
    private String name;

    public Stadium() {
    }

    public Stadium(City city, String name) {
        this.city = city;
        this.name = name;
    }

}

package ru.practicum.mainserver.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "location")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float lat;

    @Column(nullable = false)
    private Float lon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationEntity)) return false;
        LocationEntity location = (LocationEntity) o;
        return Objects.equals(id, location.id) ||
                Objects.equals(lat, location.lat) &&
                        Objects.equals(lon, location.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {

        return "\nclass LocationEntity {\n" +
                "    id: " + id + "\n" +
                "    latitude: " + lat + "\n" +
                "    longitude: " + lon + "\n" +
                "}";
    }
}

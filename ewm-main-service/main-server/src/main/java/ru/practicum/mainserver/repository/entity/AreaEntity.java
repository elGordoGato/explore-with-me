package ru.practicum.mainserver.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "with-location",
        attributeNodes = {
                @NamedAttributeNode("location"),
        }
)
public class AreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private LocationEntity location;

    @Column(nullable = false)
    private Float radius;

    @Override
    public String toString() {

        return "\nclass AreaEntity {\n" +
                "    id: " + (id) + "\n" +
                "    title: " + (title) + "\n" +
                "    location: " + (location.getId()) + "\n" +
                "    radius: " + (radius) + "\n" +
                "}";
    }
}

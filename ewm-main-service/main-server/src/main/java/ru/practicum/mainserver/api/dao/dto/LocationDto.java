package ru.practicum.mainserver.api.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

/**
 * Широта и долгота места проведения события
 */

@Getter
@Builder
public class LocationDto {
    private Long id;

    @JsonProperty("lat")
    private Float lat;

    @JsonProperty("lon")
    private Float lon;


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationDto locationDto = (LocationDto) o;
        return Objects.equals(this.lat, locationDto.lat) && Objects.equals(this.lon, locationDto.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }

    @Override
    public String toString() {

        String sb = "class LocationDto {\n" +
                "    lat: " + lat + "\n" +
                "    lon: " + lon + "\n" +
                "}";
        return sb;
    }
}

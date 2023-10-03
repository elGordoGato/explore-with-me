package ru.practicum.mainserver.api.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Широта и долгота места проведения события
 */
@Validated
public class LocationDto {
    @JsonProperty("lat")
    private Float lat = null;

    @JsonProperty("lon")
    private Float lon = null;

    public LocationDto lat(Float lat) {
        this.lat = lat;
        return this;
    }

    /**
     * Широта
     *
     * @return lat
     **/
    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public LocationDto lon(Float lon) {
        this.lon = lon;
        return this;
    }

    /**
     * Долгота
     *
     * @return lon
     **/


    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }


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
        StringBuilder sb = new StringBuilder();
        sb.append("class Location {\n");

        sb.append("    lat: ").append(toIndentedString(lat)).append("\n");
        sb.append("    lon: ").append(toIndentedString(lon)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

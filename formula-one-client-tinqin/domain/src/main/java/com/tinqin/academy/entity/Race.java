package com.tinqin.academy.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "race")
@Getter
@Setter(AccessLevel.PRIVATE)
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_race;

    private String circuitName;

    private LocalDate raceDate;
    @ManyToOne
    @JoinColumn(name = "id_driver")
    private Driver winner;

    private Double latitude;

    private Double longitude;

    private Double distancePerLap;

    private Integer laps;

    public Race() {
    }

    public Race(String circuitName, LocalDate raceDate, Driver winner, Double latitude, Double longitude, Double distancePerLap, Integer laps) {
        this.circuitName = circuitName;
        this.raceDate = raceDate;
        this.winner = winner;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distancePerLap = distancePerLap;
        this.laps = laps;
    }
    public Race(String circuitName, LocalDate raceDate, Double latitude, Double longitude, Double distancePerLap, Integer laps) {
        this.circuitName = circuitName;
        this.raceDate = raceDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distancePerLap = distancePerLap;
        this.laps = laps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return Objects.equals(id_race, race.id_race) && Objects.equals(circuitName, race.circuitName) && Objects.equals(raceDate, race.raceDate) && Objects.equals(winner, race.winner) && Objects.equals(latitude, race.latitude) && Objects.equals(longitude, race.longitude) && Objects.equals(distancePerLap, race.distancePerLap) && Objects.equals(laps, race.laps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_race, circuitName, raceDate, winner, latitude, longitude, distancePerLap, laps);
    }
}

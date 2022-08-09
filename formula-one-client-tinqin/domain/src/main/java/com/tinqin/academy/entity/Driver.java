package com.tinqin.academy.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "driver")
@Getter
@Setter
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_driver;

    private String firstName;

    private String lastName;

    private Double salary;

    private Integer championships;

    @ManyToOne
    @JoinColumn(name = "id_team")
    private Team team;

    public Driver() {
    }

    public Driver(String firstName, String lastName, Double salary, Integer championships, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.championships = championships;
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id_driver, driver.id_driver) && Objects.equals(firstName, driver.firstName) && Objects.equals(lastName, driver.lastName) && Objects.equals(salary, driver.salary) && Objects.equals(championships, driver.championships) && Objects.equals(team, driver.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_driver, firstName, lastName, salary, championships, team);
    }
}

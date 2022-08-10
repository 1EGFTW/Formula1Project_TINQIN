package com.tinqin.academy.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "team")
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_team;

    private String teamName;

    private Double budget;

    @OneToMany(mappedBy = "team")
    private Set<Driver> drivers=new HashSet<>();

    public Team() {
    }

    public Team(String teamName, Double budget) {
        this.teamName = teamName;
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id_team, team.id_team) && Objects.equals(teamName, team.teamName) && Objects.equals(budget, team.budget) && Objects.equals(drivers, team.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_team, teamName, budget, drivers);
    }
}

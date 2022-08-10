package com.tinqin.academy.data.repository;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    Optional<Driver> findDriverByFirstNameAndLastName(String firstName,String lastName);
    List<Driver> findDriversByTeam(Team team);
}

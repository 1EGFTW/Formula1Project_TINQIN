package com.tinqin.academy.repository;

import com.tinqin.academy.entity.Driver;
import com.tinqin.academy.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RaceRepository extends JpaRepository<Race,Long> {
    Optional<Race> getRaceByCircuitName(String circuitName);
    List<Race> getRacesByWinner(Driver driver);
}

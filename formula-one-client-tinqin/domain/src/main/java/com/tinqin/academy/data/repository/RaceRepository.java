package com.tinqin.academy.data.repository;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RaceRepository extends JpaRepository<Race,Long> {
    Optional<Race> getRaceByCircuitName(String circuitName);
    List<Race> getRacesByWinner(Driver driver);
}

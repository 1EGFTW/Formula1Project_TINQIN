package com.tinqin.academy.repository;

import com.tinqin.academy.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    Optional<Driver> findDriverByFirstNameAndLastName(String firstName,String lastName);
}

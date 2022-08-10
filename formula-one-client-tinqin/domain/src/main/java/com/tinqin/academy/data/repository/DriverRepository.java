package com.tinqin.academy.data.repository;

import com.tinqin.academy.data.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    Optional<Driver> findDriverByFirstNameAndLastName(String firstName,String lastName);
}

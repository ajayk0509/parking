package com.nl.parking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nl.parking.entity.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String>{

	Optional<Parking> findByLicencePlateNumber(String licencePlateNumber);
}

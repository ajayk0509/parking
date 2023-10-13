package com.nl.parking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nl.parking.entity.ParkingPriceMaster;

@Repository
public interface ParkingPriceMasterRepository extends JpaRepository<ParkingPriceMaster, String>{

	Optional<ParkingPriceMaster> findByStreetName(String streetName);
}

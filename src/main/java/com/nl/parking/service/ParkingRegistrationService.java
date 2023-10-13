package com.nl.parking.service;

import java.util.List;

import com.nl.parking.entity.Parking;
import com.nl.parking.payloads.request.ParkingRegistrationRequest;

public interface ParkingRegistrationService {

	Parking register(ParkingRegistrationRequest parkingRegistrationRequest);
	Parking unregister(ParkingRegistrationRequest parkingRegistrationRequest);
	List<Parking> getAllParkings();
}

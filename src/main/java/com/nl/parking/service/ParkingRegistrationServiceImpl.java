package com.nl.parking.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nl.parking.entity.Parking;
import com.nl.parking.entity.ParkingPriceMaster;
import com.nl.parking.exception.BadRequestException;
import com.nl.parking.payloads.request.ParkingRegistrationRequest;
import com.nl.parking.repository.ParkingPriceMasterRepository;
import com.nl.parking.repository.ParkingRepository;

@Service
public class ParkingRegistrationServiceImpl implements ParkingRegistrationService{

	private static final Logger log = LoggerFactory.getLogger(ParkingRegistrationServiceImpl.class);
	
	@Autowired
	private ParkingRepository parkingRepository;
	
	@Autowired
	private ParkingPriceMasterRepository parkingPriceMasterRepository; 
	
	@Override
	public Parking register(ParkingRegistrationRequest parkingRegistrationRequest) {
		Instant startTime = Instant.now();		
		Parking parking = new Parking();
		parking.setStreetName(parkingRegistrationRequest.getStreetName());
		parking.setLicencePlateNumber(parkingRegistrationRequest.getLicencePlateNumber());
		parking.setStartTime(startTime);
		return parkingRepository.save(parking);
	}

	@Override
	public Parking unregister(ParkingRegistrationRequest parkingRegistrationRequest) {
		Optional<Parking> parkingOptional = parkingRepository.findByLicencePlateNumber(parkingRegistrationRequest.getLicencePlateNumber());
		return parkingOptional.filter(parking -> parking.getLicencePlateNumber().equals(parkingRegistrationRequest.getLicencePlateNumber()))
		.map(parking -> {
			Instant endTime = Instant.now();
			parking.setEndTime(endTime);
			parking.setAmount(calculateParkingCharges(parking.getStreetName(), parking.getLicencePlateNumber(), parking.getStartTime(), endTime));
			return parkingRepository.save(parking);
		}).orElseGet(() -> {
			throw new BadRequestException("Licence plate number is not registerd");
		});		
	}
	
	private Integer calculateParkingCharges(String streetName, String licencePlateNumber, Instant startTime, Instant endTime) {
		Optional<ParkingPriceMaster> parkingMaster = parkingPriceMasterRepository.findByStreetName(streetName);
		if(parkingMaster.isPresent()) {
			Integer price = parkingMaster.get().getPrice();
			int minutesBetween = (int)Duration.between(startTime, endTime).toMinutes();		
			return price*minutesBetween;
		}
		return 0;		
	}

	@Override
	public List<Parking> getAllParkings() {		
		return parkingRepository.findAll();
	}

}

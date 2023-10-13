package com.nl.parking.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nl.parking.entity.Parking;
import com.nl.parking.payloads.request.ParkingRegistrationRequest;
import com.nl.parking.service.ParkingRegistrationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("/api")
public class ParkingSystemController {

	private static final Logger log = LoggerFactory.getLogger(ParkingSystemController.class);
	
	@Autowired
	private ParkingRegistrationService parkingRegistrationService;	
	
	@ApiOperation(value = "Parking registration")
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "car parking registration has been successfully"),
							@ApiResponse(code = 201, message = "Internal Server Error")})
	@PostMapping("register")
	public ResponseEntity<Parking> registerParking(@RequestBody ParkingRegistrationRequest parkingRegistrationRequest){
		ResponseEntity<Parking> response = null;
		log.info("Request recieved for parking registration : {}", parkingRegistrationRequest);
		Parking parking = parkingRegistrationService.register(parkingRegistrationRequest);
		//mailService.sendErrorEmails("Parking registration successfully",parking.toString());  // Need to configure the SMTP server details
		response = ResponseEntity.status(HttpStatus.CREATED).body(parking);
		return response;
	}
	
	@ApiOperation(value = "Parking unregistration")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "car parking registration has been successfully"),
							@ApiResponse(code = 201, message = "Internal Server Error")})
	@PutMapping("unregister")
	public ResponseEntity<Parking> unregisterParking(@RequestBody ParkingRegistrationRequest parkingRequest){
		ResponseEntity<Parking> response = null;
		Parking parking = parkingRegistrationService.unregister(parkingRequest);		
		response = ResponseEntity.status(HttpStatus.ACCEPTED).body(parking);
		return response;
	}
	
	@ApiOperation(value = "List all parking registered")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "car parking registration has been successfully"),
							@ApiResponse(code = 201, message = "Internal Server Error")})
	@GetMapping("unregister")
	public ResponseEntity<List<Parking>> getAllParkings(){
		ResponseEntity<List<Parking>> response = null;
		List<Parking> parkingList = parkingRegistrationService.getAllParkings();		
		response = ResponseEntity.status(HttpStatus.OK).body(parkingList);
		return response;
	}
}

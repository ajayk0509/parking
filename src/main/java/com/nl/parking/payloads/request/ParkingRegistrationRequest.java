package com.nl.parking.payloads.request;

import lombok.Data;

@Data
public class ParkingRegistrationRequest {
	private String streetName;
	private String licencePlateNumber;

}

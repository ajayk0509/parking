package com.nl.parking.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.parking.entity.Parking;
import com.nl.parking.entity.ParkingPriceMaster;
import com.nl.parking.payloads.request.ParkingRegistrationRequest;
import com.nl.parking.repository.ParkingPriceMasterRepository;
import com.nl.parking.repository.ParkingRepository;

@SpringBootTest
public class ParkingRegistrationServiceTest {
	
	@Autowired
	private ObjectMapper mapper;

	@InjectMocks
	ParkingRegistrationServiceImpl parkingRegistrationServiceImpl;
	
	@Mock
	private ParkingRepository parkingRepository;
	
	@Mock
	private ParkingPriceMasterRepository parkingPriceMasterRepository; 
	
	private static final String REQ = "{\r\n"
			+ "    \"streetName\" : \"Jakarta\",\r\n"
			+ "    \"licencePlateNumber\" : \"DL7C5877\"\r\n"
			+ "}";
	
	private static final String RES = "{\r\n"
			+ "    \"id\": 0,\r\n"
			+ "    \"streetName\": \"Jakarta\",\r\n"
			+ "    \"licencePlateNumber\": \"DL7C5877\",\r\n"
			+ "    \"startTime\": \"2023-10-13T09:36:41.276Z\",\r\n"
			+ "    \"endTime\": null,\r\n"
			+ "    \"amount\": null\r\n"
			+ "}";
	private static final String RES_UNREGISTER = "{\r\n"
			+ "    \"id\": 2,\r\n"
			+ "    \"streetName\": \"Jakarta\",\r\n"
			+ "    \"licencePlateNumber\": \"DL7C5877\",\r\n"
			+ "    \"startTime\": \"2023-10-13T05:49:28Z\",\r\n"
			+ "    \"endTime\": \"2023-10-13T05:53:07.011Z\",\r\n"
			+ "    \"amount\": 39\r\n"
			+ "}";
	
	@Test
	public void testRegister() throws Exception {
		Parking parking = mapper.readValue(RES, Parking.class);
		ParkingRegistrationRequest parkingRegistrationRequest = mapper.readValue(REQ, ParkingRegistrationRequest.class);
		Mockito.when(parkingRepository.save(Mockito.any())).thenReturn(parking);
		Parking response = parkingRegistrationServiceImpl.register(parkingRegistrationRequest);
		assertThat(response.getStreetName().equals(parkingRegistrationRequest.getStreetName()));
		assertThat(response.getLicencePlateNumber().equals(parkingRegistrationRequest.getLicencePlateNumber()));
		assertThat(response.getStartTime()).isNotNull();
		assertThat(response.getEndTime()).isNull();
	}
	
	@Test
	public void testUnRegister() throws Exception {
		Parking parking = mapper.readValue(RES_UNREGISTER, Parking.class);
		ParkingPriceMaster parkingPriceMaster = new ParkingPriceMaster();
		parkingPriceMaster.setStreetName("Jakarta");
		parkingPriceMaster.setPrice(13);		
		Optional<Parking> parkingOptuional = Optional.of(parking);
		Optional<ParkingPriceMaster> parkingPriceOptuional = Optional.of(parkingPriceMaster);
		ParkingRegistrationRequest parkingRegistrationRequest = mapper.readValue(REQ, ParkingRegistrationRequest.class);		
		Mockito.when(parkingRepository.findByLicencePlateNumber(Mockito.any())).thenReturn(parkingOptuional);
		Mockito.when(parkingPriceMasterRepository.findByStreetName(Mockito.any())).thenReturn(parkingPriceOptuional);
		Mockito.when(parkingRepository.save(Mockito.any())).thenReturn(parking);
		
		Parking response = parkingRegistrationServiceImpl.unregister(parkingRegistrationRequest);
		assertThat(response.getStreetName().equals(parkingRegistrationRequest.getStreetName()));
		assertThat(response.getLicencePlateNumber().equals(parkingRegistrationRequest.getLicencePlateNumber()));
		assertThat(response.getStartTime()).isNotNull();
		assertThat(response.getEndTime()).isNotNull();
		assertThat(response.getAmount()).isNotNull();
	}
}

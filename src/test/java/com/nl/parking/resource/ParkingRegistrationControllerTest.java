package com.nl.parking.resource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.parking.entity.Parking;
import com.nl.parking.payloads.request.ParkingRegistrationRequest;
import com.nl.parking.service.ParkingRegistrationService;

@WebMvcTest(ParkingSystemController.class)
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class ParkingRegistrationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private ParkingRegistrationService parkingRegistrationService;		
	
	@Spy
	@InjectMocks
	private ParkingSystemController parkingSystemController;
	
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
			+ "    \"licencePlateNumber\": \"dl0396\",\r\n"
			+ "    \"startTime\": \"2023-10-13T05:49:28Z\",\r\n"
			+ "    \"endTime\": \"2023-10-13T05:53:07.011Z\",\r\n"
			+ "    \"amount\": 39\r\n"
			+ "}";
	
	
	
	@Test
	public void testRegister() throws Exception {
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequest));
		ParkingRegistrationRequest parkingRegistrationRequest = mapper.readValue(REQ, ParkingRegistrationRequest.class);
		Parking parking = mapper.readValue(RES, Parking.class);
		Mockito.when(parkingRegistrationService.register(Mockito.any(ParkingRegistrationRequest.class))).thenReturn(parking);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/register");
		builder.accept(MediaType.APPLICATION_JSON);
		builder.contentType(MediaType.APPLICATION_JSON);
		builder.content(mapper.writeValueAsString(parkingRegistrationRequest));
		httpServletRequest.setMethod("POST");				
		MvcResult result = mockMvc.perform(builder).andReturn();
		String responseAsString = result.getResponse().getContentAsString();
		Parking parkingResObj = mapper.readValue(responseAsString, Parking.class);
		assertThat(parkingResObj.getStreetName().equals(parkingRegistrationRequest.getStreetName()));
		assertThat(parkingResObj.getLicencePlateNumber().equals(parkingRegistrationRequest.getLicencePlateNumber()));
		assertThat(parkingResObj.getStartTime()).isNotNull();
		assertThat(parkingResObj.getEndTime()).isNull();
		
	}
	
	@Test
	public void testUnRegister() throws Exception {
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequest));
		ParkingRegistrationRequest parkingRegistrationRequest = mapper.readValue(REQ, ParkingRegistrationRequest.class);
		Parking parking = mapper.readValue(RES_UNREGISTER, Parking.class);
		Mockito.when(parkingRegistrationService.unregister(Mockito.any(ParkingRegistrationRequest.class))).thenReturn(parking);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/unregister");
		builder.accept(MediaType.APPLICATION_JSON);
		builder.contentType(MediaType.APPLICATION_JSON);
		builder.content(mapper.writeValueAsString(parkingRegistrationRequest));
		httpServletRequest.setMethod("PUT");				
		MvcResult result = mockMvc.perform(builder).andReturn();
		String responseAsString = result.getResponse().getContentAsString();
		Parking parkingResObj = mapper.readValue(responseAsString, Parking.class);
		assertThat(parkingResObj.getStreetName().equals(parkingRegistrationRequest.getStreetName()));
		assertThat(parkingResObj.getLicencePlateNumber().equals(parkingRegistrationRequest.getLicencePlateNumber()));
		assertThat(parkingResObj.getStartTime()).isNotNull();
		assertThat(parkingResObj.getEndTime()).isNotNull();
		
	}

}

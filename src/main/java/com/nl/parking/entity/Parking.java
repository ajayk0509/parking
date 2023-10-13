package com.nl.parking.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parking_registration")
public class Parking {

	@Id
	private long id;
	
	@Column(name = "street_name")
	private String streetName;
	
	@Column(name = "licence_plate_number")
	private String licencePlateNumber;
	
	@Column(name = "request_start_time")
	private Instant startTime;
	
	@Column(name = "request_end_time")
	private Instant endTime;
	
	@Column
	private Integer amount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getLicencePlateNumber() {
		return licencePlateNumber;
	}

	public void setLicencePlateNumber(String licencePlateNumber) {
		this.licencePlateNumber = licencePlateNumber;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}

	public Instant getEndTime() {
		return endTime;
	}

	public void setEndTime(Instant endTime) {
		this.endTime = endTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer integer) {
		this.amount = integer;
	}

	@Override
	public String toString() {
		return "Parking [id=" + id + ", streetName=" + streetName + ", licencePlateNumber=" + licencePlateNumber
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", amount=" + amount + "]";
	}
}

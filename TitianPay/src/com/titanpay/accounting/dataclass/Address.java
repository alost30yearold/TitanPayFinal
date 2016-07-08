package com.titanpay.accounting.dataclass;

import java.io.Serializable;

import lombok.Data;
@SuppressWarnings("serial")
@Data
public class Address implements Serializable {
	private String streetAddress;
	private String city;
	private String state;
	private int zip;

	public Address(String streetAddress, String city, String state, int zip){
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public Address() {
		// TODO Auto-generated constructor stub
	}

	public String getAddress() {
		String address = streetAddress+" "+city+" "+state+" "+zip;
		return address;
	}
	@Override
	public String toString(){
		String theString = "Address :\tSalary: "+this.streetAddress+"\tCity: "+this.city+"\tState: "+this.state+"\tZip: "+zip;
		return theString;
	}
}
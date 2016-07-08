package com.titanpay.accounting.dataclass;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Receipt implements Serializable {
	
	private DateTime date;
	private double saleAmt;
	
	public Receipt(String date, double saleAmt){
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd-HH:mm");
		this.date = DateTime.parse(date, formatter);
		//Turning the inputed string into a Date we can use.
		this.saleAmt = saleAmt;  
	}
	public Receipt(double saleAmt){
		this.date = DateTime.now();
		this.saleAmt = saleAmt;  
	}
	public double calculateCom(double commissionRate){
		double commissionPayed = (this.saleAmt*commissionRate);
		return commissionPayed;
	}
	public int getDateOfMonth() {
		return date.getDayOfMonth();
	}
	@Override
	public String toString(){
		String theString = "Receipt :\tDate: "+this.date+"\tSale Amount: "+this.saleAmt;
		return theString;
	}
	
}

package com.titanpay.accounting.dataclass;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.titanpay.accounting.dataclass.TimeCard;

public class TimeCardTest {

	String nStartDate = "6/20/2016-0900";
	String nEndDate = "6/20/2016-1700";
	String oStartDate = "6/20/2016-0900";
	String oEndDate = "6/20/2016-1800";
	TimeCard cut;
	double hourlyRate = 11.50;
	double hoursWorked;
	double pay;
	double normalDaysPay = hourlyRate * 8;
	double overTimePay;

	@Before
	public void setUp() {
		cut = new TimeCard();
	}

	@Test
	public void calculateDailyPayNormalDay() {
		cut = new TimeCard(nStartDate, nEndDate);

		pay = cut.calculateDailyPay(hourlyRate);

		assertEquals(pay, normalDaysPay, 0);

	}

	@Test
	public void calculateDailyPayOverTimeDay() {
		cut = new TimeCard(oStartDate, oEndDate);

		pay = cut.calculateDailyPay(hourlyRate);
		hoursWorked = 9;
		overTimePay = (hourlyRate * (hoursWorked - 8)) * 1.5;

		assertEquals(pay, normalDaysPay + overTimePay, 0);

	}

}

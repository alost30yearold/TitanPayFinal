package com.titanpay.accounting.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.joda.time.DateTime;

import org.junit.Before;
import org.junit.Test;

import com.titanpay.accounting.dataclass.Address;
import com.titanpay.accounting.dataclass.BankAccount;
import com.titanpay.accounting.dataclass.DirectDepositPayment;
import com.titanpay.accounting.dataclass.PaymentMethod;
import com.titanpay.accounting.dataclass.TimeCard;
import com.titanpay.accounting.entity.HourlyEmployee;

public class HourlyEmployeeTest {
	Address bobSalAdd = new Address("324 Heary Cheese St", "Clearwater", "FL", 33444);
	BankAccount bobAcc = new BankAccount("Chartway", 245777, 52677769);
	PaymentMethod bobPay = new DirectDepositPayment("Lois Lane", 0, bobAcc);
	String startPayDate = "20160601-07:30";
	String endPayDate = "20180630-22:00";
	TimeCard bobPay1 = new TimeCard("6/20/2016-0900", "6/20/2016-1600");
	TimeCard bobPay2 = new TimeCard("6/21/2016-0900", "6/21/2016-1600");
	TimeCard bobPay3 = new TimeCard("6/22/2016-0900", "6/22/2016-1600");

	HourlyEmployee cut; // class under test

	@Before
	public void setUp() {
		cut = new HourlyEmployee();
	}

	@Test
	public void testPayToString() {
		cut = new HourlyEmployee(1, "first ", "last ", 150, 11, bobSalAdd, bobPay);
		
		cut.addTimeCard(bobPay1);
		cut.addTimeCard(bobPay2);
		cut.addTimeCard(bobPay3);

		// cut.setTimeCards(timeCards);
		// once you set up this cut class with how you need it
		String expected = "Depositing $ 231.00 in Chartway Acount Number: 245777 using Routing Number: 52677769 for first last ";
		assertEquals(expected, cut.payToString(startPayDate, endPayDate));
	}

	@Test
	public void clockInHasAddedTimeCard() {
		cut = new HourlyEmployee(1, "first ", "last ", 150, 11, bobSalAdd, bobPay);
		cut.clockIn();

		assertEquals(cut.getTimeCards().size(), 1);

	}

	@Test
	public void clockInHasCurrentDate() {
		cut = new HourlyEmployee(1, "first ", "last ", 150, 11, bobSalAdd, bobPay);
		cut.clockIn();

		TimeCard theCard = cut.getTimeCards().get(0);

		DateTime dt = new DateTime();

		assertEquals(theCard.getDate(), dt);

	}

	@Test
	public void clockOut() {

		cut = new HourlyEmployee(1, "first ", "last ", 150, 11, bobSalAdd, bobPay);
		cut.clockIn();
		cut.clockOut();

		TimeCard theCard = cut.getTimeCards().get(0);

		DateTime dt = new DateTime();
		// clockOut has created Data in a TimeCard
		assertNotNull(theCard.getEndTime());
		// clockOut has current Date
	
		assertEquals(theCard.getDayOfYear(), dt.getDayOfYear());

	}

	@Test
	public void addTimeCard() {
		cut = new HourlyEmployee(1, "first ", "last ", 150, 11, bobSalAdd, bobPay);

		cut.addTimeCard(bobPay1);

		assertNotSame(cut.getTimeCards().size(), 0);

	}

}

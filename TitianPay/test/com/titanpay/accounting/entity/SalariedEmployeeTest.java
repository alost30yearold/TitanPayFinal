package com.titanpay.accounting.entity;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.titanpay.accounting.dataclass.Address;
import com.titanpay.accounting.dataclass.BankAccount;
import com.titanpay.accounting.dataclass.DirectDepositPayment;
import com.titanpay.accounting.dataclass.PaymentMethod;
import com.titanpay.accounting.dataclass.Receipt;
import com.titanpay.accounting.entity.SalariedEmployee;

public class SalariedEmployeeTest {
	Address bobSalAdd = new Address("324 Heary Cheese St", "Clearwater", "FL", 33444);
	BankAccount bobAcc = new BankAccount("Chartway", 245777, 52677769);
	PaymentMethod bobPay = new DirectDepositPayment("Lois Lane", 0, bobAcc);
	String startPayDate = "20160601-07:30";
	String endPayDate = "20180630-22:00";
	Receipt bob1 = new Receipt(1300.00);
	Receipt bob2 = new Receipt(599.99);
	Receipt bob3 = new Receipt(700.00);

	SalariedEmployee cut; // class under test

	@Before
	public void setUp() {
		cut = new SalariedEmployee();
	}

	@Test
	public void testPayToString() {
		cut = new SalariedEmployee(1, "first ", "last ", 150, 5, 60000, bobSalAdd, bobPay);
		// once you set up this cut class with how you need it
		cut.addReceipt(bob1);
		cut.addReceipt(bob2);
		cut.addReceipt(bob3);

		String expected = "Depositing $ 72999.95 in Chartway Acount Number: 245777 using Routing Number: 52677769 for first last ";
		Assert.assertEquals(expected, cut.payToString(startPayDate, endPayDate));
	}

	@Test
	public void makeSale() {
		cut = new SalariedEmployee(1, "first ", "last ", 150, 5, 60000, bobSalAdd, bobPay);

		cut.makeSale(1300.00);

		assertNotSame(cut.getReceipts().size(), 0);

	}

	@Test
	public void addReciept() {
		cut = new SalariedEmployee(1, "first ", "last ", 150, 5, 60000, bobSalAdd, bobPay);
		cut.addReceipt(bob1);

		assertNotSame(cut.getReceipts().size(), 0);

	}
}

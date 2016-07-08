package com.titanpay.accounting.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.titanpay.accounting.dataclass.Address;
import com.titanpay.accounting.dataclass.PaymentMethod;
import com.titanpay.accounting.dataclass.Receipt;

import lombok.Data;

@Entity
@Data
@DiscriminatorValue(value = "SE")
public class SalariedEmployee extends Employee {

	private double salary;
	private double commissionRate;
	private ArrayList<Receipt> receipts = new ArrayList<Receipt>();

	public SalariedEmployee(int emplid, String fName, String lName, double unionDues, double commissionRate,
			double salary, Address address, PaymentMethod paymentMethod) {
		super(emplid, fName, lName, unionDues, address, paymentMethod);
		this.salary = salary;
		this.commissionRate = commissionRate;

	}

	public SalariedEmployee() {
		super();

	}

	public void setReceiptsArray(List<Receipt> receipt) {
		this.receipts = (ArrayList<Receipt>) receipt;

	}
	public void makeSale(double amount){
		Receipt madeSale = new Receipt(amount);
		receipts.add(madeSale);
		//System.out.println("added sales receipt: "+madeSale);
		
	}
	public void addReceipt(Receipt receipt){
		receipts.add(receipt);
		
	}

	public String payToString(String startDate, String endDate) {
		// Take incoming strings and make them useful Date objects
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd-HH:mm");
		DateTime startedDate = DateTime.parse(startDate, formatter);
		DateTime endedDate = DateTime.parse(endDate, formatter);

		double paySum = this.salary;
		for (Receipt r : receipts) {

			if (r.getDateOfMonth() >= startedDate.getDayOfMonth() && r.getDateOfMonth() <= endedDate.getDayOfMonth()) {
				paySum += r.calculateCom(commissionRate);

			}
		}
		return this.getPaymentMethod().payToString((this.getFName() + this.getLName()), paySum);

	}

}

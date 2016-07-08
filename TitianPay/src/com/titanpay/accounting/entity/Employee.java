package com.titanpay.accounting.entity;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.titanpay.accounting.dataclass.Address;
import com.titanpay.accounting.dataclass.PaymentMethod;
import com.titanpay.accounting.dataclass.TimeCard;

import lombok.Data;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Data
public class Employee implements Serializable {
	@Id
	// @GeneratedValue(strategy= GenerationType.AUTO)
	private int emplid;
	private String fName;
	private String lName;
	private double unionDues;
	private Address address;
	private PaymentMethod paymentMethod;

	public Employee(int emplid, String fName, String lName, double unionDues, Address address,
			PaymentMethod paymentMethod) {
		this.emplid = emplid;
		this.setFName(fName);
		this.setLName(lName);
		this.unionDues = unionDues;
		this.address = address;
		this.paymentMethod = paymentMethod;
	}

	public Employee() {
		super();
	}

	public String payToString(String startDate, String endDate) {
		// Take incoming strings and make them useful Date objects
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd-HH:mm");
		DateTime startedDate = DateTime.parse(startDate, formatter);
		DateTime endedDate = DateTime.parse(endDate, formatter);
		double paySum = 0;
		return this.getPaymentMethod().payToString((this.getFName() + this.getLName()), paySum);

	}

}

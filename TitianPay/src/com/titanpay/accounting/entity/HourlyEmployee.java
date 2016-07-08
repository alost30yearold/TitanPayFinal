package com.titanpay.accounting.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.titanpay.accounting.dataclass.Address;
import com.titanpay.accounting.dataclass.PaymentMethod;
import com.titanpay.accounting.dataclass.TimeCard;

import lombok.Data;

@Entity
@Data
@DiscriminatorValue(value = "HE")
public class HourlyEmployee extends Employee {

	private double hourlyRate;
	private ArrayList<TimeCard> timeCards = new ArrayList<TimeCard>();

	public HourlyEmployee(int emplid, String fName, String lName, double unionDues, double hourlyRate, Address address,
			PaymentMethod paymentMethod) {
		super(emplid, fName, lName, unionDues, address, paymentMethod);
		this.hourlyRate = hourlyRate;
	}

	public HourlyEmployee() {
		super();

	}

	public void setTimeCardsArray(List<TimeCard> timeCard) {
		this.timeCards = (ArrayList<TimeCard>) timeCard;

	}

	public void clockIn() {
		TimeCard clockedIn = new TimeCard(DateTime.now());
		timeCards.ensureCapacity(31);
		timeCards.add(clockedIn);
		// System.out.println("Clocked in at "+clockedIn);
	}

	public void clockOut() {
		DateTime currentDay = new DateTime();
		for (TimeCard t : timeCards) {

			if (t.getDayOfYear() == currentDay.getDayOfYear() && t.getYear() == currentDay.getYear()) {

				TimeCard clockedOut = t;
				clockedOut.setEndTime(DateTime.now());
				// System.out.println("Clocked out at "+clockedOut);

			}
		}
	}

	public void addTimeCard(TimeCard timeCard) {
		timeCards.add(timeCard);

	}

	public String payToString(String startDate, String endDate) {
		// Take incoming strings and make them useful Date objects
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd-HH:mm");
		DateTime startedDate = DateTime.parse(startDate, formatter);
		DateTime endedDate = DateTime.parse(endDate, formatter);
		double paySum = 0;
		for (TimeCard t : this.timeCards) {

			if (t.getDayOfYear() >= startedDate.getDayOfYear() && t.getDayOfYear() <= endedDate.getDayOfYear()
					&& t.getYear() >= startedDate.getYear() && t.getYear() <= endedDate.getYear()) {
				paySum += t.calculateDailyPay(hourlyRate);

			}
		}

		return this.getPaymentMethod().payToString((this.getFName() + this.getLName()), paySum);

	}

}

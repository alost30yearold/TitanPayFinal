package com.titanpay.accounting.dataclass;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.DateTime.Property;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class TimeCard implements Serializable {
	private DateTime date;
	private DateTime startTime;
	private DateTime endTime;
	private int day;

	public TimeCard() {
		this.startTime = DateTime.now();
		this.endTime = DateTime.now();
		this.date = startTime;

	}

	public TimeCard(DateTime startTime, DateTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = DateTime.now();

	}

	public TimeCard(DateTime startTime) {
		this.startTime = startTime;
		this.date = startTime;

	}

	public TimeCard(String startTime, String endTime) {
		// Take incoming strings and make them useful Date objects
		// DateTimeFormatter formatter =
		// DateTimeFormat.forPattern("yyyyMMdd-HH:mm");
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy-HHmm");
		this.startTime = DateTime.parse(startTime, formatter);
		this.endTime = DateTime.parse(endTime, formatter);

		this.date = this.startTime;
	}

	public TimeCard(String startTime, String endTime, String date) {
		// Take incoming strings and make them useful Date objects
		// DateTimeFormatter formatter =
		// DateTimeFormat.forPattern("yyyyMMdd-HH:mm");
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy-HHmm");
		DateTimeFormatter startformatter = DateTimeFormat.forPattern("MM/dd/yyyy-Hmm");

		this.startTime = DateTime.parse(startTime, startformatter);
		this.endTime = DateTime.parse(endTime, formatter);
		this.date = DateTime.parse(date, formatter);

	}

	public double calculateDailyPay(double rate) {
		Duration normalWorkDay = Duration.standardHours(8); // standard 8 hour
															// work day
		Duration hoursWorked = new Duration(startTime, endTime);// finding hours
																// worked.
		double workedHours = hoursWorked.getStandardHours();
		double overtimePay = 0;

		if (hoursWorked.isLongerThan(normalWorkDay)) {// Math to find out pay.
			double overtime =  (workedHours - 8);
			overtimePay = overtime * (rate * 1.5);
			workedHours = 8;
		}
		double regularPay = workedHours * rate;
		double pay = regularPay + overtimePay;
		return pay;
	}

	@Override
	public String toString() {
		String theString = "Time Card :\tDate: " + this.date + "\tStarted work: " + this.startTime + "\tEnded Work: "
				+ this.endTime;
		return theString;
	}

	public int getYear() {
		return date.getYear();
	}

	public int getDayOfYear() {
		return date.getDayOfYear();
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

}

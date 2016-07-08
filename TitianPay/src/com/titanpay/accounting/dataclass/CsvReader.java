package com.titanpay.accounting.dataclass;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.titanpay.accounting.entity.HourlyEmployee;
import com.titanpay.accounting.entity.SalariedEmployee;

//import test.CsvTest.EmployeeData;

@SuppressWarnings("serial")
public class CsvReader implements Serializable {

	@SuppressWarnings("unused")
	public static List<Receipt> parseReceipts(String csvFile, int employeeId) {
		List<Receipt> parsedReceipts = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			// Reading header line
			String headers = reader.readLine();
			String line = null;
			int emplid;
			double saleAmt;

			while ((line = reader.readLine()) != null) {
				// parse out the data
				String[] receiptData = line.split("\\s*,\\s*");
				emplid = Integer.parseInt(receiptData[0]);
				
				if (emplid == employeeId){
					saleAmt = Double.parseDouble(receiptData[3])*Double.parseDouble(receiptData[4]);			
					Receipt receipt = new Receipt(saleAmt);
					parsedReceipts.add(receipt);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parsedReceipts;
		
	}
	@SuppressWarnings("unused")
	public static List<Receipt> parseReceipts(String csvFile) {
		List<Receipt> parsedReceipts = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			// Reading header line, not caring
			String headers = reader.readLine();
			String line = null;
			int emplid;
			double saleAmt;
			

			while ((line = reader.readLine()) != null) {
				// parse out the data
				String[] receiptData = line.split("\\s*,\\s*");
				emplid = Integer.parseInt(receiptData[0]);
					saleAmt = Double.parseDouble(receiptData[3])*Double.parseDouble(receiptData[4]);
					Receipt receipt = new Receipt(saleAmt);
					parsedReceipts.add(receipt);

			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parsedReceipts;
		
	}
	
	@SuppressWarnings("unused")
	public static List<TimeCard> parseTimeCards(String csvFile,int employeeId) {
		List<TimeCard> parsedTimeCards = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			// Reading header line, not caring
			String headers = reader.readLine();
			String line = null;
			int emplid;
			String startTime;
			String endTime;
			

			while ((line = reader.readLine()) != null) {
				// parse out the data
				String[] timeData = line.split("\\s*,\\s*");
				emplid = Integer.parseInt(timeData[0]);
				if (emplid == employeeId){
					String time1 = timeData[1];
					if (time1.length()<=3)
						time1 = "0"+time1;
					String time2 = timeData[2];
					if (time2.length()<=3)
						time2 = "0"+time2;
					startTime = timeData[3]+"-"+time1;
					endTime = timeData[3]+"-"+time2;			
					TimeCard timeCard = new TimeCard(startTime,endTime);
					parsedTimeCards.add(timeCard);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parsedTimeCards;
	
	}
	@SuppressWarnings("unused")
	public static List<TimeCard> parseTimeCards(String csvFile) {
		List<TimeCard> parsedTimeCards = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			// Reading header line, not caring
			String headers = reader.readLine();
			String line = null;
			int emplid;
			String startTime;
			String endTime;
			

			while ((line = reader.readLine()) != null) {
				// parse out the data
				String[] timeData = line.split("\\s*,\\s*");
				emplid = Integer.parseInt(timeData[0]);
				String time1 = timeData[1];
				if (time1.length()<=3)
					time1 = "0"+time1;
				String time2 = timeData[2];
				if (time2.length()<=3)
					time2 = "0"+time2;
				startTime = timeData[3]+"-"+time1;
				endTime = timeData[3]+"-"+time2;			
				TimeCard timeCard = new TimeCard(startTime,endTime);
				parsedTimeCards.add(timeCard);
			
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parsedTimeCards;
	
	}
	
	@SuppressWarnings("unused")
	public static List<SalariedEmployee> parseSalariedEmployees(String csvFile) {
		List<SalariedEmployee> parsedEmployees = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			// Reading header line, not caring
			String headers = reader.readLine();
			String line = null;
			int emplid;
			double salary;
			double commissionRate; 
			double unionDues;
			
			//Stand in info...
			Address bobSalAdd = new Address("324 Heary Cheese St", "Clearwater", "FL", 33444);
			BankAccount bobAcc = new BankAccount("Chartway", 245777, 52677769);

			while ((line = reader.readLine()) != null) {
				// parse out the data
				String[] employeeData = line.split("\\s*,\\s*");
				emplid = Integer.parseInt(employeeData[0]);
				commissionRate = Double.parseDouble(employeeData[4]);
				salary = Double.parseDouble(employeeData[3]);
				if (employeeData[5].contains("-"))
					employeeData[5] = "0";
				unionDues = Double.parseDouble(employeeData[5]);
				String name = employeeData[1]+" "+employeeData[2];
				if (employeeData[6].contains("DD")){
		
					SalariedEmployee employee = new SalariedEmployee(emplid, employeeData[2], employeeData[1], unionDues, commissionRate, salary, bobSalAdd, new DirectDepositPayment(name, 0, bobAcc));			
					parsedEmployees.add(employee);

				}
				else if (employeeData[6].contains("PU")){
					
					SalariedEmployee employee = new SalariedEmployee(emplid, employeeData[2], employeeData[1], unionDues, commissionRate, salary, bobSalAdd, new PickUpPayment(name, 0));	
					parsedEmployees.add(employee);

				}
				else if (employeeData[6].contains("MA")){
					SalariedEmployee employee = new SalariedEmployee(emplid, employeeData[2], employeeData[1], unionDues, commissionRate, salary, bobSalAdd, new MailPayment(name, 0, bobSalAdd));
					parsedEmployees.add(employee);

				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parsedEmployees;
	
	}
	
	@SuppressWarnings("unused")
	public static List<HourlyEmployee> parseHourlyEmployees(String csvFile) {
		List<HourlyEmployee> parsedEmployees = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			// Reading header line, not caring
			String headers = reader.readLine();
			String line = null;
			int emplid;
			double hourlyRate;
			double unionDues;
			
			//Stand in info...
			Address bobHourAdd = new Address("6807 Z Stupid Way", "Tampa", "FL", 33655);
			BankAccount bobAcc = new BankAccount("Super Money 'Merica", 245888, 52648569);

			while ((line = reader.readLine()) != null) {
				// parse out the data
				String[] employeeData = line.split("\\s*,\\s*");
				emplid = Integer.parseInt(employeeData[0]);
				hourlyRate = Double.parseDouble(employeeData[3]);

				if (employeeData[4].contains("-"))
					employeeData[4] = "0";
				unionDues = Double.parseDouble(employeeData[4]);
				String name = employeeData[1]+" "+employeeData[2];
				if (employeeData[5].contains("DD")){
		
					HourlyEmployee employee = new HourlyEmployee(emplid, employeeData[2], employeeData[1], unionDues, hourlyRate, bobHourAdd, new DirectDepositPayment(name, 0, bobAcc));			
					parsedEmployees.add(employee);

				}
				else if (employeeData[5].contains("PU")){
					
					HourlyEmployee employee = new HourlyEmployee(emplid, employeeData[2], employeeData[1], unionDues, hourlyRate, bobHourAdd, new PickUpPayment(name, 0));	
					parsedEmployees.add(employee);

				}
				else if (employeeData[5].contains("MA")){
					HourlyEmployee employee = new HourlyEmployee(emplid, employeeData[2], employeeData[1], unionDues, hourlyRate, bobHourAdd, new MailPayment(name, 0, bobHourAdd));
					parsedEmployees.add(employee);

				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parsedEmployees;
	
	}
}

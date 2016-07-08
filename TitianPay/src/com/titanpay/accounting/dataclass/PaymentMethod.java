package com.titanpay.accounting.dataclass;

import java.io.Serializable;

//import com.titanpay.accounting.Employee;

public abstract class PaymentMethod implements Serializable {
	
	protected String eName;
	protected double paymentAmount;

	public PaymentMethod(String eName, double paymentAmount){
		
		this.eName = eName;
		this.paymentAmount = paymentAmount;
	}
	/* 
	public PaymentMethod(Payable employee){
		this.eName = employee.getFullNameFL();
		String startDate = null;
		String endDate = null;
		this.paymentAmount = employee.pay(startDate, endDate);
	}
	*/

	public abstract void pay(String name,double amt);
	public abstract String payToString(String eName, double paymentAmount);
	
	public String toString(){
		String theString = "name "+eName+"payment amount : "+paymentAmount;
		return theString;
		
	}
	//public abstract void pay(Employee employ, Payable payable);
	
}

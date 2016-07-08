 package com.titanpay.accounting.dataclass;

import java.text.DecimalFormat;

@SuppressWarnings("serial")
public class PickUpPayment extends PaymentMethod {

	public PickUpPayment(String eName, double paymentAmount) {
		super(eName, paymentAmount);
	}

	public void pay(String eName, double paymentAmount) {
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("A check for $ "+df.format(paymentAmount)+" is waiting for "+eName+" at the PostMaster.");

	}
	public String payToString(String eName, double paymentAmount){
		if (paymentAmount != 0){
		DecimalFormat df = new DecimalFormat("#.00");
		String paye = "A check for $ "+df.format(paymentAmount)+" is waiting for "+eName+" at the PostMaster.";
		return paye;
		}
	return null;

	}

}

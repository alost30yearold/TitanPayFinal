package com.titanpay.accounting.dataclass;

import java.text.DecimalFormat;

@SuppressWarnings("serial")
public class MailPayment extends PaymentMethod {
	
	private Address address;

	public MailPayment(String eName, double paymentAmount, Address address){
		super(eName, paymentAmount);
		this.address = address;
	
	}
	public void pay(String eName, double paymentAmount){
		
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("Mailing check to "+this.eName+" for $ "+df.format(paymentAmount)+" to "+address.getAddress());

	}
	public String payToString(String eName, double paymentAmount){
		if (paymentAmount !=0){
		DecimalFormat df = new DecimalFormat("#.00");
		String paye = "Mailing check to "+this.eName+" for $ "+df.format(paymentAmount)+" to "+address.getAddress();
		return paye;
		}
		return null;

	}
	//@Override
	//public void pay(Payable payable) {
	//	System.out.println("Mail to"+payable.getAddress()+" for "+payable.performPay(", endDate));

		
	//}
	//@Override
	//public void pay(Employee employ, Payable payable) {
		// TODO Auto-generated method stub
		
	//}
	

}

package com.titanpay.accounting.dataclass;

import java.io.Serializable;
import java.text.DecimalFormat;

@SuppressWarnings("serial")
public class BankAccount implements Serializable{
		
	private String bankName;
	private int routingNumber;
	private int accountId;
	
	public BankAccount(String bankName, int accountId, int routingNumber){
		this.bankName = bankName;	
		this.accountId = accountId;
		this.routingNumber = routingNumber;
	}
	public String deposit(double amt){
		DecimalFormat df = new DecimalFormat("#.00");
		String depositMessage = "Depositing $ " + df.format(amt) +" in " + this.bankName +" Acount Number: "+ this.accountId+" using Routing Number: " +this.routingNumber;
		return depositMessage;
		
	}
	@Override
	public String toString(){
		String theString = "Bank Account :\tID: "+this.accountId+"\tRouting Number: "+this.routingNumber+"\tBank Name: "+this.bankName;
		return theString;
	}
}

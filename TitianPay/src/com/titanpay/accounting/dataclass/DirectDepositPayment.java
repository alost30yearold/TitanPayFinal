package com.titanpay.accounting.dataclass;

@SuppressWarnings("serial")
public class DirectDepositPayment extends PaymentMethod {
	private BankAccount bankAccount;
	
	public DirectDepositPayment(String eName, double paymentAmount, BankAccount bankAccount) {
		super(eName, paymentAmount);
		this.bankAccount = bankAccount;
		
	}
	
	public void pay(String eName, double paymentAmount) {
		
		System.out.println(bankAccount.deposit(paymentAmount)+" for "+eName);

	}
	public String payToString(String eName, double paymentAmount){
		if (paymentAmount !=0){
		String paye = bankAccount.deposit(paymentAmount)+" for "+eName;
		return paye;
		}
		return null;

	}

}



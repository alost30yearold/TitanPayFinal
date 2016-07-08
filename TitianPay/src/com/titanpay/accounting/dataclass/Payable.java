package com.titanpay.accounting.dataclass;

public interface Payable {
	public void pay(String startDate,String endDate);

	String payToString(String startDate, String endDate);

}

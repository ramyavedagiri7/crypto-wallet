package com.example.crytowallet;

public class example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RESTClient rs = new RESTClient();
		String str=rs.get("https://api.bitcoinaverage.com/ticker/global/INR/");
		System.out.println("rest op is :"+str);
		
	}

}

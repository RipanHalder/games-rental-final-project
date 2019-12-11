package com.ripanhalder.exception;

@SuppressWarnings("serial")
public class PayPalException extends Exception{


	public PayPalException(String message) {
		super("PayPalException-" + message);
	}

	public PayPalException(String message, Throwable cause) {
		super("PayPalException-" + message, cause);
	}
}

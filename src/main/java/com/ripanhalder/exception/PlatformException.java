package com.ripanhalder.exception;

@SuppressWarnings("serial")
public class PlatformException extends Exception{


	public PlatformException(String message) {
		super("PlatformException-" + message);
	}

	public PlatformException(String message, Throwable cause) {
		super("PlatformException-" + message, cause);
	}
}

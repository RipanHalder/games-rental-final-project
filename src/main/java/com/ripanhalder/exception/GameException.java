package com.ripanhalder.exception;

@SuppressWarnings("serial")
public class GameException extends Exception{


	public GameException(String message) {
		super("GameException-" + message);
	}

	public GameException(String message, Throwable cause) {
		super("GameException-" + message, cause);
	}
}

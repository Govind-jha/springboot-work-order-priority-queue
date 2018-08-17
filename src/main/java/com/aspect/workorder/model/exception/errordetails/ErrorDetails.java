package com.aspect.workorder.model.exception.errordetails;

import java.util.Date;

@SuppressWarnings("unused")
public class ErrorDetails {
	private final Date timestamp;
	private final String message;
	private final String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

}

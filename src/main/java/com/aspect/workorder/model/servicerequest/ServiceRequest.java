package com.aspect.workorder.model.servicerequest;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.aspect.workorder.config.AppConstant;
import com.aspect.workorder.utility.RequestType;

/**
 * Describes a Service request. The abstract class can be extended to create
 * custom request types POJO.
 * 
 * @author kumjha
 *
 */
public abstract class ServiceRequest implements RankedServiceRequest, Comparable<ServiceRequest> {

	private final Long requestorId;
	private final Long timeOfRequest;

	private final Date date;
	private Long secondsElapsed;
	private final RequestType requestType;

	// Ticker : runs a function after specified interval.
	private final ScheduledExecutorService ticker = Executors.newScheduledThreadPool(1);

	public ServiceRequest(Long id, Long timeOfRequest, RequestType requestType) {
		this.requestorId = id;
		this.date = new Date();
		this.requestType = requestType;
		this.timeOfRequest = timeOfRequest;
		this.secondsElapsed = 0L;

		ticker.scheduleAtFixedRate((() -> secondsElapsed++), 0, AppConstant.TIMER_TICK_INVERVAL, TimeUnit.MILLISECONDS);
	}

	public Long getRequesterId() {
		return requestorId;
	}

	public Date getDate() {
		return date;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public Long getTimeOfRequest() {
		return timeOfRequest;
	}

	public Long getSecondsElapsed() {
		return secondsElapsed;
	}

	/*
	 * This method is overridden to specify a natural sorting order.
	 */
	@Override
	public int compareTo(ServiceRequest o) {
		return this.getRequestType().getRank() - o.getRequestType().getRank();
	}

}

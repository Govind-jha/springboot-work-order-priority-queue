package com.aspect.workorder.model.servicerequest;

import java.util.Date;

import com.aspect.workorder.utility.RequestType;

/**
 * Describes a Service request. The abstract class can be extended to create
 * custom request types POJO.
 * 
 * @author kumjha
 *
 */
abstract public class ServiceRequest implements Comparable<ServiceRequest> {

	private final Long requestorId;
	private final Date date;

	private final RequestType requestType;
	private final Long timeOfRequest;

	public ServiceRequest(Long id, Long timeOfRequest, RequestType requestType) {
		this.requestorId = id;
		this.date = new Date();
		this.requestType = requestType;
		this.timeOfRequest = timeOfRequest;
	}
	
	/**
	 * This method has to be implemented by the concrete child class for calculating
	 * the rank of the request object. This rank can be used by comparator for
	 * deciding the priority of the object in the priority queue.
	 * 
	 * @return {@link Long}
	 */
	abstract public Long getRank();

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

	/*
	 * This method is overridden to specify a natural sorting order.
	 */
	@Override
	public int compareTo(ServiceRequest o) {
		return this.getRequestType().getRank() - o.getRequestType().getRank();
	}

}

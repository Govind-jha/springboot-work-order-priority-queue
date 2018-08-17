package com.aspect.workorder.model.servicerequest;

import com.aspect.workorder.utility.RequestType;

/**
 * POJO for Priority Service Request
 * 
 * @author kumjha
 *
 */
public class PriorityServiceRequest extends ServiceRequest {

	public PriorityServiceRequest(Long id, Long timeOfRequest, RequestType requestType) {
		super(id, timeOfRequest, requestType);
	}

	@Override
	public Long getRank() {
		final Long timeInQueue = System.currentTimeMillis() - this.getTimeOfRequest();
		return (long) Math.max(3, timeInQueue * Math.log(timeInQueue));
	}

}

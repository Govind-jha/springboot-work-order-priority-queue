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
	public Long calculateRank() {
		return (long) Math.max(3, this.getSecondsElapsed() * Math.log(this.getSecondsElapsed()));
	}

}

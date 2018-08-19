package com.aspect.workorder.model.servicerequest;

import com.aspect.workorder.utility.RequestType;

/**
 * POJO for VIP Service Request
 * 
 * @author kumjha
 *
 */
public class VIPServiceRequest extends ServiceRequest {

	public VIPServiceRequest(Long id, Long timeOfRequest, RequestType requestType) {
		super(id, timeOfRequest, requestType);
	}

	@Override
	public Long calculateRank() {
		return (long) Math.max(4, 2 * this.getSecondsElapsed() * Math.log(this.getSecondsElapsed()));
	}

}

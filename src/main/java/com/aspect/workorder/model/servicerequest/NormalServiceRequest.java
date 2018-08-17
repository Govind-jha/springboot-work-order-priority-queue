package com.aspect.workorder.model.servicerequest;

import com.aspect.workorder.utility.RequestType;

/**
 * POJO for Normal Service Request
 * @author kumjha
 *
 */
public class NormalServiceRequest extends ServiceRequest {

	public NormalServiceRequest(Long id, Long timeOfRequest, RequestType requestType) {
		super(id, timeOfRequest, requestType);
	}

	@Override
	public Long getRank() {
		return System.currentTimeMillis() - this.getTimeOfRequest();
	}

}

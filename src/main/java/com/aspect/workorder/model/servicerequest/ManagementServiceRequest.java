package com.aspect.workorder.model.servicerequest;

import com.aspect.workorder.utility.RequestType;

/**
 * POJO for Management Service Request
 * 
 * @author kumjha
 *
 */
public class ManagementServiceRequest extends ServiceRequest {

	public ManagementServiceRequest(Long id, Long timeOfRequest, RequestType requestType) {
		super(id, timeOfRequest, requestType);
	}

	@Override
	public Long getRank() {
		return System.currentTimeMillis() - this.getTimeOfRequest();
	}

}

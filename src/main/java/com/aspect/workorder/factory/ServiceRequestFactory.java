package com.aspect.workorder.factory;

import com.aspect.workorder.model.servicerequest.ManagementServiceRequest;
import com.aspect.workorder.model.servicerequest.NormalServiceRequest;
import com.aspect.workorder.model.servicerequest.PriorityServiceRequest;
import com.aspect.workorder.model.servicerequest.ServiceRequest;
import com.aspect.workorder.model.servicerequest.VIPServiceRequest;
import com.aspect.workorder.utility.RequestType;

/**
 * A factory class for Service Requests, which returns appropriate instance of
 * {@link ServiceRequest} based on the request type.
 * 
 * There are 4 classes of IDs {@see RequestType}
 * 
 * You can determine the class of the ID using the following method:
 * 
 * (1) IDs that are evenly divisible by 3 are priority IDs.
 * 
 * (2) IDs that are evenly divisible by 5 are VIP IDs.
 * 
 * (3) IDs that are evenly divisible by both 3 and 5 are management override
 * IDs.
 * 
 * (4) IDs that are not evenly divisible by 3 or 5 are normal IDs.
 * 
 * @author kumjha
 *
 */
public final class ServiceRequestFactory {

	private ServiceRequestFactory() {
		// Factory class
	}

	/**
	 * Returns an instance of service request using the requester id and the
	 * timeOfRequest.
	 * 
	 * @param requesterId
	 * @param timeOfRequest
	 * @return {@link ServiceRequest}
	 */
	public static ServiceRequest getRequest(Long requesterId, Long timeOfRequest) {
		final RequestType requestType = ServiceRequestFactory.assignRequestType(requesterId);
		final ServiceRequest request;
		final Long timeInSec = timeOfRequest / 1000;

		switch (requestType.getRank()) {
		case 1:
			request = new ManagementServiceRequest(requesterId, timeInSec, requestType);
			break;
		case 2:
			request = new VIPServiceRequest(requesterId, timeInSec, requestType);
			break;
		case 3:
			request = new PriorityServiceRequest(requesterId, timeInSec, requestType);
			break;
		default:
			request = new NormalServiceRequest(requesterId, timeInSec, requestType);
		}

		return request;
	}

	/**
	 * Returns the request type based on the time of request.
	 * 
	 * @param time
	 * @return {@link RequestType}
	 */
	private static RequestType assignRequestType(Long time) {
		Boolean divBy3 = time % 3 == 0;
		Boolean divBy5 = time % 5 == 0;

		if (divBy3 && divBy5) {
			return RequestType.MANAGEMENT;
		} else if (divBy5) {
			return RequestType.VIP;
		} else if (divBy3) {
			return RequestType.PRIORITY;
		}

		return RequestType.NORMAL;
	}

}

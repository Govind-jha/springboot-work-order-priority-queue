package com.aspect.workorder.model.servicerequest;

public interface RankedServiceRequest {

	/**
	 * This method has to be implemented for calculating the rank of the request
	 * object.
	 * 
	 * @return {@link Long}
	 */
	public Long calculateRank();

}

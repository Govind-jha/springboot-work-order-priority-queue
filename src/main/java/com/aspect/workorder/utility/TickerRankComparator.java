package com.aspect.workorder.utility;

import java.util.Comparator;
import com.aspect.workorder.model.servicerequest.ServiceRequest;

public class TickerRankComparator implements Comparator<ServiceRequest> {

	@Override
	public int compare(ServiceRequest arg1, ServiceRequest arg2) {
		RequestType reqType1 = arg1.getRequestType();
		RequestType reqType2 = arg2.getRequestType();

		if (reqType1 == RequestType.MANAGEMENT && reqType2 != RequestType.MANAGEMENT) {
			return -1;
		} else if (reqType1 != RequestType.MANAGEMENT && reqType2 == RequestType.MANAGEMENT) {
			return 1;
		}

		return (int) (arg1.calculateRank() - arg2.calculateRank());
	}

}

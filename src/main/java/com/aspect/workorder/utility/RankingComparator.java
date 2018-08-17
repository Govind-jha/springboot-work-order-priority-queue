package com.aspect.workorder.utility;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.aspect.workorder.model.servicerequest.ServiceRequest;

/**
 * Comparator describes comparison of Service Request based on request type and
 * it's ranking.
 * 
 * Ranking is based on following rule:
 * 
 * (1) Normal IDs are given a rank equal to the number of seconds they’ve been
 * in the queue.
 * 
 * (2) Priority IDs are given a rank equal to the result of applying the
 * following formula to the number of seconds they’ve been in the queue:
 * 
 * <code>max(3,nlogn)</code>
 * 
 * (3) VIP IDs are given a rank equal to the result of applying the following
 * formula to the number of seconds they’ve been in the queue:
 * 
 * <code>max(4,2nlogn)</code>
 * 
 * (4) Management Override IDs are always ranked ahead of all other IDs and are
 * ranked among themselves according to the number of seconds they’ve been in
 * the queue.
 * 
 * @author kumjha
 *
 */
@Component
public class RankingComparator implements Comparator<ServiceRequest> {

	@Override
	public int compare(ServiceRequest arg1, ServiceRequest arg2) {
		RequestType reqType1 = arg1.getRequestType();
		RequestType reqType2 = arg2.getRequestType();

		Long rankOf1 = arg1.getRank();
		Long rankOf2 = arg2.getRank();

		if (reqType1 == RequestType.MANAGEMENT && reqType2 == RequestType.MANAGEMENT) {
			return (int) (arg1.getTimeOfRequest() - arg2.getTimeOfRequest());
		} else if (reqType1 == RequestType.MANAGEMENT && reqType2 != RequestType.MANAGEMENT) {
			return -1;
		} else if (reqType1 != RequestType.MANAGEMENT && reqType2 == RequestType.MANAGEMENT) {
			return 1;
		}

		return (int) (rankOf2 - rankOf1);
	}
}

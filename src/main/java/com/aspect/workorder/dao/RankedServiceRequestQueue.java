package com.aspect.workorder.dao;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.stereotype.Component;

import com.aspect.workorder.config.AppConstant;
import com.aspect.workorder.model.servicerequest.ServiceRequest;
import com.aspect.workorder.utility.RankingComparator;

/**
 * This class provides a priority queue sorted using {@link RankingComparator}
 * 
 * @author kumjha
 *
 */
@Component
final class RankedServiceRequestQueue {

	private PriorityBlockingQueue<ServiceRequest> queue;
	private final Comparator<ServiceRequest> rankingComparator;

	public RankedServiceRequestQueue() {
		rankingComparator = new RankingComparator();
		queue = new PriorityBlockingQueue<ServiceRequest>(AppConstant.INITIAL_QUEUE_SIZE, rankingComparator);
	}

	/**
	 * Returns instance of concurrent priority queue.
	 * 
	 * @return {@link PriorityBlockingQueue<ServiceRequest>}
	 */
	public PriorityBlockingQueue<ServiceRequest> getQueue() {
		return queue;
	}

	/**
	 * This methods enables runtime overriding of the comparator for the priority
	 * queue.
	 * 
	 * Debatable to have this feature enabled
	 * 
	 * @param rankingComparator
	 */
//	public void setRankingComparator(Comparator<ServiceRequest> rankingComparator) {
//		this.rankingComparator = rankingComparator;
//	}

}

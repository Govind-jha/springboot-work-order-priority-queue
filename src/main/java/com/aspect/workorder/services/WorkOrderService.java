package com.aspect.workorder.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.aspect.workorder.model.servicerequest.ServiceRequest;

@Component
public interface WorkOrderService {

	/**
	 * For adding a ID to queue (enqueue).
	 * 
	 * @param id
	 * @param timeOfRequest
	 * @return {@link ServiceRequest}
	 */
	public Boolean addOrder(Long requestorId, Long timeOfRequest);

	/**
	 * For getting the top ID from the queue and removing it (dequeue).
	 * 
	 * @return {@link Long}
	 */
	public Long removeNextOrder();

	/**
	 * For getting the list of IDs in the queue.
	 * 
	 * @return {@link List<Long>}
	 */
	public List<Long> getOrderList();

	/**
	 * For removing a speciﬁc ID from the queue.
	 *  
	 * @param id
	 * @return {@link Long}
	 */
	public Long remove(Long requestorId);

	/**
	 * To get the position of a specific ID in the queue.
	 * 
	 * @param id
	 * @return {@link Integer}
	 */
	public Integer getPosition(Long requestorId);

	/**
	 * Return the average (mean) number of seconds that each ID has been waiting in the queue.
	 * 
	 * @param currentTime
	 * @return {@link Double}
	 */
	public Double avgWaitTime(Long currentTime);
}

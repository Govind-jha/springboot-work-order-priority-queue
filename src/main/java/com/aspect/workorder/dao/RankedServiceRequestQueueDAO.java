package com.aspect.workorder.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.aspect.workorder.model.servicerequest.ServiceRequest;

public interface RankedServiceRequestQueueDAO {

	/**
	 * Inserts the specified element into this priority queue.
	 * 
	 * @param request
	 * @return {@link Boolean}
	 */
	public Boolean enque(final ServiceRequest request);

	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue
	 * is empty.
	 * 
	 * @return {@link Optional<ServiceRequest>}
	 */
	public Optional<ServiceRequest> deque();

	/**
	 * Removes a single instance of the specified element for the request id from
	 * this queue, if it is present.
	 * 
	 * @param requestorId
	 * @return {@link Boolean}
	 */
	public Boolean remove(final Long requestorId);

	/**
	 * Returns a List containing the elements in the priority queue. The List
	 * maintains the order of elements same as the order of elements in the Priority
	 * Queue.
	 * 
	 * @return {@link List<ServiceRequest>}
	 */
	public List<ServiceRequest> getQueue();

	/**
	 * Returns a Map of Service Request with requester's id as the key.
	 * 
	 * @return {@link Map<String, ServiceRequest>}
	 */
	public Map<String, ServiceRequest> getServiceRequestByIdMap();

}

package com.aspect.workorder.resources;

import org.springframework.http.ResponseEntity;

import com.aspect.workorder.model.request.ordergiftrequest.WorkOrderRequest;
import com.aspect.workorder.model.response.ordergiftresponse.WorkOrderResponse;

/**
 * @author kumjha
 *
 */
public interface WorkOrderResource {

	public ResponseEntity<WorkOrderResponse> addOrder(WorkOrderRequest request);

	/**
	 * For getting the top ID from the queue and removing it (dequeue).
	 *
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<WorkOrderResponse> removeNextOrder();

	/**
	 * For getting the list of IDs in the queue.
	 *
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<WorkOrderResponse> getOrderList();

	/**
	 * For removing a speciﬁc ID from the queue.
	 * 
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<Void> remove(Long id);

	/**
	 * * To get the position of a specific ID in the queue.
	 * 
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<WorkOrderResponse> getPosition(Long id);

	/**
	 * Return the average (mean) number of seconds that each ID has been waiting in
	 * the queue.
	 * 
	 * @param currentTime
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<WorkOrderResponse> avgWaitTime(Long currentTime);
}

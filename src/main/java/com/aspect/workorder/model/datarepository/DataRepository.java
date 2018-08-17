package com.aspect.workorder.model.datarepository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.stereotype.Component;

import com.aspect.workorder.model.servicerequest.ServiceRequest;

/**
 * This class is used for creating data repository based on
 * {@link PriorityBlockingQueue}. This class is model class for maintaining the
 * state of a {@link ServiceRequest}s data repository.
 * 
 * This class can be used to provide Create, Read, Update, Delete (CRUD) feature
 * and rules for managing CRUD operations using a priority queue and a hash map.
 * 
 * @author kumjha
 *
 */
@Component
public class DataRepository implements Serializable {

	private static final long serialVersionUID = -794187659896159731L;

	private volatile PriorityBlockingQueue<ServiceRequest> pq;
	private volatile HashMap<String, ServiceRequest> serviceRequestsMap;

	/**
	 * Returns an unbounded blocking(concurrent) queue that uses the same ordering
	 * rules as class {@link PriorityQueue} and supplies blocking retrieval
	 * operations.
	 * 
	 * @return {@link PriorityBlockingQueue}
	 */
	public PriorityBlockingQueue<ServiceRequest> getQueue() {
		return pq;
	}

	/**
	 * Set the value of Priority Blocking Queue
	 * 
	 * @param {@link PriorityBlockingQueue<ServiceRequest>}
	 */
	public synchronized void setQueue(PriorityBlockingQueue<ServiceRequest> priorityBlockingQueue) {
		this.pq = priorityBlockingQueue;
	}

	/**
	 * This implementation provides a hash map. This class makes no guarantees as to
	 * the order of the map; in particular, it does not guarantee that the order
	 * will remain constant over time.
	 * 
	 * This could be used for storing metadata of records/requests, helpful in
	 * enforcing rules on the priority queue.
	 * 
	 * @return {@link HashMap<Long, ServiceRequest>}
	 */
	public synchronized HashMap<String, ServiceRequest> getServiceRequestsMap() {
		return serviceRequestsMap;
	}

	/**
	 * Set the value of HashMap.
	 * 
	 * @param {@link HashMap<Long, ServiceRequest>}
	 */
	public synchronized void setServiceRequestsMap(HashMap<String, ServiceRequest> serviceRequestsMap) {
		this.serviceRequestsMap = serviceRequestsMap;
	}

}

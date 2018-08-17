package com.aspect.workorder.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.stereotype.Component;

import com.aspect.workorder.config.AppConstant;
import com.aspect.workorder.model.datarepository.DataRepository;
import com.aspect.workorder.model.servicerequest.ServiceRequest;

/**
 * This class implements {@link RankedServiceRequestQueueDAO}.
 * 
 * @author kumjha
 *
 */
@Component
public class RankedServiceRequestQueueDAOImpl implements RankedServiceRequestQueueDAO {

	DataRepository repository;

	public RankedServiceRequestQueueDAOImpl() {
		repository = new DataRepository();
		this.repository.setQueue(new RankedServiceRequestQueue().getQueue());
		this.repository.setServiceRequestsMap(new HashMap<>(AppConstant.INITIAL_QUEUE_SIZE));
	}

	public synchronized Boolean enque(final ServiceRequest request) {
		if (!repository.getServiceRequestsMap().containsKey(request.getRequesterId().toString())) {
			repository.getQueue().add(request);
			repository.getServiceRequestsMap().put(request.getRequesterId().toString(), request);
			return Boolean.TRUE;
		}		

		return Boolean.FALSE;
	}

	public synchronized Optional<ServiceRequest> deque() {
		final ServiceRequest request;

		request = repository.getQueue().poll();
		if (request != null) {
			repository.getServiceRequestsMap().remove(request.getRequesterId().toString());
		}

		return Optional.ofNullable(request);

	}

	public synchronized Boolean remove(final Long requestorId) {
		final ServiceRequest request = repository.getServiceRequestsMap().get(requestorId.toString());

		if (request == null) {
			return Boolean.FALSE;
		}

		repository.getQueue().remove(request);
		repository.getServiceRequestsMap().remove(requestorId.toString());

		return Boolean.TRUE;
	}

	public synchronized List<ServiceRequest> getQueue() {
		final PriorityBlockingQueue<ServiceRequest> tmpPQ;
		final List<ServiceRequest> tmpQueueCopyAsList = new ArrayList<>();

		tmpPQ = new PriorityBlockingQueue<>(repository.getQueue());
		tmpPQ.drainTo(tmpQueueCopyAsList);

		return Collections.unmodifiableList(tmpQueueCopyAsList);
	}

	public synchronized Map<String, ServiceRequest> getServiceRequestByIdMap() {
		return Collections.unmodifiableMap(repository.getServiceRequestsMap());
	}

}

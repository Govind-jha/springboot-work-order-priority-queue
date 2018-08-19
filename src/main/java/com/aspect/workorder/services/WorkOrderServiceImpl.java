package com.aspect.workorder.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.aspect.workorder.config.AppConstant;
import com.aspect.workorder.factory.ServiceRequestFactory;
import com.aspect.workorder.model.servicerequest.ServiceRequest;
import com.aspect.workorder.utility.TickerRankComparator;

/**
 * Gift Order Service Implementation.
 * 
 * @author kumjha
 *
 */
@Component
public class WorkOrderServiceImpl implements WorkOrderService {

	private volatile Map<Long, ServiceRequest> map = new HashMap<>(AppConstant.INITIAL_QUEUE_SIZE);

	@Override
	public Optional<ServiceRequest> addOrder(Long requesterId, Long timeOfRequest) {
		if (map.get(requesterId) == null) {
			ServiceRequest order = ServiceRequestFactory.getRequest(requesterId, timeOfRequest);
			map.put(requesterId, order);

			return Optional.of(order);
		}

		return Optional.empty();
	}

	@Override
	public Optional<ServiceRequest> removeNextOrder() {
		final List<ServiceRequest> serviceRequests;

		synchronized (map) {
			serviceRequests = new ArrayList<>(map.values());
		}

		ServiceRequest requestToRemove = null;

		if (!serviceRequests.isEmpty()) {
			Collections.sort(serviceRequests, new TickerRankComparator());
			requestToRemove = serviceRequests.get(0);

			synchronized (map) {
				map.remove(requestToRemove.getRequesterId());
			}
		}

		return Optional.ofNullable(requestToRemove);
	}

	@Override
	public Optional<List<ServiceRequest>> getSortedOrderList() {
		final List<ServiceRequest> serviceRequests;

		synchronized (map) {
			serviceRequests = new ArrayList<>(map.values());
		}

		Collections.sort(serviceRequests, new TickerRankComparator());

		return Optional.ofNullable(serviceRequests);
	}

	@Override
	public Optional<ServiceRequest> remove(final Long requesterId) {
		final ServiceRequest request;

		synchronized (map) {
			request = map.get(requesterId);
		}

		return Optional.ofNullable(request);
	}

	@Override
	public Optional<Integer> getPosition(Long requestorId) {
		final ServiceRequest request = map.get(requestorId);
		final List<ServiceRequest> serviceRequests;

		if (request != null) {
			synchronized (map) {
				serviceRequests = new ArrayList<>(map.values());
			}
			Collections.sort(serviceRequests, new TickerRankComparator());

			return Optional.of(serviceRequests.indexOf(request));
		}

		return Optional.empty();
	}

	@Override
	public Double avgWaitTime(Long currentTimeInSecond) {
		final List<Long> listOfRequestTime = new ArrayList<>();
		final List<ServiceRequest> serviceRequests;

		synchronized (map) {
			serviceRequests = new ArrayList<>(map.values());
		}

		serviceRequests.parallelStream().forEach(e -> listOfRequestTime.add(e.getSecondsElapsed()));

		return listOfRequestTime.stream().mapToLong(x -> x).summaryStatistics().getAverage();
	}

}

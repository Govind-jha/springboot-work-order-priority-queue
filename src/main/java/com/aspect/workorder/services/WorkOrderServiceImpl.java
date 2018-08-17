package com.aspect.workorder.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aspect.workorder.dao.RankedServiceRequestQueueDAOImpl;
import com.aspect.workorder.factory.ServiceRequestFactory;
import com.aspect.workorder.model.servicerequest.ServiceRequest;

/**
 * Gift Order Service Implementation.
 * 
 * @author kumjha
 *
 */
@Component
public class WorkOrderServiceImpl implements WorkOrderService {

	@Autowired
	RankedServiceRequestQueueDAOImpl giftOrderRequestDAO;

	@Override
	public Boolean addOrder(Long requestorId, Long timeOfRequest) {
		return giftOrderRequestDAO.enque(ServiceRequestFactory.getRequest(requestorId, timeOfRequest)) ? Boolean.TRUE
				: Boolean.FALSE;
	}

	@Override
	public Long removeNextOrder() {
		final Optional<ServiceRequest> requestOptional = giftOrderRequestDAO.deque();

		if (requestOptional.isPresent()) {
			return requestOptional.get().getRequesterId();
		}

		return -1L;
	}

	@Override
	public List<Long> getOrderList() {
		final List<ServiceRequest> sortedListOfRequests = new ArrayList<>(giftOrderRequestDAO.getQueue());

		return sortedListOfRequests.stream().map(e -> e.getRequesterId()).collect(Collectors.toList());
	}

	@Override
	public Long remove(final Long requestorId) {
		return giftOrderRequestDAO.remove(requestorId) ? requestorId : -1L;
	}

	@Override
	public Integer getPosition(Long requestorId) {
		return giftOrderRequestDAO.getQueue().indexOf(giftOrderRequestDAO.getServiceRequestByIdMap().get(requestorId.toString()));
	}

	@Override
	public Double avgWaitTime(Long currentTimeInSecond) {
		final List<Long> listOfRequestTime = new ArrayList<>();
		giftOrderRequestDAO.getQueue().stream()
				.forEach(e -> listOfRequestTime.add(currentTimeInSecond - e.getTimeOfRequest()));

		return listOfRequestTime.stream().mapToLong((x) -> x).summaryStatistics().getAverage();
	}

}

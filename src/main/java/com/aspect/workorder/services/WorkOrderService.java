package com.aspect.workorder.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.aspect.workorder.model.servicerequest.ServiceRequest;

@Component
public interface WorkOrderService {

	public Optional<ServiceRequest> addOrder(Long requestorId, Long timeOfRequest);

	public Optional<ServiceRequest> removeNextOrder();

	public Optional<List<ServiceRequest>> getSortedOrderList();

	public Optional<ServiceRequest> remove(Long requestorId);

	public Optional<Integer> getPosition(Long requestorId);

	public Double avgWaitTime(Long currentTime);
}

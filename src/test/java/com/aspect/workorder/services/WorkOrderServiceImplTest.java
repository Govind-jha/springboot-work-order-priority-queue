package com.aspect.workorder.services;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.aspect.workorder.services.WorkOrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class WorkOrderServiceImplTest {

	@Autowired
	WorkOrderService giftService;

	private final Long requesterId_1 = 30L;
	private final Long timeOfRequest_1 = 30_000_000L;

	private final Long requesterId_2 = 50L;
	private final Long timeOfRequest_2 = 70_000_000L;

	private final Long requesterId_3 = 7L;
	private final Long timeOfRequest_3 = 70_000_000L;

	private final Long[] queueInSortedManner = { requesterId_1, requesterId_2, };

	@Test
	public void testAddOrder() {
		final Boolean result = giftService.addOrder(requesterId_3, timeOfRequest_3);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Enqueue was sucessful.").isTrue();
		softly.assertAll();
	}

	@Test
	public void testRemoveNextOrder() {
		giftService.addOrder(requesterId_1, timeOfRequest_1);
		final Long result = giftService.removeNextOrder();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Dequeue was sucessful.").isInstanceOf(Long.class);
		softly.assertThat(result).as("Managment id 30 is on top.").isEqualTo(requesterId_1);
		softly.assertAll();
	}

	@Test
	public void testRemoveNextOrder_emptyQueue() {
		giftService.remove(requesterId_1);
		giftService.remove(requesterId_2);
		giftService.remove(requesterId_3);

		final Long result = giftService.removeNextOrder();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Dequeue was sucessful.").isInstanceOf(Long.class);
		softly.assertThat(result).as("Managment id 30 is on top.").isEqualTo(-1L);
		softly.assertAll();
	}

	@Test
	public void testGetOrderList() {
		giftService.addOrder(requesterId_1, timeOfRequest_1);
		giftService.addOrder(requesterId_2, timeOfRequest_2);
		giftService.addOrder(requesterId_3, timeOfRequest_3);
		giftService.addOrder(requesterId_1, timeOfRequest_1);

		final List<Long> result = giftService.getOrderList();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Result is an array of Long.").isInstanceOf(List.class);
		softly.assertThat(result).as("List contains all ids.").contains(queueInSortedManner);
		softly.assertAll();
	}

	@Test
	public void testGetOrderList_emptyQueue() {
		giftService.remove(requesterId_1);
		giftService.remove(requesterId_2);
		giftService.remove(requesterId_3);

		final List<Long> result = giftService.getOrderList();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Result is an array of Long.").isInstanceOf(List.class);
		softly.assertThat(result.size()).as("List contains all ids.").isEqualTo(0);
		softly.assertAll();
	}

	@Test
	public void testRemove() {
		giftService.addOrder(requesterId_1, timeOfRequest_1);
		giftService.addOrder(requesterId_2, timeOfRequest_2);
		giftService.addOrder(requesterId_3, timeOfRequest_3);

		final Long result = giftService.remove(requesterId_1);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Result is an array of Long.").isInstanceOf(Long.class);
		softly.assertThat(result).as("Requester_1 id was removed from queue.").isEqualTo(requesterId_1);
		softly.assertAll();
	}

	@Test
	public void testRemove_idNotPresentInQueue() {
		giftService.remove(requesterId_1);
		giftService.remove(requesterId_2);
		giftService.remove(requesterId_3);

		final Long result = giftService.remove(requesterId_1);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Result is an array of Long.").isInstanceOf(Long.class);
		softly.assertThat(result).as("Requester_1 id was removed from queue.").isEqualTo(-1L);
		softly.assertAll();
	}

	@Test
	public void testGetPosition() {
		giftService.addOrder(requesterId_1, timeOfRequest_1);
		giftService.addOrder(requesterId_2, timeOfRequest_2);
		giftService.addOrder(requesterId_3, timeOfRequest_3);

		final Integer result = giftService.getPosition(requesterId_1);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Result is an array of Long.").isInstanceOf(Integer.class);
		softly.assertThat(result).as("Requester_1 id was removed from queue.").isEqualTo(0);
		softly.assertAll();
	}

	@Test
	public void testGetPosition_idNotPresentInQueue() {
		giftService.remove(requesterId_1);
		giftService.remove(requesterId_2);
		giftService.remove(requesterId_3);

		final Integer result = giftService.getPosition(requesterId_1);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Result is an array of Long.").isInstanceOf(Integer.class);
		softly.assertThat(result).as("Requester_1 id was removed from queue.").isEqualTo(-1);
		softly.assertAll();
	}

	@Test
	public void testAvgWaitTime() {
		giftService.addOrder(requesterId_1, timeOfRequest_1);
		giftService.addOrder(requesterId_3, timeOfRequest_3);

		final Double result = giftService.avgWaitTime(timeOfRequest_3);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Result is an array of Long.").isInstanceOf(Double.class);
		softly.assertThat(result).as("Requester_1 id was removed from queue.").isGreaterThan(0d);
		softly.assertAll();
	}

	@Test
	public void testAvgWaitTime_emptyQueue() {
		giftService.remove(requesterId_1);
		giftService.remove(requesterId_2);
		giftService.remove(requesterId_3);

		final Double result = giftService.avgWaitTime(timeOfRequest_3);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result).as("Result is an array of Long.").isInstanceOf(Double.class);
		softly.assertThat(result).as("Requester_1 id was removed from queue.").isEqualTo(0d);
		softly.assertAll();
	}

}

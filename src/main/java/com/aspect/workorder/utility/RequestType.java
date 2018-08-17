package com.aspect.workorder.utility;

/**
 * ENUM for RequestTypes.
 * 
 * There are 4 classes of IDs, normal, priority, VIP, and management override. 
 * 
 * @author kumjha
 *
 */
public enum RequestType {

	MANAGEMENT(1), VIP(2), PRIORITY(3), NORMAL(4);

	private int rank;

	private RequestType(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}
}

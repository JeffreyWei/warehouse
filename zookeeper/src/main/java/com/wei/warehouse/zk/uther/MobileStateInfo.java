package com.wei.warehouse.zk.uther;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by wei on 16/6/15.
 */
public class MobileStateInfo implements Serializable{
	public MobileStateInfo() {
	}

	private static final long serialVersionUID = -2239259808023361336L;
	private Date date;
	private Long customerID;
	private String method;

	public MobileStateInfo( Long customerID, String method) {
		this.customerID = customerID;
		this.method = method;
		this.date = new Date();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

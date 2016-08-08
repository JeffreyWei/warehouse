package com.wei.warehouse.disruptor.demo1;

/**
 * Created by wei on 16/8/8.
 */
public class LongEvent {
	private long value;

	public void setValue(long vaule) {
		this.value = vaule;
	}

	public long getValue() {
		return value;
	}
}

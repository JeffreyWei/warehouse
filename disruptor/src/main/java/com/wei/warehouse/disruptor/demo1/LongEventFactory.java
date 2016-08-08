package com.wei.warehouse.disruptor.demo1;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wei on 16/8/8.
 */
public class LongEventFactory implements EventFactory<LongEvent> {
	public LongEvent newInstance() {
		return new LongEvent();
	}
}

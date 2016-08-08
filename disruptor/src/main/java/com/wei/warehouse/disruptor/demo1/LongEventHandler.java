package com.wei.warehouse.disruptor.demo1;


import com.lmax.disruptor.EventHandler;

/**
 * Created by wei on 16/8/8.
 */
public class LongEventHandler implements EventHandler<LongEvent> {

	public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
		System.out.println(longEvent.getValue()+" begin "+Thread.currentThread().getName());

		Thread.sleep(100);
		System.out.println(longEvent.getValue()+" end "+Thread.currentThread().getName());
	}
}

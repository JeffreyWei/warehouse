package com.wei.warehouse.disruptor.demo1;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by wei on 16/8/8.
 */
public class LongEventMain {
	public static void main(String[] args) throws Exception {
		Executor executor = Executors.newCachedThreadPool();
		LongEventFactory longEventFactory = new LongEventFactory();
		int buffersize = 1024;
		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(longEventFactory, buffersize, executor);
		disruptor.handleEventsWith(new LongEventHandler());
		RingBuffer<LongEvent> ringBuffer = disruptor.start();
		LongEventProducer producer = new LongEventProducer(ringBuffer);
		ByteBuffer byteBuffer = ByteBuffer.allocate(8);
		for (long i = 0; true; i++) {
			byteBuffer.putLong(0, i);
			producer.onData(byteBuffer);
//			Thread.sleep(100);
		}


	}


}

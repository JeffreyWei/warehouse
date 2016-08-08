package com.wei.warehouse.disruptor.demo1;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by wei on 16/8/8.
 */
public class LongEventProducer {

	private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
		public void translateTo(LongEvent longEvent, long l, ByteBuffer byteBuffer) {
			longEvent.setValue(byteBuffer.getLong(0));
		}
	};
	private final RingBuffer<LongEvent> ringBuffer;

	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onData(ByteBuffer byteBuffer) {
		ringBuffer.publishEvent(TRANSLATOR, byteBuffer);
	}
}

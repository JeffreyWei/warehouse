package com.wei.warehouse.disruptor.demo2;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wei on 16/8/8.
 */


	public final class ValueEvent {
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public final static EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>() {
			public ValueEvent newInstance() {
				return new ValueEvent();
			}
		};
	}

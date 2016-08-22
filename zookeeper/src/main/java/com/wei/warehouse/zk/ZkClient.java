package com.wei.warehouse.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * Created by wei on 16/8/9.
 */
public class ZkClient {
	private static CuratorFramework CLIENT = null;

	static {
		CLIENT = CuratorFrameworkFactory.newClient("192.168.220.164:2181", 5000, 3000, new ExponentialBackoffRetry(1000, 3));
		CLIENT.start();
	}

	public static CuratorFramework getCLIENT() {
		return CLIENT;
	}

	public static void close() {
		CloseableUtils.closeQuietly(CLIENT);
	}
}

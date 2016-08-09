package com.wei.warehouse.zk.lock.demo1;

import com.wei.warehouse.zk.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * 排它锁
 * Created by wei on 16/8/9.
 */
public class ZkLockDemo {
	private static String PATH = "/zklock/contract/schedule/";

	public void createLock(String lockName) {
		try {
			String nodePath = PATH + lockName;
			//临时节点
			nodePath= ZkClient.getCLIENT().create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(nodePath);
			//持久节点
//			nodePath= ZkClient.getCLIENT().create().creatingParentsIfNeeded().forPath(nodePath);
			ZkClient.getCLIENT().delete().forPath(nodePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ZkLockDemo zkLockDemo = new ZkLockDemo();
		zkLockDemo.createLock("task_1");
	}
}

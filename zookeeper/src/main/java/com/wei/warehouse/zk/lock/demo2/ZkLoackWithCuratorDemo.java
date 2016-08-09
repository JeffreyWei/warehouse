package com.wei.warehouse.zk.lock.demo2;

import com.wei.warehouse.zk.ZkClient;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * Created by wei on 16/8/9.
 */
public class ZkLoackWithCuratorDemo {
	static String lock_path = "/zklock/contract/schedule/taks1";

	public void createLock() {
		InterProcessMutex lock = new InterProcessMutex(ZkClient.getCLIENT(), lock_path);
		try {
			lock.acquire();
		} catch (Exception e) {

		}
		System.out.println("begin");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("done");
		try {
			lock.release();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		ZkLoackWithCuratorDemo zkLoackWithCuratorDemo = new ZkLoackWithCuratorDemo();
		zkLoackWithCuratorDemo.createLock();
	}
}

package com.wei.warehouse.zk.uther;

import com.wei.warehouse.zk.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * Created by wei on 16/11/12.
 */
public class ClusterStateUtilsTest {
	public static void main(String[] args) {

		try {
			Thread t = new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							ZkClient.getCLIENT().create().withMode(CreateMode.EPHEMERAL).forPath("/lock");
							System.out.println("A获得锁");
							Thread.sleep((long) (Math.random() * 1000));
							ZkClient.getCLIENT().delete().forPath("/lock");
							System.out.println("A释放锁");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			});

			Thread t2 = new Thread(new Runnable() {
				public void run() {

					int count = 0;
					while (count < 5) {
						try {
							ZkClient.getCLIENT().create().withMode(CreateMode.EPHEMERAL).forPath("/lock");
							System.out.println("B获得锁");
							count++;
							Thread.sleep((long) (Math.random() * 1000));
							ZkClient.getCLIENT().delete().forPath("/lock");
							System.out.println(Thread.currentThread().getName() + "B释放锁");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			t.start();
			t2.start();
			while (true) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}


//		Set<String> regions = new HashSet<String>();
//		regions.add("mobileState");
//		regions.add("mobileResult");
//		regions.add("mobileDailyFailure");
//		ClusterStateUtils mobileStateManager = new ClusterStateUtils("mobile", regions);
//		MobileStateInfo mobileStateInfo1 = new MobileStateInfo(1L,"method1-1");
//		MobileStateInfo mobileStateInfo2 = new MobileStateInfo(1L,"method1-2");
//		MobileStateInfo mobileStateInfo3 = new MobileStateInfo(2L,"method2-1");
//		MobileStateInfo mobileStateInfo4 = new MobileStateInfo(3L,"method3-1");


//		System.out.println(mobileStateManager.acquire("testlock"));
//		System.out.println(mobileStateManager.set("mobileState", "ZJD1", mobileStateInfo1));
//		MobileStateInfo obj=mobileStateManager.get("mobileState", "ZJD1",MobileStateInfo.class );
//		System.out.println(obj);
//		mobileStateManager.set("mobileState", "ZJD1", mobileStateInfo2);
//		obj=mobileStateManager.get("mobileState", "ZJD1",MobileStateInfo.class );
//		System.out.println(obj);
//		System.out.println(mobileStateManager.set("mobileState", "ZJD2", mobileStateInfo3)+"set ZJD2");
//		System.out.println(mobileStateManager.remove("mobileState", "ZJD2")+"remove ZJD2");
//
//		System.out.println(mobileStateManager.set("mobileState", "ZJD3", mobileStateInfo4)+" SET ZJD3");
//		System.out.println(mobileStateManager.set("mobileState", "ZJD3", mobileStateInfo4)+" SET ZJD3");
//
//
//		System.out.println(mobileStateManager.remove("mobileState", "ZJD1")+"remove ZJD1");
//		System.out.println(mobileStateManager.remove("mobileState", "ZJDX") + "remove ZJDX");

//		System.out.println(mobileStateManager.release("testlock"));

	}


}

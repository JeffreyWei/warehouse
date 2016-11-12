package com.wei.warehouse.zk.uther;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wei on 16/11/12.
 */
public  class ClusterStateUtilsTest {
	public static void main(String[] args) {
		Set<String> regions = new HashSet<String>();
		regions.add("mobileState");
		regions.add("mobileResult");
		regions.add("mobileDailyFailure");
		ClusterStateUtils mobileStateManager = new ClusterStateUtils("mobile", regions);
		MobileStateInfo mobileStateInfo1 = new MobileStateInfo(1L,"method1-1");
		MobileStateInfo mobileStateInfo2 = new MobileStateInfo(1L,"method1-2");
		MobileStateInfo mobileStateInfo3 = new MobileStateInfo(2L,"method2-1");
		MobileStateInfo mobileStateInfo4 = new MobileStateInfo(3L,"method3-1");


		System.out.println(mobileStateManager.acquire("testlock"));
		System.out.println(mobileStateManager.set("mobileState", "ZJD1", mobileStateInfo1));
		MobileStateInfo obj=mobileStateManager.get("mobileState", "ZJD1",MobileStateInfo.class );
		System.out.println(obj);
		mobileStateManager.set("mobileState", "ZJD1", mobileStateInfo2);
		obj=mobileStateManager.get("mobileState", "ZJD1",MobileStateInfo.class );
		System.out.println(obj);
		System.out.println(mobileStateManager.set("mobileState", "ZJD2", mobileStateInfo3)+"set ZJD2");
		System.out.println(mobileStateManager.remove("mobileState", "ZJD2")+"remove ZJD2");

		System.out.println(mobileStateManager.set("mobileState", "ZJD3", mobileStateInfo4)+" SET ZJD3");
		System.out.println(mobileStateManager.set("mobileState", "ZJD3", mobileStateInfo4)+" SET ZJD3");


		System.out.println(mobileStateManager.remove("mobileState", "ZJD1")+"remove ZJD1");
		System.out.println(mobileStateManager.remove("mobileState", "ZJDX") + "remove ZJDX");

		System.out.println(mobileStateManager.release("testlock"));

	}


}

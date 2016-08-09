package com.wei.warehouse.zk.masterselect.demo1;

import com.wei.warehouse.zk.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

/**
 * Created by wei on 16/8/9.
 */
public class RecipesMasterSelect {
	private static String MASTERPATH = "/zkmaster/contract/task/master1";

	public boolean MasterSelect() {
		boolean result = false;
		try {
			LeaderSelector selector = new LeaderSelector(ZkClient.getCLIENT(), MASTERPATH, new LeaderSelectorListenerAdapter() {
				public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
					System.out.println("begin");
					Thread.sleep(5000);
					System.out.println("done");
				}
			});
			//事件循环
//			selector.autoRequeue();
			selector.start();
			result = true;
		} catch (Exception e) {

		}
		return result;
	}

	public static void main(String[] args) {
		RecipesMasterSelect recipesMasterSelect = new RecipesMasterSelect();
		recipesMasterSelect.MasterSelect();
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

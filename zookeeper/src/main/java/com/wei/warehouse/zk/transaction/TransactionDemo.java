package com.wei.warehouse.zk.transaction;

import com.wei.warehouse.zk.ZkClient;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;

import java.util.Collection;

/**
 * zk事务
 * Created by wei on 16/8/9.
 */
public class TransactionDemo {
	public static void main(String[] args) {
		try {
			CuratorTransaction transaction = ZkClient.getCLIENT().inTransaction();
			Collection<CuratorTransactionResult> results = transaction.create()
					.forPath("/a/path", "some data".getBytes()).and().setData()
					.forPath("/another/path", "other data".getBytes()).and().delete().forPath("/yet/another/path")
					.and().commit();

			for (CuratorTransactionResult result : results) {
				System.out.println(result.getForPath() + " - " + result.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ZkClient.close();
		}
	}
}

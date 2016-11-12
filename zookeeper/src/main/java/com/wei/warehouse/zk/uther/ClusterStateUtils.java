package com.wei.warehouse.zk.uther;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.wei.warehouse.zk.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.data.Stat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 共享状态
 * Created by wei on 16/11/12.
 */
public final class ClusterStateUtils {
	private final String namespace = "/RCM";//命名空间
	private final String lockpath = "/lock";//锁
	private final Kryo kryoSerialize = new Kryo();
	private String businessType;//业务类型
	private Set<String> regions;
	private CuratorFramework client;


	private ClusterStateUtils() {

	}

	public ClusterStateUtils(String businessType, Set<String> regions) {
		this.businessType = "/" + businessType;
		this.client = ZkClient.getCLIENT();
		this.regions = regions;
		kryoSerialize.register(Date.class,97);
		initWorkPath();
	}


	/**
	 * 初始化工作目录
	 */
	private void initWorkPath() {
		try {
			safeCreatePath(namespace + businessType + lockpath);
			for (String region : regions) {
				safeCreatePath(namespace + businessType + "/" + region);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void safeCreatePath(String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		if (stat == null) {
			client.create().creatingParentsIfNeeded().forPath(path);
		}
	}

	/**
	 * 获取锁
	 *
	 * @param lockName
	 * @return
	 */
	public boolean acquire(String lockName) {
		boolean success = false;
		try {
			InterProcessMutex lock = new InterProcessMutex(client, namespace + businessType + lockpath + "/" + lockName);
			lock.acquire();
			success = true;
		} catch (Exception e) {

		}
		return success;
	}

	/**
	 * 释放锁
	 *
	 * @param lockName
	 * @return
	 */
	public boolean release(String lockName) {
		boolean success = false;
		try {
			InterProcessMutex lock = new InterProcessMutex(client, namespace + businessType + lockpath + "/" + lockName);
			lock.release();
			success = true;
		} catch (Exception e) {

		}
		return success;
	}

	/**
	 * 重建目录
	 *
	 * @param region
	 * @return
	 */
	public boolean rebuildPath(String region) {
		if (!regions.contains(region) || region.equals(lockpath)) {
			return false;
		}
		boolean success = false;
		try {
			client.delete().deletingChildrenIfNeeded().forPath(namespace + businessType + "/" + region);
			client.create().forPath(namespace + businessType + "/" + region);
			success = true;
		} catch (Exception e) {

		}
		return success;
	}

	/**
	 * 获取值
	 *
	 * @param region
	 * @param key
	 * @return
	 */
	public byte[] get(String region, String key) {
		byte[] bytes = null;
		try {
			bytes = client.getData().forPath(namespace + businessType + "/" + region + "/" + key);
		} catch (Exception e) {

		}
		return bytes;
	}

	/**
	 * 获取值
	 *
	 * @param region
	 * @param key
	 * @param targetClass
	 * @param <T>
	 * @return
	 */
	public <T> T get(String region, String key, Class<T> targetClass) {
		T obj = null;
		byte[] bytes = null;
		Input ois = null;
		try {
			bytes = get(region, key);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ois = new Input(bais);
			obj = kryoSerialize.readObject(ois, targetClass);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (ois != null)
				ois.close();
		}
		return obj;
	}

	/**
	 * 写入值
	 *
	 * @param region
	 * @param key
	 * @param bytes
	 * @return
	 */
	public boolean set(String region, String key, byte[] bytes) {
		boolean success = false;
		try {
			Stat stat = client.checkExists().forPath(namespace + businessType + "/" + region + "/" + key);
			if (stat != null) {
				client.delete().forPath(namespace + businessType + "/" + region + "/" + key);
			}
			client.create().forPath(namespace + businessType + "/" + region + "/" + key, bytes);
			success = true;
		} catch (Exception e) {

		}
		return success;
	}

	/**
	 * 写入值
	 *
	 * @param region
	 * @param key
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public <T> boolean set(String region, String key, T obj) {
		boolean success = false;
		Output output = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			output = new Output(baos);
			kryoSerialize.writeClassAndObject(output, obj);
			output.flush();
			set(region, key, baos.toByteArray());
			success = true;
		} catch (Exception e) {

		} finally {
			if (output != null)
				output.close();
		}
		return success;
	}

	/**
	 * 移除
	 *
	 * @param region
	 * @param key
	 * @return
	 */
	public boolean remove(String region, String key) {
		boolean success = false;
		try {
			client.delete().guaranteed().forPath(namespace + businessType + "/" + region + "/" + key);
			success = true;
		} catch (Exception e) {

		}
		return success;
	}


	/**
	 * 获取子节点
	 *
	 * @param region
	 * @return
	 */
	public List<String> getChildren(String region) {
		List<String> keys = new ArrayList<String>();
		try {
			keys = client.getChildren().forPath(namespace + businessType + "/" + region);
		} catch (Exception e) {

		}
		return keys;
	}
}

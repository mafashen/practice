package com.mafashen.structure.arithmetic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


/**
 * 一致性hash算法
 * @author mafashen
 * created on 2019/3/10.
 */
public class ConsistentHash <K, V> {

	/**
	 * 实际节点在环上的放大倍数
	 */
	private static final int DEFAULT_ENLARGEMENT_FACTOR = 640;
	private  int enlargement_factor ;
	private static final int DEFAULT_LOOP_SIZE = 65536*2;
	private static Random random = new Random();
	/**
	 * 实际节点与其对应的虚拟节点映射关系
	 */
	private Map<Cache, Set<Integer>> actualToVirtualNodesMap = new HashMap<>();
	private Map<Integer, Cache<K, V>> virtualToActualNodeMap = new HashMap<>();
	private List<Integer> inUsedVirtualNodes = new LinkedList<>();
	//todo size
	private Map<Integer, Cache> inUsedCaches = new HashMap<>();

	private Set<K> keys = new HashSet<>();

	public ConsistentHash() {
		this(DEFAULT_ENLARGEMENT_FACTOR);
	}

	public ConsistentHash(int enlargement_factor) {
		this.enlargement_factor = enlargement_factor;

		// init
		for (int i = 0; i < 5; i++) {
			addCacheNode(i);
		}
	}

	private MessageDigest md5 = null;

	private int hash(K key){
//		int hashCode;
//		return key == null ? 0 : ((hashCode = key.hashCode()) ^ hashCode >>> 16) % DEFAULT_LOOP_SIZE;
		if (md5 == null) {
			 try {
				 md5 = MessageDigest.getInstance("MD5");
			 } catch (NoSuchAlgorithmException e) {
				 throw new IllegalStateException("no md5 algrithm found");
			 }
		}

		 md5.reset();
		 md5.update(key.toString().getBytes());
		 byte[] bKey = md5.digest();
		 //具体的哈希函数实现细节--每个字节 & 0xFF 再移位
		 long result = ((long) (bKey[3] & 0xFF) << 24)
				 | ((long) (bKey[2] & 0xFF) << 16
						 | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF));
		 return (int) (result & 0xffff);
	}

	private Set<Integer> generateVirtualNodes(){
		Set<Integer> newVirtualNodes = new HashSet<>();
		while(newVirtualNodes.size() < enlargement_factor){
			newVirtualNodes.add(random.nextInt(DEFAULT_LOOP_SIZE));
		}
		return newVirtualNodes;
	}

	private int getNearestCacheNode(int dataIndex){
		//todo binary search first >= dataIndex
		for (Integer inUsedVirtualNode : inUsedVirtualNodes) {
			if (inUsedVirtualNode >= dataIndex){
				return inUsedVirtualNode;
			}
		}
		return inUsedVirtualNodes.get(0);
	}

	private void addCacheNode(int i){
		Cache<K, V> cache = new Cache<>(i, "Cache-"+i);
		inUsedCaches.put(i, cache);

		Set<Integer> virtualNodes = generateVirtualNodes();
		inUsedVirtualNodes.addAll(virtualNodes);
		actualToVirtualNodesMap.put(cache, virtualNodes);
		for (Integer virtualNode : virtualNodes) {
			virtualToActualNodeMap.put(virtualNode, cache);
		}
		Collections.sort(inUsedVirtualNodes);
	}

	private void removeCacheNode(Integer i){
		Cache remove = inUsedCaches.remove(i);
		Set<Integer> virtualNodes = actualToVirtualNodesMap.get(remove);
		inUsedVirtualNodes.removeAll(virtualNodes);
	}

	private void putCache(K key, V value){
		int nearestCacheNode = getNearestCacheNode(hash(key));
		Cache<K, V> cache = virtualToActualNodeMap.get(nearestCacheNode);
		if (cache != null){
			cache.putCache(key, value);
			keys.add(key);
		}
	}

	private V getCache(K key){
		int nearestCacheNode = getNearestCacheNode(hash(key));
		Cache<K, V> cache = virtualToActualNodeMap.get(nearestCacheNode);
		if (cache != null){
			return cache.getCache(key);
		}
		return null;
	}

	public Set<K> getKeys() {
		return keys;
	}

	static class Cache<K, V>{
		private int id;
		private String name;
		private Map<K, V> map;

		public Cache(int id, String name) {
			this.id = id;
			this.name = name;
			map = new HashMap<>();
		}

		private void putCache(K key, V value){
			map.put(key, value);
			System.out.printf("put [%s : %s] into Cache [%d : %s] \n", key, value, id, name);
		}

		private V getCache(K key){
			V v = map.get(key);
			if(v != null){
				System.out.printf("get [%s:%s] from Cache [%d : %s] \n", key, v, id, name);
			}else{
				System.out.printf("get %s from Cache  [%d : %s] failed \n ", key, id, name);
			}
			return v;
		}
	}

	public static void main(String[] args) {
		ConsistentHash<String, String> consistentHash = new ConsistentHash<>();

		consistentHash.putCache("Key1", "Value1");
		consistentHash.putCache("KKey2", "Value2");
		consistentHash.putCache("KKKey3", "Value3");
		consistentHash.putCache("KKKKey4", "Value4");
		consistentHash.putCache("KKKKKey5", "Value5");
		consistentHash.putCache("KKKKKKey6", "Value6");
		consistentHash.putCache("KKKKKKKey7", "Value7");
		consistentHash.putCache("KKKKKKKKey8", "Value8");
		consistentHash.putCache("KKKKKKKKKey9", "Value9");
		consistentHash.putCache("KKKKKKKKKKey10", "Value10");

		consistentHash.addCacheNode(6);
		consistentHash.removeCacheNode(4);
		for (String key : consistentHash.getKeys()) {
			consistentHash.getCache(key);
		}
	}
}

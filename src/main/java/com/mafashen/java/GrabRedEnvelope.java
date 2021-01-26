package com.mafashen.java;

import com.alibaba.fastjson.JSONObject;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * 抢红包
 * @author mafashen
 * @since 2020-12-28.
 */
public class GrabRedEnvelope {

    int num;

    /**
     * 金额, 分
     */
    int amount;

    int[] envelopes;

    ConcurrentHashMap<Integer, Integer> existIndex;

    Semaphore semaphore;

    public GrabRedEnvelope(int num, int amount) {
        this.num = num;
        this.amount = amount;
        envelopes = new int[num];
        semaphore = new Semaphore(num);
        existIndex = new ConcurrentHashMap<>(num * 2);

        // 生成红包的同时, 生成随机的红包分布
        Random random = new Random();
        int last = amount-1;
        int max = amount / num * 3;
        for (int i = 0; i <= num-2; i++) {
            // 不能小于0
            int r = 0;
            int maxStep = 6;
            do{
                r = random.nextInt(last);
                maxStep--;
            }while((r == 0 || r > max) && maxStep > 0 );

            if (r == 0){
                r = 1;
            }
            if (r > max){
                r = random.nextInt(max);
            }
            envelopes[i] = r;
            last -= envelopes[i];
        }
        envelopes[num-1] = last + 1;
    }

    public int grab(){
        System.out.printf("线程开始抢%s\n", Thread.currentThread());
        int m = 0;
        if (semaphore.tryAcquire()) {
            int step = 0;
            do{
                // 单机情况下并发访问时, 怎么确定对应的顺序,
                // 如果是集群场景, 请求的是不同的服务器, 该怎么加锁且提升访问数量, 基于zk创建分布式共享锁, 红包对象放入缓存, cas修改
                int index = (Thread.currentThread().hashCode() + step) % num;
                if (existIndex.get(index) == null){
                    m = envelopes[index];
                    existIndex.put(index, m);
                    System.out.printf("线程%s 抢到 第 [%d] 个红包, 金额 [%d]\n", Thread.currentThread(), index, m);
                    return m;
                }else{
                    step++;
                }
            }while(step < num);
        }
        System.out.printf("线程%s 没有抢到\n", Thread.currentThread());
        return m;
    }

    public static void main(String[] args) {
        GrabRedEnvelope grabRedEnvelope = new GrabRedEnvelope(10, 1000);
        System.out.println(JSONObject.toJSONString(grabRedEnvelope.envelopes));

        for (int i = 0; i < 20; i++) {
            new Thread(grabRedEnvelope::grab, "" + i).start();
        }
    }
}

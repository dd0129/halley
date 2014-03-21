package com.dianping;

import com.dianping.constant.SystemConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hongdi.tang on 14-2-4.
 */
public class ConcurrencyHashMapTest {
    public static Map<String,Long> map = new ConcurrentHashMap<String, Long>();
    public static Integer num = 0;

    public static void main(String... args) throws InterruptedException {
//        Long a = System.currentTimeMillis();
//        ConcurrencyHashMapTest.singlePushQueue();
//        Long a1 = System.currentTimeMillis();
//        System.out.println(a1-a);

        Long b = System.currentTimeMillis();
        ConcurrencyHashMapTest.pushQueue();
        Long b1 = System.currentTimeMillis();
        System.out.println(b1-b);
        //ConcurrencyHashMapTest.pushQueue();
        //System.out.println(map.size());
        while (true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (String num : map.keySet()){
                        map.remove(num);
                    }
                }
            }).start();
        }

    }

    public static void pullQueue() throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String num : map.keySet()){
                    map.remove(num);
                }
            }
        }).start();

    }

    public static void pushQueue() throws InterruptedException {
        for (int i =0 ;i <1000;i++) {
            final Long timeMillis = System.currentTimeMillis();
            //System.out.println(timeMillis);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    map.put(String.valueOf(num++), timeMillis);
                }
            }).start();
        }
    }
    public static void singlePushQueue() throws InterruptedException {
        for (int i =0 ;i <1000;i++) {
            //System.out.println(System.currentTimeMillis());
            map.put(String.valueOf(num++), System.currentTimeMillis());
        }
    }


}

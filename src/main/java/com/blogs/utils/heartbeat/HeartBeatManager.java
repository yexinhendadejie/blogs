package com.blogs.utils.heartbeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 心跳管理器,主要功能有:
 * (1) refresh:刷新指定key的心跳
 * (2) isTimeout:判断心跳是否超时
 * (3) listAllAlive:列出所有活跃的心跳元素
 * (4) clearDeath:清除已死亡的心跳
 * (5) getHeartBeat:获取指定心跳
 * (6) 删除、清空心跳等操作
 *
 * @param <K>
 * @param <E>
 */
public class HeartBeatManager<K, E> {

    // 心跳周期
    private final long interval;

    private final Map<K, HeartBeat<E>> heartBeatMap = new ConcurrentHashMap<>();

    // 构造函数，接收心跳周期参数
    public HeartBeatManager(long interval) {
        this.interval = interval;
    }

    // Supplier用于不需要输入参数，但是需要返回值的场景
    public void refresh(K k, Supplier<E> supplier) {
        if (k == null) {
            throw new HeartBeatException("键名不能为空");
        }

        // 获取心跳 (根据userId获取心跳)
        HeartBeat<E> heartBeat = heartBeatMap.get(k);

        // 应用场景 仍然是改对聊天切出去和别人聊天 但是在时间内又回来聊天那么给他刷新时间
        if (supplier == null) {
            if (heartBeat != null) {
                // 刷新心跳
                heartBeat.setLastHeartBeatTime(System.currentTimeMillis());
            }
            return;
        }

        // 根据外部定义()->channel 传入的参数，获取当前的channel
        E element = supplier.get();
        if (element == null) {
            throw new HeartBeatException("element can not be null");
        }

        // 如果心跳不为空，那么就设置心跳元素和最后心跳时间
        if (heartBeat != null) {
            heartBeat.setElement(element);
            heartBeat.setLastHeartBeatTime(System.currentTimeMillis());
            return;
        }

        // 如果心跳为空，那么就创建一个新的心跳
        HeartBeat<E> newHeartBeat = new HeartBeat<>();
        newHeartBeat.setElement(element);
        newHeartBeat.setLastHeartBeatTime(System.currentTimeMillis());
        heartBeatMap.put(k, newHeartBeat);
    }

    public void refresh(K k) {
        refresh(k, null);
    }

    /**
     * 判断指定心跳是否超时
     *
     * @param k 键名
     * @return 是否超时
     */
    public boolean isTimeout(K k) {
        if (k == null) {
            throw new HeartBeatException("键名不能为空");
        }

        HeartBeat<E> heartBeat = heartBeatMap.get(k);
        if (heartBeat == null) {
            return true;
        }
        return System.currentTimeMillis() - heartBeat.getLastHeartBeatTime() > interval;
    }

    /**
     * 获得还有心跳的所有心跳元素
     *
     * @return 元素列表
     */
    public List<E> listAllAlive() {
        List<E> list = new ArrayList<>();
        for (Map.Entry<K, HeartBeat<E>> entry : heartBeatMap.entrySet()) {
            if (!isTimeout(entry.getKey())) {
                HeartBeat<E> value = entry.getValue();
                if (value == null || value.getElement() == null) {
                    continue;
                }
                list.add(value.getElement());
            }
        }
        return list;
    }

    /**
     * 清空已经死亡的心跳
     */
    public void clearDeath() {
        for (Map.Entry<K, HeartBeat<E>> entry : heartBeatMap.entrySet()) {
            if (isTimeout(entry.getKey())) {
                heartBeatMap.remove(entry.getKey());
            }
        }
    }

    /**
     * 清空所有心跳
     */
    public void clearAll() {
        heartBeatMap.clear();
    }

    /**
     * 删除指定心跳
     *
     * @param k 键名
     */
    public void delete(K k) {
        heartBeatMap.remove(k);
    }

    /**
     * 获得指定心跳
     *
     * @param k 键名
     * @return
     */
    public E getHeartBeat(K k) {
        if (k == null) {
            throw new HeartBeatException("键名不能为空");
        }
        if (isTimeout(k)) {
            delete(k);
            return null;
        }
        // 返回心跳元素

        return heartBeatMap.get(k).getElement();
    }

    /**
     * 刷新所有心跳
     */
    public void refreshAll() {
        for (Map.Entry<K, HeartBeat<E>> entry : heartBeatMap.entrySet()) {
            refresh(entry.getKey());
        }
    }
}

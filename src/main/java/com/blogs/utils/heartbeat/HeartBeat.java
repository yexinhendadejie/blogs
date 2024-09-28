package com.blogs.utils.heartbeat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代表一个心跳的模型类,包含心跳对应的元素和最后心跳时间
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeartBeat<E> {

    private E element;
    private long lastHeartBeatTime;

}

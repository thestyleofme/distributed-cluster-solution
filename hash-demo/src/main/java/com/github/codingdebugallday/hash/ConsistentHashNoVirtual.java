package com.github.codingdebugallday.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>
 * 一致性Hash不含虚拟节点
 * </p>
 *
 * @author isaac 2020/10/11 1:06
 * @since 1.0.0
 */
public class ConsistentHashNoVirtual {

    public static void main(String[] args) {
        // 1. 初始化：把服务器节点ip的哈希值对应到哈希环上
        // 定义服务器ip
        String[] servers = new String[]{"123.111.0.3", "123.101.3.4", "111.20.35.5", "123.98.26.6"};
        SortedMap<Integer, String> hashServerMap = new TreeMap<>();
        for (String server : servers) {
            // 储存hash值与ip的对应关系
            int serverHash = Math.abs(server.hashCode());
            hashServerMap.put(serverHash, server);
        }
        // 2. 针对客户端ip求出哈希值
        String[] clients = {"126.12.17.18", "10.78.15.2", "113.25.68.4"};
        for (String client : clients) {
            int clientHash = Math.abs(client.hashCode());
            // 3. 针对客户端，找到能够处理当前客户端请求的服务器（哈希环顺时针最近）
            SortedMap<Integer, String> tailMap = hashServerMap.tailMap(clientHash);
            Integer index;
            if (tailMap.isEmpty()) {
                index = hashServerMap.firstKey();
            } else {
                index = tailMap.firstKey();
            }
            System.out.println("客户端：" + client + " 被路由到服务器ip为：" + hashServerMap.get(index));
        }
    }
}

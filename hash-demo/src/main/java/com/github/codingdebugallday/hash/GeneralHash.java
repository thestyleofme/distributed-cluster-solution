package com.github.codingdebugallday.hash;

/**
 * <p>
 * 普通Hash算法实现
 * </p>
 *
 * @author isaac 2020/10/10 1:06
 * @since 1.0.0
 */
public class GeneralHash {

    public static void main(String[] args) {
        // 定义客户端IP
        String[] clients = {"126.12.17.18", "10.78.15.2", "113.25.68.4"};
        // 定义服务器集群数量 编号对应 0 1 2
        // int serverCount = 3;
        int serverCount = 2;
        // ip路由到哪台服务器的计算
        // hash(ip)%node_count=index
        // 根据index锁定应该路由到服务器
        for (String client : clients) {
            int hash = Math.abs(client.hashCode());
            int index = hash % serverCount;
            System.out.println("客户端：" + client + " 被路由到服务器编号为：" + index);
        }
    }
}

package com.github.codingdebugallday.global;

import redis.clients.jedis.Jedis;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/12 1:05
 * @since 1.0.0
 */
public class RedisGenerator {

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            Long id = jedis.incr("id");
            System.out.println("从redis中获取的分布式id为：" + id);
        }
    }
}

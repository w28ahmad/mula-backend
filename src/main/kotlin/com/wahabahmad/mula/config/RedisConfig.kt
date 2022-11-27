package com.wahabahmad.mula.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.Jedis

@Configuration
class RedisConfig(
    private val environment: Environment
) {
    @Bean
    fun redisConnection() : Jedis =
        Jedis(
            HostAndPort(
                environment.getProperty("REDIS_HOST"),
                environment.getProperty("REDIS_PORT")?.toInt() ?: 6379
            )
        )
}
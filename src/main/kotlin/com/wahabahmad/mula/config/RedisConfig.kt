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
    fun redisConnection(): Jedis {
        val host = System.getProperty("REDIS_SERVICE_HOST") ?: environment.getProperty("REDIS_SERVICE_HOST")
        val port = System.getProperty("REDIS_SERVICE_PORT")?.toInt() ?: environment.getProperty("REDIS_SERVICE_PORT")
            ?.toInt() ?: 6379
        println("Connecting to HOST=${host} on PORT=${port}")
        return Jedis(HostAndPort(host, port))
    }
}
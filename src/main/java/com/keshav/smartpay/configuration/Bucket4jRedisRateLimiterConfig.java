package com.keshav.smartpay.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class Bucket4jRedisRateLimiterConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    @Bean
    public RedisClient redisClient() {
        return RedisClient.create(RedisURI.builder()
                .withHost(redisHost)
                .withPort(redisPort)
                .build());
    }
    @Bean
    public ProxyManager<String> lettuceBasedProxyManager(RedisClient redisClient) {
        StatefulRedisConnection<String,byte[]> redisConnection = redisClient
                .connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));
        return Bucket4jLettuce.casBasedBuilder(redisConnection)
                .expirationAfterWrite(ExpirationAfterWriteStrategy
                        .basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(10)))
                .build();
    }
    @Bean Supplier<BucketConfiguration> bucketConfigurationSupplier(){
        return ()-> BucketConfiguration.builder()
                .addLimit(Bandwidth.builder()
                        .capacity(1)
                        .refillGreedy(1, Duration.ofSeconds(30))
                        .build())
                .build();
    }

}


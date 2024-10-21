package io.github.yienruuuuu.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.yienruuuuu.bean.entity.Bot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Configuration
public class CacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(100, TimeUnit.MINUTES) // 設定緩存時間
                .maximumSize(100) // 最大緩存大小
                .recordStats(); // 記錄緩存統計
    }

    @Bean
    public Cache<String, Bot> botCache(Caffeine<Object, Object> caffeine) {
        return caffeine.build();
    }
}

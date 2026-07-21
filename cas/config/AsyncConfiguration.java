package com.itechro.cas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 *
 * @author tharushi
 */
@Configuration
public class AsyncConfiguration {

    @Value("${apps.core.pool.size}")
    private Integer corePoolSize;

    @Value("${apps.max.pool.size}")
    private Integer maxPoolSize;

    @Value("${apps.queue.capacity}")
    private Integer queueCapacity;

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(10);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // refer https://www.baeldung.com/java-rejectedexecutionhandler for reject handling
        executor.setThreadNamePrefix("Async Executor -");
//    executor.initialize();

        return executor;
    }
}

package com.itechro.cas.config;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Global asynchronous configuration
 *
 * @author : chamara
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

	@Value("${apps.core.pool.size}")
	private Integer corePoolSize;

	@Value("${apps.max.pool.size}")
	private Integer maxPoolSize;

	@Value("${apps.queue.capacity}")
	private Integer queueCapacity;

	private static final Logger LOG = LoggerFactory.getLogger(AsyncConfig.class);

	@Override
	public Executor getAsyncExecutor() {
		LOG.info("START : Thread pool executor initialization");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("CAS-");
		executor.initialize();

		LOG.info("END : Thread pool executor initialization");
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (throwable, method, objects) -> LOG.error("Exception occured in asynchronous operation : {} : {}",
			throwable, Pair.of(method.getName(), objects));
	}
}

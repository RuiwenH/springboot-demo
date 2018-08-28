package com.reven.task;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author reven
 */
@Configuration
@EnableScheduling
public class SchedulingConfiguration {
    // 给sched用的，默认只有1个，时间太长会阻塞
    // 多个@Scheduled可以并发执行了，最高并发度是3，但是同一个@Schedule不会并发执行
    @Bean(destroyMethod = "shutdown")
    public Executor taskScheduler() {
        // 数量跟Scheduled要多一些
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(10);
        executor.initialize();
        return executor;
    }
}
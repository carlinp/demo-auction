package software.jevera.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Slf4j
@Configuration
public class SchedulerConfig {

    @Bean
    public TaskScheduler taskScheduler(@Value("${scheduler.thread.count}") int threadCount) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        log.info("Thread count {}", threadCount);
        taskScheduler.setPoolSize(threadCount);
        return taskScheduler;
    }
}

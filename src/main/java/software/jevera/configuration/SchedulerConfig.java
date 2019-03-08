package software.jevera.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import software.jevera.service.ScheduleExecutor;
import software.jevera.service.ScheduleExecutorThreadImpl;

@Configuration
public class SchedulerConfig {

    @Bean
    public ScheduleExecutor scheduleExecutor(@Value("${scheduler.thread.count}") int threadCount) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(threadCount);
        return new ScheduleExecutorThreadImpl(taskScheduler);
    }
}

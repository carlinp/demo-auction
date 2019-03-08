package software.jevera.service;

import static java.time.Instant.now;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@RequiredArgsConstructor
public class ScheduleExecutorThreadImpl implements ScheduleExecutor {

    private final TaskScheduler taskScheduler;

    @Override
    public void scheduleFinish(Long id, Instant finishDate, Consumer<Long> handler) {
        taskScheduler.schedule(() -> handler.accept(id), finishDate);
    }
}

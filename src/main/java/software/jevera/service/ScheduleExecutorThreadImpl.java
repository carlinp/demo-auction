package software.jevera.service;

import static java.time.Instant.now;

import java.time.Instant;
import java.util.function.Consumer;

public class ScheduleExecutorThreadImpl implements ScheduleExecutor {
    @Override
    public void scheduleFinish(Long id, Instant finishDate, Consumer<Long> handler) {
        new Thread(() -> {
            try {
                Thread.sleep(finishDate.toEpochMilli() - now().toEpochMilli());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            handler.accept(id);
        }).start();

    }
}

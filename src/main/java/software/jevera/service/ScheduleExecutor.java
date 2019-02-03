package software.jevera.service;

import java.time.Instant;
import java.util.function.Consumer;
import software.jevera.domain.Product;

public interface ScheduleExecutor {
    void scheduleFinish(Long id, Instant finishDate, Consumer<Long> handler);
}

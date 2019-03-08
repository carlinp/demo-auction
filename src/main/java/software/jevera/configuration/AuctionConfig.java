package software.jevera.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TaskScheduler;
import software.jevera.dao.CommentRepository;
import software.jevera.dao.ProductRepository;
import software.jevera.dao.UserRepository;
import software.jevera.dao.inmemory.CommentInMemoryRepository;
import software.jevera.dao.inmemory.ProductInMemoryRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;
import software.jevera.service.ProductService;
import software.jevera.service.ScheduleExecutor;
import software.jevera.service.ScheduleExecutorThreadImpl;
import software.jevera.service.product.Archived;
import software.jevera.service.product.Deleted;
import software.jevera.service.product.Finished;
import software.jevera.service.product.New;
import software.jevera.service.product.ProductState;
import software.jevera.service.product.Published;
import software.jevera.service.product.StateMachine;

@ComponentScan("software.jevera")
@Configuration
public class AuctionConfig {

}

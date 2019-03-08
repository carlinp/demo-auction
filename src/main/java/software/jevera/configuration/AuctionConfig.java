package software.jevera.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
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

@Configuration
@Import(SchedulerConfig.class)
@EnablePropertyPlaceholder
public class AuctionConfig {

    @Bean
    public ProductRepository productRepository() {
        return new ProductInMemoryRepository();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserInMemoryRepository();
    }

    @Bean
    public CommentRepository commentRepository() {
        return new CommentInMemoryRepository();
    }

    @Bean
    public Archived archived() {
        return new Archived();
    }

    @Bean
    public Deleted deleted() {
        return new Deleted();
    }

    @Bean
    public Finished finished() {
        return new Finished();
    }

    @Bean
    public New newState() {
        return new New();
    }

    @Bean
    public Published published() {
        return new Published();
    }

    @Bean
    public StateMachine stateMachine(List<ProductState> productStateList) {
        return new StateMachine(productStateList);
    }

    @Bean
    public ProductService productService(ProductRepository productRepository, StateMachine stateMachine,
                                         ScheduleExecutor scheduleExecutor) {
        return new ProductService(productRepository, stateMachine, scheduleExecutor);
    }



}

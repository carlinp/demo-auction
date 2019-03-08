package software.jevera.configuration;

import java.util.List;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import software.jevera.dao.CommentRepository;
import software.jevera.dao.ProductRepository;
import software.jevera.dao.UserRepository;
import software.jevera.dao.inmemory.CommentInMemoryRepository;
import software.jevera.dao.inmemory.ProductInMemoryRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;
import software.jevera.domain.Product;
import software.jevera.service.ProductService;
import software.jevera.service.product.Archived;
import software.jevera.service.product.Deleted;
import software.jevera.service.product.Finished;
import software.jevera.service.product.New;
import software.jevera.service.product.ProductState;
import software.jevera.service.product.Published;
import software.jevera.service.product.StateMachine;

@Configuration
public class AuctionConfig {

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource("application.properties"));
        return propertyPlaceholderConfigurer;
    }

    public ProductRepository productRepository() {
        return new ProductInMemoryRepository();
    }

    public UserRepository userRepository() {
        return new UserInMemoryRepository();
    }

    public CommentRepository commentRepository() {
        return new CommentInMemoryRepository();
    }

    public Archived archived() {
        return new Archived();
    }

    public Deleted deleted() {
        return new Deleted();
    }

    public Finished finished() {
        return new Finished();
    }

    public New newState() {
        return new New();
    }

    public Published published() {
        return new Published();
    }

    public StateMachine stateMachine(List<ProductState> productStateList) {
        return new StateMachine(productStateList);
    }

    public ProductService productService() {

    }


}

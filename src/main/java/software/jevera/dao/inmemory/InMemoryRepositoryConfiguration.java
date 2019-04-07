package software.jevera.dao.inmemory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "application.datamode", havingValue = "inmemory")
public class InMemoryRepositoryConfiguration {

    @Bean
    public CommentInMemoryRepository commentInMemoryRepository() {
        return new CommentInMemoryRepository();
    }

    @Bean
    public ProductInMemoryRepository productInMemoryRepository() {
        return new ProductInMemoryRepository();
    }

    @Bean
    public UserInMemoryRepository userInMemoryRepository() {
        return new UserInMemoryRepository();
    }

}

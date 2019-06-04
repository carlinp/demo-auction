package software.jevera.dao.jpa;

import javax.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.jevera.dao.CommentRepository;
import software.jevera.dao.ProductRepository;
import software.jevera.dao.inmemory.ProductInMemoryRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;

@Configuration
@ConditionalOnProperty(value = "application.datamode", havingValue = "jpa")
public class JpaRepositoryConfiguration {

    @Bean
    public CommentRepository jdbcCommentRepository(EntityManagerFactory entityManagerFactory) {
        return new JpaCommentRepository(entityManagerFactory);
    }

    @Bean
    public ProductRepository productInMemoryRepository(EntityManagerFactory entityManagerFactory) {
        return new JpaProductRepository(entityManagerFactory);
    }

    @Bean
    public UserInMemoryRepository userInMemoryRepository() {
        return new UserInMemoryRepository();
    }

}

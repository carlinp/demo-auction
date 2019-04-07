package software.jevera.dao.jdbc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.jevera.dao.ProductRepository;
import software.jevera.dao.inmemory.CommentInMemoryRepository;
import software.jevera.dao.inmemory.ProductInMemoryRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;

@Configuration
@ConditionalOnProperty(value = "application.datamode", havingValue = "jdbc")
public class JdbcRepositoryConfiguration {

    @Bean
    public JdbcCommentRepository jdbcCommentRepository(ConnectionManager connectionManager, ProductRepository productRepository) {
        return new JdbcCommentRepository(connectionManager, productRepository);
    }

    @Bean
    public JdbcProductRepository productInMemoryRepository(ConnectionManager connectionManager) {
        return new JdbcProductRepository(connectionManager);
    }

    @Bean
    public UserInMemoryRepository userInMemoryRepository() {
        return new UserInMemoryRepository();
    }

}

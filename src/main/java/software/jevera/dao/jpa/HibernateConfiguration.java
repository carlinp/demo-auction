package software.jevera.dao.jpa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.jevera.domain.Bid;
import software.jevera.domain.Comment;
import software.jevera.domain.Product;
import software.jevera.domain.User;

@Configuration
@ConditionalOnProperty(value = "application.datamode", havingValue = "jpa")
public class HibernateConfiguration {

    @Bean
    public EntityManagerFactory createEntityManagerFactory(DataSourceProperties dataSourceProperties) {

        Map<String, Object> properties = new HashMap<>();
        properties.put(AvailableSettings.LOADED_CLASSES, Arrays.asList(Bid.class,
                                                   Comment.class,
                                                   Product.class,
                                                   User.class));
        properties.put(AvailableSettings.JPA_JDBC_DRIVER, dataSourceProperties.getDriverClassName());
        properties.put(AvailableSettings.URL, dataSourceProperties.getUrl());
        properties.put(AvailableSettings.USER, dataSourceProperties.getUsername());
        properties.put(AvailableSettings.PASS, dataSourceProperties.getPassword());
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, );

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("auction", properties);
        return entityManagerFactory;
    }

}

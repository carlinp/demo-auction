package software.jevera.dao;

import java.util.List;
import java.util.Optional;
import software.jevera.domain.Product;
import software.jevera.domain.User;

public interface ProductRepository {

    Product save(Product product);

    List<Product> findAll();

    List<Product> findByUser(User user);

    Optional<Product> findById(Long id);
}

package software.jevera.service;

import java.util.List;
import software.jevera.domain.Product;
import software.jevera.domain.User;

public interface ProductService {

    Product createProduct(Product product, User user);

    List<Product> getAllProducts();

    List<Product> getUserProducts(User user);

    void publish(Long id);

    void delete(Long id);

    void finish(Long id);

    void addBid(Long id, Integer amount, User user);
}

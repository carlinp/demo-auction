package software.jevera.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Bid;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.exceptions.BusinessException;
import software.jevera.exceptions.EntityNotFound;
import software.jevera.service.product.StateMachine;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private StateMachine stateMachine;
    @Autowired
    private ScheduleExecutor scheduleExecutor;

    public ProductService() {
    }

    @Autowired
    public ProductService(ProductRepository productRepository, StateMachine stateMachine) {
        this.productRepository = productRepository;
        this.stateMachine = stateMachine;
    }

    public Product createProduct(Product product, User user) {
        assertIsNull(product.getId(), "Id already exists.");
        product.setOwner(user);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public List<Product> getUserProducts(User user) {
        return this.productRepository.findByUser(user);
    }

    @SneakyThrows
    public void publish(Long id, User user) {
        Product product = getProduct(id);
        if (!product.getOwner().equals(user)) {
            throw new AccessDeniedException(user.getLogin());
        }
        stateMachine.publish(product);
        scheduleExecutor.scheduleFinish(id, product.getFinishDate(), this::finish);
        productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = getProduct(id);;
        stateMachine.delete(product);
        productRepository.save(product);
    }

    public void finish(Long id) {
        Product product = getProduct(id);;
        stateMachine.finish(product);
        productRepository.save(product);
    }

    public void addBid(Long id, Integer amount, User user) {
        Product product = getProduct(id);;
        stateMachine.addBid(product, new Bid(amount, product, user));
        productRepository.save(product);
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(EntityNotFound::new);
    }

    private void assertIsNull(Long id, String message) {
        if (id != null) {
            throw new BusinessException(message);
        }
    }

    public List<Product> getAllProducts(Optional<Integer> maxPrice) {
        if (maxPrice.isPresent()) {
            return productRepository.findByMaxPrice(maxPrice.get());
        } else {
            return productRepository.findAll();
        }
    }
}

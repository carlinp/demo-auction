package software.jevera.service;

import static software.jevera.service.product.Event.DELETE;
import static software.jevera.service.product.Event.FINISH;
import static software.jevera.service.product.Event.PUBLISH;

import java.util.List;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Bid;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.exceptions.AmountOfBidUncorrect;
import software.jevera.exceptions.BussinesException;
import software.jevera.exceptions.EntityNotFound;
import software.jevera.service.product.StateMachine;

public class ProductService {

    private final ProductRepository productRepository;
    private final StateMachine stateMachine;
    private final ScheduleExecutor scheduleExecutor;

    public ProductService(ProductRepository productRepository, StateMachine stateMachine,
                          ScheduleExecutor scheduleExecutor) {
        this.productRepository = productRepository;
        this.stateMachine = stateMachine;
        this.scheduleExecutor = scheduleExecutor;
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

    public void publish(Long id) {
        Product product = getProduct(id);
        stateMachine.onEvent(product, PUBLISH);
        scheduleExecutor.scheduleFinish(id, product.getFinishDate(), this::finish);
    }

    public void delete(Long id) {
        Product product = getProduct(id);;
        stateMachine.onEvent(product, DELETE);
    }

    public void finish(Long id) {
        Product product = getProduct(id);;
        stateMachine.onEvent(product, FINISH);
    }

    public void addBid(Long id, Integer amount, User user) {
        Product product = getProduct(id);;
        if (product.getMaxBid().filter(bid -> bid.getAmount() >= amount).isPresent()) {
            throw new AmountOfBidUncorrect();
        }
        product.addBid(new Bid(amount, product, user));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(EntityNotFound::new);
    }

    private void assertIsNull(Long id, String message) {
        if (id != null) {
            throw new BussinesException(message);
        }
    }
}

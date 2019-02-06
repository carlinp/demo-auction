package software.jevera.service.product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import software.jevera.domain.Bid;
import software.jevera.domain.Product;

public class StateMachine {

    private final Map<ProductStateEnum, ProductState> states = new ConcurrentHashMap<>();

    public StateMachine(List<ProductState> productStatus) {
        productStatus.forEach(status -> states.put(status.getStatusName(), status));
    }

    private ProductState getState(Product product) {
        return states.get(product.getStatus());
    }

    public void publish(Product product) {
        getState(product).publish(product);
    }

    public void delete(Product product) {
        getState(product).delete(product);
    }

    public void finish(Product product) {
        getState(product).delete(product);
    }

    public void addBid(Product product, Bid bid) {
        getState(product).addBid(product, bid);
    }
}

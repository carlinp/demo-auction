package software.jevera.service.product;

import software.jevera.domain.Bid;
import software.jevera.domain.Product;
import software.jevera.exceptions.StateTransitionException;

public abstract class ProductState {

    private void noStateTransition(Product product) {
        throw new StateTransitionException("Illegal state transition " + product);
    }

    public abstract ProductStateEnum getStatusName();

    public void publish(Product product) {
        noStateTransition(product);
    }

    public void finish(Product product) {
        noStateTransition(product);
    }

    public void delete(Product product) {
        noStateTransition(product);
    }

    public void addBid(Product product, Bid bid) {
        throw new IllegalStateException(getStatusName().name());
    }
}

package software.jevera.service.product;

import static software.jevera.service.product.Event.DELETE;
import static software.jevera.service.product.Event.FINISH;
import static software.jevera.service.product.ProductStatus.ARCHIVED;
import static software.jevera.service.product.ProductStatus.FINISHED;
import static software.jevera.service.product.ProductStatus.PUBLISHED;

import software.jevera.domain.Bid;
import software.jevera.domain.Product;

public class Published {
    public Published(StateMachine transitions) {
        transitions.from(PUBLISHED)
                .on(FINISH).transitTo(FINISHED, this::finished)
                .on(DELETE).transitTo(ARCHIVED);
    }

    private void finished(Product product, ProductStatus productStatus) {
        product.getMaxBid().map(Bid::getUser).ifPresent(product::setBuyer);
        product.setStatus(productStatus);
    }
}

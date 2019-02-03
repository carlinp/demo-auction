package software.jevera.service.product;

import static software.jevera.service.product.Event.DELETE;
import static software.jevera.service.product.Event.PUBLISH;
import static software.jevera.service.product.ProductStatus.DELETED;
import static software.jevera.service.product.ProductStatus.NEW;
import static software.jevera.service.product.ProductStatus.PUBLISHED;

import software.jevera.dao.ProductRepository;
import software.jevera.domain.Product;

public class New {
    public New(StateMachine transitions) {
        transitions.from(NEW)
                .on(PUBLISH).transitTo(PUBLISHED)
                .on(DELETE).transitTo(DELETED);
    }
}

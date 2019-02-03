package software.jevera.service.product;

import static software.jevera.service.product.Event.FINISH;
import static software.jevera.service.product.ProductStatus.ARCHIVED;
import static software.jevera.service.product.ProductStatus.FINISHED;

public class Archived {
    public Archived(StateMachine transition) {
        transition.from(ARCHIVED).on(FINISH).transitTo(FINISHED);
    }
}

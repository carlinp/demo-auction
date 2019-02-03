package software.jevera.service.product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import software.jevera.domain.Product;
import software.jevera.exceptions.StateTransitionException;

public class StateMachine {
    private final Map<ProductStatus, Map<Event, Transition>> transitions = new ConcurrentHashMap<>();

    public StateMachine() {}

    public void onEvent(Product product, Event event) {
        ProductStatus status = product.getStatus();
        Transition transition = transitions.get(status).get(event);
        if (transition != null) {
            transition.action.accept(product, transition.to);
        } else {
            String message = String.format("Can not process event %s in state %s for  %s", event, status, product.getId());
            throw new StateTransitionException(message);
        }
    }

    public EventBuilder from(ProductStatus state) {
        transitions.put(state, new ConcurrentHashMap<>());
        return new EventBuilder(state);
    }

    public class EventBuilder {
        private final ProductStatus status;

        public EventBuilder(ProductStatus status) {
            this.status = status;
        }

        public TransitionBuilder on(Event event) {
            return new TransitionBuilder(event);
        }

        public class TransitionBuilder {
            private final Event event;

            public TransitionBuilder(Event event) {
                this.event = event;
            }

            public EventBuilder transitTo(ProductStatus target) {
                transitTo(target, Product::setStatus);
                return EventBuilder.this;
            }

            public EventBuilder transitTo(ProductStatus target, BiConsumer<Product, ProductStatus> action) {
                transitions.get(status).put(event, new Transition(target, action));
                return EventBuilder.this;
            }
        }
    }

    private static class Transition {
        private final ProductStatus to;
        private final BiConsumer<Product, ProductStatus> action;
        private Transition(ProductStatus to, BiConsumer<Product, ProductStatus> action) {
            this.to = to;
            this.action = action;
        }
    }
}

package software.jevera.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bid {
    private Long id;
    private Integer amount;
    private Product product;
    private User user;

    public Bid() {
    }

    public Bid(Long id, Integer amount, Product product, User user) {
        this.id = id;
        this.amount = amount;
        this.product = product;
        this.user = user;
    }

    public Bid(Integer amount, Product product, User user) {
        this.amount = amount;
        this.product = product;
        this.user = user;
    }
}

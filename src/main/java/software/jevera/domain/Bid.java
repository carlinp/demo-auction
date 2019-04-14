package software.jevera.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer amount;
    @ManyToOne
    private Product product;
    @ManyToOne
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

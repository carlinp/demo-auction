package software.jevera.domain;

import static java.util.Comparator.comparing;
import static software.jevera.service.product.ProductStateEnum.NEW;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.jevera.service.product.ProductStateEnum;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany
    private List<Comment> comments = new ArrayList<>();
    @ManyToOne
    private User owner;
    private Integer startPrice;
    @OneToMany
    private List<Bid> bids = new ArrayList<>();
    private Instant finishDate;
    @Enumerated(EnumType.STRING)
    private ProductStateEnum status = NEW;
    @ManyToOne
    private User buyer;

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public List<Bid> getBids() {
        return Collections.unmodifiableList(bids);
    }

    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setProduct(this);
    }

    public Optional<Bid> getMaxBid() {
        return bids.stream().max(comparing(Bid::getAmount));
    }

}

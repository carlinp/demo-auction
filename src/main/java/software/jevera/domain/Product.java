package software.jevera.domain;

import static java.util.Comparator.comparing;
import static software.jevera.service.product.ProductStateEnum.NEW;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import software.jevera.service.product.ProductStateEnum;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Product {
    private Long id;
    private String name;
    private String description;
    private List<Comment> comments = new ArrayList<>();
    private User owner;
    private Integer startPrice;
    private List<Bid> bids = new ArrayList<>();
    private Instant finishDate;
    private ProductStateEnum status = NEW;
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

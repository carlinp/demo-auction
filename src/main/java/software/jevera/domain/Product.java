package software.jevera.domain;

import static java.util.Comparator.comparing;
import static software.jevera.service.product.ProductStateEnum.NEW;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import software.jevera.service.product.ProductStateEnum;

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

    public Product() {
    }

    public Product(String name, String description, User owner, Integer price, Instant finishDate) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.startPrice = price;
        this.finishDate = finishDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Integer startPrice) {
        this.startPrice = startPrice;
    }

    public List<Bid> getBids() {
        return Collections.unmodifiableList(bids);
    }

    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setProduct(this);
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Instant getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Instant finishDate) {
        this.finishDate = finishDate;
    }

    public ProductStateEnum getStatus() {
        return status;
    }

    public void setStatus(ProductStateEnum status) {
        this.status = status;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Optional<Bid> getMaxBid() {
        return bids.stream().max(comparing(Bid::getAmount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package software.jevera.domain;

public class Bid {
    private Integer amount;
    private Product product;
    private User user;

    public Bid(Integer amount, Product product, User user) {
        this.amount = amount;
        this.product = product;
        this.user = user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package software.jevera.domain;

public class Comment {

    private Long id;
    private String text;
    private User author;
    private Product product;

    public Comment() {
    }

    public Comment(String text, User author, Product product) {
        this.text = text;
        this.author = author;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

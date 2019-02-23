package software.jevera.dao.inmemory;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;
import org.junit.Test;
import software.jevera.domain.Bid;
import software.jevera.domain.Product;
import software.jevera.domain.User;

public class ProductInMemoryRepositoryIntTest {

    private ProductInMemoryRepository repository = new ProductInMemoryRepository();

    @Test
    public void save() {
        Product product = createProduct("Name", 123, "owner");
        Product saved = repository.save(product);
        Optional<Product> byId = repository.findById(saved.getId());
        assertEquals(byId.get(), product);
    }

    @Test
    public void saveBid() {
        Product product = createProduct("Name", 123, "owner");
        product.addBid(new Bid(567, product, new User("byer", "byer")));
        Product saved = repository.save(product);
        Optional<Product> byId = repository.findById(saved.getId());
        assertEquals(byId.get().getBids(), product.getBids());
    }

    private Product createProduct(String name, int startPrice, String owner) {
        Product product = new Product();
        product.setName(name);
        product.setStartPrice(startPrice);
        product.setOwner(new User(owner, "p"));
        return product;
    }

    @Test
    public void findByUser() {
        List<Product> products = asList(
                createProduct("1", 1, "user1"),
                createProduct("2", 2, "user2"),
                createProduct("3", 2, "user1")
                                       );

        products.forEach(repository::save);

        List<Product> expected = asList(
                createProduct("1", 1, "user1").setId(1L),
                createProduct("3", 2, "user1").setId(3L)
                                       );

        assertEquals(expected, repository.findByUser(new User("user1", "P")));

    }

}

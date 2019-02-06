package software.jevera.dao.inmemory;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Product;
import software.jevera.domain.User;

public class ProductInMemoryRepository implements ProductRepository {

    private Set<Product> products = new HashSet<>();

    private AtomicLong counter = new AtomicLong(0);

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(counter.incrementAndGet());
        }
        products.add(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public List<Product> findByUser(User user) {
        return products.stream().filter(product -> product.getOwner().equals(user)).collect(toList());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findAny();
    }
}

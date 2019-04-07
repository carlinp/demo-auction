package software.jevera.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Bid;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.service.product.ProductStateEnum;

@RequiredArgsConstructor
public class JdbcProductRepository implements ProductRepository {

    private final ConnectionManager connectionManager;

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public List<Product> findByUser(User user) {
        return null;
    }

    @Override
    @SneakyThrows
    public Optional<Product> findById(Long id) {
        try (Connection connection = connectionManager.createConnection()) {
            PreparedStatement productStatement = connection.prepareStatement(
                    "SELECT *, owner.login as ownerlogin, owner.password_hash as ownerpassword_hash," +
                    "  buyer.login as buyerlogin, buyer.password_hash as buyerpassword_hash" +
                    " FROM product" +
                    " inner join user AS owner on owner.login = product.owner_login" +
                    " left join user AS buyer ON buyer.login = product.buyer_login" +
                    " WHERE id = ?");
            productStatement.setLong(1, id);
            ResultSet productResultSet = productStatement.executeQuery();
            while (productResultSet.next()) {
                Product product = new Product();
                product.setId(productResultSet.getLong("product.id"));
                product.setName(productResultSet.getString("product.name"));
                product.setDescription(productResultSet.getString("product.description"));
                product.setStatus(ProductStateEnum.valueOf(productResultSet.getString("product.status")));
                product.setBids(getBidsByProductId(id, connection));
                product.setOwner(new User(productResultSet.getString("ownerlogin"),
                                          productResultSet.getString("ownerpassword_hash")));
                if (productResultSet.getString("buyerlogin") != null) {
                    product.setBuyer(new User(productResultSet.getString("buyerlogin"),
                                              productResultSet.getString("buyerpassword_hash")));
                }
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    private List<Bid> getBidsByProductId(Long id, Connection connection) {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM bid WHERE product_id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        List<Bid> bids = new ArrayList<>();
        while (resultSet.next()) {
            Bid bid = new Bid();
            bid.setId(resultSet.getLong("id"));
            bid.setAmount(resultSet.getInt("amount"));
            bids.add(bid);
        }
        return bids;
    }

    @Override
    public List<Product> findByMaxPrice(Integer maxPrice) {
        return null;
    }
}

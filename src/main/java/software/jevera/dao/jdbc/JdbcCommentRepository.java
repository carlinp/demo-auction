package software.jevera.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import software.jevera.dao.CommentRepository;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Comment;
import software.jevera.domain.Product;
import software.jevera.domain.User;

@Slf4j
@RequiredArgsConstructor
public class JdbcCommentRepository implements CommentRepository {

    private final ConnectionManager connectionManager;
    private final ProductRepository productRepository;

    @Override
    @SneakyThrows
    public Comment save(Comment comment) {
        try (Connection connection = connectionManager.createConnection()) {
            connection.setAutoCommit(false);

            try {
                if (comment.getId() == null) {
                    try (Statement statement = connection.createStatement()) {

                        // bad example
                        statement.executeUpdate(String.format(
                                "INSERT INTO comment (text, author_login, product_id) VALUES ('%s', '%s', %s);", comment.getText(), comment.getAuthor().getLogin(), comment.getProduct().getId()),
                                                Statement.RETURN_GENERATED_KEYS);
                        if (statement.getGeneratedKeys().next()) {
                            comment.setId(statement.getGeneratedKeys().getLong("id"));
                        }
                    }
                } else {
                    try (PreparedStatement statement = connection.prepareStatement("UPDATE comment SET text=? WHERE id=?")) {
                        statement.setString(1, comment.getText());
                        statement.setLong(2, comment.getId());
                        statement.executeUpdate();
                    }
                }

                connection.commit();
            }  catch (Exception e) {
                log.error("Error safe product {}", e);
                connection.rollback();
                throw e;
            }
        }
        return comment;
    }

    @Override
    @SneakyThrows
    public List<Comment> findByProductId(Long productId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = connectionManager.createConnection()) {
            Optional<Product> maybeProduct = productRepository.findById(productId);

             try (PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM comment INNER JOIN user author ON author.login = comment.author_login WHERE product_id = ?")) {
                 statement.setLong(1, productId);

                 comments.addAll(mapComments(statement, maybeProduct.get()));
             }
        }
        return comments;
    }

    private List<Comment> mapComments(PreparedStatement statement, Product product) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getLong("comment.id"));
                comment.setText(resultSet.getString("comment.text"));
                comment.setProduct(product);
                comment.setAuthor(new User(resultSet.getString("user.login"), resultSet.getString("user.password_hash")));
                comments.add(comment);
            }
        }
        return comments;
    }

    @Override
    public List<Comment> findByUser(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

package software.jevera.dao.jpa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
public class JpaCommentRepository implements CommentRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Comment save(Comment comment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (comment.getId() == null) {
                entityManager.persist(comment);
            } else {
                entityManager.merge(comment);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return comment;
    }

    @Override
    public List<Comment> findByProductId(Long productId) {
        return null;
    }

    @Override
    public List<Comment> findByUser(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

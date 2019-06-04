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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import software.jevera.dao.CommentRepository;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Comment;
import software.jevera.domain.Comment_;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.domain.User_;

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
                comment = entityManager.merge(comment);
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return comment;
    }

    @Override
    public List<Comment> findByProductId(Long productId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            List comments = entityManager.createQuery("SELECT c FROM Comment c WHERE c.product.id = " + productId)
                    .getResultList();
            entityManager.getTransaction().commit();
            return comments;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Comment> findByUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
            Root<Comment> root = query.from(Comment.class);
            query = query.select(root)
                         .where(
                                 cb.equal(root.get(Comment_.author).get(User_.login), user.getLogin())
                               );

            entityManager.getTransaction().commit();
            return entityManager.createQuery(query).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Long id) {

    }
}

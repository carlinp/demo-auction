package software.jevera.dao.jpa;

import static org.hibernate.jpa.QueryHints.HINT_LOADGRAPH;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.graph.GraphSemantic;
import software.jevera.dao.CommentRepository;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Comment;
import software.jevera.domain.Product;
import software.jevera.domain.User;

@Slf4j
@RequiredArgsConstructor
public class JpaProductRepository implements ProductRepository {

    private final EntityManagerFactory entityManagerFactory;

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
    public Optional<Product> findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Product> query = entityManager
                    .createQuery("SELECT product FROM Product product WHERE product.id = :productId", Product.class);
            query.setParameter("productId", id);
            Product product = query.getSingleResult();
            entityManager.getTransaction().commit();
            return Optional.ofNullable(product);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Product> findByMaxPrice(Integer maxPrice) {
        return null;
    }

    public Optional<Product> findByIdWithBids(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            EntityGraph<?> entityGraph = entityManager.createEntityGraph(Product.class);
            entityGraph.addAttributeNodes("bids");
            TypedQuery<Product> query = entityManager
                    .createQuery("SELECT product FROM Product product WHERE product.id = :productId", Product.class)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), entityGraph);
            query.setParameter("productId", id);
            Product product = query.getSingleResult();
            //Hibernate.initialize(product.getBids());
            entityManager.getTransaction().commit();
            return Optional.ofNullable(product);
        } finally {
            entityManager.close();
        }
    }
}

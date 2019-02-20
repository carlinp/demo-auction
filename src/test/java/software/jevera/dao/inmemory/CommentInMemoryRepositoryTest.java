package software.jevera.dao.inmemory;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;
import software.jevera.domain.Comment;
import software.jevera.domain.Product;
import software.jevera.domain.User;

public class CommentInMemoryRepositoryTest {

    private CommentInMemoryRepository repository = new CommentInMemoryRepository();

    @Test
    public void save() {
        List<Comment> comments = asList(
                new Comment("1", new User("2", "3"), new Product().setId(4L)),
                new Comment("5", new User("6", "7"), new Product().setId(8L))
                                        );
        comments.forEach(repository::save);
        assertEquals(comments, repository.findAll());
    }

    @Test
    public void findByProductId() {
        List<Comment> comments = asList(
                new Comment("1", new User("1", "1"), new Product().setId(1L)),
                new Comment("2", new User("2", "2"), new Product().setId(2L)),
                new Comment("3", new User("3", "3"), new Product().setId(2L)),
                new Comment("4", new User("4", "4"), new Product().setId(1L)),
                new Comment("5", new User("5", "5"), new Product().setId(1L)),
                new Comment("6", new User("6", "6"), new Product().setId(1L))
                                       );


        List<Comment> product1 = asList(
                new Comment(1L, "1", new User("1", "1"), new Product().setId(1L)),
                new Comment(4L, "4", new User("4", "4"), new Product().setId(1L)),
                new Comment(5L, "5", new User("5", "5"), new Product().setId(1L)),
                new Comment(6L, "6", new User("6", "6"), new Product().setId(1L))
                                       );

        List<Comment> product2 = asList(
                new Comment(2L, "2", new User("2", "2"), new Product().setId(2L)),
                new Comment(3L, "3", new User("3", "3"), new Product().setId(2L))
                                       );

        comments.forEach(repository::save);

        assertEquals(product1, repository.findByProductId(1L));
        assertEquals(product2, repository.findByProductId(2L));
    }

    @Test
    public void findByUser() {
        List<Comment> comments = asList(
                new Comment("1", new User("1", "1"), new Product().setId(1L)),
                new Comment("2", new User("2", "1"), new Product().setId(2L)),
                new Comment("3", new User("2", "1"), new Product().setId(3L)),
                new Comment("4", new User("1", "1"), new Product().setId(4L)),
                new Comment("5", new User("1", "1"), new Product().setId(5L)),
                new Comment("6", new User("1", "1"), new Product().setId(6L))
                                       );


        List<Comment> user1 = asList(
                new Comment(1L, "1", new User("1", "1"), new Product().setId(1L)),
                new Comment(4L, "4", new User("1", "1"), new Product().setId(4L)),
                new Comment(5L, "5", new User("1", "1"), new Product().setId(5L)),
                new Comment(6L, "6", new User("1", "1"), new Product().setId(6L))
                                       );

        List<Comment> user2 = asList(
                new Comment(2L, "2", new User("2", "1"), new Product().setId(2L)),
                new Comment(3L, "3", new User("2", "1"), new Product().setId(3L))
                                       );

        comments.forEach(repository::save);

        assertEquals(user1, repository.findByUser(new User("1", "1")));
        assertEquals(user2, repository.findByUser(new User("2", "1")));
    }

    @Test
    public void delete() {
        List<Comment> comments = asList(
                new Comment("1", new User("1", "1"), new Product().setId(1L)),
                new Comment("2", new User("2", "2"), new Product().setId(2L)),
                new Comment("3", new User("3", "3"), new Product().setId(2L)),
                new Comment("4", new User("4", "4"), new Product().setId(1L)),
                new Comment("5", new User("5", "5"), new Product().setId(1L)),
                new Comment("6", new User("6", "6"), new Product().setId(1L))
                                       );

        List<Comment> after = asList(
                new Comment(1L,"1", new User("1", "1"), new Product().setId(1L)),
                new Comment(3L,"3", new User("3", "3"), new Product().setId(2L)),
                new Comment(4L,"4", new User("4", "4"), new Product().setId(1L)),
                new Comment(6L,"6", new User("6", "6"), new Product().setId(1L))
                                       );

        comments.forEach(repository::save);

        repository.delete(2L);
        repository.delete(5L);

        assertEquals(after, repository.findAll());
    }
}

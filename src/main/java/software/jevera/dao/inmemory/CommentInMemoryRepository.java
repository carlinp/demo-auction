package software.jevera.dao.inmemory;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import software.jevera.dao.CommentRepository;
import software.jevera.domain.Comment;
import software.jevera.domain.User;


public class CommentInMemoryRepository implements CommentRepository {

    private List<Comment> comments = new ArrayList<>();

    private AtomicLong counter = new AtomicLong(0);

    @Override
    public Comment save(Comment comment) {
        comment.setId(counter.incrementAndGet());
        comments.add(comment);
        return comment;
    }

    @Override
    public List<Comment> findByProductId(Long productId) {
        return comments.stream().filter(comment -> comment.getProduct().getId().equals(productId)).collect(toList());
    }

    @Override
    public List<Comment> findByUser(User user) {
        return comments.stream().filter(comment -> comment.getAuthor().equals(user)).collect(toList());
    }

    @Override
    public void delete(Long id) {
        comments.removeIf(comment -> comment.getId().equals(id));
    }

    public List<Comment> findAll() {
        return new ArrayList<>(comments);
    }
}

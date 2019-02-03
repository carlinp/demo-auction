package software.jevera.dao;

import java.util.List;
import software.jevera.domain.Comment;
import software.jevera.domain.User;

public interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> findByProductId(Long productId);

    List<Comment> findByUser(User user);

    void delete(Long id);
}

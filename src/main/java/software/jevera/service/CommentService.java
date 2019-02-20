package software.jevera.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import software.jevera.dao.CommentRepository;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Comment;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.exceptions.EntityNotFound;

@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    public Comment addComment(Long id, String text, User user) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFound::new);
        Comment comment = new Comment(text, user, product);
        return commentRepository.save(comment);
    }

    public List<Comment> getProductComments(Long productId) {
        return commentRepository.findByProductId(productId);
    }

    public List<Comment> getUserComments(User user) {
        return commentRepository.findByUser(user);
    }

    public void delete(Long id) {
        commentRepository.delete(id);
    }

}

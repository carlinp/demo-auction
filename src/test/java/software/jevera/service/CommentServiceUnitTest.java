package software.jevera.service;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import software.jevera.dao.CommentRepository;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Comment;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.exceptions.EntityNotFound;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceUnitTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ProductRepository productRepository;

    @Test
    public void addComment() {
        Product mockProduct = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        User mockUser = new User("mocklogin", "mockpassword");
        commentService.addComment(1L, "testComment", mockUser);
        verify(productRepository).findById(1L);
        verify(commentRepository).save(refEq(new Comment("testComment", mockUser, mockProduct)));
        verifyNoMoreInteractions(productRepository, commentRepository);
    }

    @Test(expected = EntityNotFound.class)
    public void commentNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        commentService.addComment(1L, "testCommnet", new User());
    }

    @Test
    public void getProductComments() {
        Product product = new Product();
        product.setId(1L);
        User firstUser = new User("first user login", "passwd 1");
        User secondUser = new User("second user login", "passwd 2");
        List<Comment> comments = asList(new Comment("first comment", firstUser, product), new Comment("second comment", secondUser, product));
        when(commentRepository.findByProductId(1L)).thenReturn(comments);
        List<Comment> productComments = commentService.getProductComments(1L);
        assertEquals(comments, productComments);
    }

    @Test
    public void getUserComments() {
        Product firstProduct = new Product();
        firstProduct.setId(1L);
        Product secondProduct = new Product();
        secondProduct.setId(2L);
        User user = new User("first user login", "passwd 1");
        List<Comment> comments = asList(new Comment("first comment", user, firstProduct), new Comment("second comment", user, secondProduct));
        when(commentRepository.findByUser(user)).thenReturn(comments);
        List<Comment> productComments = commentService.getUserComments(user);
        assertEquals(comments, productComments);
    }

    @Test
    public void delete() {
        commentService.delete(1L);
        verify(commentRepository).delete(1L);
        verifyNoMoreInteractions(productRepository, commentRepository);
    }
}

package software.jevera.configuration;

import static java.util.Arrays.asList;

import software.jevera.dao.inmemory.CommentInMemoryRepository;
import software.jevera.dao.inmemory.ProductInMemoryRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;
import software.jevera.service.CommentService;
import software.jevera.service.ProductService;
import software.jevera.service.ScheduleExecutorThreadImpl;
import software.jevera.service.UserService;
import software.jevera.service.product.Archived;
import software.jevera.service.product.Deleted;
import software.jevera.service.product.Finished;
import software.jevera.service.product.New;
import software.jevera.service.product.Published;
import software.jevera.service.product.StateMachine;

public class ApplicationFactory {

    public static final UserService userService;
    public static final CommentService commentService;
    public static final ProductService productService;

    static {
        userService = new UserService(new UserInMemoryRepository());
        ProductInMemoryRepository productRepository = new ProductInMemoryRepository();
        commentService = new CommentService(new CommentInMemoryRepository(), productRepository);
        StateMachine stateMachine = new StateMachine(asList(
                new Archived(),
                new Deleted(),
                new Finished(),
                new New(),
                new Published()));
        productService = new ProductService(productRepository, stateMachine, new ScheduleExecutorThreadImpl());
    }
}

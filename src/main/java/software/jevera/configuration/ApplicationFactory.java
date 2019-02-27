package software.jevera.configuration;

import static java.util.Arrays.asList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import software.jevera.dao.inmemory.CommentInMemoryRepository;
import software.jevera.dao.inmemory.ProductInMemoryRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;
import software.jevera.domain.annotations.DefaultValue;
import software.jevera.domain.annotations.LogField;
import software.jevera.service.CommentService;
import software.jevera.service.ProductService;
import software.jevera.service.ProductServiceImpl;
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
        ProductServiceImpl pService =
                new ProductServiceImpl(productRepository, stateMachine, new ScheduleExecutorThreadImpl());
        productService =
                (ProductService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                                                        new Class<?>[]{ProductService.class},
                                                        (proxy, method, args) -> {
                                                            return reflectProxMethod(pService, method, args);
                                                        });
    }

    @SneakyThrows
    private static Object reflectProxMethod(ProductServiceImpl pService, Method method,
                                            Object[] args) {
        System.out.println("Start invoke method " + method.getName());
        if (args != null) {
            Stream.of(args).forEach(ApplicationFactory::processArgument);
        }
        Object result = method.invoke(pService, args);
        System.out.println("Stop invoke method " + method.getName() + " with result " + result);
        return result;
    }

    @SneakyThrows
    private static void processArgument(Object arg) {
        Field[] declaredFields = arg.getClass().getDeclaredFields();
        for (Field field: declaredFields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DefaultValue.class)) {
                DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
                Object value = field.get(arg);
                if (value == null || (defaultValue.setIfBlank() && value.toString().isBlank())) {
                    field.set(arg, defaultValue.value());
                }
            }
            if (field.isAnnotationPresent(LogField.class)) {
                System.out.println(field.getName() + ":" + field.getType().getSimpleName() + " = " + field.get(arg));
            }
        }
    }
}

package software.jevera.service;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static software.jevera.service.product.ProductStateEnum.NEW;
import static software.jevera.service.product.ProductStateEnum.PUBLISHED;

import java.time.Instant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import software.jevera.dao.ProductRepository;
import software.jevera.domain.Product;
import software.jevera.service.product.Archived;
import software.jevera.service.product.Deleted;
import software.jevera.service.product.Finished;
import software.jevera.service.product.New;
import software.jevera.service.product.ProductState;
import software.jevera.service.product.Published;
import software.jevera.service.product.StateMachine;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProductService.class, StateMachine.class, Archived.class,
                                 Deleted.class, Finished.class, New.class, Published.class})
public class ProductServiceTest {

    public static final long PRODUCT_ID = 123;

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ScheduleExecutor scheduleExecutor;

    @Test
    public void publish() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setStatus(NEW);
        when(productRepository.findById(PRODUCT_ID)).thenReturn(of(product));
        productService.publish(PRODUCT_ID);
        Product publishedProduct = new Product();
        publishedProduct.setId(PRODUCT_ID);
        publishedProduct.setStatus(PUBLISHED);
        publishedProduct.setFinishDate(product.getFinishDate());
        verify(productRepository).save(refEq(publishedProduct));
        verify(scheduleExecutor).scheduleFinish(eq(PRODUCT_ID), eq(product.getFinishDate()), any());
    }
}

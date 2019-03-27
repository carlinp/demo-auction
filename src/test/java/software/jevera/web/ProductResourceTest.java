package software.jevera.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import software.jevera.domain.User;
import software.jevera.domain.dto.ProductMapper;
import software.jevera.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductResource.class)
@ContextConfiguration(classes = {ProductResource.class, ExceptionController.class,
                                 ProductResourceTest.ProductTestConfiguration.class})
public class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductResource productResource;

    @MockBean
    private ProductService productService;

    @Configuration
    public static class ProductTestConfiguration {
        @Bean
        public ProductMapper productMapper() {
            return Mappers.getMapper(ProductMapper.class);
        }
    }


    @Test
    @SneakyThrows
    public void createProduct() {
        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("user", new User());

        mockMvc.perform(get("/api/products").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());

    }
}

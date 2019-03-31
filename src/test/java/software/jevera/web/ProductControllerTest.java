package software.jevera.web;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.domain.dto.ProductDto;
import software.jevera.domain.dto.ProductMapper;
import software.jevera.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
@ContextConfiguration(classes = {ProductController.class, ExceptionController.class,
                                 ProductControllerTest.TestProductControllerConfiguration.class})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Configuration
    public static class TestProductControllerConfiguration {
        @Bean
        public ProductMapper productMapper() {
            return Mappers.getMapper(ProductMapper.class);
        }
    }

    @Test
    public void createProduct() throws Exception {

        ProductDto productDto = new ProductDto();
        productDto.setDescription("description");
        productDto.setName("name of product");
        productDto.setStartPrice(123);
        byte[] productJson = new ObjectMapper().writeValueAsBytes(productDto);

        User user = new User();
        user.setLogin("login");
        mockMvc.perform(post("/api/products")
                                .content(productJson)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .sessionAttrs(Map.of("user", user)))
                .andDo(print())
                .andExpect(status().isOk());

        Product product = new Product();
        product.setName("name of product");
        product.setDescription("description");
        product.setStartPrice(123);
        Mockito.verify(productService).createProduct(refEq(product), eq(user));
    }

}

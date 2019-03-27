package software.jevera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.service.ProductService;

@SpringBootApplication
public class DemoAuctionApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoAuctionApp.class, args);

        ProductService productService = context.getBean(ProductService.class);
        Product product = new Product();
        product.setStartPrice(10);
        productService.createProduct(product, new User());
        System.out.println(productService.getAllProducts());
    }

}

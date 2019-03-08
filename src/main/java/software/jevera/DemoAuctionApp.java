package software.jevera;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.service.ProductService;

public class DemoAuctionApp {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        ProductService productService = context.getBean(ProductService.class);
        productService.createProduct(new Product(), new User());
        System.out.println(productService.getAllProducts());
    }

}

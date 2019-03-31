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
    }

}

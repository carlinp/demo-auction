package software.jevera;

import static software.jevera.configuration.ApplicationFactory.productService;
import static software.jevera.configuration.ApplicationFactory.userService;

import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Stream;
import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.domain.UserDto;

public class DemoAuctionApp {

    public static void main(String[] args) throws Exception {
        User user = userService.registerUser(new UserDto("userlogin", "password"));

        Product product = new Product();
        product.setFinishDate(Instant.now().plusSeconds(3600));
        product.setName(null);
        product.setDescription(" ");
        product.setStartPrice(10);
        Product savedProduct = productService.createProduct(product, user);
        productService.getAllProducts();
        productService.publish(savedProduct.getId());

    }

}

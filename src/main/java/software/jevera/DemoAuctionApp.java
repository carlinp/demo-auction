package software.jevera;

import static software.jevera.configuration.ApplicationFactory.productService;
import static software.jevera.configuration.ApplicationFactory.userService;

import software.jevera.domain.Product;
import software.jevera.domain.User;
import software.jevera.domain.UserDto;

public class DemoAuctionApp {

    public static void main(String[] args) {
        User user = userService.registerUser(new UserDto("userlogin", "password"));
        productService.createProduct(new Product(), user);
        System.out.println(productService.getAllProducts());;
    }

}

package software.jevera;

import software.jevera.configuration.ApplicationFactory;
import software.jevera.domain.UserDto;

public class DemoAuctionApp {

    public static void main(String[] args) {
        ApplicationFactory.userService.registerUser(new UserDto());
    }

}

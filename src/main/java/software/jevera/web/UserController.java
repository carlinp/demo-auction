package software.jevera.web;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import software.jevera.domain.User;
import software.jevera.domain.UserDto;
import software.jevera.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;

    @PostMapping("/login")
    public User login(UserDto userDto) {
        User user = userService.login(userDto);
        httpSession.setAttribute("user", user);
        return user;
    }

    @PostMapping("/register")
    public User register(UserDto userDto) {
        return userService.registerUser(userDto);
    }


}

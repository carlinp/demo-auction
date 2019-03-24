package software.jevera.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.jevera.domain.User;
import software.jevera.domain.UserDto;
import software.jevera.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(value = "/login", method = POST)
    public User login(@RequestBody UserDto userDto, HttpSession session) {
        User user = userService.login(userDto);
        session.setAttribute("user", user);
        return user;
    }

    @RequestMapping(value = "/register", method = POST)
    public User login(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

}

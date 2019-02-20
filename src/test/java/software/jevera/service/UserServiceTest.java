package software.jevera.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import software.jevera.dao.UserRepository;
import software.jevera.domain.User;
import software.jevera.domain.UserDto;
import software.jevera.exceptions.UncorrectGrant;
import software.jevera.exceptions.UserAlreadyExists;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void successLoginTest() {
        User user = new User("login", "30274c47903bd1bac7633bbf09743149ebab805f");
        when(userRepository.findUserByLogin("login"))
                .thenReturn(Optional.of(user));
        User loginedUser = userService.login(new UserDto("login", "passwd"));
        assertEquals(loginedUser, user);
        verify(userRepository).findUserByLogin("login");
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected = UncorrectGrant.class)
    public void invalidLoginTest() {
        when(userRepository.findUserByLogin("login")).thenReturn(Optional.empty());
        userService.login(new UserDto("login", "passwd"));
    }

    @Test(expected = UncorrectGrant.class)
    public void invalidPassTest() {
        User user = new User("login", "passwd");
        when(userRepository.findUserByLogin("login")).thenReturn(Optional.of(user));
        userService.login(new UserDto("login", "passwd"));
    }

    @Test
    public void registerUser() {
        UserDto userDto = new UserDto("login", "passwd");
        when(userRepository.isUserWithLongExists("login")).thenReturn(false);
        userService.registerUser(userDto);
        verify(userRepository).isUserWithLongExists("login");
    }

    @Test(expected = UserAlreadyExists.class)
    public void userAlreadyExists() {
        UserDto userDto = new UserDto("login", "passwd");
        when(userRepository.isUserWithLongExists("login")).thenReturn(true);
        userService.registerUser(userDto);
    }

}

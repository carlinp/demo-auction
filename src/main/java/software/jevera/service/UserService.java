package software.jevera.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.math.BigInteger;
import java.security.MessageDigest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import software.jevera.dao.UserRepository;
import software.jevera.domain.User;
import software.jevera.domain.UserDto;
import software.jevera.exceptions.UncorrectGrant;
import software.jevera.exceptions.UserAlreadyExists;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(UserDto userDto) {
        if (userRepository.isUserWithLoginExists(userDto.getLogin())) {
            throw new UserAlreadyExists(userDto.getLogin());
        }

        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPasswordHash(encryptPassword(userDto.getPassword()));
        return userRepository.save(user);
    }

    public User login(UserDto userDto) {
        return userRepository.findUserByLogin(userDto.getLogin())
                .filter(user -> checkPassword(userDto, user))
                .orElseThrow(UncorrectGrant::new);
    }

    private boolean checkPassword(UserDto userDto, User user) {
        String encryptPassword = encryptPassword(userDto.getPassword());
        return encryptPassword.equals(user.getPasswordHash());
    }

    @SneakyThrows
    private static String encryptPassword(String password) {
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(password.getBytes(UTF_8));
        return new BigInteger(1, crypt.digest()).toString(16);
    }

}

package software.jevera.dao;

import java.util.Optional;
import software.jevera.domain.User;

public interface UserRepository {
    boolean isUserWithLoginExists(String login);
    User save(User user);
    Optional<User> findUserByLogin(String login);
}

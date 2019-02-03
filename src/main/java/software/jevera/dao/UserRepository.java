package software.jevera.dao;

import java.util.Optional;
import software.jevera.domain.User;

public interface UserRepository {
    boolean isUserWithLongExists(String login);
    User save(User user);
    Optional<User> findUserByLogin(String login);
}

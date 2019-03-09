package software.jevera.dao.inmemory;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import org.junit.Test;
import software.jevera.domain.User;

public class UserInMemoryRepositoryIntTest {

    private UserInMemoryRepository repository = new UserInMemoryRepository();

    @Test
    public void isUserWithLongExists() {
        List<User> users = asList(
                new User("1", "p1"),
                new User("2", "p2")
                                 );
        users.forEach(repository::save);
        assertTrue(repository.isUserWithLoginExists("1"));
        assertFalse(repository.isUserWithLoginExists("5"));
    }

    @Test
    public void save() {
        List<User> users = asList(
                new User("1", "p1"),
                new User("2", "p2")
                                 );
        users.forEach(repository::save);
        assertEquals(users, repository.findAll());
    }

    @Test
    public void findUserByLogin() {
        List<User> users = asList(
                new User("1", "p1"),
                new User("2", "p2")
                                 );
        users.forEach(repository::save);
        assertEquals(Optional.of(new User("1", "p1")), repository.findUserByLogin("1"));
        assertEquals(Optional.empty(), repository.findUserByLogin("5"));
    }
}

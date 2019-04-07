package software.jevera.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import software.jevera.dao.jdbc.ConnectionManager;
import software.jevera.dao.jdbc.JdbcCommentRepository;
import software.jevera.dao.jdbc.JdbcProductRepository;
import software.jevera.domain.Comment;
import software.jevera.domain.Product;
import software.jevera.domain.User;

@RunWith(JUnit4.class)
@DBUnit(url = JdbcCommentRepositoryTest.DB_URL, driver = "org.h2.Driver", user = "sa")
public class JdbcCommentRepositoryTest {

    public static final String DB_URL = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;MODE=PostgreSQL";
    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    private JdbcCommentRepository jdbcCommentRepository;

    @BeforeClass
    @SneakyThrows
    public static void beforeClass() {
        Connection connection = DriverManager.getConnection(DB_URL, "sa", null);
        Database database = DatabaseFactory
                .getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new liquibase.Liquibase("db/changelog/db.changelog-master.yaml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @Before
    public void before() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl(DB_URL);
        properties.setUsername("sa");
        ConnectionManager connectionManager = new ConnectionManager(properties);
        jdbcCommentRepository = new JdbcCommentRepository(connectionManager, new JdbcProductRepository(connectionManager));
    }

    @Test
    @DataSet(value = "testSaveComment-init.xml")
    @ExpectedDataSet(value = "testSaveComment-expected.xml")
    public void testSaveComment() {
        Comment comment = new Comment();
        comment.setText("comment text");
        User user = new User();
        user.setLogin("userlogin");
        Product product = new Product();
        product.setId(15L);
        comment.setAuthor(user);
        comment.setProduct(product);
        Comment savedComment = jdbcCommentRepository.save(comment);
        assertNotNull(savedComment.getId());
    }

    @Test
    @DataSet(value = "testUpdateComment-init.xml")
    @ExpectedDataSet(value = "testUpdateComment-expected.xml")
    public void testUpdateComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("updated comment text");
        User user = new User();
        user.setLogin("userlogin");
        Product product = new Product();
        product.setId(15L);
        comment.setAuthor(user);
        comment.setProduct(product);
        jdbcCommentRepository.save(comment);
    }

    @Test
    @DataSet(value = "testUpdateComment-init.xml")
    public void testFindComments() {
        jdbcCommentRepository.findByProductId(15L);
    }

}

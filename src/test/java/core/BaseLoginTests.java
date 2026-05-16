package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import saucedemo.LoginPage;

public class BaseLoginTests extends BaseTests {
    private static final Logger logger = LogManager.getLogger(BaseLoginTests.class);

    @BeforeMethod(groups = {"authentication"})
    public void login() {
        logger.info("[BaseLoginTests] Start to Login");
        Object[][] data = TestUtils.getTestData("src/test/resources/data/login-data-test.xlsx", "login-tests");
        logger.info("[BaseLoginTests] Get test data retrieved. Data: {}", (Object[]) data);

        String username = (String) data[1][0];
        String password = (String) data[1][1];

        logger.info("[BaseLoginTests] Login using username: {} and password: {}", username, password);
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("baseUrl"));
        loginPage.login(username, password);
    }
}

package core;

import org.testng.annotations.BeforeMethod;
import saucedemo.LoginPage;

public class BaseLoginTests extends BaseTests {

    @BeforeMethod
    public void login() {
        Object[][] data = TestUtils.getTestData("src/test/resources/data/login-data-test.xlsx", "login-tests");

        String username = (String) data[1][0];
        String password = (String) data[1][1];

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(username, password);
    }
}

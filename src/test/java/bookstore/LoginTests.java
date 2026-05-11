package bookstore;

import core.BaseTests;
import core.DriverManager;
import core.TestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import bookstore.LoginPage;

public class LoginTests extends BaseTests {
    private static final Logger logger = LogManager.getLogger(bookstore.LoginTests.class);
    @Test(priority = 1, groups = {"smoke"}, description = "Test successful login", retryAnalyzer = core.RetryAnalyzer.class)
    public void testLoginSuccess() {
        logger.info("Memulai test login dengan credential success user");
        bookstore.LoginPage loginPage = new LoginPage(DriverManager.getDriver());

        logger.info("User login menggunakan credential success user");
        loginPage.login(config.getProperty("successUser"), config.getProperty("passwordUser"));

        logger.info("Verify user sukses login dan melihat halaman Profile");
        Assert.assertTrue(loginPage.verifyLoginSuccess(),
                "User should be able to see the Profile page after logging in with valid credentials");
        logger.info("testLoginSuccess sudah dijalankan dengan sukses");
    }
    @Test(priority = 2, groups = {"smoke"}, description = "Test failed login", retryAnalyzer = core.RetryAnalyzer.class)
    public void testLoginFailed() {
        logger.info("Memulai test login dengan credential success user");
        bookstore.LoginPage loginPage = new LoginPage(DriverManager.getDriver());

        logger.info("User login menggunakan credential failed user");
        loginPage.login(config.getProperty("failedUser2"), config.getProperty("passwordUser"));

        logger.info("Verify error message muncul");
        Assert.assertTrue(loginPage.verifyLoginFailed(),
                "User should be able to see the error message and can't login");
        logger.info("testLoginFailed sudah dijalankan dengan sukses");
    }
}


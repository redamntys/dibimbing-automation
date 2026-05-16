package saucedemo;

import core.BaseTests;
import core.DriverManager;
import core.TestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoginTests extends BaseTests {
    private static final Logger logger = LogManager.getLogger(LoginTests.class);

    @Test(priority = 1, groups = {"login"}, description = "Verify successful login", retryAnalyzer = core.RetryAnalyzer.class)
    public void testLoginSuccess() {
        logger.info("Starting login test with standard user credentials");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("baseUrl"));

        logger.info("User logs in using standard user credentials");
        loginPage.login(config.getProperty("standardUser"), config.getProperty("password"));

        logger.info("Verify user successfully logs in and sees the Products page");
        Assert.assertTrue(loginPage.isUserLoggedInSuccessfully(),
                "User should be able to see the Products page after logging in with valid credentials");

        logger.info("Verify user is redirected to the inventory page");
        Assert.assertTrue(loginPage.getCurrentUrl().contains("inventory"),
                "User should be redirected to the inventory page after successful login");

        logger.info("Verify no error message is displayed after successful login");
        Assert.assertFalse(loginPage.isErrorMessageDisplayed(),
                "User should not see any error message after successful login");
        logger.info("[LOGIN] testLoginSuccess : Passed!");
    }

    @Test(priority = 1, groups = {"login"}, description = "Verify failed login", retryAnalyzer = core.RetryAnalyzer.class)
    public void testLoginFailed() {
        logger.info("Starting login test with invalid credentials");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("baseUrl"));

        logger.info("User logs in using invalid credentials");
        loginPage.login(config.getProperty("failedUsername"), config.getProperty("failedPassword"));

        logger.info("Verify user is not able to log in and does not see the Products page");
        Assert.assertFalse(loginPage.isUserLoggedInSuccessfully(),
                "User should not be able to see the Products page after logging in with valid credentials");

        logger.info("Verify user is not redirected to the inventory page");
        Assert.assertFalse(loginPage.getCurrentUrl().contains("inventory"),
                "User should not be redirected to the inventory page after successful login");

        logger.info("Verify error message is displayed after failed login");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "User should see an error message after successful login");
        logger.info("[LOGIN] testLoginFailed : Passed!");
    }

    @DataProvider(name = "loginCredentials", parallel = true)
    public Object[][] loginCredentials() {
        return TestUtils.getTestData("src/test/resources/data/login-data-test.xlsx", "login-tests");
    }

    @Test(priority = 1, dataProvider = "loginCredentials", description = "Data-driven login test")
    public void testDataDriven(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("baseUrl"));
        loginPage.login(username, password);

        SoftAssert softAssert = new SoftAssert();

        if (expectedResult.equalsIgnoreCase("success")) {
            softAssert.assertTrue(loginPage.isUserLoggedInSuccessfully(),
                    "User with username '" + username + "' should be able to login successfully");

            softAssert.assertTrue(loginPage.getCurrentUrl().contains("inventory"),
                    "User should be redirected to the inventory page after successful login");
        } else {
            softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                    "User with username '" + username + "' should see an error message");

            softAssert.assertFalse(loginPage.isUserLoggedInSuccessfully(),
                    "User should not be able to access the Products page with invalid credentials");
        }

        softAssert.assertAll();
    }

}


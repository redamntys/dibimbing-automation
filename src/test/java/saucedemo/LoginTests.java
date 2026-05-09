package saucedemo;

import core.BaseTests;
import core.DriverManager;
import core.TestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class LoginTests extends BaseTests {
    @Test(priority = 3, groups = {"smoke"}, description = "Test successful login")

    public void testLogin() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(config.getProperty("standardUser"), config.getProperty("password"));

        Assert.assertTrue(loginPage.isUserLoggedInSuccessfully(),
                 "User should be able to see the Products page after logging in with valid credentials");

        Assert.assertTrue(loginPage.getCurrentUrl().contains("inventory"),
                 "User should be redirected to the inventory page after successful login");

        Assert.assertFalse(loginPage.isErrorMessageDisplayed(),
                 "User should not see any error message after successful login");
    }

    @DataProvider(name = "loginCredentials", parallel = true)
    public Object[][] loginCredentials() {
        return TestUtils.getTestData("src/test/resources/data/login-data-test.xlsx", "login-tests");
    }

    @Test(priority = 1, dataProvider = "loginCredentials", description = "Data-driven login test")
    public void testDataDriven(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
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


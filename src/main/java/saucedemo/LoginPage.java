package saucedemo;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorAlert;

    @FindBy(className = "title")
    private WebElement pageTitle;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        waitForElementToBeVisible(usernameInput);
        logger.info("Login started");
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
        //wait.until(ExpectedConditions.urlContains("inventory"));
        logger.info("Login successful");
    }

    public boolean isUserLoggedInSuccessfully() {
        try {
            waitForElementToBeVisible(pageTitle);
            return pageTitle.isDisplayed() && pageTitle.getText().equals("Products");
        } catch (Exception e) {
            logger.error("LoginPage-isUserLoggedInSuccessfully(): Got exception: ", e);
            return false;

        }
    }
    public boolean isErrorMessageDisplayed() {
        try {
            waitForElementToBeVisible(errorAlert);
            return errorAlert.isDisplayed();
        } catch (Exception e) {
            logger.error("LoginPage-isErrorMessageDisplayed(): Got exception: ", e);
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            waitForElementToBeVisible(errorAlert);
            return errorAlert.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void openLoginPage(String url){
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}



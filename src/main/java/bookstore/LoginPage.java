package bookstore;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(css = "[data-testid='goto-signin']")
    private WebElement signInButton;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "submit")
    private WebElement loginButton;

    @FindBy(id = "flash")
    private WebElement errorMessage;

    @FindBy(id = "welcome-message")
    private WebElement welcomeMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        waitForElementToBeVisible(emailInput);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        scrollToElement(loginButton);
        loginButton.click();
    }

    public boolean verifyLoginSuccess() {
        scrollToElement(welcomeMessage);
        return welcomeMessage.isDisplayed()
                && welcomeMessage.getText().contains("Hello");
    }

    public boolean verifyLoginFailed(){
        waitForElementToBeVisible(errorMessage);
        scrollToElement(errorMessage);
        return errorMessage.isDisplayed();
    }
    public void openLoginPage(String url){
        driver.get(url);
    }

}


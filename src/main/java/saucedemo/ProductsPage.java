package saucedemo;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProductsPage extends BasePage {

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean addSingleProduct(String productName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        By addButton = By.xpath("//button[@data-test='add-to-cart-" + productName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        return true;
        }

    public String getButtonText(String productName) {
        By button = By.xpath("//button[contains(@data-test,'" + productName + "')]");
        return driver.findElement(button).getText();
    }

    public int getCartCount() {
        By badge = By.className("shopping_cart_badge");
        try {
            return Integer.parseInt(driver.findElement(badge).getText());
        } catch (Exception e) {
            return 0;
        }
    }
}
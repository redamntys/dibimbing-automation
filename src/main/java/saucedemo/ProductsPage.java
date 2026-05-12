package saucedemo;

import core.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductsPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ProductsPage.class);

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean addSingleProduct(String productName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.urlContains("inventory"));
            By addButton = By.xpath("//button[@data-test='add-to-cart-" + productName + "']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(addButton));
            wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        } catch (Exception e) {
            logger.error("ProductsPage-addSingleProduct(): Got exception: ", e);
        }
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
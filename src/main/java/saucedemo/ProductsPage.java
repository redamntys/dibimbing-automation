package saucedemo;

import core.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

public class ProductsPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ProductsPage.class);

    @FindBy(css = "[data-test='product-sort-container']")
    private WebElement filterDropdown;

    @FindBy(css = "option[value='az']")
    private WebElement filterByNameAtoZ;

    @FindBy(css = "option[value='za']")
    private WebElement filterByNameZtoA;

    @FindBy(css = "option[value='lohi']")
    private WebElement getFilterByPriceLowToHigh;

    @FindBy(css = "option[value='hilo']")
    private WebElement getFilterByPriceHighToLow;

    @FindBy(css = "option[value='lohi']")
    private WebElement filterbyPriceLowtoHigh;

    @FindBy(css = "option[value='hilo']")
    private WebElement filterbyPriceHightoLow;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getProductNames() {
        List<String> listOfProductName = new ArrayList<>();
        List<WebElement> productNames = driver.findElements(By.cssSelector("[data-test='inventory-item-name']"));

        for (WebElement product : productNames) {
            listOfProductName.add(product.getText());
        }

        logger.info("[TEST] result getProductNames(): {}", listOfProductName);
        return listOfProductName;
    }

    public List<String> getProductPrice() {
        List<String> listOfProductPrice = new ArrayList<>();
        List<WebElement> productPrice = driver.findElements(By.cssSelector("[data-test='inventory-item-price']"));

        for (WebElement product : productPrice) {
            listOfProductPrice.add(product.getText());
        }

        logger.info("[TEST] result of getProductPrice(): {}", listOfProductPrice);
        return listOfProductPrice;
    }

    public boolean filterProductsAtoZ(){
        wait.until(ExpectedConditions.elementToBeClickable(filterDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(filterByNameAtoZ)).click();
        return true;
    }

    public boolean filterProductsZtoA(){
        wait.until(ExpectedConditions.elementToBeClickable(filterDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(filterByNameZtoA)).click();
        return true;
    }

    public boolean filterProductsPriceLowToHigh(){
        wait.until(ExpectedConditions.elementToBeClickable(filterDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(getFilterByPriceLowToHigh)).click();
        return true;
    }

    public boolean filterProductsPriceHighToLow(){
        wait.until(ExpectedConditions.elementToBeClickable(filterDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(getFilterByPriceHighToLow)).click();
        return true;
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
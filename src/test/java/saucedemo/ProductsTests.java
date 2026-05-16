package saucedemo;

import core.BaseLoginTests;
import core.DriverManager;
import core.TestUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductsTests extends BaseLoginTests {
    private static final Logger logger = LogManager.getLogger(ProductsTests.class);

    @DataProvider(name = "singleProducts")
    public Object[][] singleProducts() {
        Object[][] data = TestUtils.getTestData("src/test/resources/data/products-data-test.xlsx", "inventory");
        List<Object[]> result = new ArrayList<>();

        for (Object[] row : data) {
            String productName = row[0].toString();
            String type = row[1].toString();

            if (type.equalsIgnoreCase("single")) {
                result.add(new Object[]{productName});
            }
        }
        return result.toArray(new Object[0][]);
    }

    @DataProvider(name = "multipleProducts")
    public Object[][] multipleProducts() {
        Object[][] data = TestUtils.getTestData("src/test/resources/data/products-data-test.xlsx", "inventory");
        List<String> products = new ArrayList<>();

        for (Object[] row : data) {
            String productName = row[0].toString();
            String type = row[1].toString();

            if (type.equalsIgnoreCase("multiple")) {
                products.add(productName);
            }
        }
        return new Object[][]{
                {products}
        };
    }


    @Test(priority = 1, groups = {"products"}, description = "Verify sorting product by Name A to Z",retryAnalyzer = core.RetryAnalyzer.class)
    public void testSortByNameFunctionAToZ() {
        ProductsPage page = new ProductsPage(DriverManager.getDriver());

        logger.info("Applying filter : Name(A to Z)");
        Assert.assertTrue(page.filterProductsAtoZ(),
                "User should be able to click filter products dropdown and filter by Name A to Z");

        List<String> actualProductNames = page.getProductNames();
        logger.info("Actual product names: " + actualProductNames);

        List<String> expectedProductNames = actualProductNames;
        Collections.sort(expectedProductNames);
        logger.info("Expected sorted names: " + expectedProductNames);

        Assert.assertEquals(actualProductNames, expectedProductNames);
        logger.info("[PRODUCTS] testSortByNameFunctionAToZ : Passed!");
    }

    @Test(priority = 1, groups = {"products"}, description = "Verify sorting product by Name Z to A",retryAnalyzer = core.RetryAnalyzer.class)
    public void testSortByNameFunctionZToA() {
        ProductsPage page = new ProductsPage(DriverManager.getDriver());

        logger.info("Applying filter : Name(Z to A");
        Assert.assertTrue(page.filterProductsZtoA(),
                "User should be able to click filter products dropdown and filter by Name Z to A");

        List<String> actualProductNames = page.getProductNames();
        logger.info("Actual product names: " + actualProductNames);

        List<String> expectedProductNames = actualProductNames;
        Collections.sort(expectedProductNames);
        logger.info("Expected sorted names: " + expectedProductNames);

        Assert.assertEquals(actualProductNames, expectedProductNames);
        logger.info("[PRODUCTS] testSortByNameFunctionZToA : Passed!");
    }

    @Test(priority = 1, groups = {"products"}, description = "Verify sorting product by Price low to high", retryAnalyzer = core.RetryAnalyzer.class)
    public void testSortByPriceFunctionLowToHigh() {
        ProductsPage page = new ProductsPage(DriverManager.getDriver());

        logger.info("Applying filter : Price(low to high)");
        Assert.assertTrue(page.filterProductsPriceLowToHigh(),
                "User should be able to click filter products dropdown and filter by Price low to high");

        List<String> actualProductPrice = page.getProductPrice();
        logger.info("Actual product price: " + actualProductPrice);

        List<String> expectedProductPrice = actualProductPrice;
        Collections.sort(expectedProductPrice);
        logger.info("Expected sorted price: " + expectedProductPrice);

        Assert.assertEquals(actualProductPrice, expectedProductPrice);
        logger.info("[PRODUCTS] testSortByPriceFunctionLowToHigh : Passed!");
    }

    @Test(priority = 1, groups = {"products"}, description = "Verify sorting product by Price high to low", retryAnalyzer = core.RetryAnalyzer.class)
    public void testSortByPriceFunctionHighToLow() {
        ProductsPage page = new ProductsPage(DriverManager.getDriver());

        logger.info("Applying filter : Price(high to low)");
        Assert.assertTrue(page.filterProductsPriceHighToLow(),
                "User should be able to click filter products dropdown and filter by Price high to low");

        List<String> actualProductPrice = page.getProductPrice();
        logger.info("Actual product price: " + actualProductPrice);

        List<String> expectedProductPrice = actualProductPrice;
        Collections.sort(expectedProductPrice);
        logger.info("Expected sorted price: " + expectedProductPrice);

        Assert.assertEquals(actualProductPrice, expectedProductPrice);
        logger.info("[PRODUCTS] testSortByPriceFunctionHighToLow : Passed!");
    }

    @Test(priority = 1, groups = {"products"}, dataProvider = "singleProducts", description = "Verify add single product", retryAnalyzer = core.RetryAnalyzer.class)
    public void testSingleProduct(String productName) {
        ProductsPage page = new ProductsPage(DriverManager.getDriver());

        int before = page.getCartCount();
        logger.info("Before add to cart = {}" , before);

        page.addSingleProduct(productName);
        logger.info("Add product to cart = {}", productName);

        int after = page.getCartCount();
        logger.info("After add product to cart = {}" , after);

        String buttonText = page.getButtonText(productName);
        logger.info("Button text after add product to cart = {}" , buttonText);

        logger.info("Verify cart count increases by 1 after add product to cart");
        Assert.assertTrue(after == before + 1,
                "Cart should increase by 1 after adding " + productName);

        logger.info("Verify button text changes from Add to cart to Remove");
        Assert.assertTrue(buttonText.equals("Remove"),
                "Button should change to Remove after adding " + productName);

        logger.info("Verify button text is no longer Add to cart");
        Assert.assertFalse(buttonText.equals("Add to cart"),
                "Button should change from Add to cart");

        logger.info("[PRODUCTS] testSingleProduct : Passed!");
    }

    @Test(priority = 1, groups = {"products"}, dataProvider = "multipleProducts", description = "Verify add multiple products",retryAnalyzer = core.RetryAnalyzer.class)
    public void testMultipleProducts(List<String> productNames) {

        SoftAssert softAssert = new SoftAssert();
        ProductsPage page = new ProductsPage(DriverManager.getDriver());

        int before = page.getCartCount();
        logger.info("Before add to cart = {}" , before);

        for (String product : productNames) {
            page.addSingleProduct(product);
        }
        logger.info("Add product to cart = {}", productNames);

        int after = page.getCartCount();
        logger.info("After add product to cart = {}" , after);

        logger.info("Verify cart count increases after adding multiple products");
        softAssert.assertTrue(after > before,
                "Cart should increase after adding product");

        logger.info("Verify button text changes from Add to cart to Remove");
        for (String product : productNames) {
            String buttonText = page.getButtonText(product);
            softAssert.assertTrue("Remove".equals(buttonText),
                    "Button should change to Remove after adding " + product);
        }

        softAssert.assertAll();
        logger.info("[PRODUCTS] testMultipleProducts : Passed!");
    }


}
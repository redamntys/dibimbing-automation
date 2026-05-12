package saucedemo;

import core.BaseLoginTests;
import core.DriverManager;
import core.TestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
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

        logger.info("testSingleProduct : Passed!");
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
        logger.info("testMultipleProducts : Passed!");
    }
}
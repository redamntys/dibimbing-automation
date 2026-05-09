package saucedemo;

import core.BaseLoginTests;
import core.DriverManager;
import core.TestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class ProductsTests extends BaseLoginTests {
//Assignment Day 29 - Test Runner, Assertions, and Data-Driven
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

    @Test(priority = 1, dataProvider = "singleProducts", description = "Verify add single product")
    public void testSingleProduct(String productName) {
        ProductsPage page = new ProductsPage(DriverManager.getDriver());

        int before = page.getCartCount();
        System.out.println("Before = " + before);

        page.addSingleProduct(productName);
        System.out.println("Add to cart = " + productName);

        int after = page.getCartCount();
        System.out.println("After = " + after);

        String buttonText = page.getButtonText(productName);

        Assert.assertTrue(after == before + 1,
                "Cart should increase by 1 after adding " + productName);

        Assert.assertTrue(buttonText.equals("Remove"),
                "Button should change to Remove after adding " + productName);

        Assert.assertFalse(buttonText.equals("Add to cart"),
                "Button should change from Add to cart");

        System.out.println("testSingleProduct : Passed!");
    }

    @Test(priority = 1, dataProvider = "multipleProducts", description = "Verify multiple products")
    public void testMultipleProducts(List<String> productNames) {

        SoftAssert softAssert = new SoftAssert();
        ProductsPage page = new ProductsPage(DriverManager.getDriver());

        int before = page.getCartCount();
        System.out.println("Before = " + before);

        for (String product : productNames) {
            page.addSingleProduct(product);
        }
        System.out.println("Add to cart = " + productNames);

        int after = page.getCartCount();
        System.out.println("After = " + after);

        softAssert.assertTrue(after > before,
                "Cart should increase after adding product");

        for (String product : productNames) {
            String buttonText = page.getButtonText(product);
            softAssert.assertTrue("Remove".equals(buttonText),
                    "Button should change to Remove after adding " + product);
        }

        softAssert.assertAll();
        System.out.println("testMultipleProducts : Passed!");
    }
}
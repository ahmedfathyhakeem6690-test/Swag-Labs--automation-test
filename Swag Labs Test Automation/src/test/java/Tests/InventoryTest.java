package Tests;

import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.DataDriven;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.DataDriven.getValidUser;

public class InventoryTest extends BaseTest {
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }
    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {
        return DataDriven.jsonReader();
    }
    @Test
    @Feature("Inventory Feature")
    @Epic("Inventory Epic")
    @Description("Test that user can buy product")
    @Severity(SeverityLevel.BLOCKER)
    public void inventoryTest ()  throws IOException {
        SoftAssert softAssert = new SoftAssert();
        Map<String, String> user = getValidUser();

        Allure.step("user enter valid user name and valid password");
        loginPage.validateLogin(user.get("username"), user.get("password"));

        Allure.step("Verify that inventory page opened and add product");
        inventoryPage.open();

        softAssert.assertTrue(inventoryPage.open().contains("inventory.html") , "inventory is not opened");

        softAssert.assertTrue(inventoryPage.TitleisExist().contains("Swag Labs") , "swaglabs not found");

        softAssert.assertTrue(inventoryPage.CardIconExist(), "card icon not found");

        List < WebElement> products = inventoryPage.getProducts();
        softAssert.assertTrue(products.size()==6 , "products not found");
        softAssert.assertAll();
    }
    @Test
    @Feature("Social Links")
    @Description ("Verify Social Links")
    @Severity(SeverityLevel.NORMAL)
    public void socialLinksTest () throws IOException {
    SoftAssert softAssert = new SoftAssert();
    Map<String, String> user = getValidUser();
    Allure.step("user enter valid user name and valid password");
    loginPage.validateLogin(user.get("username"), user.get("password"));

    Allure.step("Verify that inventory page opened and add product");
    inventoryPage.open();
    softAssert.assertTrue(inventoryPage.open().contains("inventory.html") , "inventory not opened");
        Allure.step("Verify Social Links");
        List<String> expectedDomains = List.of("x.com", "facebook.com", "linkedin.com");
        List<String> links = inventoryPage.SocialLinks();

        softAssert.assertEquals(links.size(), expectedDomains.size(),
                "Number of opened social pages is wrong");

        for (String domain : expectedDomains) {
            boolean opened = links.stream()
                    .anyMatch(link -> link.toLowerCase().contains(domain));
            softAssert.assertTrue(opened,
                    domain + " page was not opened. Opened links: " + links);
        }

        softAssert.assertAll();

    }
    @Test
    @Feature("Verify Cart Total Price")
    @Description("Verify Cart Total Price Before add to cart")
    @Severity(SeverityLevel.NORMAL)
    public void cartTotalPriceTest () throws IOException {
        SoftAssert softAssert = new SoftAssert();
        Map<String, String> user = getValidUser();
        Allure.step("user enter valid user name and valid password");
        loginPage.validateLogin(user.get("username"), user.get("password"));
        Allure.step("Read the price of each product and calculate the sum");
        List<String> expectedProducts = DataDriven.getCartProducts();
        List<Double> prices = inventoryPage.getProductsPrices(expectedProducts);
        System.out.println("Product Prices" + prices);
        double expectedSum = inventoryPage.getTotalPrice(expectedProducts);
        Allure.step("Add products to the cart");
        for (String productName : expectedProducts) {
            inventoryPage.addProductToCart(productName);
        }
        Allure.step("Verify Cart Total Price");
        double itemTotal = inventoryPage.getTotalPrice(expectedProducts);
        softAssert.assertEquals(itemTotal, expectedSum,
                "Item total does not match the sum of the product prices");
        System.out.println("Total price is " + itemTotal);
        Allure.step("Verify Cart Total Price is equal to the total price");
        inventoryPage.clickOnCardIcon();
        CartPage cartPage = inventoryPage.cartPage();
        cartPage.CheckoutBtn();
        CheckoutPage checkoutPage = cartPage.checkout();
        checkoutPage.validCheckoutPageContinue();
        softAssert.assertEquals(checkoutPage.getTotalPrice(),expectedSum,"Total price does not match the sum of the product prices");

        softAssert.assertAll();
    }



}




package Tests;

import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.DataDriven;

import java.io.IOException;
import java.util.Map;

import static utils.DataDriven.getValidUser;

public class CheckoutTest extends BaseTest {
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

    }

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {
        return DataDriven.jsonReader();
    }

    @Test
    @Feature("Checkout Feature")
    @Epic("Checkout Epic")
    @Description("Checkout With an Empty Cart")
    @Severity (SeverityLevel.CRITICAL)
    public void checkoutTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        Map<String, String> user = getValidUser();
        Allure.step("user enter valid user name and valid password");
        loginPage.validateLogin(user.get("username"), user.get("password"));
        Allure.step("user enter cart List Page");
        inventoryPage.clickOnCardIcon();
        CartPage cartPage = inventoryPage.cartPage();
        Allure.step("user Checkout Page");
        cartPage.CheckoutBtn();
        CheckoutPage checkoutPage = cartPage.checkout();
        Allure.step("user Leave Checkout Page option empty and click continue");
        softAssert.assertTrue(checkoutPage.errorMsgloaded(),"Fields is not Empty");
        softAssert.assertAll();

    }

}

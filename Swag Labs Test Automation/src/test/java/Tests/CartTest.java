package Tests;

import Pages.CartPage;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
    }
    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {
        return DataDriven.jsonReader();
    }
    @Test
    @Feature("Cart Feature")
    @Epic("Product Epic")
    @Description ("Check Cart is Empty")
    @Severity(SeverityLevel.NORMAL)
    public void checkCartIsEmpty() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        Map<String,String> user = DataDriven.getValidUser();
        Allure.step("Login With ValidUser");
        loginPage.validateLogin(user.get("username"), user.get("password"));
        Allure.step("Check Cart is Empty");
        inventoryPage.clickOnCardIcon();
        List<String> expected = List.of("QTY", "Description");
        List<String> actual = cartPage.cartlist();
        softAssert.assertEquals(actual, expected, "Cart not Empty");
        softAssert.assertAll();
    }
    @Test
    @Feature("Cart Feature")
    @Epic("Product Epic")
    @Description("Add products from test data and verify they appear in the cart in the same order")
    @Severity(SeverityLevel.CRITICAL)
    public void addThreeProductsAndVerifyCartTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        List<String> expectedProducts = DataDriven.getCartProducts();
        Map<String, String> user = DataDriven.getValidUser();

        Allure.step("Login with a valid user");
        loginPage.validateLogin(user.get("username"), user.get("password"));

        Allure.step("Add products from test data to the cart");
        for (String product : expectedProducts) {
            inventoryPage.addProductToCart(product);
        }

        Allure.step("Open the cart");
        inventoryPage.clickOnCardIcon();
        List<String> actualProducts = cartPage.getCartItemNames();

        Allure.step("Verify cart items and their order");
        softAssert.assertEquals(actualProducts.size(), expectedProducts.size(),
                "Cart item count mismatch");
        softAssert.assertEquals(actualProducts, expectedProducts,
                "Cart items or their order do not match the order they were added");


        Allure.step("Check out the cart");
        softAssert.assertEquals(actualProducts.size(), 3, "Cart should contain 3 products before removal");

        Allure.step("Remove products from the cart");
        String removedProduct = expectedProducts.get(1);
        cartPage.ProductRemove(removedProduct);
        List <String> expectedAfterRemovedProducts = new ArrayList<>(expectedProducts);
        expectedAfterRemovedProducts.remove(removedProduct);
        List<String>cartAfterRemovedProducts = cartPage.getCartItemNames();
        softAssert.assertEquals(cartAfterRemovedProducts, expectedAfterRemovedProducts,
                "Cart content after removal is wrong");

        Allure.step("Back To inventory Bage");
        cartPage.continueShopping();

        Allure.step("Check Button Change");
        softAssert.assertTrue(inventoryPage.getbuttontexeproduct(removedProduct).contains("Add to cart"),removedProduct + " should show Add to cart");
        for (String product : expectedAfterRemovedProducts) {
            softAssert.assertTrue(inventoryPage.getbuttontexeproduct(product).contains("Remove"),
                    product + " should show Remove");
        }
        softAssert.assertAll();
    }
    @Test
    @Feature("Logout")
    @Description("Cart State After Logout/Login")
    @Severity(SeverityLevel.NORMAL)
    public void logout() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        Map<String, String> user = DataDriven.getValidUser();
        List<String> expectedProducts = DataDriven.getCartProducts();
        Allure.step("Login With ValidUser");
        loginPage.validateLogin(user.get("username"), user.get("password"));
        Allure.step("Add products from test data to the cart");
        for (String product : expectedProducts) {
            inventoryPage.addProductToCart(product);
        }
        Allure.step("Open the cart");
        inventoryPage.clickOnCardIcon();
        List<String> actualProducts = cartPage.getCartItemNames();

        Allure.step("Verify cart items and their order");
        softAssert.assertEquals(actualProducts.size(), expectedProducts.size(),
                "Cart item count mismatch");
        softAssert.assertEquals(actualProducts, expectedProducts,
                "Cart items or their order do not match the order they were added");

        Allure.step("Logout");
        inventoryPage.logout();

        Allure.step("Login again after logout");
        loginPage.validateLogin(user.get("username"), user.get("password"));
        inventoryPage.clickOnCardIcon();
        List<String> actualAfterLogout = cartPage.getCartItemNames();
        softAssert.assertEquals(actualAfterLogout,expectedProducts,"Cart content changed after logout and login");

        softAssert.assertAll();


    }

}

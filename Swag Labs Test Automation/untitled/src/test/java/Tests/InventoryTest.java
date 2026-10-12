package Tests;

import Pages.InventoryPage;
import Pages.LoginPage;
import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.DataDriven;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InventoryTest extends BaseTest {
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }
    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {
        return DataDriven.jsonReader();
    }
    @Test (dataProvider = "LoginData")
    public void inventoryTest(Map<String, String> data)  {
        SoftAssert softAssert = new SoftAssert();
        String username = data.get("username");
        String password = data.get("password");
        String expected = data.get("expected");

        loginPage.validateLogin(username, password);


        switch (expected) {
            case "success":

                inventoryPage.open();

                softAssert.assertTrue(inventoryPage.open().contains("inventory.html") , "inventory is not opened");

                softAssert.assertTrue(inventoryPage.TitleisExist().contains("Swag Labs") , "swaglabs not found");

                softAssert.assertTrue(inventoryPage.clickOnCardIcon(), "card icon not found");

                List < WebElement> products = inventoryPage.getProducts();
                softAssert.assertTrue(products.size()==6 , "products not found");

                break;
                case "failed":
                    String msg = loginPage.enterpasswordMsg();
                    softAssert.assertEquals(msg, "Epic sadface: Username and password do not match any user in this service");
                    softAssert.assertFalse(loginPage.erroricon().isEmpty());

                break;
                case "locked" :
                    String msg2 = loginPage.enterpasswordMsg();
                    softAssert.assertEquals(msg2, "Epic sadface: Sorry, this user has been locked out.");
                break;
                case "invalid":
                    String msg3 = loginPage.enterpasswordMsg();
                    softAssert.assertEquals(msg3, "Epic sadface: Password is required");



        }

        softAssert.assertAll();


    }
}

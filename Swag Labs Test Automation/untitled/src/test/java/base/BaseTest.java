package base;

import com.beust.jcommander.Parameter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;


    @BeforeMethod
//    @Parameters({"browser" , "BaseUrl"})
//    public void startbrowser(String browser , String BaseUrl) {
//        if (browser.equalsIgnoreCase("chrome")) {
//        driver = new ChromeDriver();
//        }else if (browser.equalsIgnoreCase("firefox")) {
//            driver = new FirefoxDriver();
//        }else if (browser.equalsIgnoreCase("edge")) {
//            driver = new EdgeDriver();
//        }else {
//            browser = "chrome";
//            BaseUrl = "https://www.saucedemo.com/";
//        }
    public void startbrowser(){
        String browser = "chrome";
        String BaseUrl = "https://www.saucedemo.com/";
        driver = new ChromeDriver();
        driver.get(BaseUrl);
//        driver.manage().window().maximize();
    }

    @AfterMethod (alwaysRun = true)
    public void quitbrowser(){
        driver.quit();

    }
}

package tests;

import core.TestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.base.BasePage;
import pageobjects.pages.bookstorepage.BookStorePage;
import pageobjects.pages.bookstorepage.LoginPage;
import pageobjects.pages.bookstorepage.ProfilePage;
import pageobjects.pages.landingpage.LandingPage;

public class LogInTest extends TestBase{
    private WebDriver driver;
    private BasePage basePage;
    private LoginPage loginPage;
    private LandingPage landingPage;
    private BookStorePage bookStorePage;
    private ProfilePage profilePage;
    private String baseUrl = "https://demoqa.com/";

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\PC\\IdeaProjects\\DemoQA-AWS-Automation\\src\\test\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        landingPage = new LandingPage(driver);
    }

    @Test
    public void testLogIntoBookStore() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        logger.info("Scroll down to the Book Store element");
        js.executeScript("window.scrollBy(0,250)", "");
        landingPage.clickOnBookStoreApp();
        bookStorePage = new BookStorePage(driver);
        bookStorePage.clickLoginButton();
        loginPage = new LoginPage(driver);


        String actualText = loginPage.getText();
        String expectedText = "Login in Book Store";
        Assert.assertEquals("The login text is not the same",expectedText,actualText);

        loginPage.enterUserName("FrankyProky00");
        loginPage.enterPassword("valid11Password!");
        loginPage.clickLoginButton();

        profilePage = new ProfilePage(driver);
        profilePage.waitForPageToLoad();

        String actualUserName = profilePage.getUserName();
        String expectedUserName = "FrankyProky00";
        Assert.assertEquals("The actual username does not match the expected username",expectedUserName,actualUserName);
    }

    @Test
    public void testLogoutBookStore(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        logger.info("Scroll down to the Book Store element");
        js.executeScript("window.scrollBy(0,250)", "");
        landingPage.clickOnBookStoreApp();
        bookStorePage = new BookStorePage(driver);
        bookStorePage.clickLoginButton();
        loginPage = new LoginPage(driver);


        String actualText = loginPage.getText();
        String expectedText = "Login in Book Store";
        Assert.assertEquals("The login text is not the same",expectedText,actualText);

        loginPage.enterUserName("FrankyProky00");
        loginPage.enterPassword("valid11Password!");
        loginPage.clickLoginButton();

        profilePage = new ProfilePage(driver);
        profilePage.waitForPageToLoad();

        profilePage.verifyButtons();
        profilePage.clickLogOut();
    }
}

package tests;

import core.TestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.pages.bookstorepage.BookStorePage;
import pageobjects.pages.bookstorepage.LoginPage;
import pageobjects.pages.bookstorepage.RegistrationPage;
import pageobjects.pages.landingpage.LandingPage;

public class CreateUserTest extends TestBase {

    private WebDriver driver;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private LandingPage landingPage;
    private BookStorePage bookStorePage;
    private String baseUrl = "https://demoqa.com/";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\PC\\IdeaProjects\\DemoQA-AWS-Automation\\src\\test\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        landingPage = new LandingPage(driver);
    }
    @Test
    public void testCreateUser() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");
        landingPage.clickOnBookStoreApp();
        bookStorePage = new BookStorePage(driver);
        bookStorePage.clickLoginButton();
        loginPage = new LoginPage(driver);

        loginPage.clickRegistrationLink();
        registrationPage = new RegistrationPage(driver);

        registrationPage.enterFirstName("Franky02");
        registrationPage.enterLastName("Testing02");
        registrationPage.enterUserName("FrankyProky02");
        registrationPage.enterPassword("valid11Password!");
        registrationPage.checkCaptacha();
        registrationPage.clickRegisterButton();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"name\"]")));

        String successMessage = registrationPage.getSuccessMessage();
        Assert.assertEquals("Thank you for your registration", successMessage);
    }
}
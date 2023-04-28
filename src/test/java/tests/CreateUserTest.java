package tests;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import pageobjects.pages.bookstorepage.LoginPage;
import pageobjects.pages.bookstorepage.RegistrationPage;

class CreateUserTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private String baseUrl = "https://demoqa.com/";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\PC\\IdeaProjects\\DemoQA-AWS-Automation\\src\\test\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
    }
    @Test
    public void testCreateUser() {
        loginPage.clickRegistrationLink();
        registrationPage.enterFirstName("John");
        registrationPage.enterLastName("Doe");
        registrationPage.enterUserName("johndoe");
        registrationPage.enterPassword("password123");
        registrationPage.clickSubmitButton();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("piereg_message")));

        String successMessage = registrationPage.getSuccessMessage();
        Assert.assertEquals("Thank you for your registration", successMessage);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
  
}
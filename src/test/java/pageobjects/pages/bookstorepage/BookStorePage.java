package pageobjects.pages.bookstorepage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePage;
import ru.yandex.qatools.allure.annotations.Step;

public class BookStorePage extends BasePage {
    public BookStorePage(WebDriver driver) {super(driver);}

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div[1]/div/div/div[1]/span/div")
    private WebElement bookElementDropdown;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div[1]/div/div/div[2]/span/div")
    private WebElement bookFormsDropdown;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div[1]/div/div/div[3]/span/div")
    private WebElement bookAlertsDropdown;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div[1]/div/div/div[4]/span/div")
    private WebElement bookWidgetsDropdown;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div[1]/div/div/div[5]/span/div")
    private WebElement bookInteractionsDropdown;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div[1]/div/div/div[6]/span/div")
    private WebElement bookStoreDropdown;

    //Elements from the BookStore Dropdown
    @FindBy(xpath = "//*[@id=\"item-0\"]/span[contains(text(),'Login')]")
    private WebElement loginButton;

    @FindBy(id = "item-2")
    private WebElement bookStoreButton;

    @FindBy(id = "item-3")
    private WebElement profileButton;

    @FindBy(id = "item-4")
    private WebElement APIButton;

    /**
     * Click on the "Login" button
     *
     * @return LoginPage
     */
    @Step("Click on the 'Login' button")
    public LoginPage clickLoginButton(){
        logger.debug("Click on the 'Login' button in the left panel");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");
        waitForElementToAppear(loginButton,"Login button did not appear");
        loginButton.click();
        return new LoginPage(driver);
    }

    /**
     * Click on the "Book Store" button
     *
     * @return BookStorePage
     */
    @Step("Click on the 'Book Store' button")
    public BookStorePage clickBookStoreButton(){
        logger.debug("Click on the 'Book Store' button in the left panel");
        loginButton.click();
        return new BookStorePage(driver);
    }

    /**
     * Click on the "Profile" button
     *
     * @return ProfilePage
     */
    @Step("Click on the 'Profile' button")
    public ProfilePage clickProfileButton(){
        logger.debug("Click on the 'Profile' button in the left panel");
        loginButton.click();
        return new ProfilePage(driver);
    }

    /**
     * Click on the "Book Store API" button
     *
     * @return BookAPIPage
     */
    @Step("Click on the 'Book Store API' button")
    public BookStoreAPIPage clickAPIButton(){
        logger.debug("Click on the 'Book Store API' button in the left panel");
        loginButton.click();
        return new BookStoreAPIPage(driver);
    }

}

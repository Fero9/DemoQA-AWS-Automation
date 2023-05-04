package pageobjects.pages.bookstorepage;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePage;
import ru.yandex.qatools.allure.annotations.Step;

public class ProfilePage extends BasePage {
    public ProfilePage(WebDriver driver){super(driver);}

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[1]/div[contains(text(),'Profile')]")
    private WebElement mainHeaderText;
    @FindBy(xpath = "//*[@id=\"userName-value\"]")
    private WebElement userNameValue;
    @FindBy(id = "gotoStore")
    private WebElement goToBookStoreButton;
    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div[3]/div[2]/button[contains(text(),' Account')]")
    private WebElement deleteAccountButton;
    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div[3]/div[3]/button[contains(text(),' Books')]")
    private WebElement deleteAllBooksButton;
    @FindBy(xpath = "/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div[1]/div[3]/button[contains(text(),'Log out')]")
    private WebElement logOutButton;

    @Step("Wait for the 'Profile' page to load")
    public void waitForPageToLoad(){
        logger.debug("Wait for the 'Profile' page to load");
        waitForElementToAppear(mainHeaderText, "The 'Profile' page has not been loaded correctly");
    }
    @Step("Get username value")
    public String getUserName(){
        logger.debug("Get the username value");
        waitForElementToAppearWithCustomTime(userNameValue,"The username did not appear", 5);
        return userNameValue.getText();
    }

    @Step("Verify that the buttons are present")
    public void verifyButtons(){
        logger.debug("Verify that the 'Go To Book Store' button is present");
        String expectedBookStoreButtonText = "Go To Book Store";
        String actualBookStoreButtonText = goToBookStoreButton.getText().toString();
        Assert.assertEquals("The text on the button is not correct",expectedBookStoreButtonText,actualBookStoreButtonText);

        logger.debug("Verify that the 'Delete Account' button is present");
        String expectedDeleteAccountButtonText = "Delete Account";
        String actualDeleteAccountButtonText = deleteAccountButton.getText().toString();
        Assert.assertEquals("The text on the button is not correct",expectedDeleteAccountButtonText,actualDeleteAccountButtonText);

        logger.debug("Verify that the 'Delete All Books' button is present");
        String expectedDeleteBooksButtonText = "Delete All Books";
        String actualDeleteBooksButtonText = deleteAllBooksButton.getText().toString();
        Assert.assertEquals("The text on the button is not correct",expectedDeleteBooksButtonText,actualDeleteBooksButtonText);
    }

    @Step("Log out from the page")
    public void clickLogOut(){
        logger.debug("Click on the 'Log out' button");
        logOutButton.click();
    }
}

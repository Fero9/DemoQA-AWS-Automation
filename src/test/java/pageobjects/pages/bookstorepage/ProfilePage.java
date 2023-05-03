package pageobjects.pages.bookstorepage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePage;
import ru.yandex.qatools.allure.annotations.Step;

public class ProfilePage extends BasePage {
    public ProfilePage(WebDriver driver){super(driver);}


    @FindBy(className = "form-label")
    private WebElement userNameValue;

    @Step("Get username value")
    public String getUserName(){
        logger.debug("Get the username value");
        waitForElementToAppearWithCustomTime(userNameValue,"The username did not appear", 5);
        return userNameValue.getText();
    }
}

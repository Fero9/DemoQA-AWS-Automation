package pageobjects.pages.bookstorepage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePage;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver){super(driver);}
    @FindBy(id = "userName")
    private WebElement userName;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login")
    private WebElement loginButton;

    @FindBy(id = "newUser")
    private WebElement registrationLink;

    @FindBy(xpath = "//*[@id=\"userForm\"]/div[1]/h5")
    private WebElement loginText;

    public void enterUserName(String username) {
        logger.debug("Enter username");
        userName.sendKeys(username);
    }

    public void enterPassword(String pass) {
        logger.debug("Enter password");
        password.sendKeys(pass);
    }

    public void clickLoginButton() {
        logger.debug("Click on the 'Login' button");
        loginButton.click();
    }

    public void clickRegistrationLink() {
        logger.debug("Click on the 'New User' button");
        registrationLink.click();
    }

    public String getText(){
       return loginText.getText();
    }
}

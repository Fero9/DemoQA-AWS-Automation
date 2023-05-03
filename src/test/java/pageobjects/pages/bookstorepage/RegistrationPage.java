package pageobjects.pages.bookstorepage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePage;

public class RegistrationPage extends BasePage {
    public RegistrationPage(WebDriver driver){super(driver);}

    @FindBy(id = "firstname")
    private WebElement firstNameField;

    @FindBy(id = "lastname")
    private WebElement lastNameField;

    @FindBy(id = "userName")
    private WebElement userNameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"recaptcha-anchor\"]/div[contains(text(),' robot')]")
    private WebElement captchaBox;
    @FindBy(id = "register")
    private WebElement registerButton;

    @FindBy(id = "gotologin")
    private WebElement goToLoginButton;

    /*
    @FindBy(className = "piereg_message")
    private WebElement successMessage;

     */


    public void enterFirstName(String firstname) {
        logger.debug("Enter a first name");
        firstNameField.sendKeys(firstname);
    }

    public void enterLastName(String lastname) {
        logger.debug("Enter a last name");
        lastNameField.sendKeys(lastname);
    }

    public void enterUserName(String username) {
        logger.debug("Enter a username");
        userNameField.sendKeys(username);
    }

    public void enterPassword(String pass) {
        logger.debug("Enter a password");
        passwordField.sendKeys(pass);
    }

    public void clickRegisterButton() {
        logger.debug("Click on the 'Register' Button");
        registerButton.click();
    }

    public void clickBackToLoginButton(){
        logger.debug("Click on the 'Back to Login' button");
        goToLoginButton.click();
    }

    public void checkCaptacha(){
        logger.debug("Check the Captcha");
        waitForElementToAppear(captchaBox,"The captcha box did not appear");
        captchaBox.click();
    }

    public String getSuccessMessage() {
        driver.switchTo().alert().accept();
        return driver.switchTo().alert().getText();
    }
}

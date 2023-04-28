package pageobjects.pages.landingpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePage;
import pageobjects.pages.alertswindowspage.AlertsWindowsPage;
import pageobjects.pages.bookstorepage.BookStorePage;
import pageobjects.pages.elementspage.ElementsPage;
import pageobjects.pages.formspage.FormsPage;
import pageobjects.pages.interactionspage.InteractionsPage;
import pageobjects.pages.widgetspage.WidgetsPage;
import ru.yandex.qatools.allure.annotations.Step;

public class LandingPage extends BasePage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div/div[1]")
    private WebElement elements;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div/div[2]")
    private WebElement forms;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div/div[3]")
    private WebElement alertsFrameWindows;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div/div[4]")
    private WebElement widgets;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div/div[5]")
    private WebElement interactions;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div/div[6]")
    private WebElement bookStoreApplication;

    public LandingPage(WebDriver driver) {super(driver);}

    /**
     * Click on the "Elements" element
     *
     * @return ElementsPage
     */
    @Step("Click on the 'Elements' element")
    public ElementsPage clickOnElements() {
        logger.debug("Click on the 'Elements' element");
        elements.click();
        return new ElementsPage(driver);
    }

    /**
     * Click on the "Forms" element
     *
     * @return FormsPage
     */
    @Step("Click on the 'Interactions' element")
    public FormsPage clickOnForms() {
        logger.debug("Click on the 'Forms' element");
        forms.click();
        return new FormsPage(driver);
    }

    /**
     * Click on the "Alerts, Frame & Windows" element
     *
     * @return AlertsWindowPage
     */
    @Step("Click on the 'Alerts, Frame & Windows' element")
    public AlertsWindowsPage clickOnAlerts(){
        logger.debug("Click on the 'Alerts, Frame & Windows' element");
        alertsFrameWindows.click();
        return new AlertsWindowsPage(driver);
    }

    /**
     * Click on the "Widgets" element
     *
     * @return WidgetsPage
     */
    @Step("Click on the 'Interactions' element")
    public WidgetsPage clickOnWidgets(){
        logger.debug("Click on the 'Widgets' element");
        widgets.click();
        return new WidgetsPage(driver);
    }

    /**
     * Click on the "Interactions" element
     *
     * @return InteractionsPage
     */
    @Step("Click on the 'Interactions' element")
    public InteractionsPage clickOnInteractions(){
        logger.debug("Click on the 'Interactions' element");
        interactions.click();
        return new InteractionsPage(driver);
    }

    /**
     * Click on the "Book Store Application" element
     *
     * @return BookStorePage
     */
    @Step("Click on the 'Book Store Application' element")
    public BookStorePage clickOnBookStoreApp(){
        logger.debug("Click on the 'Book Store Application' element");
        bookStoreApplication.click();
        return new BookStorePage(driver);
    }

}

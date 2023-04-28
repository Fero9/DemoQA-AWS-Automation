package pageobjects.base;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage extends LoadableComponent<BasePage> {
    protected static final int FULL_WAIT = 30;

    protected static final int numberOfCyclesForWait = 75;
    protected static final int SHORT_WAIT = 5;
    protected static int WAIT = FULL_WAIT;

    protected static Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait webDriverWait;

    protected BasePage(WebDriver driver) {
        init(driver);
        PageFactory.initElements(driver, this);
        this.get();
    }

    /**
     * BasePage constructor.
     *
     * @param driver WebDriver
     * @param root   root location
     * @param wait   wait
     */
    public BasePage(WebDriver driver, By root, boolean wait) {
        if (wait) {
            init(driver);

            WebElement rootelement = driver.findElement(root);
            while (waitForElementToDisappear(rootelement)) {
                rootelement = driver.findElement(root);
            }
            initElementsUnderRoot(rootelement);
        } else {
            init(driver);
            initElementsUnderRoot(driver.findElement(root));
        }
    }

    protected BasePage(WebDriver driver, By root) {
        this(driver, root, false);
    }

    protected BasePage(WebDriver driver, WebElement root) {
        init(driver);
        initElementsUnderRoot(root);
    }

    public static final boolean isPresent(Class<? extends BasePage> clazz, Object... parameters) {
        return getNewInstanceOrNull(clazz, parameters) != null;
    }

    protected final boolean isPresent(By locator) {
        return isChildPresent(driver.findElement(By.xpath("//html")), locator);
    }

    /**
     * Checking if element is present.
     *
     * @param element WebElement
     * @return result
     */
    protected final boolean isPresent(WebElement element) {
        try {
            driver.manage().timeouts().implicitlyWait(SHORT_WAIT, TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(FULL_WAIT, TimeUnit.SECONDS);
        }
    }

    protected final Boolean waitForElementToBeInvisible(By locator, String errorMessage) {
        return webDriverWait.withMessage(errorMessage)
                .until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
    }

    @SuppressWarnings("unchecked")
    public static final BasePage getNewInstanceOrNull(Class<? extends BasePage> clazz, Object... parameters) {
        WebDriver driver = null;
        try {
            Class<? extends Object>[] parametertyps = new Class[parameters.length];
            int i = 0;
            for (Object parameter : parameters) {
                parametertyps[i] = parameter.getClass();
                i++;
            }
            logger.trace("getNewInstance starts");
            driver = (WebDriver) parameters[0];
            driver.manage().timeouts().implicitlyWait(SHORT_WAIT, TimeUnit.SECONDS);
            return (BasePage) getConstructorForArgs(clazz, parametertyps).newInstance(parameters);
        } catch (Exception | Error e) {
            return null;
        } finally {
            logger.trace("getNewInstance ends");
            driver.manage().timeouts().implicitlyWait(FULL_WAIT, TimeUnit.SECONDS);
        }
    }

    protected final boolean isChildPresent(WebElement root, By childLocator) {
        try {
            driver.manage().timeouts().implicitlyWait(SHORT_WAIT, TimeUnit.SECONDS);

            root.findElement(childLocator);

            return true;
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(FULL_WAIT, TimeUnit.SECONDS);
        }
    }

    protected final boolean isChildDisplayed(WebElement root, By childLocator) {
        try {
            driver.manage().timeouts().implicitlyWait(SHORT_WAIT, TimeUnit.SECONDS);

            return root.findElement(childLocator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(FULL_WAIT, TimeUnit.SECONDS);
        }
    }

    private void init(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, WAIT);
    }

    private void initElementsUnderRoot(WebElement root) {
        //waitForElementToAppear(root, "The root element should be present");
        PageFactory.initElements(new DefaultElementLocatorFactory(root), this);
        this.get();
    }

    protected final WebElement waitForElementToAppear(WebElement webElement, String errorMessage) {
        return webDriverWait.withMessage(errorMessage).until(ExpectedConditions.visibilityOf(webElement));
    }

    protected final WebElement waitForElementToAppearWithCustomTime(WebElement webElement, String errorMessage,
                                                                    int waitInSeconds) {
        return new WebDriverWait(driver, waitInSeconds).withMessage(errorMessage)
                .until(ExpectedConditions.visibilityOf(webElement));
    }

    protected final WebElement waitForElementToAppearWithCustomTime(By locator, String errorMessage, int waitInSeconds) {
        return new WebDriverWait(driver, waitInSeconds).withMessage(errorMessage)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected final List<WebElement> waitForAllElementsToAppear(List<WebElement> webElements, String errorMessage) {
        return webDriverWait.withMessage(errorMessage).until(ExpectedConditions.visibilityOfAllElements(webElements));
    }

    @SuppressWarnings("ucd")
    protected final List<WebElement> waitForAllElementsToAppearByLocatorWithCustomTime(By locator, String errorMessage,
                                                                                       int waitInSeconds) {
        return new WebDriverWait(driver, waitInSeconds).withMessage(errorMessage)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }


    @Override
    protected void isLoaded(){

    }

    @Override
    protected void load(){

    }

    protected final List<WebElement> waitForAllElementsToAppearByLocator(By locator, String errorMessage) {
        return webDriverWait.withMessage(errorMessage).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected final WebElement waitForElementToAppearByLocator(By locator, String errorMessage) {
        return webDriverWait.withMessage(errorMessage).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected final WebElement waitForElementToAppearByLocatorWithCustomTime(By locator, String errorMessage, int waitInSeconds) {
        return new WebDriverWait(driver, waitInSeconds).withMessage(errorMessage).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected final WebElement waitForElementToBeClickable(WebElement webElement, String errorMessage) {
        return webDriverWait.withMessage(errorMessage).until(ExpectedConditions.elementToBeClickable(webElement));
    }

    protected final WebElement waitForElementToBeClickableByLocator(By locator, String errorMessage) {
        return webDriverWait.withMessage(errorMessage).until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected final WebElement waitForElementToBeVisible(By webElement, String errorMessage) {
        return webDriverWait.withMessage(errorMessage).until(ExpectedConditions.visibilityOfElementLocated(webElement));
    }

    @SuppressWarnings("ucd")
    protected final boolean waitForElementToDisappear(By element, String errorMessage) {

        return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    protected final boolean waitForElementToDisappear(WebElement element, String errorMessage) {
        try {
            element.findElement(By.xpath("."));
            return webDriverWait.withMessage(errorMessage).until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            if (e.getCause() instanceof NoSuchElementException) {
                return true;
            } else {
                e.printStackTrace();
                return false;
            }
        } catch (StaleElementReferenceException e) {
            logger.trace("SERE");
            sleep(100);
        } catch (NoSuchElementException e) {
            logger.trace("No such element");
            // Disappeared
            return true;
        } finally {
            driver.manage().timeouts().implicitlyWait(FULL_WAIT, TimeUnit.SECONDS);
        }
        return false;
    }

    protected final boolean waitForElementToDisappear(WebElement rootelement, int cycles) {
        int count = cycles;
        while (count > 0) {
            try {
                if (rootelement.isDisplayed()) {
                    sleep(200);
                } else {
                    // Disappeared
                    return true;
                }
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                // Disappeared
                return true;
            }
            count--;
        }
        return false;
    }

    protected final boolean waitForElementToDisappear(WebElement rootelement) {
        return waitForElementToDisappear(rootelement, 10);
    }

    protected final boolean waitForElementToDisappear(By locator) {
        return waitForElementToDisappear(driver, locator);
    }

    protected final boolean waitForElementToDisappearWithCustomTime(By locator, int cycle) {
        return waitForElementToDisappear(driver, locator, cycle);
    }

    protected final boolean waitForElementToDisappear(SearchContext root, By locator, int cycles) {
        int count = cycles;
        List<WebElement> elements;
        while (count > 0) {
            try {
                driver.manage().timeouts().implicitlyWait(SHORT_WAIT, TimeUnit.SECONDS);
                elements = root.findElements(locator);

                boolean isVisible = false;
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        sleep(100);
                        isVisible = true;
                        break;
                    }
                }
                if (!isVisible) {
                    // Disappeared
                    logger.trace("Disappeared");
                    return true;
                }
            } catch (StaleElementReferenceException e) {
                logger.trace("SERE");
                sleep(100);
            } catch (NoSuchElementException e) {
                logger.trace("No such element");
                // Disappeared
                return true;
            } finally {
                driver.manage().timeouts().implicitlyWait(FULL_WAIT, TimeUnit.SECONDS);
            }
            count--;
        }
        return false;

    }

    protected final boolean waitForElementToDisappear(SearchContext root, By locator) {
        return waitForElementToDisappear(root, locator, 10);
    }

    protected final void waitForOneToAppear(WebElement root, By... locators) {
        int count = 0;
        final int maxCount = 10;

        while (count < maxCount) {
            for (By locator : locators) {
                if (isChildPresent(root, locator) && root.findElement(locator).isDisplayed()) {
                    return;
                }
            }
            count++;
            sleep(WAIT * 1000 / maxCount);
        }

        throw new AssertionError("None of the given elemetnts appeared");
    }

    protected final void waitForAttibuteToContain(WebElement root, By locator, String attribute, String value) {
        try {
            driver.manage().timeouts().implicitlyWait(SHORT_WAIT, TimeUnit.SECONDS);
            int count = 10;
            while (count > 0) {
                try {
                    WebElement element = root.findElement(locator);
                    String attributeValue = element.getAttribute(attribute);
                    Assert.assertTrue("The attribut not contains the require value", attributeValue.contains(value));
                    return;
                } catch (NoSuchElementException e) {
                    count--;
                } catch (AssertionError e) {
                    sleep(2000);
                    count--;
                }
            }
            throw new Error("The attribute is still missing\n" + locator + " " + root.findElement(locator).getAttribute("outerHTML"));
        } finally {
            driver.manage().timeouts().implicitlyWait(FULL_WAIT, TimeUnit.SECONDS);
        }
    }

    protected final void waitForAttibuteToDisappear(WebElement root, By locator, String attribute, String value) {
        try {
            driver.manage().timeouts().implicitlyWait(SHORT_WAIT, TimeUnit.SECONDS);
            int count = 20;
            while (count > 0) {
                try {
                    WebElement element = root.findElement(locator);
                    Assert.assertFalse("The attribute contains the require value",
                            element.getAttribute(attribute).contains(value));
                    return;
                } catch (AssertionError e) {
                    sleep(2000);
                    count--;
                } catch (NullPointerException | NoSuchElementException | StaleElementReferenceException e) {
                    e.printStackTrace();
                    return;
                }
            }
        } finally {
            driver.manage().timeouts().implicitlyWait(FULL_WAIT, TimeUnit.SECONDS);
        }

    }

    protected final void waitForElementToStop(WebElement element) {
        int count = 100;
        Point location = element.getLocation();
        sleep(200);
        boolean isMoved = !location.equals(element.getLocation());
        while (count > 0 && isMoved) {
            logger.trace("Element is moving");
            location = element.getLocation();
            count--;
            sleep(200);
            isMoved = !location.equals(element.getLocation());
        }

        if (isMoved) {
            throw new AssertionError("Element still moving!");
        }
    }

    protected final void waitForElementToStopChanging(WebElement element, String attribute) {
        int count = 100;
        String previousAttribute = element.getAttribute(attribute);
        sleep(200);
        String actualAttribute = element.getAttribute(attribute);
        while (count > 0 && !previousAttribute.equals(actualAttribute)) {
            previousAttribute = actualAttribute;
            sleep(200);
            actualAttribute = element.getAttribute(attribute);
            count--;
        }

        if (!previousAttribute.equals(actualAttribute)) {
            throw new AssertionError("Element is still changing!");
        }
    }

    protected final WebElement getElementOrNull(By locator) {
        WebDriverWait customWaiter = new WebDriverWait(driver, 2, 200);
        try {
            customWaiter.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator);
        } catch (TimeoutException e) {
            return null;
        }

    }

    /**
     * Open a new tab.
     *
     * @param link tab link
     */
    public final void openNewTab(String link) {
        logger.debug("Open a new tab");
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank', '_blank');");
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        driver.get(link);
    }

    /**
     * Close the current tab.
     */
    public final void closeTab() {
        logger.debug("Close the current tab");
        driver.close();
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
    }

    protected final void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("{0}")
    protected final void allureStep(String step) {
        logger.debug(step);
    }

    /**
     * https://stackoverflow.com/a/13483496
     */
  /*protected final String xpathSafe(String origin) {
    return "concat('" + origin.replace("'", "', \"'\", '") + "', '')"; */
    protected final String xpathSafe(String origin) {
        return "\"" + origin + "\"";
    }


    /**
     * https://stackoverflow.com/a/9244175 returns the first match, or null when no
     * match found
     */
    @SuppressWarnings("rawtypes")
    private static final Constructor<?> getConstructorForArgs(Class<?> klass, Class[] args) {
        // Get all the constructors from given class
        Constructor<?>[] constructors = klass.getConstructors();

        for (Constructor<?> constructor : constructors) {
            // Walk through all the constructors, matching parameter amount and parameter
            // types with given types (args)
            Class<?>[] types = constructor.getParameterTypes();
            if (types.length == args.length) {
                boolean argumentsMatch = true;
                for (int i = 0; i < args.length; i++) {
                    // Note that the types in args must be in same order as in the constructor if
                    // the checking is done this way
                    if (!types[i].isAssignableFrom(args[i])) {
                        argumentsMatch = false;
                        break;
                    }
                }

                if (argumentsMatch) {
                    // We found a matching constructor, return it
                    return constructor;
                }
            }
        }

        // No matching constructor
        logger.error("No constructor found in class: " + klass + " with parameter types: " + args);
        return null;
    }

    public final void waitForAttributeToDisappear(SearchContext root, By locator, String attribute, String value, int waitTimeInMillis) {
        logger.trace("waitForAttributeToDisappear(WebElement root, By locator, String attribute, String value, int waitTimeInMillis)");
        try {
            int count = 0;
            while (count < numberOfCyclesForWait) {
                try {
                    WebElement element = root.findElement(locator);
                    if (element.getAttribute(attribute).contains(value)) {
                        throw new AssertionError("The attribute contains the value, attribute value: " + element.getAttribute(attribute) + ", should not contain: " + value);
                    }
                    return;
                } catch (StaleElementReferenceException | NoSuchElementException | AssertionError e) {
                    e.printStackTrace();
                    count++;
                    if (count < numberOfCyclesForWait) {
                        sleep(waitTimeInMillis);
                    } else
                        throw e;
                }
            }
        } catch (NullPointerException e) {
        }
    }


    public final void waitForAttwributeToDisappear(SearchContext root, By locator, String attribute, String value, int waitTimeInMillis) {
        logger.trace("waitForAttributeToDisappear(WebElement root, By locator, String attribute, String value, int waitTimeInMillis)");
        int count = 0;
        while (count < numberOfCyclesForWait) {
            try {
                WebElement element = root.findElement(locator);
                if (element.getAttribute(attribute).contains(value)) {
                    throw new AssertionError("The attribute contains the value, attribute value: " + element.getAttribute(attribute) + ", should not contain: " + value);
                }
                return;
            } catch (StaleElementReferenceException | NoSuchElementException | AssertionError e) {
                e.printStackTrace();
                count++;
                if (count < numberOfCyclesForWait) {
                    sleep(waitTimeInMillis);
                } else {
                    throw e;
                }
            } catch (NullPointerException e) {
                return;
            }
        }
    }
}

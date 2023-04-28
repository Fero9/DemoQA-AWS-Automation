package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    protected String browser;
    protected boolean headless;

    /**
     * . Class constructor which sets mode and browser taken from system properties
     */
    public DriverConfiguration() {
        this(
                getBrowserType(System.getProperty("browser")),
                getHeadless(System.getProperty("headless")));
    }

    /**
     * . Class constructor which takes browser and mode as parameters
     */
    public DriverConfiguration(String browser, boolean headless) {
        this.browser = browser;
        this.headless = headless;

        logger.debug("Browser: " + browser);
        logger.debug("Headless: " + headless);

    }

    public String getBrowser(){return browser;}

    private static String getBrowserType(String testBrowser){
        TestBrowser result = null;

        if (testBrowser == null || testBrowser.isEmpty()) {
            logger.info("Test browser was null. Setting CHROME mode.");
            result = TestBrowser.chrome;
            return result.name().toString();
        }

        switch (testBrowser) {
            case "chrome":
                result = TestBrowser.chrome;
                break;
            case "edge":
                result = TestBrowser.edge;
                break;
            case "firefox":
                result = TestBrowser.firefox;
                break;
            default:
                break;
        }

        logger.info("Test browser set to: " + result.toString());
        return result.name().toString();
    }

    public boolean isHeadless(){return headless;}

    private static boolean getHeadless(String headless){
        if (headless == null || headless.isEmpty()) {
            return false;
        }
        try {
            return Boolean.parseBoolean(headless);
        } catch (Throwable e) {
            return false;
        }
    }
}

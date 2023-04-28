package core;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {

    public static Logger logger = LoggerFactory.getLogger(TestBase.class);
    private boolean retryFlag = false;
    protected static DriverConfiguration driverConfiguration;
    static int retry;

    static {
        driverConfiguration = new DriverConfiguration();

        if (System.getProperty("retry") != null) {
            retry = Integer.parseInt(System.getProperty("retry"));
        } else {
            retry = 1;
        }
        logger.debug("Retry: " + retry);

    }

    protected WebDriver driver;

}

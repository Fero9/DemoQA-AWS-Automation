package core;

import com.microsoft.edge.seleniumtools.EdgeDriver;
import com.microsoft.edge.seleniumtools.EdgeDriverService;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;


public class DriverFactory {

    private static final Logger logger_DriverFactory = LoggerFactory.getLogger(DriverFactory.class);

    private static final String DOWNLOAD_FILE_PATH_ROOT = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "QA-Download";

    private static DriverFactory instance = new DriverFactory();

    public String downloadFilePath;

    private DriverFactory(){}

    public static DriverFactory getInstance() {return instance;}

    public WebDriver createDriver(DriverConfiguration testConfig, boolean headless){
        return getDriver(testConfig.getBrowser(), headless);
    }

    private WebDriver getDriver(String browser, boolean headless){
        WebDriver result = null;
        String driverLocation = getDriverLocation(browser);
        downloadFilePath = DOWNLOAD_FILE_PATH_ROOT + File.separator + "driver" + Thread.currentThread().getId();

        switch (browser) {
            case "firefox":

                System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverLocation);

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                setDriverBrowserLogging(firefoxOptions);

                LoggingPreferences prefFirfox = new LoggingPreferences();
                prefFirfox.enable(LogType.BROWSER, Level.ALL);
                firefoxOptions.setCapability(CapabilityType.LOGGING_PREFS, prefFirfox);

                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.download.useDownloadDir", true);
                profile.setPreference("browser.download.folderList", 2);
                profile.setPreference("browser.download.dir", downloadFilePath);
                profile.setPreference("browser.download.defaultFolder", downloadFilePath);
                profile.setPreference("browser.download.manager.showWhenStarting", false);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword, application/csv, application/vnd.ms-powerpoint, "
                        + "application/ris, text/csv, image/png, image/jpeg, application/pdf, text/html, text/plain, image/svg+xml, image/svg, "
                        + "application/zip, application/x-zip, application/x-zip-compressed, "
                        + "application/download, application/octet-stream, application/xls, "
                        + "application/vnd.ms-excel, application/x-xls, application/x-ms-excel, "
                        + "application/msexcel, application/x-msexcel, application/x-excel");
                profile.setPreference("browser.helperApps.alwaysAsk.force", false);
                profile.setPreference("browser.helperApps.neverAsk.openFile", " ");
                profile.setPreference("browser.download.manager.useWindow", false);
                profile.setPreference("browser.download.manager.focusWhenStarting", false);
                profile.setPreference("browser.download.manager.retention", 1);
                profile.setPreference("browser.download.manager.quitBehavior", 2);
                profile.setPreference("browser.download.manager.showAlertOnComplete", false);
                profile.setPreference("browser.download.manager.closeWhenDone", true);
                profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                profile.setPreference("security.fileuri.strict_origin_policy", false);

                firefoxOptions.setProfile(profile);


                result = new FirefoxDriver(firefoxOptions);
                result.manage().window().setSize(new Dimension(1920, 1080));

                if (!headless) {
                    result.manage().window().setSize(new Dimension(1920, 1080));
                }

                break;
            case "edge":
                System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, driverLocation);

                EdgeOptions edgeOptions = new EdgeOptions();
                setDriverBrowserLogging(edgeOptions);
                LoggingPreferences prefEdge = new LoggingPreferences();
                prefEdge.enable(LogType.BROWSER, Level.ALL);
                edgeOptions.setCapability(CapabilityType.LOGGING_PREFS, prefEdge);

                HashMap<String, Object> prefs = new HashMap<>();
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.default_directory", downloadFilePath);
                prefs.put("download.prompt_for_download", false);

                edgeOptions.setExperimentalOption("prefs", prefs);

                result = new EdgeDriver(edgeOptions);
                if (!headless) {
                    result.manage().window().setSize(new Dimension(1920, 1080));
                }
                break;
            default:
            case "chrome":
                System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverLocation);
                System.setProperty("webdriver.chrome.logfile",
                        System.getProperty("java.io.tmpdir") + File.separator + "chromedriver.log");
                System.setProperty("webdriver.chrome.verboseLogging", "true");
                HashMap<String, Object> chromePrefs = new HashMap<>();

                chromePrefs.put("download.default_directory", downloadFilePath);
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.prompt_for_download", false);
                chromePrefs.put("Browser.setDownloadBehavior", "allow");
                ChromeOptions chromeOptions = new ChromeOptions();

                LoggingPreferences prefChrome = new LoggingPreferences();
                prefChrome.enable(LogType.BROWSER, Level.ALL);
                chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, prefChrome);

                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
                chromeOptions.addArguments("disable-web-security"); // this should be removed after CORS policy issue is resolved
                chromeOptions.addArguments("window-size=1920x1080");
                // options.addArguments("screenshot");
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                setDriverBrowserLogging(chromeOptions);
                result = new ChromeDriver(chromeOptions);
                if (!headless) {
                    result.manage().window().setSize(new Dimension(1920, 1080));
                }
                break;
        }
        return result;
    }

    private void setDriverBrowserLogging(MutableCapabilities mc) {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        logs.enable(LogType.PERFORMANCE, Level.ALL);
        mc.setCapability(CapabilityType.LOGGING_PREFS, logs);
    }

    private String getDriverLocation(String browser) {
        String driverLocation = null;
        logger_DriverFactory.info("OS: " + System.getProperty("os.name"));
        String osLowerCase = StringUtils.deleteWhitespace(System.getProperty("os.name").toLowerCase());

        if (browser.equalsIgnoreCase("firefox")) {
            if (osLowerCase.contains("windows")) {
                driverLocation = new File("./src/test/resources/drivers/windows/geckodriver.exe").getAbsolutePath();
            } else if (osLowerCase.contains("linux")) {
                driverLocation = new File("./src/test/resources/drivers/linux/geckodriver").getAbsolutePath();
            } else if (osLowerCase.contains("osx")) {
                driverLocation = new File("./src/test/resources/drivers/mac/geckodriver").getAbsolutePath();
            }
        } else if (browser.equalsIgnoreCase("chrome")) {
            if (osLowerCase.contains("windows")) {
                driverLocation = new File("./src/test/resources/drivers/windows/chromedriver.exe").getAbsolutePath();
            } else if (osLowerCase.contains("linux")) {
                driverLocation = new File("./src/test/resources/drivers/linux/chromedriver").getAbsolutePath();
            } else if (osLowerCase.contains("osx")) {
                driverLocation = new File("./src/test/resources/drivers/mac/chromedriver").getAbsolutePath();
            }
        } else if (browser.equalsIgnoreCase("edge")) {
            if (osLowerCase.contains("windows")) {
                driverLocation = new File("./src/test/resources/drivers/windows/msedgedriver.exe").getAbsolutePath();
            } else if (osLowerCase.contains("linux")) {
                driverLocation = new File("./src/test/resources/drivers/linux/msedgedriver").getAbsolutePath();
            } else if (osLowerCase.contains("osx")){
                driverLocation = new File("./src/test/resources/drivers/mac/msedgedriver").getAbsolutePath();
            }
        }
        return driverLocation;
    }
}

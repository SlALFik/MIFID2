package utils;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by nlebedko on 21.03.16.
 */
public class SetupHelper {

    private static WebDriver driver = null;
    private static final Logger LOG = getLogger(SetupHelper.class);

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = getChromeDriver();
        }
        return driver;
    }

    /*This is additional approach how to manage driver*/
    /*public synchronized static WebDriver getDriver2() {
        if (driver == null) {
            driver = getChromeDriver();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    driver.close();
                }
            });
        }
        return driver;
    }*/

    public static void closeDriver() {
        try {
            getDriver().quit();
            driver = null;
            LOG.info("closing the browser");
        } catch (UnreachableBrowserException e) {
            LOG.info("cannot close browser: unreachable browser");
        }
    }


    public static WebDriver getFirefoxDriver() {
        WebDriver webDriver = new FirefoxDriver();
        LOG.info("Open Firefox browser");
        webDriver.manage().window().maximize();
        return webDriver;
    }

    public static WebDriver getChromeDriver() {
        setPathToChromeDriver();
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        LOG.info("Open Chrome browser");
        return webDriver;
    }

    private static void setPathToChromeDriver() {
        String os = System.getProperty("os.name").toLowerCase();
        String sep = File.separator;
        String pathToDriver = System.getProperty("user.dir") + sep + "src" + sep + "main" + sep + "resources" + sep;
        if (os.contains("win")) {
            pathToDriver += "chromedriver.exe";
        } else if (os.contains("linux")) {
            pathToDriver += "chromedriver";
        } else {
            Assert.assertTrue("OS unrecognised, unable to open chrome browser.", false);
        }
        System.setProperty("webdriver.chrome.driver", pathToDriver);
        LOG.info("path to chrome driver " + pathToDriver);
    }


}

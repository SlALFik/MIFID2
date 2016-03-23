package utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by nlebedko on 18.03.2016.
 */
public class PageMethods {

    private static int DELAY = 30;
    public WebDriver driver;
    private static final Logger LOG = getLogger(PageMethods.class);

    public PageMethods(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElement(final WebElement element) {
        try {
            new WebDriverWait(driver, DELAY).until(ExpectedConditions.visibilityOf(element));
            Callable callable = new Callable() {
                public Boolean call() throws Exception {
                    try {
                        return element.isDisplayed();
                    } catch (ExceptionInInitializerError | NoSuchElementException e) {
                        return false;
                    }
                }
            };
            Utils.waitFor(DELAY, callable);
        } catch (Exception exception) {
            assertNull("Element was NOT displayed " + element.toString(), exception.getMessage());
        }
        LOG.info("Element is displayed " + element.toString());
    }


}

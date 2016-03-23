package pages.google;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PageMethods;

import java.util.List;

/**
 * Created by nlebedko on 18.03.2016.
 */
public class GoogleAddResultPage extends PageMethods {

    public GoogleAddResultPage(WebDriver driver) {
        super(driver);
    }

    By resultName = By.xpath("//div[contains(@class, 'cwtlotc')]");

    public void verifyPageIsLoaded() {
        Assert.assertTrue(driver.findElements(resultName).size() > 0);
    }

    public List<WebElement> getResultList() {
        return driver.findElements(resultName);
    }
}

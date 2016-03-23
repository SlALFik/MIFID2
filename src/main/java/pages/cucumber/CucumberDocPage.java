package pages.cucumber;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.PageMethods;

/**
 * Created by nlebedko on 18.03.2016.
 */
public class CucumberDocPage extends PageMethods {

    public CucumberDocPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[@class='header' and contains(.,'Documentation Overview')]")
    WebElement docPageHeader;

    public void verifyPageIsLoaded() {
        waitForElement(docPageHeader);
    }

}

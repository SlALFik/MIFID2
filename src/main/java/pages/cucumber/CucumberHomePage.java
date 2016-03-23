package pages.cucumber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.PageMethods;

/**
 * Created by nlebedko on 18.03.2016.
 */
public class CucumberHomePage extends PageMethods {

    public CucumberHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[contains(.,'Docs')]")
    WebElement docButton;

    public void openPage() {
        driver.get("http://cucumber.io/");
        waitForElement(docButton);
    }

    public void goToDoc() {
        docButton.click();
    }

}

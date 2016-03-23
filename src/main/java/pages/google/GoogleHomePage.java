package pages.google;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.PageMethods;

/**
 * Created by nlebedko on 18.03.2016.
 */
public class GoogleHomePage extends PageMethods {

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "lst-ib")
    WebElement searchTextBox;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement searchButton;

    public void openPage() {
        driver.get("http://google.com");
        waitForElement(searchTextBox);
    }

    public void typetoSearch(String searchFor) {
        searchTextBox.sendKeys(searchFor);
    }

    public void doSearch(String searchFor) {
        searchTextBox.sendKeys(searchFor);
        searchButton.click();
    }

}

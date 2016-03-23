package steps.googleadd;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import pages.google.GoogleHomePage;
import pages.google.GoogleAddResultPage;

import java.io.File;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static utils.SetupHelper.getDriver;
import static utils.Utils.doScreenshot;
import static utils.Utils.saveScreenshot;

/**
 * Created by nlebedko on 18.03.2016.
 */
public class GoogleAddSteps {

    private GoogleHomePage googleHomePage = PageFactory.initElements(getDriver(), GoogleHomePage.class);
    private GoogleAddResultPage googleAddResultPage = PageFactory.initElements(getDriver(), GoogleAddResultPage.class);
    private static final Logger LOG = getLogger(GoogleAddTest.class);
    private static File screenshot;

    @After
    public void after(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            screenshot = doScreenshot(getDriver());
            saveScreenshot(screenshot, scenario);
            LOG.error(" >>>>>>>>>>> TEST FAILED " + scenario.getName() + " <<<<<<<<<<<< ");
        } else {
            LOG.error("TEST PASSED " + scenario.getName());
        }
    }

    @Given("I open google home page")
    public void openGoogleHomePage() {
        googleHomePage.openPage();
    }

    @When("first additive is (.*)")
    public void searchFor1(String searchFor) {
        googleHomePage.typetoSearch(searchFor + " + ");
    }

    @And("second additive is (.*)")
     public void searchFor2(String searchFor) {
        googleHomePage.doSearch(searchFor);
    }


    @Then("result is (.*)")
    public void verifySearchResult(String searchFor) {
        googleAddResultPage.verifyPageIsLoaded();
        List<WebElement> results = googleAddResultPage.getResultList();
        boolean resultCorrect = false;
        for (WebElement result : results) {
            if (result.getText().toLowerCase().contains(searchFor.toLowerCase())) {
                resultCorrect = true;
            }
        }
        LOG.info("Result aof aadition is " + results.get(0).getText());
        Assert.assertTrue("Google result is invalid", resultCorrect);
    }


}

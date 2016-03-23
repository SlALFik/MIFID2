package steps.google;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import pages.google.GoogleHomePage;
import pages.google.GoogleResultPage;

import java.io.File;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static utils.SetupHelper.getDriver;
import static utils.Utils.doScreenshot;
import static utils.Utils.saveScreenshot;

/**
 * Created by nlebedko on 18.03.2016.
 */
public class GoogleSearchSteps {

    private GoogleHomePage googleHomePage = PageFactory.initElements(getDriver(), GoogleHomePage.class);
    private GoogleResultPage googleResultPage = PageFactory.initElements(getDriver(), GoogleResultPage.class);
    private static final Logger LOG = getLogger(GoogleSearchTest.class);
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

    @When("I search for (.*) website")
    public void searchFor(String searchFor) {
        googleHomePage.doSearch(searchFor);
    }


    @Then("(.*) website is first result")
    public void verifySearchResult(String searchFor) {
        googleResultPage.verifyPageIsLoaded();
        List<WebElement> results = googleResultPage.getResultList();
        boolean resultCorrect = false;
        for (WebElement result : results) {
            if (result.getText().toLowerCase().contains(searchFor.toLowerCase())) {
                resultCorrect = true;
            }
        }
        LOG.info("First element is " + results.get(0).getText());
        Assert.assertTrue("Google result is invalid", resultCorrect);
    }


}

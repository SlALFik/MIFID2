package steps.cucumber;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import pages.cucumber.CucumberDocPage;
import pages.cucumber.CucumberHomePage;
import pages.google.GoogleHomePage;
import pages.google.GoogleResultPage;
import steps.google.GoogleSearchTest;

import java.io.File;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static utils.SetupHelper.getDriver;
import static utils.Utils.doScreenshot;
import static utils.Utils.saveScreenshot;

/**
 * Created by nlebedko on 18.03.2016.
 */
public class CucumberSteps {

    private CucumberHomePage cucumberHomePage = PageFactory.initElements(getDriver(), CucumberHomePage.class);
    private CucumberDocPage cucumberDocPage = PageFactory.initElements(getDriver(), CucumberDocPage.class);
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

    @Given("I open cucumber home page")
    public void openCucumberHomePage() {
        cucumberHomePage.openPage();
    }

    @When("I go to doc page")
    public void openDocs() {
        cucumberHomePage.goToDoc();
    }


    @Then("doc page is opened")
    public void verifyDocPage() {
        cucumberDocPage.verifyPageIsLoaded();
        LOG.info("Documenation page is opened");
    }


}

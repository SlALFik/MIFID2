package steps.cucumber;

import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import steps.AfterSuite;
import steps.BeforeSuite;
import steps.ExtendedRunner;
import utils.SetupHelper;
import utils.Utils;

/**
 * Created by nlebedko on 18.03.2016.
 */

@CucumberOptions(features = {"classpath:features/cucumber.feature"},
        glue = {"steps.cucumber"},
        plugin = {"pretty", "html:target/cucumber-html-report"}
//        format = {"pretty", "html:target/site/cucumber-pretty", "json:target/site/cucumber-pretty/cucumber2.json"}
)
@RunWith(ExtendedRunner.class)
public class CucumberTest {

    @BeforeSuite
    public static void setUp() {
        SetupHelper.getDriver();
    }

    @AfterSuite
    public static void tearDown() throws Exception {
        SetupHelper.closeDriver();
        Utils.clearResults();
    }

}

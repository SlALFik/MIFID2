package steps.googleadd;

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
@CucumberOptions(features = {"classpath:features/googleadd.feature"},
        glue = {"steps.googleadd"},
        plugin = {"pretty", "html:target/cucumber-html-report"}
//        format = {"html:target/test-report", "json:target/google.json", "junit:target/google.xml"}
//        format = {"pretty", "html:target/site/cucumber-pretty", "json:target/site/cucumber-pretty/cucumber.json"}
)
@RunWith(ExtendedRunner.class)
public class GoogleAddTest {

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

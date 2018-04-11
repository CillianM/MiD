package ie.mid.identityengine.integration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import ie.mid.identityengine.category.IntegrationTests;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@Category(IntegrationTests.class)
@RunWith(Cucumber.class)
@CucumberOptions(
        features = ".",
        format = {"pretty", "html:target/cucumber", "json:target/mid-integration-test.json"},
        glue = {"ie.mid.identityengine.integration.stepdefs"}
)
public class MiDIntegrationTests {
}

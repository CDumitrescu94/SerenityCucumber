package starter.definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import starter.steps.GenericSteps;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GenericDefinitions {


    @Steps
    GenericSteps genericSteps;

    private Response response;

    @Given("the client uses the following url parameters:")
    public void theFollowingUrlParameters(Map<String, String> urlParameters) {
        genericSteps.setDecorateUrl(urlParameters);
    }

    @When("the client calls {string} endpoint")
    public void theUserCallsEndpoint(String webServiceEndpoint) {
        this.response = genericSteps.callEndpoint(webServiceEndpoint);
    }
    @When("the client calls {string} endpoint with success")
    public void theUserCallsEndpointWithSuccess(String webServiceEndpoint) {
        theUserCallsEndpoint(webServiceEndpoint);
        theClientShouldReceiveHttpResponseCode(200);
    }
    @When("the client calls {string} endpoint with body {string}")
    public void theUserCallsEndpoint(String webServiceEndpoint, String body) {
        this.response = genericSteps.callEndpoint(webServiceEndpoint, body);
    }

    @Then("the client should receive an HTTP {int} response code")
    public void theClientShouldReceiveHttpResponseCode(int httpCode) {
        assertThat(response.getStatusCode(), equalTo(httpCode));
    }

    @Then("the client using jsonPath {string} should see {string}")
    public void theClientShouldSee(String jsonPath, String expected) {
        String result = JsonPath.from(response.asString()).get(jsonPath).toString();
        assertThat(result, equalTo(expected));
    }


}

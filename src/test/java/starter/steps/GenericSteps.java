package starter.steps;

import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import starter.WebServiceEndPoints;

import java.util.Map;
import java.util.stream.Collectors;

public class GenericSteps {

    private String decorateUrl = "";

    @Step("Call web service endpoint")
    public Response callEndpoint(String webServiceEndpoint) {
        return callEndpointHttp(webServiceEndpoint,"");
    }

    @Step("Call web service endpoint with body")
    public Response callEndpoint(String webServiceEndpoint, String body) {
        return callEndpointHttp(webServiceEndpoint, body);
    }


    private String decorateUrl(Map<String,String> parameters) {

        return parameters.keySet().stream()
                .map(key -> key + "=" + parameters.get(key))
                .collect(Collectors.joining("&","?",""));
    }

    public void setDecorateUrl(Map<String,String> parameters) {
        this.decorateUrl = decorateUrl(parameters);
    }

    private Response callEndpointHttp(String webServiceEndpoint, String body) {
        String url = WebServiceEndPoints.valueOf(webServiceEndpoint).getUrl() + this.decorateUrl;
        String method = WebServiceEndPoints.valueOf(webServiceEndpoint).getMethod();
        return callEndpointHttpMethod(url, method, body);

    }

    private Response callEndpointHttpMethod(String url, String method, String body) {
        switch (method) {
            case "GET":
                return callEndpointHttpMethodGet(url);
            case "POST":
                return callEndpointHttpMethodPost(url, body);
            default:
                throw new Error("Method" + method + "not supported");
        }
    }

    private Response callEndpointHttpMethodGet(String url) {
        System.out.println(url);
        Response response = SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .get(url);
        if(response.asString().contains("API rate limit exceeded")) {
            Serenity.pendingStep("API rate limit exceeded");
        }
        return response;

    }

    private Response callEndpointHttpMethodPost(String url, String body) {
        Response response = SerenityRest.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(url);
        return response;
    }
}

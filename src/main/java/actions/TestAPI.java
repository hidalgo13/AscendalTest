package actions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestAPI {
    RequestSpecification request = null;
    JSONObject requestParams;

    @DataProvider(name = "NegativeCases")

    public static Object[][] negativeCases() {

        return new Object[][]{{"4000000000009979", "stolen_card"}, {"4000000000009995", "insufficient_funds"}, {"4000000000009987", "lost_card"}};

    }

    @DataProvider(name = "ErrorCases")
    public static Object[][] errorCases() {

        return new Object[][]{{"40000007600000", "invalid_request_error"}};

    }

    @DataProvider(name = "PositiveCases")
    public static Object[][] positiveCases() {

        return new Object[][]{{"4100000000000019", "blocked"}, {"4242424242424242", "succeeded"}};

    }

    @BeforeTest
    public void Setup() {
        RestAssured.baseURI = "http://5df9c4eee9f79e0014b6b2eb.mockapi.io/";
        request = RestAssured.given();

        // Add a header stating the Request body is a JSON
        request.header("Content-Type", "application/json");
        requestParams = new JSONObject();
        requestParams.put("amount", "999");
        requestParams.put("currenncy", "usd");
    }

    @Test(dataProvider = "NegativeCases")
    public void testNegativeCases(String codeID, String description) {


// Add the Json to the body of the request
        request.body(requestParams.toJSONString());

// Post the request and check the response
        Response response = request.post("charge/" + codeID);
// Assert the status code of response
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);
        // Assert the body contains string as expectation
        String bodyAsString = response.body().asString();
        Assert.assertEquals(bodyAsString.contains(description), true);
        System.out.println("Response body contains: " + bodyAsString);
    }

    @Test(dataProvider = "PositiveCases")
    public void testPositiveCases(String codeID, String description) {


        JSONObject requestParams = new JSONObject();

// Add the Json to the body of the request
        request.body(requestParams.toJSONString());

// Post the request and check the response
        Response response = request.post("charge/" + codeID);

        // Assert the status code of response
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);

        // Assert the body contains string as expectation
        String bodyAsString = response.body().asString();
        Assert.assertEquals(bodyAsString.contains(description), true);
        System.out.println("Response body contains: " + bodyAsString);
    }

    @Test(dataProvider = "ErrorCases")
    public void testErrorCases(String codeID, String description) {
        JSONObject requestParams = new JSONObject();

// Add the Json to the body of the request
        request.body(requestParams.toJSONString());

// Post the request and check the response
        Response response = request.post("charge/" + codeID);

// Assert the status code of response
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);

// Assert the body contains string as expectation
        String bodyAsString = response.body().asString();
        Assert.assertEquals(bodyAsString.contains(description), true);
        System.out.println("Response body contains: " + bodyAsString);
    }

}

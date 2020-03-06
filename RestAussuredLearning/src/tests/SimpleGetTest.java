package tests;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.*;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.hamcrest.Matchers.equalTo;

public class SimpleGetTest {

	@Test
	public void GetWeatherDetails()
	{   
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

		// Get the RequestSpecification of the request that you want to sent
		// to the server. The server is specified by the BaseURI that we have
		// specified in the above step.
		RequestSpecification httpRequest = RestAssured.given();

		// Make a request to the server by specifying the method Type and the method URL.
		// This will return the Response from the server. Store the response in a variable.
		Response response = httpRequest.request(Method.GET, "/Indore");

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

	}
	
	@Test public void
    body_is_url_encoded_when_setting_body() throws Exception {
        given().
                contentType(ContentType.URLENC).
                body("firstName=John&lastName=Doe&").
        expect().
                body("greeting.firstName", equalTo("John")).
                body("greeting.lastName", equalTo("Doe")).
        when().
                post("/greetXML");
    }
	
	@Test
	public void exampleRestTest() {
	    given()
	        .contentType(ContentType.JSON)
	        .pathParam("id", "AskJsd8Sd")
	    .when()
	        .get("/examplepath/{id}")
	    .then()
	        .statusCode(400)
	        .body("firstName", equalTo("Onur"))
	        .body("Surname", equalTo("Baskirt"));
	}

}
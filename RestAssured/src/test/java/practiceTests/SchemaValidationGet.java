package practiceTests;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.*;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SchemaValidationGet {

	@BeforeTest
	public void setup(){
		
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1/employee";
		RestAssured.basePath = "/17370";
	}
    
	@Test(enabled = true)
	public void DataValidation() {
		Response response = given().get();
		
		//Capturing complete response body and converting it to string.
		String responseBody = response.body().asString();
		System.out.println("response Body is: " +responseBody);

		Assert.assertEquals(response.statusCode(), 200, "Correct status code is returned.");
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		 
		String eName = jsonPathEvaluator.get("employee_name");
		// Validate the response
		Assert.assertEquals(eName, "Ravi", "Correct value of Employee name received in the Response");
	 
	}
	@Test(enabled = true) 
	public void SchemaValidation() {
		try {
			given()
	        .get()
	        .then()
	        .assertThat()
	        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getSchema.json"))
	        .extract()
	        .response();
		}
		finally {
			System.out.println("Schema is validated.");
		}
	}
	
}

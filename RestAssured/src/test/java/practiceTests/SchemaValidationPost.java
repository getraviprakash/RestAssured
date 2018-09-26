package practiceTests;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.json.*;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

public class SchemaValidationPost {
	
	@Test
	public void EmployeeAddition() throws JSONException, IOException
	{
	
	RestAssured.baseURI ="http://dummy.restapiexample.com/api/v1/create";
	RequestSpecification request = RestAssured.given();
	String filePath = "D:\\Eclipse Workspace\\RestAssured\\target\\test-classes\\ReadJson.json";
       
	try {
		FileInputStream fis = new FileInputStream(new File(filePath));
 
        //Reading data input file
        JsonReader reader = Json.createReader(fis);
        JsonArray arr = reader.readArray();
        for (int i = 0; i < arr.size(); i++) 
        	{
            JsonObject obj = arr.getJsonObject(i);
            request.body(obj.toString());
    		System.out.println("Request read from file is: "+obj.toString());
            
    		Response response = request.post();
    		
    		/*
    		Response response = request
				.given()
				.post()
				.then()
				.assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("postSchema.json"))
		        .extract()
		        .response();
    		*/
    		
    		System.out.println("Response Body is: " +response.body().asString());
    		Assert.assertEquals(response.getStatusCode(), 200);
    		if((response.getStatusCode() == 200))
    		{
    			System.out.println("Request is successful.");
    			    			
    			//JsonSchemaValidator.matchesJsonSchemaInClasspath("postSchema.json");
    			
    		}
    				
    		
    		JsonPath JsonPathData = response.jsonPath();
    		System.out.println(JsonPathData.get("name").toString());
        	
        	}
            reader.close();
        }
       		catch (FileNotFoundException e) {
				e.printStackTrace();
				}
	}
   
	@Test(enabled = false) 
	public void SchemaValidation() {
		try {
			Response response = 
			given()
			.contentType("application/json")
			.post()
	        .then()
	        .assertThat()
	        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("postSchema.json"))
	        .extract()
	        .response();
			
			System.out.println("Response Body is: " +response.body().asString());
		}
		finally {
			System.out.println("Schema is validated.");
		}
		
	}
}
package practiceTests;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetMethodBDD {
  @Test
  public void testGetRequest() {
	  Response response = 
	  given().
	  when().
	  get("https://reqres.in/api/users?page=2").
	  then().
	  statusCode(200).extract().response();
	  System.out.println("Response generated is: "+response.asString());
	  
  }
}

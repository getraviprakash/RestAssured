package tests;

import org.testng.TestException;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.*;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.config.JsonConfig.jsonConfig;
import static org.hamcrest.Matchers.equalTo;

public class ListUsers {

	String protocol = "https";
	String domain = "beta";
	String base_url = "myhrsupportcenter-qa.com";
	String client_id ="aa6e5a2307b89f147a5ff6de51eaa52592aa383f01a4f78cd27bffd01d27b4b2";
	String client_secret ="994eb8f0eb7b69f5ea0939cbe0b7a977cb3ef9faede393c55c1d66148839df6a";
	String AuthTokenURL = protocol+"://"+domain+"."+base_url+"/oauth/token?grant_type=client_credentials&client_id="+client_id+"&client_secret="+client_secret;
	String listActiveUsersURL = protocol+"://api."+base_url+"/v2/users";
	String auth_token;
	
	SoftAssert softAssert = new SoftAssert();
	
	@DataProvider(name = "DP1")
	public String[][] createTestData() {
	         
	    return new String[][] {
	            {"testcaseOne", "Bearer"},
	            {"testcaseTwo", "Bearer"}
	    };
	}
	
	@Test (enabled = true, dataProvider = "DP1")
	public void GenerateOauthToken(String originalText, String bearerToken)
	{   
		
		ResponseBody Resp1=
	given()
		.contentType(ContentType.JSON)
	  .when()
	    .post(AuthTokenURL)
	  .then()
	    .statusCode(200)
	    
		.assertThat()
//		.body("token_type", assertEquals(actual, bearerToken))
		.body("token_type", equalTo(bearerToken))
	    .extract()
	    .response()
	    .getBody();
		
		auth_token = Resp1.jsonPath().getString("access_token");
		System.out.println("Access Token generated is:\t "+auth_token);
//
//		String responseAsString1 = Resp1.asString();
//		System.out.println("Response as string:\t "+responseAsString1);
	}
	
	@Test (enabled = false)
	public void GetUsersList()
	{   
		ResponseBody Resp2=given()
		.header("Authorization", "Bearer "+auth_token)
	  .when()
	    .get(listActiveUsersURL)
	  .then()
	    .assertThat()
	    .statusCode(200)
	    .extract()
	    .response()
	    .getBody();
		System.out.println("Access Token received is:\t "+auth_token);
//		String responseAsString2 = Resp2.asString();
//		System.out.println("Response as string:\t "+responseAsString2);
	}
	
	
	@Test	 (enabled = false)
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
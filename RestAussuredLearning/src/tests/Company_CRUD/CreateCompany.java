package tests.Company_CRUD;

import static io.restassured.RestAssured.given;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import resources.TestDataSetup;

public class CreateCompany {
	String access_token;
	int obj_count;
	JSONArray root;
	
	@Test (enabled = true, priority = 1)
	public void GenerateOauthToken()
	{   
		RestAssured.baseURI = TestDataSetup.getV2URL();
		Response Resp1=
	given()
		.auth()
		.preemptive()
		.basic(TestDataSetup.getAuthUsername(), TestDataSetup.getAuthPassword())
		.formParam("grant_type", TestDataSetup.getGrant_type())
		.formParam("username", TestDataSetup.getUsername())
		.formParam("password", TestDataSetup.getPassword())
	.when()
	    .post(TestDataSetup.getAuthTokenURL())
	.then()
	    .statusCode(200)
    .extract()
	    .response();
		
		access_token = Resp1.path("access_token");
		System.out.println("Access Token generated is:\t "+access_token);
		// Get a single header value:
		String headerName = Resp1.getHeader("Date");
		System.out.println(headerName);
		// Get status line
		String statusLine = Resp1.getStatusLine();
		System.out.println(statusLine);
		// Get status code
		int statusCode = Resp1.getStatusCode();
		System.out.println(statusCode);
		System.out.println("Generation of Auth code completed.");

	}    
	
	@Test(enabled = false, priority = 2)
	public void TestReadingJsonObject() throws FileNotFoundException, IOException, ParseException
	{   
		JSONParser jsonParser = new JSONParser();
        JSONArray root = (JSONArray) jsonParser.parse(new FileReader("src/resources/CreateCompany_Multiple.json"));
        for (int i=0;i<obj_count;i++)
        {
        JSONObject rootObj = (JSONObject) root.get(i);
        System.out.println (rootObj.get("TestCase"));
        System.out.println (rootObj.get("ExpectedStatus"));
        }
	}
	
	@Test (enabled = true, priority = 3, dependsOnMethods = "GenerateOauthToken")
	public void Create_Company_TC01() throws FileNotFoundException, IOException, ParseException
	{   
		RestAssured.baseURI = TestDataSetup.getV2URL();
		JSONParser jsonParser = new JSONParser();
        JSONArray root = (JSONArray) jsonParser.parse(new FileReader("src/resources/CreateCompany_Multiple.json"));

        JSONObject rootObj = (JSONObject) root.get(0);
        System.out.println (rootObj.get("TestCase"));
        System.out.println (rootObj.get("ExpectedStatus"));
        
        Response Resp=
	given()
		.header("Authorization", "Bearer "+access_token)
		.body(rootObj.toString())
		.contentType("application/json")
    .when()
	    .post("v2/companies")
	.then()
	    .statusCode(201)
    .extract()
    	.response();
	String respBody = Resp.getBody().asString();
	System.out.println(respBody);
	
	SoftAssert softAssert = new SoftAssert();
	softAssert.assertEquals(Resp.jsonPath().getString("code"), "201");
	softAssert.assertEquals(Resp.jsonPath().getString("company.companyName"), rootObj.get("API_Body.companyName"));
	softAssert.assertEquals(Resp.jsonPath().getString("company.companyUrl"), rootObj.get("API_Body.companyUrl"));
	softAssert.assertAll();

	}
	
	@Test (enabled = true, priority = 4, dependsOnMethods = "GenerateOauthToken")
	public void Create_Company_TC02() throws FileNotFoundException, IOException, ParseException
	{   
		RestAssured.baseURI = TestDataSetup.getV2URL();
		JSONParser jsonParser = new JSONParser();
        JSONArray root = (JSONArray) jsonParser.parse(new FileReader("src/resources/CreateCompany_Multiple.json"));

        JSONObject rootObj = (JSONObject) root.get(1);
        System.out.println (rootObj.get("TestCase"));
        System.out.println (rootObj.get("ExpectedStatus"));
        
        Response Resp=
	given()
		.header("Authorization", "Bearer "+access_token)
		.body(rootObj.toString())
		.contentType("application/json")
    .when()
	    .post("v2/companies")
	.then()
	    .statusCode(201)
    .extract()
    	.response();
	String respBody = Resp.getBody().asString();
	System.out.println(respBody);
	
	SoftAssert softAssert = new SoftAssert();
	softAssert.assertEquals(Resp.jsonPath().getString("code"), "201");
	softAssert.assertEquals(Resp.jsonPath().getString("company.companyName"), rootObj.get("API_Body.companyName"));
	softAssert.assertEquals(Resp.jsonPath().getString("company.companyUrl"), rootObj.get("API_Body.companyUrl"));
	softAssert.assertAll();

	}
}

package tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import resources.TestDataSetup;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class V2Tests {

	String access_token;
	
	@Test (enabled = false)
	public void GenerateOauthToken()
	{   
		RestAssured.baseURI = TestDataSetup.getV2URL();
		ResponseBody Resp1=
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
	
		.assertThat()
	    .extract()
	    .response()
	    .getBody();
		
		access_token = Resp1.jsonPath().getString("access_token");
		System.out.println("Access Token generated is:\t "+access_token);
	}
	
	@Test (enabled = true, priority = 1)
	public void GenerateOauthTokenNew()
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
    
	@Test (enabled = false, priority = 2, dependsOnMethods = "GenerateOauthTokenNew")
	public void GetCompanyTypes()
	{   
		RestAssured.baseURI = TestDataSetup.getV2URL();
		Response Resp2=
	given()
		.header("Authorization", "Bearer "+access_token)
    .when()
	    .get(TestDataSetup.getCompanyTypesURL())
	.then()
	    .statusCode(200)
	    .body("totalRecords", equalTo("3"))
	    .body("companyTypes.name[0]", equalTo("broker_partner"))
		.body("companyTypes.name[1]", equalTo("client"))
		.body("companyTypes.name[2]", equalTo("direct"))
    .extract()
    	.response();
	String respBody = Resp2.getBody().asString();
	System.out.println(respBody);
	}
	
	@Test (enabled = false, priority = 2)
	public void SoftAssertGetCompanyTypes()
	{   
		RestAssured.baseURI = TestDataSetup.getV2URL();
		Response Resp2=
	given()
		.header("Authorization", "Bearer "+access_token)
    .when()
	    .get(TestDataSetup.getCompanyTypesURL())
	.then()
	    .statusCode(200)
    .extract()
    	.response();
//	String respBody = Resp2.getBody().asString();
//	System.out.println(respBody);
	
	SoftAssert softAssert = new SoftAssert();
	softAssert.assertEquals(Resp2.jsonPath().getString("status"), "OK");
	softAssert.assertEquals(Resp2.jsonPath().getString("code"), "200");
	softAssert.assertEquals(Resp2.jsonPath().getString("limit"), "50");
	softAssert.assertEquals(Resp2.jsonPath().getString("offset"), "0");
	softAssert.assertEquals(Resp2.jsonPath().getString("sort"), "+name");
	softAssert.assertEquals(Resp2.jsonPath().getString("companyTypes.name[0]"), "broker_partner");
	softAssert.assertEquals(Resp2.jsonPath().getString("companyTypes.name[1]"), "client");
	softAssert.assertEquals(Resp2.jsonPath().getString("companyTypes.name[2]"), "direct");
	softAssert.assertAll();
	
	}

	@Test (enabled = false, priority = 3, dependsOnMethods = "GenerateOauthTokenNew")
	public void CreateSystemAdminRole_TC_01()
	{   
	    File payload_file = new File("src/resources/payload_file.json");
	    String content = null;
		RestAssured.baseURI = TestDataSetup.getV2URL();
		Response Resp2=
	given()
		.header("Authorization", "Bearer "+access_token)
		.body(payload_file)
		.with()
		.contentType("application/json")
    .when()
	    .post(TestDataSetup.getUserRole())
	.then()
	    .statusCode(200)
    .extract()
    	.response();
	String respBody = Resp2.getBody().asString();
	System.out.println(respBody);
	
	SoftAssert softAssert = new SoftAssert();
	softAssert.assertEquals(Resp2.jsonPath().getString("status"), "OK");
	softAssert.assertEquals(Resp2.jsonPath().getString("code"), "200");
	softAssert.assertAll();
	
	}
	
	@Test (enabled = false, priority = 4, dependsOnMethods = "GenerateOauthTokenNew")
	public void Create_Company() throws IOException
	{   
		byte[] payload_file = Files.readAllBytes(Paths.get("src/resources/CreateCompany.json"));
//	    File payload_file = new File("src/resources/CreateCompany.json");
		RestAssured.baseURI = TestDataSetup.getV2URL();
		Response Resp2=
	given()
		.header("Authorization", "Bearer "+access_token)
		.body(payload_file)
		.contentType("application/json")
    .when()
	    .post("v2/companies")
	.then()
	    .statusCode(201)
    .extract()
    	.response();
	String respBody = Resp2.getBody().asString();
	System.out.println(respBody);
	
	SoftAssert softAssert = new SoftAssert();
	softAssert.assertEquals(Resp2.jsonPath().getString("status"), "OK");
	softAssert.assertEquals(Resp2.jsonPath().getString("code"), "201");
	softAssert.assertEquals(Resp2.jsonPath().getString("totalRecords"), "20");
	softAssert.assertEquals(Resp2.jsonPath().getString("companies.companyName[0]"), "Shiva Inc.");
	softAssert.assertEquals(Resp2.jsonPath().getString("companies.companyName[1]"), "Arpit Inc.");
	softAssert.assertAll();
	
	}
	
	@Test (enabled = true, priority = 4, dependsOnMethods = "GenerateOauthTokenNew")
	public void Create_Company_from_JSON() throws IOException, ParseException
	{   
		RestAssured.baseURI = TestDataSetup.getV2URL();
		JSONParser jsonParser = new JSONParser();
        JSONArray root = (JSONArray) jsonParser.parse(new FileReader("src/resources/CreateCompany_Multiple.json"));
        
//        for (int i=0;i<root.size();i++)
//        {
        JSONObject rootObj = (JSONObject) root.get(1);
        System.out.println (rootObj.get("TestCase"));
        System.out.println (rootObj.get("ExpectedStatus"));
        
        Response Resp2=
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
	String respBody = Resp2.getBody().asString();
	System.out.println(respBody);
	
	SoftAssert softAssert = new SoftAssert();
	softAssert.assertEquals(Resp2.jsonPath().getString("status"), "OK");
	softAssert.assertEquals(Resp2.jsonPath().getString("code"), "201");
	softAssert.assertEquals(Resp2.jsonPath().getString("company.companyName"), rootObj.get("API_Body.companyName"));
	softAssert.assertEquals(Resp2.jsonPath().getString("company.companyUrl"), rootObj.get("API_Body.companyUrl"));
	softAssert.assertAll();
//        }
	}
}
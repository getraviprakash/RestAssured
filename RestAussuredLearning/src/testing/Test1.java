package testing;

import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import resources.TestDataSetup;

public class Test1 {
	private static final int run_count = 5;
	
	@Test(enabled = false, invocationCount=run_count)
	public void testRunner() {
		
		  System.out.println("Sample Test");
	  }

	@Test (enabled = true)
	public void TestReadingJsonObject() throws FileNotFoundException, IOException, ParseException
	{   
		JSONParser jsonParser = new JSONParser();
        JSONArray root = (JSONArray) jsonParser.parse(new FileReader("src/resources/CreateCompany_Multiple.json"));
        for (int i=0;i<root.size();i++)
        {

        JSONObject rootObj = (JSONObject) root.get(i);
//        String fullname=(String) rootObj.get("name");
        System.out.println (rootObj.get("TestCase"));
        System.out.println (rootObj.get("ExpectedStatus"));
        }
	}

	@Test (enabled = false)
	public void Payload_as_Byte_Array() throws ParseException, IOException
	{
		byte[] jsonData = Files.readAllBytes(Paths.get("src/resources/sampleJsonArray.json"));
		System.out.println(jsonData.toString());
	}


}

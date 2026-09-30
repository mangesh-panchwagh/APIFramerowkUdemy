package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResource;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StepDefinitions extends Utils{

	RequestSpecification res ;
	ResponseSpecification resspec;

	Response  response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String address, String language) throws IOException {
	   
		System.out.println("######################## Step 1 Add Place ########################");
//		RestAssured.baseURI = "https://rahulshettyacademy.com/";
//		
//		// here we are using RequestSpecBuilder
//		 req = new RequestSpecBuilder().setContentType(ContentType.JSON)
//				.setBaseUri("https://rahulshettyacademy.com/")
//				.addQueryParam("key", "qaclick123").build();
		
	   // ########## Added above two lines under Utils class ##################
	   
		

		 res = given().spec(requestSpecification())	// calling method requestSpecification() from Utils class
			.body(data.addPlacePayload(name, address, language));
		
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
	    
		System.out.println("######################## Step 2 Add user callas with POST Method ####");
		
		APIResource resourceAPI = APIResource.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		// here we are using ResponseSpecBuilder
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		// here we are using Response
		if(method.equalsIgnoreCase("POST"))
		{
		response = res.when()
			//.post("maps/api/place/add/json")
				.post(resourceAPI.getResource());
		}
		else if(method.equalsIgnoreCase("GET")) {
			response = res.when().get(resourceAPI.getResource());
		}
	}
	@Then("the API calls got success with status code {int}")
	public void the_api_calls_got_success_with_status_code(Integer int1) {
		System.out.println("######################## step 3 check success message ########################");
		System.out.println("response.getStatusCode() : " + response.getStatusCode());
		assertEquals(response.getStatusCode(), 200);
		
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
	  
		System.out.println("######################## step 4 validate response ########################");
//		String resp = response.asString();
//		js = new JsonPath(resp);
	 	
		assertEquals(getJsonPath(response,keyValue), Expectedvalue);
		System.out.println(keyValue + " " + Expectedvalue);
		
		 
	}
	
	@Then("verify {string} in place_id created maps to {string} using {string}")
	public void verify_in_place_id_created_maps_to_using(String key, String expectedValue, String resource) throws IOException {
	    
		System.out.println("######################## step 5 validate response and verify name and address ########################");
		// reqspec
		// Get place_id from Add Place response
		// Get place_id only once from Add Place POST response
		
		if (place_id == null) {
			place_id = getJsonPath(response, "place_id");
		}
		
		System.out.println("Place_id : " + place_id);
		 
		// Create request specification for Get Place API
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		
		// getAPI call // Call Get Place API
		user_calls_with_http_request(resource,"GET");
		
		// Dynamically get value based on key: name/address
		String actualValue = getJsonPath(response, key);
		
		System.out.println("Key : " + key);
		System.out.println("Actual Value : " + actualValue);
		System.out.println("Expected Value: " + expectedValue);
		
		assertEquals(actualValue, expectedValue);
		
		
		// Constructor will be called with value of resource which you pass
//		APIResource resourceAPI = APIResource.valueOf(resource);
//		System.out.println("resourceAPI.getResource() : " + resourceAPI.getResource());
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    
		res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}


}

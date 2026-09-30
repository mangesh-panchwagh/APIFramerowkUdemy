package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		
		// write code that will give you place id
		// execute this code when place id is null
		
		StepDefinitions m = new StepDefinitions();
		if(m.place_id==null) {
		
			m.add_place_payload_with("Panchwagh", "French","Asia");
			m.user_calls_with_http_request("addPlaceAPI", "POST");
			m.verify_in_place_id_created_maps_to_using("name", "Panchwagh","getPlaceAPI");
			m.verify_in_place_id_created_maps_to_using("address", "Asia","getPlaceAPI");
			
		}
		
		
	}
}

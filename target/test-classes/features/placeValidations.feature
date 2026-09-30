Feature:Validating Place API's
@AddPlace @Regression
Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "addPlaceAPI" with "POST" http request
	Then the API calls got success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify "name" in place_id created maps to "<name>" using "getPlaceAPI"
	And verify "address" in place_id created maps to "<address>" using "getPlaceAPI"
	
Examples:
	|name    | language |address	       |
	|AAhouse | English	|World cross center|
#	|BBhouse | Japan	|World India Center|

@DeletePlace @Regression
Scenario: Verify if Delete Place functinality is working
	Given DeletePlace Payload
	When user calls "deletePlaceAPI" with "POST" http request
    Then the API calls got success with status code 200
	And "status" in response body is "OK"
package reusables;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.rahul.utils.Utilities;
import payloads.GoogleApiPayloads;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CommonMethods {
    RequestSpecification requestSpecification = null;
    Response response = null;
    public  RequestSpecification getAddPlacePayload(String name, String language, String address) throws IOException {
        try {
            Utilities utilities=new Utilities();
            requestSpecification = RestAssured.given().relaxedHTTPSValidation().spec(utilities.setRequestSpecification())
                    .body(GoogleApiPayloads.setAddPlaceValues(name,language,address));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return requestSpecification;
    }

    public Response callTheApi(RequestSpecification requestSpecification,String resource, String requestType) {
        switch (requestType.toUpperCase()) {
            case "POST":
                response= requestSpecification.when().post(resource);
                break;
            case "GET":
                response= requestSpecification.when().get(resource);
                break;
            case "DELETE":
                response= requestSpecification.when().delete(resource);
                break;
            case "PUT":
                response= requestSpecification.when().put(resource);
                break;
            default:
                break;
        }
        return response;
    }
}

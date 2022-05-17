package org.rahul.tests;

import enums.APIResources;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.rahul.pojos.AddPlaceResponse;
import org.rahul.utils.ExcelUtility;
import org.rahul.utils.Utilities;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import payloads.GoogleApiPayloads;
import reusables.CommonMethods;

import java.io.IOException;

public class GoogleApiAddPlaceTest {
    RequestSpecification requestSpecification;
    Response response;
    public static String placeId;
    @Test(dataProvider = "addPlaceData")
    public void validateAddPlace(String name,String language,String address,String statusCode,String status,String	scope){
        AddPlaceResponse addPlaceResponse= null;
        Utilities utilities=new Utilities();
        CommonMethods commonMethods=new CommonMethods();
        try {
            requestSpecification = RestAssured.given().relaxedHTTPSValidation().spec(utilities.setRequestSpecification())
                    .body(GoogleApiPayloads.setAddPlaceValues(name,language,address));
            APIResources resourceAPI=APIResources.valueOf("AddPlaceAPi");
            response=commonMethods.callTheApi(requestSpecification,resourceAPI.getResource(),"POST");
            addPlaceResponse=response.as(AddPlaceResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        placeId=addPlaceResponse.getPlace_id();
        System.out.println(placeId);
        Assert.assertEquals(response.getStatusCode(),Integer.parseInt(statusCode));
        Assert.assertEquals(addPlaceResponse.getStatus(),status);
        Assert.assertEquals(addPlaceResponse.getScope(),scope);
        String server= response.header("Server");
        String accessControl= response.header("Access-Control-Max-Age");
        String connection= response.header("Connection");
        Assert.assertEquals(server,"Apache/2.4.41 (Ubuntu)");
        Assert.assertEquals(accessControl,"3600");
        Assert.assertEquals(connection,"Keep-Alive");

    }
    @Test(dependsOnMethods = "validateAddPlace")
    public void validateGetPlace(){
        RequestSpecification requestSpecification;
        CommonMethods commonMethods=new CommonMethods();
        Utilities utilities=new Utilities();
        //authentication demo
        /*PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
        authScheme.setUserName("Rahul");
        authScheme.setPassword("Rahul123");
        RestAssured.authentication=authScheme;*/
        try {
            requestSpecification = RestAssured.given().relaxedHTTPSValidation().spec(utilities.setRequestSpecificationGET(placeId));
            APIResources resourceAPI=APIResources.valueOf("GetPlaceAPi");
            response=commonMethods.callTheApi(requestSpecification,resourceAPI.getResource(),"GET");
            JsonPath js=new JsonPath(response.asString());
            String name=js.get("name");
            System.out.println(name);
            System.out.println("Status code "+response.getStatusCode());
            System.out.println(response.asString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @DataProvider(name="addPlaceData")
    public Object[][] getData() throws IOException {
        //get data from excel
        String path=System.getProperty("user.dir")+"/src/test/resources/"+"APITestData.xlsx";
        String sheetName="AddPlace";
        ExcelUtility utility=new ExcelUtility(path);
        int noOfRows=utility.getRowCount(sheetName);
        int noOfColumns=utility.getCellCount(sheetName,0);
        Object[][] addPlaceData=new Object[noOfRows][noOfColumns];
        for(int i=1;i<=noOfRows;i++){
            for (int j=0;j<noOfColumns;j++){
                addPlaceData[i-1][j]=utility.getCellData(sheetName,i,j);
            }
        }
        return addPlaceData;
    }


}

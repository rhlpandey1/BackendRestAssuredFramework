package org.rahul.tests;

import enums.APIResources;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.rahul.pojos.AddPlaceResponse;
import org.rahul.utils.ExcelUtility;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reusables.CommonMethods;

import java.io.IOException;

public class AddPlaceTest {
    RequestSpecification requestSpecification;
    Response response;
    public static String placeId;
    @Test(dataProvider = "addPlaceData")
    public void validateAddPlace(String name,String language,String address,String statusCode,String status,String	scope){
        CommonMethods commonMethods=new CommonMethods();
        AddPlaceResponse addPlaceResponse= null;
        try {
            requestSpecification =commonMethods.getAddPlacePayload(name,language,address);
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

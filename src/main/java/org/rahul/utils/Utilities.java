package org.rahul.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utilities {
    public static RequestSpecification requestSpecification;

    public RequestSpecification setRequestSpecification() throws IOException {
        if(requestSpecification==null){
            PrintStream log=new PrintStream(new FileOutputStream("log.txt"));
            requestSpecification=new RequestSpecBuilder().setBaseUri(getProperties("baseURI")).
                    setContentType("application/json").addQueryParam("key","qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
            return requestSpecification;
        }
        return requestSpecification;
    }
    public RequestSpecification setRequestSpecificationGET(String placeId) throws IOException {

        if(requestSpecification==null){
            PrintStream log = new PrintStream(new FileOutputStream("log.txt"));
            requestSpecification = new RequestSpecBuilder().setBaseUri(getProperties("baseURI")).
                    setContentType("application/json").addQueryParam("key", "qaclick123")
                    .addQueryParam("place_id", placeId)
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log)).build();

            return requestSpecification;
        }
        return requestSpecification;
    }

    public static String getProperties(String key) throws IOException {
        String filePath=System.getProperty("user.dir")+"/src/test/resources/"+"config.properties";
        FileInputStream fis=new FileInputStream(filePath);
        Properties prop=new Properties();
        prop.load(fis);
        String value=prop.getProperty(key);
        return value;
    }
}

package com.outfit7.client;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseClient {

    @Value("${base.url}")
    private String baseUrl;
    public RequestSpecification setUp(){
        RestAssured.baseURI = baseUrl;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    public Response get(String path) {
        return setUp()
                .when()
                .get(path)
                .andReturn();
    }

    public Response get(String path, String pathParam) {
        return setUp()
                .when()
                .get(path, pathParam)
                .andReturn();
    }

    public Response get(String path, String pathParam1, String pathParam2) {
        return setUp()
                .when()
                .get(path, pathParam1, pathParam2)
                .andReturn();
    }

}

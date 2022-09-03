package com.outfit7.util;

import io.qameta.allure.Step;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiUtils {

    @Step("Taking serialized body as {clazz}")
    public static <T> T getBody(Class<T> clazz, Response response){
        return response.getBody().as(clazz);
    }
}

package com.outfit7.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import static com.outfit7.endpoints.Endpoints.*;

@Component
public class BreedClient extends BaseClient {

    @Step("Getting list of all breeds")
    public Response getAllBreedsList() {
        return get(LIST_ALL_BREEDS_ENDPOINT);
    }
    @Step("Getting random image")
    public Response getRandomBreedImage() {
        return get(RANDOM_BREED_IMAGE_ENDPOINT);
    }

    @Step("Getting multiple random images with path param: {param}")
    public Response getMultipleRandomImages(String param) {
        return get(MULTIPLE_RANDOM_BREED_IMAGES_ENDPOINT, param);
    }

    @Step("Getting images for breed: {param}")
    public Response getImagesForSpecificBreed(String param) {
        return get(IMAGES_BY_BREED_ENDPOINT, param);
    }

    @Step("Getting random image for breed: {param}")
    public Response getRandomImageForSpecificBreed(String param) {
        return get(RANDOM_SPECIFIC_BREED_IMAGE_ENDPOINT, param);
    }

    @Step("Getting {count}} images for breed {breed}")
    public Response getMultipleRandomImagesForSpecificBreed(String breed, String count) {
        return get(MULTIPLE_RANDOM_SPECIFIC_BREED_IMAGES_ENDPOINT, breed, count);
    }

    @Step("Getting list of sub-breed for {breed}")
    public Response getSubBreedList(String breed) {
        return get(SUB_BREED_LIST_ENDPOINT, breed);
    }

    @Step("Getting list of all images for sub-breed {subBreed} of breed {breed}")
    public Response getListAllImagesForSpecificSubBreed(String breed, String subBreed) {
        return get(ALL_IMAGES_BY_SUB_BREED_ENDPOINT, breed, subBreed);
    }

}

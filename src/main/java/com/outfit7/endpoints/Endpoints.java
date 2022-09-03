package com.outfit7.endpoints;

public class Endpoints {

    // all breeds list endpoint
    public static final String LIST_ALL_BREEDS_ENDPOINT = "/breeds/list/all";

    // random image endpoints
    public static final String RANDOM_BREED_IMAGE_ENDPOINT = "/breeds/image/random";
    public static final String MULTIPLE_RANDOM_BREED_IMAGES_ENDPOINT = "/breeds/image/random/{count}";

    // images by breed endpoints
    public static final String IMAGES_BY_BREED_ENDPOINT = "/breed/{breed}/images";
    public static final String RANDOM_SPECIFIC_BREED_IMAGE_ENDPOINT = "/breed/{breed}/images/random";
    public static final String MULTIPLE_RANDOM_SPECIFIC_BREED_IMAGES_ENDPOINT = "/breed/{breed}/images/random/{count}";

    // images by sub-breed endpoints

    public static final String SUB_BREED_LIST_ENDPOINT = "/breed/{breed}/list";
    public static final String ALL_IMAGES_BY_SUB_BREED_ENDPOINT = "/breed/{breed}/{subBreed}/images";
    public static final String SINGLE_RANDOM_SUB_BREED_IMAGE_ENDPOINT = "/breed/{breed}/{suBreed}/images/random";
    public static final String MULTIPLE_RANDOM_SUB_BREED_IMAGES_ENDPOINT = "/breed/{breed}/{subBreed}/images/random/{count}";

}

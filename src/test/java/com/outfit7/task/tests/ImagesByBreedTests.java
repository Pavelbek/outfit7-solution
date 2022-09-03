package com.outfit7.task.tests;

import com.outfit7.client.BreedClient;
import com.outfit7.dto.response.BreedNotFoundResponse;
import com.outfit7.dto.response.MultipleBreedImagesResponse;
import com.outfit7.dto.response.RandomBreedImageResponse;
import com.outfit7.util.ApiUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.outfit7.task.assertions.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ImagesByBreedTests extends BaseTest {
    @Autowired
    private BreedClient breedClient;

    @Test
    void checkCanGetImageTest() {
        String breed = "hound";
        Response response = breedClient.getImagesForSpecificBreed(breed);
        MultipleBreedImagesResponse body = ApiUtils.getBody(MultipleBreedImagesResponse.class, response);

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_OK);
            checkResponseStatusIsSuccess(body.getStatus());
            softly.assertThat(body.getMessage())
                    .withFailMessage("Message is null")
                    .isNotNull();
            softly.assertThat(body.getMessage().get(0))
                    .contains(IMAGE_URL);
            softly.assertThat(body.getMessage().get(0))
                    .contains(breed);
        });
    }

    @Test
    void checkAllBreedImagesContainBreedName() {
        String breed = "cattledog";
        Response response = breedClient.getImagesForSpecificBreed(breed);
        MultipleBreedImagesResponse body = ApiUtils.getBody(MultipleBreedImagesResponse.class, response);

        boolean allContains = body.getMessage()
                .stream()
                .allMatch(image -> image.contains(breed));

        assertThat(allContains)
                .withFailMessage("Not all images in response contain breed name")
                .isTrue();
    }

    @Test
    void checkSecondRequestForSameBreedImagesReturnsSameNumberOfImages() {
        String breed = "cattledog";
        Response firstResponse = breedClient.getImagesForSpecificBreed(breed);
        int sizeFirstResponse = ApiUtils.getBody(MultipleBreedImagesResponse.class, firstResponse)
                .getMessage().size();

        Response secondResponse = breedClient.getImagesForSpecificBreed(breed);
        int sizeSecondResponse = ApiUtils.getBody(MultipleBreedImagesResponse.class, secondResponse)
                .getMessage().size();

        assertThat(sizeFirstResponse)
                .withFailMessage("First time we got: " + sizeFirstResponse + " number of images, but second time: " + sizeSecondResponse)
                .isEqualTo(sizeSecondResponse);
    }

    @Test
    void checkRequestForUnknownBreedImage() {
        String breed = "puppy";
        Response response = breedClient.getRandomImageForSpecificBreed(breed);
        BreedNotFoundResponse body = ApiUtils.getBody(BreedNotFoundResponse.class, response);

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
            checkResponseStatusIsError(body.getStatus());
            softly.assertThat(body.getMessage())
                    .withFailMessage("Error message is incorrect.")
                    .isEqualTo(BREED_NOT_FOUND_MESSAGE);
            softly.assertThat(body.getCode())
                    .withFailMessage("Response status code wasn't 404")
                    .isEqualTo(String.valueOf(HttpStatus.SC_NOT_FOUND));
        });
    }

    @Test
    void checkRandomImageForBreedReturnsData() {
        String breed = "hound";
        Response response = breedClient.getRandomImageForSpecificBreed(breed);
        RandomBreedImageResponse body = ApiUtils.getBody(RandomBreedImageResponse.class, response);

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_OK);
            checkResponseStatusIsSuccess(body.getStatus());
            softly.assertThat(body.getMessage())
                    .withFailMessage("Message is null")
                    .isNotNull();
            softly.assertThat(body.getMessage())
                    .contains(IMAGE_URL);
            softly.assertThat(body.getMessage())
                    .contains(breed);
        });
    }

    /**
     * If we accidentally receive same random images - we retrying the test
     * If we receive two same random pictures twice, I think we can consider it as an issue
     */
    @RetryingTest(maxAttempts=2)
    void checkWeReceiveRandomImagesForBreed() {
        String breed = "hound";
        Response response1 = breedClient.getRandomImageForSpecificBreed(breed);
        String image1 = ApiUtils
                .getBody(RandomBreedImageResponse.class, response1)
                .getMessage();
        Response response2 = breedClient.getRandomImageForSpecificBreed(breed);
        String image2 = ApiUtils
                .getBody(RandomBreedImageResponse.class, response2)
                .getMessage();

        Assertions.assertThat(image1)
                .withFailMessage("Two random images was the same, first one: " + image1 + " and second one: " + image2)
                .isNotEqualTo(image2);
    }

    @Test
    void checkRequestForUnknownRandomBreedImage() {
        String breed = "puppy";
        Response response = breedClient.getRandomImageForSpecificBreed(breed);
        BreedNotFoundResponse body = ApiUtils.getBody(BreedNotFoundResponse.class, response);

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
            checkResponseStatusIsError(body.getStatus());
            softly.assertThat(body.getMessage())
                    .withFailMessage("Error message is incorrect.")
                    .isEqualTo(BREED_NOT_FOUND_MESSAGE);
            softly.assertThat(body.getCode())
                    .withFailMessage("Response status code wasn't 404")
                    .isEqualTo(String.valueOf(HttpStatus.SC_NOT_FOUND));
        });
    }

    @Test
    void checkCanGetMultipleRandomImagesForBreed() {
        String count = "3";
        String breed = "hound";
        Response response = breedClient.getMultipleRandomImagesForSpecificBreed(breed, count);
        MultipleBreedImagesResponse body = ApiUtils.getBody(MultipleBreedImagesResponse.class, response);

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_OK);
            checkResponseStatusIsSuccess(body.getStatus());
            softly.assertThat(body.getMessage())
                    .withFailMessage("Message is null")
                    .isNotEmpty();
            softly.assertThat(body.getMessage().get(0))
                    .contains(IMAGE_URL);
            softly.assertThat(body.getMessage().size())
                    .withFailMessage("Expected images count was 3, but actual is: " + body.getMessage().size())
                    .isEqualTo(3);
        });
    }

    @Test
    void checkAllImagesAreForTheSameBreed() {
        String count = "3";
        String breed = "hound";
        Response response = breedClient.getMultipleRandomImagesForSpecificBreed(breed, count);
        MultipleBreedImagesResponse body = ApiUtils.getBody(MultipleBreedImagesResponse.class, response);

        boolean allContains = body.getMessage()
                .stream()
                .allMatch(image -> image.contains(breed));

        assertThat(allContains)
                .withFailMessage("Not all images in response contain breed name")
                .isTrue();
    }

    @Test
    void checkResponseForRandomImagesForUnknownBreed() {
        String breed = "puppy";
        String count = "3";
        Response response = breedClient.getMultipleRandomImagesForSpecificBreed(breed, count);
        BreedNotFoundResponse body = ApiUtils.getBody(BreedNotFoundResponse.class, response);

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
            checkResponseStatusIsError(body.getStatus());
            softly.assertThat(body.getMessage())
                    .withFailMessage("Error message is incorrect.")
                    .isEqualTo(BREED_NOT_FOUND_MESSAGE);
            softly.assertThat(body.getCode())
                    .withFailMessage("Response status code wasn't 404")
                    .isEqualTo(String.valueOf(HttpStatus.SC_NOT_FOUND));
        });
    }

    @Test
    void checkGetMinusOneRandomImageForSpecificBreed() {
        String breed = "hound";
        String count = "-1";
        Response response = breedClient.getMultipleRandomImagesForSpecificBreed(breed, count);

        Assertions.assertThat(response.getStatusCode())
                .withFailMessage("Expected response status code for 'test' is " + HttpStatus.SC_BAD_REQUEST + ", but actual code is: " + response.getStatusCode())
                .isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void checkGetOneRandomImageForSpecificBreed() {
        String breed = "hound";
        String count = "1";
        Response response = breedClient.getMultipleRandomImagesForSpecificBreed(breed, count);
        MultipleBreedImagesResponse body = ApiUtils.getBody(MultipleBreedImagesResponse.class, response);

        Assertions.assertThat(body.getMessage().size())
                .withFailMessage("Expected size is 1, but actual size is " + body.getMessage().size())
                .isEqualTo(1);
    }

    @Test
    void checkGetRandomImageForSpecificBreedWithIncorrectCountParam() {
        String breed = "hound";
        String count = "test";
        Response response = breedClient.getMultipleRandomImagesForSpecificBreed(breed, count);

        Assertions.assertThat(response.getStatusCode())
                .withFailMessage("Expected response status code for 'test' is " + HttpStatus.SC_BAD_REQUEST + ", but actual code is: " + response.getStatusCode())
                .isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }
}

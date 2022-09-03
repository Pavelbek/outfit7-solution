package com.outfit7.task.tests;

import com.outfit7.client.BreedClient;
import com.outfit7.dto.response.MultipleBreedImagesResponse;
import com.outfit7.dto.response.RandomBreedImageResponse;
import com.outfit7.util.ApiUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.outfit7.task.assertions.Assertions.*;
import static com.outfit7.task.assertions.Assertions.IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RandomBreedImagesTests extends BaseTest {

    @Autowired
    private BreedClient breedClient;

    @Test
    void checkCanGetImageTest() {
        Response response = breedClient.getRandomBreedImage();
        RandomBreedImageResponse body = ApiUtils.getBody(RandomBreedImageResponse.class, response);

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_OK);
            checkResponseStatusIsSuccess(body.getStatus());
            softly.assertThat(body.getMessage())
                    .withFailMessage("Message is null")
                    .isNotNull();
            softly.assertThat(body.getMessage())
                    .contains(IMAGE_URL);
        });
    }

    /**
     * If we accidentally receive same random images - we retrying the test
     * If we receive two same random pictures twice, I think we can consider it as an issue
     */
    @RetryingTest(maxAttempts=2)
    void checkRandomImageIsReceivedTest() {
        Response firstResponse = breedClient.getRandomBreedImage();
        String image1 = ApiUtils
                .getBody(RandomBreedImageResponse.class, firstResponse)
                .getMessage();

        Response secondResponse = breedClient.getRandomBreedImage();
        String image2 = ApiUtils
                .getBody(RandomBreedImageResponse.class, secondResponse)
                .getMessage();

        assertThat(image1)
                .withFailMessage("Two random images was the same, first one: " + image1 + " and second one: " + image2)
                .isNotEqualTo(image2);
    }

    @Test
    void checkCanGetMultipleRandomImages() {
        Response response = breedClient.getMultipleRandomImages("3");
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
    void checkMultipleRandomImagesWhenRequestZero() {
        Response response = breedClient.getMultipleRandomImages("0");
        MultipleBreedImagesResponse body = ApiUtils.getBody(MultipleBreedImagesResponse.class, response);

        assertThat(body.getMessage().size())
                .withFailMessage("Expected size is 0, but actual size is " + body.getMessage().size())
                .isEqualTo(0);
    }

    @Test
    void checkMultipleRandomImagesWhenRequestOne() {
        Response response = breedClient.getMultipleRandomImages("1");
        MultipleBreedImagesResponse body = ApiUtils.getBody(MultipleBreedImagesResponse.class, response);

        assertThat(body.getMessage().size())
                .withFailMessage("Expected size is 1, but actual size is " + body.getMessage().size())
                .isEqualTo(1);
    }

    @Test
    void checkMultipleRandomImagesWhenRequestMinusOne() {
        Response response = breedClient.getMultipleRandomImages("-1");

        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status code for -1 is " + HttpStatus.SC_BAD_REQUEST + ", but actual code is: " + response.getStatusCode())
                .isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void checkMultipleRandomImagesWhenRequestFifty() {
        Response response = breedClient.getMultipleRandomImages("50");
        MultipleBreedImagesResponse body = ApiUtils.getBody(MultipleBreedImagesResponse.class, response);

        assertThat(body.getMessage().size())
                .withFailMessage("Expected size is 50, but actual size is " + body.getMessage().size())
                .isEqualTo(50);
    }

    @Test
    void checkMultipleRandomImagesWhenRequestFiftyOne() {
        Response response = breedClient.getMultipleRandomImages("51");

        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status code for 51 is " + HttpStatus.SC_BAD_REQUEST + ", but actual code is: " + response.getStatusCode())
                .isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void checkMultipleRandomImagesWhenRequestIncorrectPathParam() {
        Response response = breedClient.getMultipleRandomImages("test");

        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status code for 'test' is " + HttpStatus.SC_BAD_REQUEST + ", but actual code is: " + response.getStatusCode())
                .isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

}

package com.outfit7.task.tests;

import com.outfit7.client.BreedClient;
import com.outfit7.dto.response.BreedNotFoundResponse;
import com.outfit7.dto.response.MultipleBreedImagesResponse;
import com.outfit7.dto.response.SubBreedListResponse;
import com.outfit7.util.ApiUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.outfit7.task.assertions.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ImagesBySubBreedTests extends BaseTest {

    // SINGLE RANDOM IMAGE FROM A SUB BREED COLLECTION

    // MULTIPLE IMAGES FROM A SUB-BREED COLLECTION

    @Autowired
    private BreedClient breedClient;

    @Test
    void checkCanGetSubBreedList() {
        String breed = "hound";
        String expectedSubBreeds = "[afghan, basset, blood, english, ibizan, plott, walker]";

        Response response = breedClient.getSubBreedList(breed);
        SubBreedListResponse body = ApiUtils.getBody(SubBreedListResponse.class, response);

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_OK);
            checkResponseStatusIsSuccess(body.getStatus());
            softly.assertThat(body.getMessage())
                    .withFailMessage("Message is null")
                    .isNotNull();
            softly.assertThat(body.getMessage())
                    .withFailMessage("Sub-breeds list doesn't match expected.")
                    .isEqualTo(expectedSubBreeds);
        });
    }

    @Test
    void checkSubBreedsRequestForUnknownBreedIsHandledCorrectly() {
        String breed = "fluffykin";

        Response response = breedClient.getSubBreedList(breed);
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
    void checkCanGetSubBreedImagesTest() {
        String breed = "hound";
        String subBreed = "afghan";

        Response response = breedClient.getListAllImagesForSpecificSubBreed(breed, subBreed);
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
            softly.assertThat(body.getMessage().get(0))
                    .contains(subBreed);
        });
    }

    @Test
    void checkSubBreedsImagesRequestForUnknownBreedIsHandledCorrectly() {
        String breed = "fluffykin";
        String subBreed = "afghan";

        Response response = breedClient.getListAllImagesForSpecificSubBreed(breed, subBreed);
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
    void checkSubBreedsImagesRequestForUnknownSubBreedIsHandledCorrectly() {
        String breed = "hound";
        String subBreed = "fluffykin";

        Response response = breedClient.getListAllImagesForSpecificSubBreed(breed, subBreed);
        BreedNotFoundResponse body = ApiUtils.getBody(BreedNotFoundResponse.class, response);

        SoftAssertions.assertSoftly(softly -> {
            checkStatusCode(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
            checkResponseStatusIsError(body.getStatus());
            softly.assertThat(body.getMessage())
                    .withFailMessage("Error message is incorrect.")
                    .isEqualTo(SUB_BREED_NOT_FOUND_MESSAGE);
            softly.assertThat(body.getCode())
                    .withFailMessage("Response status code wasn't 404")
                    .isEqualTo(String.valueOf(HttpStatus.SC_NOT_FOUND));
        });
    }

    @Test
    void checkAllImagesContainBreedAndSubBreedNames() {
        String breed = "hound";
        String subBreed = "afghan";

        Response response = breedClient.getListAllImagesForSpecificSubBreed(breed, subBreed);
        MultipleBreedImagesResponse body = ApiUtils.getBody(MultipleBreedImagesResponse.class, response);

        boolean allContains = body.getMessage()
                .stream()
                .allMatch(image -> image.contains(breed + "-" + subBreed));

        assertThat(allContains)
                .withFailMessage("Not all images in response contain breed name")
                .isTrue();
    }

}

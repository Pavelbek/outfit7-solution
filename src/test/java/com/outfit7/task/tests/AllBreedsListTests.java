package com.outfit7.task.tests;

import com.outfit7.client.BreedClient;
import com.outfit7.dto.response.AllBreedsResponse;
import com.outfit7.util.ApiUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.outfit7.task.assertions.Assertions.*;

@SpringBootTest
public class AllBreedsListTests extends BaseTest {
	@Autowired
	private BreedClient breedClient;

	@Test
	void checkCanGetAllBreedsListTest() {
		Response response = breedClient.getAllBreedsList();
		AllBreedsResponse body = ApiUtils.getBody(AllBreedsResponse.class, response);

		SoftAssertions.assertSoftly(softly -> {
			checkStatusCode(response.getStatusCode(), HttpStatus.SC_OK);
			checkResponseStatusIsSuccess(body.getStatus());
			softly.assertThat(body.getMessage())
					.withFailMessage("Message is null")
					.isNotNull();
			softly.assertThat(body.getMessage().toString())
					.isEqualTo(ALL_BREEDS_EXPECTED_LIST);
		});
	}
}


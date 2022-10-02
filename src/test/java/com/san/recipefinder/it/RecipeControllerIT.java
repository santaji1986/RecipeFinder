package com.san.recipefinder.it;

import com.san.recipefinder.RecipeFinderApplication;
import com.san.recipefinder.dto.AddRecipePayload;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RecipeFinderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeControllerIT {
    @LocalServerPort
    private int port;

    @Test
    void testAddRecipe() {
        AddRecipePayload addRecipePayload = new AddRecipePayload(true, "recipe1", 2, "Ingredients", "Instructions");
        Response response = given()
                .port(port)
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(addRecipePayload)
                .put("/addRecipe")
                .then()
                .statusCode(CREATED.value())
                .extract()
                .response();
        assertThat(response.body().asString()).isEqualTo("Success");
    }
}

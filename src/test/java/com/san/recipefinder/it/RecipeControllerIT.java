package com.san.recipefinder.it;

import com.san.recipefinder.RecipeFinderApplication;
import com.san.recipefinder.dto.AddRecipePayload;
import com.san.recipefinder.dto.UpdateRecipePayload;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {RecipeFinderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeControllerIT {
    @LocalServerPort
    private int port;

    @Test
    @Order(1)
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

    @Test
    @Order(2)
    void testUpdateRecipe() {
        UpdateRecipePayload updateRecipePayload = new UpdateRecipePayload(1L, true, "recipe1", 2, "Ingredients", "Instructions");
        Response response = given()
                .port(port)
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateRecipePayload)
                .post("/updateRecipe")
                .then()
                .statusCode(OK.value())
                .extract()
                .response();
        assertThat(response.body().asString()).isEqualTo("Success");
    }

    @SneakyThrows
    @Test
    @Order(3)
    void testGetAllRecipes() {
        UpdateRecipePayload updateRecipePayload = new UpdateRecipePayload(1L, true, "recipe1", 2, "Ingredients", "Instructions");
        Response response = given()
                .port(port)
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateRecipePayload)
                .get("/getAllRecipes")
                .then()
                .statusCode(OK.value())
                .extract()
                .response();
        String expectedJson = """
                [
                {"id":1,"recipeName":"recipe1","numberOfServings":2,"ingredients":"Ingredients","instructions":"Instructions","vegetarian":true}
                ]
                """;

        JSONAssert.assertEquals(response.body().asString(), expectedJson, new CustomComparator(JSONCompareMode.LENIENT));
    }

    @Test
    @Order(4)
    void testDeleteRecipe() {
        Response response = given()
                .port(port)
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("recipeId", 1L)
                .delete("/deleteRecipe")
                .then()
                .statusCode(OK.value())
                .extract()
                .response();
        assertThat(response.body().asString()).isEqualTo("Success");
    }

    @Test
    void testUpdateRecipeFailed() {
        UpdateRecipePayload updateRecipePayload = new UpdateRecipePayload(10L, true, "recipe1", 2, "Ingredients", "Instructions");
        Response response = given()
                .port(port)
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateRecipePayload)
                .post("/updateRecipe")
                .then()
                .statusCode(NOT_FOUND.value())
                .extract()
                .response();
        assertThat(response.body().asString()).isEqualTo("No recipe found.");
    }
}

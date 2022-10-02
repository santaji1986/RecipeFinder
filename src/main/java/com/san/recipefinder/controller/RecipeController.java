package com.san.recipefinder.controller;

import com.san.recipefinder.dto.AddRecipePayload;
import com.san.recipefinder.dto.UpdateRecipePayload;
import com.san.recipefinder.model.Recipe;
import com.san.recipefinder.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "name", required = false) String name) {
        String greeting = "Hello " + name;
        LOGGER.info(greeting);
        return greeting;
    }

    @PutMapping(value = "/addRecipe")
    public ResponseEntity<String> addRecipe(@RequestBody AddRecipePayload addRecipePayload) {
        LOGGER.info("addRecipe {}", addRecipePayload);
        recipeService.addRecipe(addRecipePayload);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);
    }

    @PostMapping(value = "/updateRecipe")
    public ResponseEntity<String> updateRecipe(@RequestBody UpdateRecipePayload updateRecipePayload) {
        LOGGER.info("updateRecipe {}", updateRecipePayload);
        recipeService.updateRecipe(updateRecipePayload);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteRecipe")
    public ResponseEntity<String> deleteRecipe(@RequestParam Long recipeId) {
        LOGGER.info(recipeId.toString());
        recipeService.deleteRecipe(recipeId);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @GetMapping(value = "/getAllRecipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        LOGGER.info("Get All Recipes");
        List<Recipe> recipeList = recipeService.getAllRecipes();
        return new ResponseEntity<List<Recipe>>(recipeList, HttpStatus.OK);
    }
}

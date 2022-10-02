package com.san.recipefinder.service;

import com.san.recipefinder.dto.AddRecipePayload;
import com.san.recipefinder.dto.UpdateRecipePayload;
import com.san.recipefinder.model.Recipe;

import java.util.List;

public interface RecipeService {
    void addRecipe(AddRecipePayload addRecipePayload);

    void updateRecipe(UpdateRecipePayload updateRecipePayload);

    void deleteRecipe(Long recipeId);

    List<Recipe> getAllRecipes();
}

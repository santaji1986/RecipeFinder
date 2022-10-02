package com.san.recipefinder.service;

import com.san.recipefinder.dao.RecipeRepository;
import com.san.recipefinder.dto.AddRecipePayload;
import com.san.recipefinder.dto.UpdateRecipePayload;
import com.san.recipefinder.model.Recipe;
import com.san.recipefinder.service.exception.RecipeNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    private RecipeRepository recipeRepository = Mockito.mock(RecipeRepository.class);
    private RecipeService recipeService = new RecipeServiceImpl(recipeRepository);

    @Test
    void testAddRecipe() {
        AddRecipePayload addRecipePayload = new AddRecipePayload(true, "recipe1", 2, "Ingredients", "Instructions");
        Recipe expectedRecipe = new Recipe(null, true, "recipe1", 2, "Ingredients", "Instructions");

         recipeService.addRecipe(addRecipePayload);

        verify(recipeRepository, times(1)).save(expectedRecipe);
    }

    @Test
    void testUpdateRecipe() {
        UpdateRecipePayload updateRecipePayload = new UpdateRecipePayload(1L, true, "UpdatedRecipe1", 4, "UpdatedIngredients", "UpdatedInstructions");
        Recipe recipe = new Recipe(1L, true, "Recipe1", 2, "Ingredients", "Instructions");
        Recipe expectedRecipe = new Recipe(1L, true, "UpdatedRecipe1", 4, "UpdatedIngredients", "UpdatedInstructions");
        when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipe));

        recipeService.updateRecipe(updateRecipePayload);

        verify(recipeRepository,times(1)).save(expectedRecipe);
    }

    @Test
    void testUpdateRecipeFailed() {
        UpdateRecipePayload updateRecipePayload = new UpdateRecipePayload(1L, true, "Recipe1", 2, "Ingredients", "Instructions");
        when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        RecipeNotFoundException recipeNotFoundException = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.updateRecipe(updateRecipePayload);
        });

        assertEquals(recipeNotFoundException.getMessage(), "No recipe found with recipeId : 1" );
        verify(recipeRepository, never()).save(any());
    }

    @Test
    void testDeleteRecipe() {
        Long recipeId = 1L;

        recipeService.deleteRecipe(recipeId);

        verify(recipeRepository, times(1)).deleteById(recipeId);
    }

    @Test
    void testGetAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe(1L, true, "Recipe1", 2, "Ingredients1", "Instructions1"));
        recipeList.add(new Recipe(2L, false, "Recipe2", 4, "Ingredients2", "Instructions2"));
        when(recipeRepository.findAll()).thenReturn(recipeList);

        List<Recipe> allRecipes = recipeService.getAllRecipes();

        verify(recipeRepository, times(1)).findAll();
        assertEquals(allRecipes, recipeList);
    }
}
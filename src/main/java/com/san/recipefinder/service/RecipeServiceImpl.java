package com.san.recipefinder.service;

import com.san.recipefinder.dao.RecipeRepository;
import com.san.recipefinder.dto.AddRecipePayload;
import com.san.recipefinder.dto.UpdateRecipePayload;
import com.san.recipefinder.model.Recipe;
import com.san.recipefinder.service.exception.RecipeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public void addRecipe(AddRecipePayload addRecipePayload) {
        Recipe recipe = new Recipe();
        recipe.setVegetarian(addRecipePayload.isVegetarian());
        recipe.setRecipeName(addRecipePayload.getRecipeName());
        recipe.setNumberOfServings(addRecipePayload.getNumberOfServings());
        recipe.setIngredients(addRecipePayload.getIngredients());
        recipe.setInstructions(addRecipePayload.getInstructions());
        recipeRepository.save(recipe);
    }

    @Override
    public void updateRecipe(UpdateRecipePayload updateRecipePayload) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(updateRecipePayload.getRecipeId());
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            recipe.setVegetarian(updateRecipePayload.isVegetarian());
            recipe.setRecipeName(updateRecipePayload.getRecipeName());
            recipe.setNumberOfServings(updateRecipePayload.getNumberOfServings());
            recipe.setIngredients(updateRecipePayload.getIngredients());
            recipe.setInstructions(updateRecipePayload.getInstructions());
            recipeRepository.save(recipe);
        } else {
            throw new RecipeNotFoundException("No recipe found with recipeId : " + updateRecipePayload.getRecipeId());
        }
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> searchAllRecipes(String isVegetarian, String recipeName, String numberOfServings, String ingredients, String instructions) {
        return recipeRepository.findAllRecipes(isVegetarian,recipeName, numberOfServings, ingredients, instructions);
    }
}

package com.san.recipefinder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRecipePayload {
    Long recipeId;
    boolean isVegetarian;
    String recipeName;
    int numberOfServings;
    String ingredients;
    String instructions;
}

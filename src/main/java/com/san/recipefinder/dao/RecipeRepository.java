package com.san.recipefinder.dao;

import com.san.recipefinder.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("FROM Recipe r " +
            "WHERE " +
            "coalesce(to_char(r.isVegetarian), '%%') like  CONCAT('%',upper(:isVegetarian),'%') " +
            "and coalesce(upper(r.recipeName), '%%') like CONCAT('%',upper(:recipeName),'%') " +
            "and coalesce(to_char(r.numberOfServings), '%%') like  CONCAT('%',:numberOfServings,'%') "+
            "and coalesce(upper(r.ingredients), '%%') like CONCAT('%',upper(:ingredients),'%') "+
            "and coalesce(upper(r.instructions), '%%') like CONCAT('%',upper(:instructions),'%') "
    )
    List<Recipe> findAllRecipes(
            @Param("isVegetarian") String isVegetarian,
            @Param("recipeName") String recipeName,
            @Param("numberOfServings")String numberOfServings,
            @Param("ingredients") String ingredients,
            @Param("instructions") String instructions
    );
}

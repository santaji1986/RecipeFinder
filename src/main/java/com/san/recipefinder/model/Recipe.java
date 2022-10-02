package com.san.recipefinder.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isVegetarian;
    private String recipeName;
    private int numberOfServings;
    private String ingredients;
    private String instructions;
}


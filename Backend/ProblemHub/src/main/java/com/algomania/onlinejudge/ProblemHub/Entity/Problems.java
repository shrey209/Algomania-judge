package com.algomania.onlinejudge.ProblemHub.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Algomania_Problems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problems {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;

    private int problemDetailsId;

    private int difficultyId;
    
    private int categoryId;

    private int submissions;

    private String title;
}

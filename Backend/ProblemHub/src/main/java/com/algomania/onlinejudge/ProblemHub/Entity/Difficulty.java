package com.algomania.onlinejudge.ProblemHub.Entity;



import jakarta.persistence.Column;
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
@Table(name = "Algomania_Difficulty")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Difficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;

    @Column(length = 6)
    private String level;

    private int count;
}

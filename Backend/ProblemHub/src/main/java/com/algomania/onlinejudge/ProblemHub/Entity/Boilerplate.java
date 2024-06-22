package com.algomania.onlinejudge.ProblemHub.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Algomania_Boilerplate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Boilerplate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;

    private int problemDetailsId;

    @Column(length = 10)
    private String languageType;

    @Lob
    private String boilerplateCode;
}

package com.algomania.onlinejudge.ProblemHub.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Algomania_ProblemDetails")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name="solution_id")
    private int solutionId;

    @Lob
    @Column(name="description")
    private String description;

    @Lob
    @Column(name="constraints")
    private String constraints;

    @OneToMany(mappedBy = "problemDetails")
    @JsonManagedReference
    private List<SampleTestCase> sampleTestCases;
}

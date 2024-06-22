package com.algomania.onlinejudge.ProblemHub.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Algomania_SampleTestCase")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleTestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

   @Column
   @Lob
 private  String input;
   
   @Column
   @Lob
  private String output;
   
   @Column
   @Lob
  private String explanation;

   @ManyToOne
   @JoinColumn(name = "problem_details_id")
   @JsonBackReference
   private ProblemDetails problemDetails;
}

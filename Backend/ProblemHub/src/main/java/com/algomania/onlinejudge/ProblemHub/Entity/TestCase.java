package com.algomania.onlinejudge.ProblemHub.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "test_cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCase {

    @Override
	public String toString() {
		return "TestCase [id=" + id + ", problemId=" + problemId + ", input=" + input + ", output=" + output + "]";
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int problemId;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String input;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String output;
}

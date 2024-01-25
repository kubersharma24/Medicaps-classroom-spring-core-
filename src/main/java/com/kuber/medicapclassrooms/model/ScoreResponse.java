package com.kuber.medicapclassrooms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreResponse {
    private int quizId;
    private String userId;
    private int score;
    private int totalmarks;
}

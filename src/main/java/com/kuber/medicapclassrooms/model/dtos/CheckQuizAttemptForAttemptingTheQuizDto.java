package com.kuber.medicapclassrooms.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckQuizAttemptForAttemptingTheQuizDto {
    private int quizId;
    private String userId;
}

package com.kuber.medicapclassrooms.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizRespounseDto {
    private int quizId;
    private String quizTitle;
    private String quizDescription;
    private String status;
}

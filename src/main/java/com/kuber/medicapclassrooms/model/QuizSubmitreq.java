package com.kuber.medicapclassrooms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizSubmitreq {
    private int questionId;
    private String answer;
}

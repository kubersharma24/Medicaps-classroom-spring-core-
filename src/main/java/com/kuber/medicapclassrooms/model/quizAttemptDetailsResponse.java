package com.kuber.medicapclassrooms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class quizAttemptDetailsResponse {
    private int questionId;
    private int serialNo;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}

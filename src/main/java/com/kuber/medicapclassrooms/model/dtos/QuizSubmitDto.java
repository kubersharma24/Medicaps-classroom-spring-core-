package com.kuber.medicapclassrooms.model.dtos;

import com.kuber.medicapclassrooms.model.QuizSubmitreq;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
@Getter
@Setter
public class QuizSubmitDto {
    private int quizId;
    private String userId;
    private String classId;
    List <QuizSubmitreq> response;
}

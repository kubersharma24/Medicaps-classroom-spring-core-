package com.kuber.medicapclassrooms.model.dtos;

import com.kuber.medicapclassrooms.model.QuestionResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class QuestionListResponseDto {
    private List<QuestionResponse> Questions;
    private int quizId;
}

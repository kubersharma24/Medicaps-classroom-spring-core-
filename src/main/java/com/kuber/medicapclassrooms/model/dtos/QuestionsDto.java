package com.kuber.medicapclassrooms.model.dtos;
import com.kuber.medicapclassrooms.model.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionsDto {
    private int quizId;
    private List<Question>questions;
}

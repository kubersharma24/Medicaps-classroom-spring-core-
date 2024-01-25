package com.kuber.medicapclassrooms.services;

import com.kuber.medicapclassrooms.model.*;
import com.kuber.medicapclassrooms.model.dtos.*;

import java.util.List;

public interface Services {
    public boolean isValidUser(Logininfo logininfo);

    String getRole(Logininfo logininfo);

    boolean isValidEmail(Logininfo logininfo);

    boolean isValidPassword(Logininfo logininfo);

    boolean createAccount(Signup signup);

    List<quizAttemptDetailsResponse> getAttemptQuizDetails(QuizIdDto quizId);

    boolean submitQuizResponse(QuizSubmitDto quizSubmit);

    boolean setScoreOfthequiz(QuizSubmitDto quizSubmit);

    List<ClassroomResponse> findAllClassOfStudent(StudentIdDto studentId);

    boolean checkIfuserHAsAllradyJoinedCLass(CLassCodeDto joinInclass);

    boolean joinStudentInClass(CLassCodeDto joinInclass);

    boolean De_roleFromClassByClassIdAndUserId(CLassCodeDto classCode);

    List<QuizResponseToStudent> getAllQuizOfStudentInClass(CLassCodeDto cLassCode);

    Object getScoresOfStudentAttendedTheQuizByQuizId(StudentScoreDto studentScore);

    boolean setQuizStatusToOn(QuizIdDto quizIdDto);

    Object getCLassDetails(CLassCodeDto cLassCodeDto);

    List<Student> getAllStudentsInClass(CLassCodeDto classCode);

    boolean setQuizStatusToOFF(QuizIdDto quizIdDto);

    boolean attachQuestionsToQuiz(QuestionsDto questions);

    QuestionListResponseDto getQuizDetails(QuizRespounseDto quizRespounse);

    List<QuizRespounseDto> findAllQuiz(QuizRequestDto quizRequest);

    boolean createNewQuiz(QuizCreationDto newQuiz);

    boolean deleteQuizById(QuizIdDto quizDeleteReq);

    Object getQuizStatusById(QuizIdDto quizIdDto);

    List<ScoreResponse> getScoresOfStudentAttendedTheQuiz(QuizIdDto quizId);
}

package com.kuber.medicapclassrooms.utils;

import com.kuber.medicapclassrooms.model.*;
import com.kuber.medicapclassrooms.model.dtos.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.kuber.medicapclassrooms.utils.Constants.*;

public class DBExecuter {
    public static Connection con;
    static private AtomicInteger seq;

    public DBExecuter() {
        con = DBconnecter.getConnection();
        seq = this.getSequencer();
    }

    public static List<QuizRespounseDto> findAllQuiz(QuizRequestDto quizRequest) {
        List<QuizRespounseDto> list = new ArrayList<>();
        PreparedStatement stm;

        try {
            stm = con.prepareStatement(FIND_ALL_QUIZ_IN_CLASS);
            stm.setString(1, quizRequest.getClassId());
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                int quizid = resultSet.getInt("quizId");
                stm = con.prepareStatement(FIND_QUIZ_IN_CLASS);
                stm.setInt(1, quizid);
                ResultSet resultSet1 = stm.executeQuery();
                while (resultSet1.next()) {
                    QuizRespounseDto respounse = new QuizRespounseDto();
                    respounse.setQuizId(quizid);
                    respounse.setQuizDescription(resultSet1.getString("quizDesc"));
                    respounse.setQuizTitle(resultSet1.getString("title"));
                    respounse.setStatus(resultSet1.getString("status"));
                    list.add(respounse);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static boolean deleteQuizById(QuizIdDto quizDeleteReq) {
        PreparedStatement stm ;                                           // 2 DELETE_SCORE_RELATED_TO_QUIZ_ID_FROM_SCOREBOARD_TABLE
        try {                                                            // 3  DELETE_QUESTION_IN_QUIZ
            stm = con.prepareStatement(DELETE_QUIZ_RESPONSE_FROM_QUIZRESPONSE_TABLE); //1   //  4  DELETE_QUIZ_BY_ID_FROM_CLASSROOM_QUIZ_TABLE
            stm.setInt(1,quizDeleteReq.getQuizId());        //  5  DELETE_QUIZ_BY_ID_FROM_QUIZ_TABLE
            if(stm.executeUpdate()>=0){
                stm = con.prepareStatement(DELETE_SCORE_RELATED_TO_QUIZ_ID_FROM_SCOREBOARD_TABLE); // 2
                stm.setInt(1,quizDeleteReq.getQuizId());
                if(stm.executeUpdate()>=0){
                    stm = con.prepareStatement(DELETE_QUESTION_IN_QUIZ); // 3
                    stm.setInt(1, quizDeleteReq.getQuizId());
                    if (stm.executeUpdate() >=0) {
                        stm = con.prepareStatement(DELETE_QUIZ_BY_ID_FROM_CLASSROOM_QUIZ_TABLE);// 4
                        stm.setInt(1, quizDeleteReq.getQuizId());
                        if(stm.executeUpdate()>=0){
                            stm = con.prepareStatement(DELETE_QUIZ_BY_ID_FROM_QUIZ_TABLE);// 5
                            stm.setInt(1, quizDeleteReq.getQuizId());
                            if(stm.executeUpdate()>=0){
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean attachQuestionsToQuiz(QuestionsDto questions) {
        PreparedStatement stm;
        List<Question> allquestions = questions.getQuestions();
        int quizId = questions.getQuizId();
        try {
            for(Question quest : allquestions){
                stm = con.prepareStatement(ADD_QUESTIONS_IN_QUIZ);
                stm.setInt(1, quizId);
                stm.setInt(2,quest.getQuestionId());
                stm.setString(3, quest.getQuestion());
                stm.setString(4, quest.getOption1());
                stm.setString(5, quest.getOption2());
                stm.setString(6, quest.getOption3());
                stm.setString(7, quest.getOption4());
                stm.setString(8, quest.getAnswer());
                stm.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static QuestionListResponseDto getQuizDetails(QuizRespounseDto quizRespounse) {
        PreparedStatement stm;
        List<QuestionResponse> list = new ArrayList<>();
        QuestionListResponseDto questions = new QuestionListResponseDto();
        questions.setQuizId(quizRespounse.getQuizId());
        try {
            stm = con.prepareStatement(FIND_ONE_QUIZ_DETAILS_WITH_QUIZ_ID);
            stm.setInt(1,quizRespounse.getQuizId());
            ResultSet resultSet = stm.executeQuery();
            while(resultSet.next()){
                QuestionResponse quest = new QuestionResponse();
                quest.setQuestionId(resultSet.getInt("questionId"));
                quest.setSerialNo(resultSet.getInt("serialNo"));
                quest.setQuestion(resultSet.getString("question"));
                quest.setOption1(resultSet.getString("option1"));
                quest.setOption2(resultSet.getString("option2"));
                quest.setOption3(resultSet.getString("option3"));
                quest.setOption4(resultSet.getString("option4"));
                quest.setAnswer(resultSet.getString("answer"));
                list.add(quest);
            }
            questions.setQuestions(list);
            return questions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean joinInClass(CLassCodeDto joinInclass) {
        PreparedStatement stm ;
        try {
            stm = con.prepareStatement(JOIN_STUDENT_IN_CLASS_QUERY);
            stm.setString(1,joinInclass.getClassCode());
            stm.setString(2,joinInclass.getUserId());
            if(stm.executeUpdate()>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static List<Student> getAllStudentsInClass(CLassCodeDto classCode) {
        PreparedStatement stm ;
        List<Student> list = new ArrayList<>();
        try {
            stm = con.prepareStatement(GET_ALL_STUDENST_USER_ID);
            stm.setString(1,classCode.getClassCode());
            ResultSet resultSet = stm.executeQuery();
            while(resultSet.next()){
                stm = con.prepareStatement(GET_ALL_INFO_ABOUT_STUENT_IN_CLASS);
                stm.setString(1, resultSet.getString("userID"));
                ResultSet resultSet1 = stm.executeQuery();
                while(resultSet1.next()){
                    Student student = new Student();
                    student.setRole(resultSet1.getString("role"));
                    student.setUserId(resultSet1.getString("email"));
                    student.setName(resultSet1.getString("name"));
                    list.add(student);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ClassroomResponse> findAllClassOfStudent(StudentIdDto studentId) {
        PreparedStatement stm ;
        List<ClassroomResponse> list = new ArrayList<>();
        try {
            stm = con.prepareStatement(FIND_ALL_CLASSID_BY_STUDENTID_IN_user_in_table);
            stm.setString(1,studentId.getUserId());
            ResultSet resultSet = stm.executeQuery();
            while(resultSet.next()) {
                stm = con.prepareStatement(FIND_ALL_CLASS_DETAILS_BY_CLASS_ID);
                stm.setString(1, resultSet.getString("classId"));
                ResultSet resultSet1 = stm.executeQuery();
                while(resultSet1.next()){
                    ClassroomResponse classroomResponse = new ClassroomResponse();
                    classroomResponse.setId(resultSet.getString("classId"));
                    classroomResponse.setDescription(resultSet1.getString("description"));
                    classroomResponse.setSubject(resultSet1.getString("subject"));
                    classroomResponse.setHandlerName(resultSet1.getString("handlername"));
                    list.add(classroomResponse);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<QuizResponseToStudent> getAllQuizOfStudentInClass(CLassCodeDto cLassCode) {
        PreparedStatement stm ;
        List<QuizResponseToStudent> list = new ArrayList<>();

        try {
            stm = con.prepareStatement(GET_ALL_QUIZ_ID_BY_CLASS_CODE_FROM_CLASSROOMQUIZ_TABLE);
            stm.setString(1,cLassCode.getClassCode());
            ResultSet resultSet = stm.executeQuery();
            while(resultSet.next()){
                stm = con.prepareStatement(GET_QUIZ_INFO_BY_QUIZID);
                stm.setInt(1,resultSet.getInt("quizId"));
                ResultSet resultSet1 = stm.executeQuery();
                while(resultSet1.next()){
                    QuizResponseToStudent quizinfo = new QuizResponseToStudent();
                    quizinfo.setQuizId(resultSet.getInt("quizId"));
                    quizinfo.setDescriptions(resultSet1.getString("quizDesc"));
                    quizinfo.setTitle(resultSet1.getString("title"));
                    list.add(quizinfo);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<quizAttemptDetailsResponse> getAttemptQuizDetails(QuizIdDto quizId) {
        PreparedStatement stm ;
        List<quizAttemptDetailsResponse> list =  new ArrayList<>();
        try {
            stm = con.prepareStatement(GET_QUIZINFO_FOR_ATTEMPTINGIT_FROM_QUESTIONS);
            stm.setInt(1,quizId.getQuizId());
            ResultSet resultSet = stm.executeQuery();
            while(resultSet.next()){
                quizAttemptDetailsResponse questions = new quizAttemptDetailsResponse();
                questions.setQuestionId(resultSet.getInt("questionId"));
                questions.setSerialNo(resultSet.getInt("serialno"));
                questions.setQuestion(resultSet.getString("question"));
                questions.setOption1(resultSet.getString("option1"));
                questions.setOption2(resultSet.getString("option2"));
                questions.setOption3(resultSet.getString("option3"));
                questions.setOption4(resultSet.getString("option4"));
                list.add(questions);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean submitQuizResponse(QuizSubmitDto quizSubmit) {
        PreparedStatement stm ;
        List<QuizSubmitreq> list = quizSubmit.getResponse();
        int quizId = quizSubmit.getQuizId();
        String userId = quizSubmit.getUserId();
        String classId = quizSubmit.getClassId();
        try {
            for(QuizSubmitreq resp : list){
                stm = con.prepareStatement(SUBMIT_RESPOUNSE_OF_QUIZREPONSETABLE);
                stm.setString(1,quizSubmit.getUserId());
                stm.setInt(2,quizSubmit.getQuizId());
                stm.setString(3,quizSubmit.getClassId());
                stm.setInt(4,resp.getQuestionId());
                stm.setString(5,resp.getAnswer());
                stm.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean setScoreOfthequiz(QuizSubmitDto quizSubmit) {
        PreparedStatement stm ;
        try {
            stm = con.prepareStatement(GET_ALL_ANSWERS_FROM_QUIZANSWERTABLE_WITH_USERID_AND_QUIZID);
            stm.setString(1,quizSubmit.getUserId());
            stm.setInt(2,quizSubmit.getQuizId());
            ResultSet resultSet = stm.executeQuery();

            stm = con.prepareStatement(GET_ALL_CORRECT_ANSERS_FROM_QUESTIONS_TABLE_USING_QUIZID);
            stm.setInt(1,quizSubmit.getQuizId());
            ResultSet resultSet1 = stm.executeQuery();

            List <String> answers = new ArrayList<>();
            List <String> correctAnswers = new ArrayList<>();
            int correctAns = 0;
            int totalQuestions=0;
            while(resultSet.next() && resultSet1.next()){
//                correctAnswers.add(resultSet1.getString("answer"));
//                answers.add(resultSet.getString("answer"));
                if(resultSet1.getString("answer").equals(resultSet.getString("answer"))){
                    correctAns++;
                }
                totalQuestions++;
            }
            stm = con.prepareStatement(UPDATE_SCORE_IN_THE_SCOREBOARD_TABLE); //quizId, userId, score , totalmarks
            stm.setInt(1,quizSubmit.getQuizId());
            stm.setString(2,quizSubmit.getUserId());
            stm.setInt(3,correctAns);
            stm.setInt(4,totalQuestions);
            if(stm.executeUpdate()>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static List<ScoreResponse> getScoresOfStudentAttendedTheQuiz(QuizIdDto quizId) {
        PreparedStatement stm ;
        try {
            stm = con.prepareStatement(GET_LIST_OF_SCORE_OF_QUIZ_WITH_QUIZID);
            stm.setInt(1,quizId.getQuizId());
            ResultSet resultSet = stm.executeQuery();
            List<ScoreResponse> list = new ArrayList<>();
            while(resultSet.next()){
                ScoreResponse score = new ScoreResponse();
                score.setScore(resultSet.getInt("score"));
                score.setTotalmarks(resultSet.getInt("totalmarks"));
                score.setQuizId(resultSet.getInt("quizId"));
                score.setUserId(resultSet.getString("userId"));
                list.add(score);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getScoresOfStudentAttendedTheQuizByQuizId(StudentScoreDto studentScore) {
        PreparedStatement stm ;
        try {
            stm = con.prepareStatement(GET_SCORE_OF_THE_STUDENT_BY_USERID_AND_QUIZID);
            stm.setString(1,studentScore.getUserId());
            stm.setInt(2,studentScore.getQuizId());
            ResultSet resultSet = stm.executeQuery();
            while(resultSet.next()) {
                ScoreResponse score = new ScoreResponse();
                score.setQuizId(studentScore.getQuizId());
                score.setUserId(studentScore.getUserId());
                score.setScore(resultSet.getInt("score"));
                score.setTotalmarks(resultSet.getInt("totalmarks"));
                return score;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean deleteClassById(CLassCodeDto cLassCode) {
        PreparedStatement stm ;
        try {
            stm= con.prepareStatement(REMOVING_USERS_FROM_CLASS_BY_REMOVING_THEM_FROM_UERS_IN_CLASS_TABLE);
            stm.setString(1,cLassCode.getClassCode());
            int del = stm.executeUpdate();
            if(del >=0){
                stm= con.prepareStatement(DELETE_CLASS_FROM_CLASS_TABLE_BY_CLASSID);
                stm.setString(1,cLassCode.getClassCode());
                if(stm.executeUpdate()>0){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static List<QuizIdDto> findAllQuizByClassId(CLassCodeDto cLassCode) {
        List<QuizIdDto> list = new ArrayList<>();
        PreparedStatement stm ;
        try {
            stm = con.prepareStatement(FIND_ALL_QUIZ_IN_CLASS);
            stm.setString(1,cLassCode.getClassCode());
            ResultSet resultSet1 = stm.executeQuery();
            while(resultSet1.next()){
                QuizIdDto quiz = new QuizIdDto();
                quiz.setQuizId(resultSet1.getInt("quizId"));
                list.add(quiz);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean CheckValidation(Logininfo logininfo) {
        String email = logininfo.getEmail();
        String password = logininfo.getPassword();
        try {
            PreparedStatement stm = con.prepareStatement(CHECK_VALIDATION_QUERY);
            stm.setString(1, email);
            ResultSet resultSet = stm.executeQuery();
            if (!resultSet.next()) {
                return false;
            } else {
                String passStringDB = resultSet.getString("password"), passStringLoginString = logininfo.getPassword();
                if (passStringDB.equals(passStringLoginString)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getRoleByEmail(String email) {
        try {
            PreparedStatement stm = con.prepareStatement(GET_ROLE_BY_EMAIL);
            stm.setString(1, email);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean checkEmail(Logininfo logininfo) {
        String email = logininfo.getEmail();
        try {
            PreparedStatement stm = con.prepareStatement(CHECK_EMAIL_QUERY);
            stm.setString(1, email);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkPassword(Logininfo logininfo) {
        String email = logininfo.getEmail();
        String password = logininfo.getPassword();
        try {
            PreparedStatement stm = con.prepareStatement(CHECK_PASSWORD_QUERY);
            stm.setString(1, email);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                String passStringDB = resultSet.getString("password"), passStringLoginString = logininfo.getPassword();
                if (passStringDB.equals(passStringLoginString)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean De_roleFromClassByClassIdAndUserId(CLassCodeDto classCode) {
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(DROP_CLASSROOM_BY_CLASSID_USING_STUDENT_USER_NAME_AND_CLASSCODE);
            stm.setString(1,classCode.getUserId());
            stm.setString(2,classCode.getClassCode());
            if(stm.executeUpdate()>=0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean checkIfUserHasAttemptedTheQuiz(CheckQuizAttemptForAttemptingTheQuizDto quizAttemptForAttemptingTheQuiz) {
        PreparedStatement stm ;
        try {
            stm = con.prepareStatement(CHECK_IF_USER_HAS_ATTEMPTED_THE_QUIZ_OR_NOT);
            stm.setInt(1,quizAttemptForAttemptingTheQuiz.getQuizId());
            stm.setString(2, quizAttemptForAttemptingTheQuiz.getUserId());
            ResultSet resultSet1 = stm.executeQuery();
            if(resultSet1.next())
                return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean checkIfuserHAsAllradyJoinedCLass(CLassCodeDto joinInclass) {
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(SEE_IF_STUDENT_HAS_ALRADY_JOINED_THE_CLASS_OR_NOT_IN_USER_IN_CLASSROOM_TABLE);
            stm.setString(1, joinInclass.getUserId());
            stm.setString(2, joinInclass.getClassCode());
            ResultSet resultSet1 = stm.executeQuery();
            if(resultSet1.next())
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean setQuizStatusToOn(QuizIdDto quizIdDto) {
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(SET_QUIZ_STATUS_TO_ON_FROM_OF_IN_QUIZ_TABLE);
            stm.setString(1,"ON");
            stm.setString(2,"OFF");
            stm.setInt(3,quizIdDto.getQuizId());
            if(stm.executeUpdate()>=0){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean setQuizStatusToOFF(QuizIdDto quizIdDto) {
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(SET_QUIZ_STATUS_TO_OFF_FROM_ON_IN_QUIZ_TABLE);
            stm.setString(1,"OFF");
            stm.setString(2,"ON");
            stm.setInt(3,quizIdDto.getQuizId());
            if(stm.executeUpdate()>=0){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getQuizStatusById(QuizIdDto quizIdDto) {
        PreparedStatement stm ;
        try {
            stm = con.prepareStatement(GET_STATUS_OF_QUIZ_FROM_QUIZ_ID);
            stm.setInt(1,quizIdDto.getQuizId());
            ResultSet resultSet1 = stm.executeQuery();
            if(resultSet1.next()){
                return resultSet1.getString("status");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getCLassDetails(CLassCodeDto cLassCodeDto) {
        PreparedStatement stm ;
        try {
            stm = con.prepareStatement(GET_DETAILS_OFF_ONE_CLASSROOM);
            stm.setString(1,cLassCodeDto.getClassCode());
            ResultSet resultSet = stm.executeQuery();
            while(resultSet.next()){
                Classroom classroomResponse = new Classroom();
                classroomResponse.setDescription(resultSet.getString("description"));
                classroomResponse.setId(cLassCodeDto.getClassCode());
                classroomResponse.setSubject(resultSet.getString("subject"));
                classroomResponse.setName(resultSet.getString("handlername"));
                classroomResponse.setEmail(resultSet.getString("handlerId"));
                return classroomResponse;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private AtomicInteger getSequencer() {
        int random = 1 + (int) (Math.random() * 999);
        return new AtomicInteger(random);
    }

    public static boolean createAccount(Signup signup) {
        String email = signup.getEmail();
        String password = signup.getPassword();
        String name = signup.getName();
        String role = signup.getRole();

        try {
            PreparedStatement stm = con.prepareStatement(CREATE_USER_IN_DATABASE_QUERY);
            stm.setString(1, name);
            stm.setString(2, email);
            stm.setString(3, password);
            stm.setString(4, role);

            if (stm.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Classroom> findAllClassroom(String email) {
        List<Classroom> list = new ArrayList<>();
        try {
            PreparedStatement stm = con.prepareStatement(GET_ALL_CLASS_QUERY);
            stm.setString(1, email);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                Classroom newClass = new Classroom();
                newClass.setId(resultSet.getString("classId"));
                newClass.setSubject(resultSet.getString("subject"));
                newClass.setName(resultSet.getString("handlername"));
                newClass.setEmail(resultSet.getString("handlerId"));
                newClass.setDescription(resultSet.getString("description"));
                list.add(newClass);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Classroom createNewClass(Classroom classroom) {
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(CREATE_CLASSROOM_QUERY);
            stm.setString(1, classroom.getId());
            stm.setString(2, classroom.getSubject());
            stm.setString(3, classroom.getDescription());
            stm.setString(4, classroom.getEmail());
            stm.setString(5, classroom.getName());
            if (stm.executeUpdate() > 0) {
                return classroom;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateClassCode() {
        LocalDate now = LocalDate.now();
        return new StringBuilder().append(now.getYear())
                .append("-")
                .append(now.getMonth().getValue())
                .append("-")
                .append(now.getDayOfMonth())
                .append("-")
                .append(seq.getAndIncrement()).toString();
    }

    public static boolean createNewQuiz(QuizCreationDto newQuiz) {
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(CREATE_NEW_QUIZ);
            stm.setString(1, newQuiz.getQuizTitle());
            stm.setString(2, newQuiz.getQuizDescription());
            stm.setString(3,"OFF");
            if (stm.executeUpdate() > 0) {
                int id = 0;
                stm = con.prepareStatement(QUERY_TO_GET_LASTENTRY);
                ResultSet resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    id = resultSet.getInt("quizId");
                }
                stm = con.prepareStatement(JOIN_NEW_QUIZ_CLASSID);
                stm.setString(1, newQuiz.getClassId());
                stm.setInt(2, id);
                if (stm.executeUpdate() > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}


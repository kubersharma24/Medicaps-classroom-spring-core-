package com.kuber.medicapclassrooms.utils;

public class Constants {
    public static final String CREATE_NEW_QUIZ =  "insert into quiz(title,quizDesc,status) values(?,?,?)";
	public static final String JOIN_NEW_QUIZ_CLASSID = "insert into classroom_quiz values(?,?)";
	public static final String QUERY_TO_GET_LASTENTRY = "select quizId from quiz ORDER BY quizId DESC LIMIT 1";
    public static final String FIND_ALL_QUIZ_IN_CLASS = "select * from Classroom_quiz where classId=?";
	public static final String FIND_QUIZ_IN_CLASS = "select * from quiz where quizId=?";
	public static final String DELETE_QUIZ_BY_ID_FROM_CLASSROOM_QUIZ_TABLE =  "delete from classroom_quiz where quizId=?";
	public static final String DELETE_QUIZ_BY_ID_FROM_QUIZ_TABLE =	"delete from quiz where quizId=?";
    public static final String ADD_QUESTIONS_IN_QUIZ ="insert into questions( quizId,serialNo,question,option1,option2,option3,option4,answer) values(?,?,?,?,?,?,?,?);";
    public static final String DELETE_QUESTION_IN_QUIZ = "delete from  questions where quizId=?";
	public static final String FIND_ONE_QUIZ_DETAILS_WITH_QUIZ_ID =  "select * from questions where quizId=?";
    public static final String JOIN_STUDENT_IN_CLASS_QUERY = "insert into user_in_class values (?,?)";
    public static final String GET_ALL_STUDENST_USER_ID =  "select * from user_in_Class where classId=?";
	public static final String GET_ALL_INFO_ABOUT_STUENT_IN_CLASS = "select email,name,role from user where email=?";
	public static final String FIND_ALL_CLASSID_BY_STUDENTID_IN_user_in_table = "select classId from user_in_class where userId=?";
	public static final String FIND_ALL_CLASS_DETAILS_BY_CLASS_ID = "select subject,handlername, description from classroom where classId =?";
    public static final String GET_ALL_QUIZ_ID_BY_CLASS_CODE_FROM_CLASSROOMQUIZ_TABLE = "select quizId from classroom_quiz where classId=?";
	public static final String GET_QUIZ_INFO_BY_QUIZID = "select * from quiz where quizId =?";
    public static final String GET_QUIZINFO_FOR_ATTEMPTINGIT_FROM_QUESTIONS = "select questionId,serialno,question,option1,option2,option3,option4 from questions where quizId=?";
    public static final String SUBMIT_QUIZ_RESP_TO_QUIZ_RESPONSE_TABLE = "insert into quizresponse values(?,?,?,?)";
	public static final String SUBMIT_RESPOUNSE_OF_QUIZREPONSETABLE = "insert into quizresponse values(?,?,?,?,?)";
    public static final String GET_ALL_ANSWERS_FROM_QUIZANSWERTABLE_WITH_USERID_AND_QUIZID = "select answer from quizresponse where userID=? and quizId=?";
	public static final String GET_ALL_CORRECT_ANSERS_FROM_QUESTIONS_TABLE_USING_QUIZID = "select answer from questions where quizId=?";
	public static final String UPDATE_SCORE_IN_THE_SCOREBOARD_TABLE = "insert into scoreboard (quizId, userId, score , totalmarks) values (?,?,?,?)";
	public static final String GET_LIST_OF_SCORE_OF_QUIZ_WITH_QUIZID = "select quizId,userId,score,totalmarks  from scoreboard where quizId=?";
    public static final String DELETE_SCORE_RELATED_TO_QUIZ_ID_FROM_SCOREBOARD_TABLE = "delete from scoreboard where quizId=?";
    public static final String GET_SCORE_OF_THE_STUDENT_BY_USERID_AND_QUIZID = "select score,totalmarks from scoreboard where userid=? and quizid=?";
	public static final String DELETE_QUIZ_RESPONSE_FROM_QUIZRESPONSE_TABLE = "delete from quizresponse where quizid=?";
	public static final String REMOVING_USERS_FROM_CLASS_BY_REMOVING_THEM_FROM_UERS_IN_CLASS_TABLE = "delete from user_in_class where classId=?";
	public static final String DELETE_CLASS_FROM_CLASS_TABLE_BY_CLASSID = "delete from classroom where classId=?";
    public static final String DROP_CLASSROOM_BY_CLASSID_USING_STUDENT_USER_NAME_AND_CLASSCODE = "delete from user_in_class where userId=? and classId=?";
    public static final String CHECK_IF_USER_HAS_ATTEMPTED_THE_QUIZ_OR_NOT = " select userId from quizresponse where quizId=? and userId=?";
    public static final String SEE_IF_STUDENT_HAS_ALRADY_JOINED_THE_CLASS_OR_NOT_IN_USER_IN_CLASSROOM_TABLE = "select userId from user_in_class where userId=? and classId=?";
	public static final String SET_QUIZ_STATUS_TO_ON_FROM_OF_IN_QUIZ_TABLE = "update quiz set status = ? where status = ? and quizID=?";
	public static final String SET_QUIZ_STATUS_TO_OFF_FROM_ON_IN_QUIZ_TABLE = "update quiz set status = ? where status = ? and quizID=?";
	public static final String GET_STATUS_OF_QUIZ_FROM_QUIZ_ID = "select status from quiz where quizid=?";
	public static final String GET_DETAILS_OFF_ONE_CLASSROOM = "select * from classroom where classId=?";
	static final String CONNECTIONS_URL = "jdbc:mysql://localhost:3306/classroom";
	static final String USERNAME ="root";
	static final String PASSWORD = "root";
	static final String VALID_EMAIL_QUERY = "select * user where email=?";            	  // VALIDATE USER 
	static final String CHECK_VALIDATION_QUERY = "select * from user where email=?";			  // 
	static final String CREATE_USER_IN_DATABASE_QUERY = "insert into user values(?,?,?,?)";	  //CREATE USER IN DB 
	static final String CHECK_PASSWORD_QUERY = "select * from user where email=?";			  //CHECK PASSWORD 
	static final String CHECK_EMAIL_QUERY = "select * from user where email=?";				  //CEHCK EMAIL IF VALID 
	static final String GET_ROLE_BY_EMAIL = "select role from user where email=?";			  //GET ROLE FROM USER FROM DB (STUDENT , TEACHER )
	static final String GET_NAME_BY_EMAIL = "select name from user where email=?";
	static final String CREATE_CLASSROOM_QUERY ="insert into classroom values(?,?,?,?,?)";
	static final String GET_ALL_CLASS_QUERY = "select * from classroom where handlerId=?";
}

package com.kuber.medicapclassrooms.services;
import com.kuber.medicapclassrooms.model.*;
import com.kuber.medicapclassrooms.model.dtos.*;
import com.kuber.medicapclassrooms.utils.DBExecuter;

import java.util.List;

public class Serviceimpl implements Services{
	DBExecuter dBexecuter;
	public Serviceimpl() {
		dBexecuter = new DBExecuter();
	}

	public boolean createAccount(Signup signup) {
		return DBExecuter.createAccount(signup);
	}

	public List<Classroom> findAllClass(String teacher) {
		return DBExecuter.findAllClassroom(teacher);
	}

	public Classroom creatNewClass(ClassRoomCreationRequestDto classroom) {
		Classroom newClassroom = new Classroom();
		newClassroom.setEmail(classroom.getEmail());
		newClassroom.setSubject(classroom.getSubject());
		newClassroom.setName(classroom.getName());
		newClassroom.setDescription(classroom.getDescription());
		newClassroom.setId(getUniqueCode());
		return DBExecuter.createNewClass(newClassroom);
	}

	private String getUniqueCode() {
		return DBExecuter.generateClassCode();
	}

	public boolean createNewQuiz(QuizCreationDto newQuiz) {
		return DBExecuter.createNewQuiz(newQuiz);
	}

	public List<QuizRespounseDto> findAllQuiz(QuizRequestDto quizRequest) {
		return DBExecuter.findAllQuiz(quizRequest);
	}

	public boolean deleteQuizById(QuizIdDto quizDeleteReq) {
		return DBExecuter.deleteQuizById(quizDeleteReq);
	}

	public boolean attachQuestionsToQuiz(QuestionsDto questions) {
		return DBExecuter.attachQuestionsToQuiz(questions);
	}

	public QuestionListResponseDto getQuizDetails(QuizRespounseDto quizRespounse) {
		return DBExecuter.getQuizDetails(quizRespounse);
	}

	public boolean joinStudentInClass(CLassCodeDto joinInclass) {
		return DBExecuter.joinInClass(joinInclass);
	}

	public List<Student> getAllStudentsInClass(CLassCodeDto classCode) {
		return DBExecuter.getAllStudentsInClass(classCode);
	}

	public List<ClassroomResponse> findAllClassOfStudent(StudentIdDto studentId) {
		return DBExecuter.findAllClassOfStudent(studentId);
	}

	public List<QuizResponseToStudent> getAllQuizOfStudentInClass(CLassCodeDto cLassCode) {
		return DBExecuter.getAllQuizOfStudentInClass(cLassCode);
	}

	public List<quizAttemptDetailsResponse> getAttemptQuizDetails(QuizIdDto quizId) {
		return DBExecuter.getAttemptQuizDetails(quizId);
	}

	public boolean submitQuizResponse(QuizSubmitDto quizSubmit) {
		return DBExecuter.submitQuizResponse(quizSubmit);
	}

	public boolean setScoreOfthequiz(QuizSubmitDto quizSubmit) {
		return DBExecuter.setScoreOfthequiz(quizSubmit);
	}

	public List<ScoreResponse> getScoresOfStudentAttendedTheQuiz(QuizIdDto quizId) {
		return DBExecuter.getScoresOfStudentAttendedTheQuiz(quizId);
	}

	public Object getScoresOfStudentAttendedTheQuizByQuizId(StudentScoreDto studentScore) {
		return DBExecuter.getScoresOfStudentAttendedTheQuizByQuizId(studentScore);
	}

	public boolean deleteClassById(CLassCodeDto cLassCode) {
		List<QuizIdDto> quizzes = DBExecuter.findAllQuizByClassId(cLassCode);
		for(QuizIdDto quiz : quizzes){
			if(deleteQuizById(quiz)){
				continue;
			}else{
				return false;
			}
		}
		return DBExecuter.deleteClassById(cLassCode);
	}

	public boolean isValidUser(Logininfo logininfo) {
		return DBExecuter.CheckValidation(logininfo);
	}

	public String getRole(Logininfo logininfo) {
		return DBExecuter.getRoleByEmail(logininfo.getEmail());
	}

	public boolean isValidEmail(Logininfo logininfo) {
		return DBExecuter.checkEmail(logininfo);
	}

	public boolean isValidPassword(Logininfo logininfo) {
		return DBExecuter.checkPassword(logininfo);
	}

	public boolean De_roleFromClassByClassIdAndUserId(CLassCodeDto classCode) {
		return DBExecuter.De_roleFromClassByClassIdAndUserId(classCode);
	}

	public boolean checkIfUserHasAttemptedTheQuiz(CheckQuizAttemptForAttemptingTheQuizDto quizAttemptForAttemptingTheQuiz) {
		return DBExecuter.checkIfUserHasAttemptedTheQuiz(quizAttemptForAttemptingTheQuiz);
	}

	public boolean checkIfuserHAsAllradyJoinedCLass(CLassCodeDto joinInclass) {
		return DBExecuter.checkIfuserHAsAllradyJoinedCLass(joinInclass);
	}

	public boolean setQuizStatusToOn(QuizIdDto quizIdDto) {
		return DBExecuter.setQuizStatusToOn(quizIdDto);
	}

	public boolean setQuizStatusToOFF(QuizIdDto quizIdDto) {
		return DBExecuter.setQuizStatusToOFF(quizIdDto);
	}

	public String getQuizStatusById(QuizIdDto quizIdDto) {
		return DBExecuter.getQuizStatusById(quizIdDto);
	}

	public Object getCLassDetails(CLassCodeDto cLassCodeDto) {
		return DBExecuter.getCLassDetails(cLassCodeDto);
	}
}

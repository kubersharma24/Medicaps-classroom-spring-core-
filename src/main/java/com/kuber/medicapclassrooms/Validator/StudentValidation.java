package com.kuber.medicapclassrooms.Validator;

import com.kuber.medicapclassrooms.model.dtos.CheckQuizAttemptForAttemptingTheQuizDto;
import com.kuber.medicapclassrooms.services.Serviceimpl;
import com.kuber.medicapclassrooms.utils.RequestResponseMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/students/classrooms/quizzes/validation")
public class StudentValidation extends HttpServlet {

    public RequestResponseMapper mapper;
    public Serviceimpl service;
    public StudentValidation (){
        service = new Serviceimpl();
        mapper = new RequestResponseMapper<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
//        CheckQuizAttemptForAttemptingTheQuizDto quizAttemptForAttemptingTheQuiz = (CheckQuizAttemptForAttemptingTheQuizDto) mapper.getRequestObject(resp,req, CheckQuizAttemptForAttemptingTheQuizDto.class);
        CheckQuizAttemptForAttemptingTheQuizDto quizAttemptForAttemptingTheQuiz = new CheckQuizAttemptForAttemptingTheQuizDto();
        int quizId = Integer.parseInt(req.getParameter("quizId"));
        String userId = req.getParameter("userId");
        quizAttemptForAttemptingTheQuiz.setQuizId(quizId);
        quizAttemptForAttemptingTheQuiz.setUserId(userId);
        resp.setContentType(MediaType.APPLICATION_JSON);
        if(service.checkIfUserHasAttemptedTheQuiz(quizAttemptForAttemptingTheQuiz)){
            out.print(mapper.setResponseObject("Attempted"));
        }else{
            out.print(mapper.setResponseObject("Not-Attempted"));
        }

    }
}

package com.kuber.medicapclassrooms.controller.TeacherContoller;

import com.kuber.medicapclassrooms.model.dtos.*;
import com.kuber.medicapclassrooms.services.Serviceimpl;
import com.kuber.medicapclassrooms.services.Services;
import com.kuber.medicapclassrooms.utils.NewContext;
import com.kuber.medicapclassrooms.utils.RequestResponseMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/teacher/classrooms/quiz")
public class QuizController extends HttpServlet {
    public Services service;
    public RequestResponseMapper mapper ;
    private static ApplicationContext context;

    @Override // return all available quiz
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = NewContext.getContext();
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        QuizRequestDto quizRequest = new QuizRequestDto();
        quizRequest.setClassId(req.getParameter("classId"));
        List <QuizRespounseDto> list = service.findAllQuiz(quizRequest);
        resp.setContentType(MediaType.APPLICATION_JSON);
        out.print(mapper.setResponseObject(list));
    }

    @Override // create a new quiz
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        context = NewContext.getContext();
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        QuizCreationDto NewQuiz = (QuizCreationDto) mapper.getRequestObject(resp,req, QuizCreationDto.class);
        resp.setContentType(MediaType.APPLICATION_JSON);
        if(service.createNewQuiz(NewQuiz)){
            out.print(mapper.setResponseObject(NewQuiz));
        }else{
            out.print("Bad Request");
        }

    }

    @Override// delete quiz
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = NewContext.getContext();
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        QuizIdDto quizDeleteReq= (QuizIdDto) mapper.getRequestObject(resp,req,QuizIdDto.class);
        resp.setContentType(MediaType.APPLICATION_JSON);
        if(service.deleteQuizById(quizDeleteReq)){
            out.print(mapper.setResponseObject("200"));
        }else{
            out.print(mapper.setResponseObject("500"));
        }
    }
}

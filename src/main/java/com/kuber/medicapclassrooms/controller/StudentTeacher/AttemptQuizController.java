package com.kuber.medicapclassrooms.controller.StudentTeacher;

import com.kuber.medicapclassrooms.model.dtos.QuizIdDto;
import com.kuber.medicapclassrooms.model.dtos.QuizSubmitDto;
import com.kuber.medicapclassrooms.model.quizAttemptDetailsResponse;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/students/classrooms/quizzes/quiz")
public class AttemptQuizController extends HttpServlet {
    public Services service;
    public RequestResponseMapper mapper;
    private static ApplicationContext context;


    @Override// get delails for attempting the quiz by quiz id
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = NewContext.getContext();                                                    // creation of been
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        QuizIdDto quizId = new QuizIdDto();
        quizId.setQuizId(Integer.parseInt(req.getParameter("quizId")));
        List <quizAttemptDetailsResponse> list = service.getAttemptQuizDetails(quizId);
        resp.setContentType(MediaType.APPLICATION_JSON);
        out.print(mapper.setResponseObject(list));
    }

    @Override// submit the quiz response and will generate the quiz score
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = NewContext.getContext();                                                     // creation of been
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        QuizSubmitDto quizSubmit = (QuizSubmitDto) mapper.getRequestObject(resp,req,QuizSubmitDto.class);
        resp.setContentType(MediaType.APPLICATION_JSON);
        if(service.submitQuizResponse(quizSubmit)){
            if(service.setScoreOfthequiz(quizSubmit)){
                out.print(mapper.setResponseObject(quizSubmit));
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

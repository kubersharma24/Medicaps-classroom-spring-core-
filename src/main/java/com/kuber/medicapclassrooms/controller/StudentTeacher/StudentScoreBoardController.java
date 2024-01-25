package com.kuber.medicapclassrooms.controller.StudentTeacher;

import com.kuber.medicapclassrooms.model.dtos.StudentScoreDto;
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

@WebServlet("/students/classrooms/quizzes/score")
public class StudentScoreBoardController extends HttpServlet {
    public Services service;
    public RequestResponseMapper mapper;
    private static ApplicationContext context;

    @Override // return score of the one perticular quiz
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = NewContext.getContext();                                                     // creation of been
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        StudentScoreDto studentScore = new StudentScoreDto();
        studentScore.setQuizId(Integer.parseInt(req.getParameter("quizId")));
        studentScore.setUserId(req.getParameter("userId"));

        resp.setContentType(MediaType.APPLICATION_JSON);
        out.print(mapper.setResponseObject(service.getScoresOfStudentAttendedTheQuizByQuizId(studentScore)));
    }
}

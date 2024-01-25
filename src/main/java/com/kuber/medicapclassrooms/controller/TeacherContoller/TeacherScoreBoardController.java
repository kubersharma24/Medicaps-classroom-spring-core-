package com.kuber.medicapclassrooms.controller.TeacherContoller;
import com.kuber.medicapclassrooms.model.ScoreResponse;
import com.kuber.medicapclassrooms.model.dtos.QuizIdDto;
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
import java.util.List;

@WebServlet("/teacher/classrooms/quizzes/score")
public class TeacherScoreBoardController extends HttpServlet {
    public Services service;
    public RequestResponseMapper mapper;
    private static ApplicationContext context;

    @Override // return total score of the quiz
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        context = NewContext.getContext();
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        QuizIdDto quizId = new QuizIdDto();
        quizId.setQuizId(Integer.parseInt(req.getParameter("quizId")));
        List<ScoreResponse> list = service.getScoresOfStudentAttendedTheQuiz(quizId);
        resp.setContentType(MediaType.APPLICATION_JSON);
        out.print(mapper.setResponseObject(list));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

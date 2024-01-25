package com.kuber.medicapclassrooms.controller.TeacherContoller;

import com.kuber.medicapclassrooms.model.dtos.QuestionListResponseDto;
import com.kuber.medicapclassrooms.model.dtos.QuestionsDto;
import com.kuber.medicapclassrooms.model.dtos.QuizRespounseDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/teacher/classrooms/quiz/question")
public class QuestionController extends HttpServlet {
    public Services service;
    private static Logger logger = LoggerFactory.getLogger(QuestionController.class);
    public RequestResponseMapper mapper ;
    private static ApplicationContext context;
    @Override // get quiz details by quiz id
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        context = NewContext.getContext();
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        logger.info("Request has reached here");
        PrintWriter out = resp.getWriter();
        QuizRespounseDto quizRespounse = new QuizRespounseDto();
        int quizId = Integer.parseInt(req.getParameter("quizId"));
        quizRespounse.setQuizId(quizId);
        QuestionListResponseDto Questions = service.getQuizDetails(quizRespounse);
        resp.setContentType(MediaType.APPLICATION_JSON);
        out.print(mapper.setResponseObject(Questions));
    }

    @Override // add questions to quiz
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = NewContext.getContext();
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        QuestionsDto questions = (QuestionsDto) mapper.getRequestObject(resp,req,QuestionsDto.class);
        resp.setContentType(MediaType.APPLICATION_JSON);
        if(service.attachQuestionsToQuiz(questions)){
            out.print(mapper.setResponseObject(questions));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

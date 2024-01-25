package com.kuber.medicapclassrooms.controller.StudentTeacher;

import com.kuber.medicapclassrooms.model.QuizResponseToStudent;
import com.kuber.medicapclassrooms.model.dtos.CLassCodeDto;
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
import jakarta.ws.rs.core.NewCookie;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/students/classrooms/quizzes")
public class StudentDetailController extends HttpServlet {
    public Services service;
    public RequestResponseMapper mapper;
    private static ApplicationContext context;
    @Override// return all quizzes in class of student bases on teacher id
            // classId-> quizinclass-> return tile and descriptin
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = NewContext.getContext();                                                     // creation of been
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        CLassCodeDto cLassCode = new CLassCodeDto();
        cLassCode.setClassCode(req.getParameter("classCode"));
        List<QuizResponseToStudent> list = service.getAllQuizOfStudentInClass(cLassCode);
        resp.setContentType(MediaType.APPLICATION_JSON);
        out.print(mapper.setResponseObject(list));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

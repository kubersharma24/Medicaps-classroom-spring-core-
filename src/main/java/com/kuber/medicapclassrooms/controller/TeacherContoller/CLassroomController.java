package com.kuber.medicapclassrooms.controller.TeacherContoller;

import com.kuber.medicapclassrooms.model.Student;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/teachers/classroom")
public class CLassroomController extends HttpServlet {
    public Services service;
    public RequestResponseMapper mapper ;
    // return list of all students in a class
    private static ApplicationContext context;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = NewContext.getContext();                                                     // creation of been
        service  = (Services) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        CLassCodeDto classCode = new CLassCodeDto();
        classCode.setClassCode(req.getParameter("classCode"));
        List<Student> list = service.getAllStudentsInClass(classCode);
        resp.setContentType(MediaType.APPLICATION_JSON);
        if(list.size()>0){
            out.print(mapper.setResponseObject(list));
        }else{
            out.print(mapper.setResponseObject(new ArrayList<String>()));
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

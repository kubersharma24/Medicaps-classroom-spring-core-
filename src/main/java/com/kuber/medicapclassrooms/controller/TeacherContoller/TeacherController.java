package com.kuber.medicapclassrooms.controller.TeacherContoller;

import com.kuber.medicapclassrooms.model.Classroom;
import com.kuber.medicapclassrooms.model.dtos.CLassCodeDto;
import com.kuber.medicapclassrooms.model.dtos.ClassRoomRequestDto;
import com.kuber.medicapclassrooms.model.dtos.ClassRoomCreationRequestDto;
import com.kuber.medicapclassrooms.services.Serviceimpl;
import com.kuber.medicapclassrooms.utils.NewContext;
import com.kuber.medicapclassrooms.utils.RequestResponseMapper;
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

@WebServlet("/teachers")
public class TeacherController extends HttpServlet {

    public Serviceimpl service;
    public RequestResponseMapper mapper;
    private static ApplicationContext context;

    @Override // return list of classroom for particular teacher
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        context = NewContext.getContext();
        service  = (Serviceimpl) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        ClassRoomRequestDto classrooms = new ClassRoomRequestDto();
        classrooms.setTeacher(req.getParameter("teacher"));
        List<Classroom> classRoomlist = service.findAllClass(classrooms.getTeacher());
        resp.setContentType(MediaType.APPLICATION_JSON);
        if (classRoomlist.size() > 0) {
            out.print(mapper.setResponseObject(classRoomlist));
        } else {
            out.print("no class room found");
        }
    }

    @Override// add new class for a teacher
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        context = NewContext.getContext();
        service  = (Serviceimpl) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        ClassRoomCreationRequestDto classroom = (ClassRoomCreationRequestDto) mapper.getRequestObject(resp, req, ClassRoomCreationRequestDto.class);
        String path = req.getPathInfo();
        Classroom NewClass = service.creatNewClass(classroom);
        resp.setContentType(MediaType.APPLICATION_JSON);
        out.print(mapper.setResponseObject(NewClass));
    }

    @Override// delete a classroom
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        context = NewContext.getContext();
        service  = (Serviceimpl) context.getBean("Serviceimpl");                         // creation of been
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of been
        PrintWriter out = resp.getWriter();
        CLassCodeDto  cLassCode = (CLassCodeDto) mapper.getRequestObject(resp,req,CLassCodeDto.class);
        resp.setContentType(MediaType.APPLICATION_JSON);
        if(service.deleteClassById(cLassCode)){
                out.print(mapper.setResponseObject("200"));
        }else{
            out.print(mapper.setResponseObject("500"));
        }

    }

}

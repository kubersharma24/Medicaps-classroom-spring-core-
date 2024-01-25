package com.kuber.medicapclassrooms.controller.RegistrationController;

import com.kuber.medicapclassrooms.model.LoginResponse;
import com.kuber.medicapclassrooms.model.Logininfo;
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

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(urlPatterns = "/login")
public class loginController extends HttpServlet {
    public Services service;
    public RequestResponseMapper mapper ;
    // this get req return the role of the user trying to login to the the web app
    private static ApplicationContext context;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = NewContext.getContext();                                                     // creation of bean
        service  = (Services) context.getBean("Serviceimpl");                         // creation of bean
        mapper = (RequestResponseMapper) context.getBean("RequestResponseMapper");       // creation of bean
        PrintWriter out = resp.getWriter();
        Logininfo logininfo = (Logininfo) mapper.getRequestObject(resp,req, Logininfo.class);
        resp.setContentType(MediaType.APPLICATION_JSON);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(logininfo.getEmail());
        loginResponse.setPassword(logininfo.getPassword());
        if (service.isValidUser(logininfo)) {
            if(service.getRole(logininfo).equalsIgnoreCase("Student")){
                loginResponse.setStatus("Student");
            }else{
                loginResponse.setStatus("Teacher");
            }
        } else {
            if (service.isValidEmail(logininfo)) {
                if (!service.isValidPassword(logininfo)) {
                    loginResponse.setStatus("Incorrect password");
                }
            } else {
                loginResponse.setStatus("NO user found");
            }
        }
        out.print(mapper.setResponseObject(loginResponse));
    }
}
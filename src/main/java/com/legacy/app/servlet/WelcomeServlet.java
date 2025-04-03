package com.legacy.app.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Simple welcome servlet to test the server configuration
 * Mapping for this servlet is defined in web.xml
 */
public class WelcomeServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Welcome Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Welcome to Legacy AngularJS Servlet Application</h1>");
        out.println("<p>Server is running!</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
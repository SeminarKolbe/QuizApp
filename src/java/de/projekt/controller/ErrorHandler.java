/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.projekt.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author Marin
 */
@WebServlet("/ErrorHandler")
public class ErrorHandler extends HttpServlet {
//    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }
    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }    
        request.setAttribute("error", "Servlet " + servletName 
                +  " has thrown an exception " + throwable.getClass().getName() 
                + " : " + throwable.getMessage() + "\nStatuscode: " + statusCode 
                + "\nAnfrage an URI:" + requestUri);
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}

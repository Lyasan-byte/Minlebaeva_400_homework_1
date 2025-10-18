package com.lays.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "Hello", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    private static String lastMessage = "";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("Hello, World!");
        writer.println(lastMessage);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
        PrintWriter writer = resp.getWriter();
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        lastMessage = "post: " + body;
        writer.println("Post data: " + body);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
        PrintWriter writer = resp.getWriter();
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        lastMessage = "put: " + body;
        writer.println("Put data" +  body);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
        PrintWriter writer = resp.getWriter();
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        lastMessage = "delete: " + body;
        writer.println("Delete data" +  body);
    }
}

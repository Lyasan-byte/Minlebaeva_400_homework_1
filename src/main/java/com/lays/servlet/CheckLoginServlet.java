package com.lays.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckLogin", urlPatterns = "/check_login")
public class CheckLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        resp.setContentType("text/plain");

        if (SignUpServlet.USERS.containsKey(login)) {
            resp.getWriter().write("taken");
        } else {
            resp.getWriter().write("free");
        }
    }
}
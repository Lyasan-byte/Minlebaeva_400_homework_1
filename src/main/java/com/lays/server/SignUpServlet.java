package com.lays.server;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignUp", urlPatterns = "/sign_up")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("signUp.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null && password != null && !login.trim().isEmpty() && !password.trim().isEmpty()) {
            LoginServlet.USERS.put(login.trim(), password.trim());
            resp.sendRedirect("login.ftl");
        } else {
            resp.sendRedirect("signUp.ftl");
        }
    }
}

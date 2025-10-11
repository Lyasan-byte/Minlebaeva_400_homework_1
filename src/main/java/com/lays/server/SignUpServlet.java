package com.lays.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SignUp", urlPatterns = "/sign_up")
public class SignUpServlet extends HttpServlet {

    public static Map<String, String> USERS = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("sign_up.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || login.isEmpty()) {
            resp.sendRedirect("sign_up.ftl");
            return;
        }

        if (password == null || password.isEmpty()) {
            resp.sendRedirect("sign_up.ftl");
            return;
        }

        if (USERS.containsKey(login)) {
            resp.sendRedirect("sign_up.ftl");
            return;
        }

        USERS.put(login, password);
        resp.sendRedirect("login.ftl");
    }
}
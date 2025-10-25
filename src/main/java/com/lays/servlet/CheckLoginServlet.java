package com.lays.servlet;

import com.lays.service.UserService;
import com.lays.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckLogin", urlPatterns = "/check_login")
public class CheckLoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");

        System.out.println("CheckLoginServlet called with login: " + login);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (login == null || login.isEmpty()) {
            resp.getWriter().write("{\"available\": false, \"message\": \"Логин не может быть пустым\"}");
            return;
        }

        boolean isAvailable = userService.isLoginAvailable(login);

        System.out.println("Login available: " + isAvailable);

        if (isAvailable) {
            resp.getWriter().write("{\"available\": true, \"message\": \"Логин доступен\"}");
        } else {
            resp.getWriter().write("{\"available\": false, \"message\": \"Логин уже занят\"}");
        }
    }
}
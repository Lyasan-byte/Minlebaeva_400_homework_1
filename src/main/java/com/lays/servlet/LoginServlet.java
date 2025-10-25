package com.lays.servlet;


import com.lays.entity.User;
import com.lays.service.UserService;
import com.lays.service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("login.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            resp.sendRedirect("login.ftl?error=empty_fields");
            return;
        }

        User user = userService.loginUser(login, password);

        if (user != null) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", user.getLogin());
            httpSession.setAttribute("userId", user.getId());
            httpSession.setAttribute("userName", user.getName());
            httpSession.setMaxInactiveInterval(60 * 60);

            Cookie cookie = new Cookie("user", user.getLogin());
            cookie.setMaxAge(24 * 60 * 60);
            resp.addCookie(cookie);

            resp.sendRedirect("main");
        } else {
            resp.sendRedirect("login.ftl?error=invalid_credentials");
        }
    }
}
package com.lays.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    public static final Map<String, String> USERS = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("login.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null && password != null
                && USERS.containsKey(login)
                && USERS.get(login).equals(password)) {

            HttpSession session = req.getSession();
            session.setAttribute("user", login);
            session.setMaxInactiveInterval(60 * 60);

            Cookie cookie = new Cookie("user", login);
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            resp.addCookie(cookie);

            req.getRequestDispatcher("main.ftl").forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }
}
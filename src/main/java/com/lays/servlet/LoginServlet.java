package com.lays.servlet;

import com.lays.dao.UserDao;
import com.lays.dao.UserDaoImpl;
import com.lays.entity.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("login.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            resp.sendRedirect("login.ftl");
            return;
        }

        UserDao userDao = new UserDaoImpl();
        User user = userDao.getByLogin(login);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", login);
            httpSession.setMaxInactiveInterval(60 * 60);

            Cookie cookie = new Cookie("user", login);
            cookie.setMaxAge(24 * 60 * 60);
            resp.addCookie(cookie);

            resp.sendRedirect("main");
        } else {
            resp.sendRedirect("login.ftl");
        }
    }
}
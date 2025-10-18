package com.lays.servlet;

import com.lays.dao.UserDao;
import com.lays.dao.UserDaoImpl;
import com.lays.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "Main", urlPatterns = "/main")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.ftl");
            return;
        }

        String login = (String) session.getAttribute("user");
        UserDao userDao = new UserDaoImpl();
        User user = userDao.getByLogin(login);

        if (user == null) {
            resp.sendRedirect("login.ftl");
            return;
        }

        req.setAttribute("username", user.getName());
        req.setAttribute("sessionId", session.getId());

        String cookieUser = "";
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("user".equals(c.getName())) {
                    cookieUser = c.getValue();
                }
            }
        }
        req.setAttribute("cookieUser", cookieUser);
        req.setAttribute("photoUrl", user.getPhotoUrl());

        req.getRequestDispatcher("main.ftl").forward(req, resp);
    }
}
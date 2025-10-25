package com.lays.servlet;

import com.lays.dao.UserDao;
import com.lays.dao.UserDaoImpl;
import com.lays.entity.User;
import com.lays.service.UserService;
import com.lays.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "Main", urlPatterns = "/main")
public class MainServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        String username = (String) session.getAttribute("user");
        String sessionId = session.getId();

        String cookieUser = "";
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("user".equals(c.getName())) {
                    cookieUser = c.getValue();
                }
            }
        }

        User user = userService.getUserByLogin(username);
        String userImage = "";

        if (user != null && user.getImage() != null && !user.getImage().isEmpty()) {
            userImage = user.getImage();
            System.out.println("DEBUG: User image found: " + userImage);
        } else {
            System.out.println("DEBUG: No user image found");
        }

        req.setAttribute("username", username);
        req.setAttribute("sessionId", sessionId);
        req.setAttribute("cookieUser", cookieUser);
        req.setAttribute("userImage", userImage);
        req.getRequestDispatcher("main.ftl").forward(req, resp);
    }
}
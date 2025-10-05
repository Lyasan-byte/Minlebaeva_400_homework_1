package com.lays.server;

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

        req.setAttribute("username", username);
        req.setAttribute("sessionId", sessionId);
        req.setAttribute("cookieUser", cookieUser);
        req.getRequestDispatcher("main.ftl").forward(req, resp);
    }
}
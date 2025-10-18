package com.lays.servlet;

import com.lays.dao.UserDao;
import com.lays.dao.UserDaoImpl;
import com.lays.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SignUp", urlPatterns = "/sign_up")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class SignUpServlet extends HttpServlet {

    public static Map<String, String> USERS = new HashMap<>();
    public static Map<String, String> USER_PHOTOS = new HashMap<>();

    private static final String UPLOAD_DIR = "/tmp/uploads";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("sign_up.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || login.isEmpty() || password == null || password.isEmpty() ||
                name == null || name.isEmpty() || lastname == null || lastname.isEmpty()) {
            resp.sendRedirect("sign_up.ftl");
            return;
        }

        UserDao userDao = new UserDaoImpl();
        if (userDao.getByLogin(login) != null) {
            resp.sendRedirect("sign_up.ftl");
            return;
        }

        String photoPath = null;
        Part photoPart = req.getPart("photo");
        if (photoPart != null && photoPart.getSize() > 0) {
            String fileName = Paths.get(photoPart.getSubmittedFileName()).getFileName().toString();
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            new File(UPLOAD_DIR).mkdirs();

            File file = new File(UPLOAD_DIR, uniqueFileName);
            try (InputStream is = photoPart.getInputStream();
                 FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            photoPath = "/uploads/" + uniqueFileName;
        }

        User user = new User(null, name, lastname, login, password, photoPath);
        userDao.save(user);

        resp.sendRedirect("login.ftl");
    }
}
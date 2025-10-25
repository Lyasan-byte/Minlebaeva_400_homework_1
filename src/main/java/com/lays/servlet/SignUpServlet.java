package com.lays.servlet;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.lays.dao.UserDao;
import com.lays.dao.UserDaoImpl;
import com.lays.entity.User;
import com.lays.service.UserService;
import com.lays.service.UserServiceImpl;
import com.lays.util.CloudinaryUtil;

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
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class SignUpServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private static final String DEFAULT_IMAGE = "default-avatar.png";
    private final Cloudinary cloudinary = CloudinaryUtil.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("sign_up.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            resp.sendRedirect("sign_up.ftl?error=empty_fields");
            return;
        }

        if (name == null) name = "";
        if (lastname == null) lastname = "";

        String imageUrl = handleFileUpload(req, login);

        User newUser = new User(0, name, lastname, login, password, imageUrl);
        boolean isRegistered = userService.registerUser(newUser);

        if (isRegistered) {
            resp.sendRedirect("login.ftl?success=registered");
        } else {
            resp.sendRedirect("sign_up.ftl?error=registration_failed");
        }
    }

    private String handleFileUpload(HttpServletRequest request, String login) throws IOException, ServletException {
        Part filePart = request.getPart("image");

        if (filePart == null || filePart.getSize() == 0) {
            return "";
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        String contentType = filePart.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return "";
        }

        File tempFile = File.createTempFile("upload_", "_" + fileName);
        try (InputStream fileContent = filePart.getInputStream();
             FileOutputStream out = new FileOutputStream(tempFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        try {
            Map uploadResult = cloudinary.uploader().upload(tempFile,
                    ObjectUtils.asMap(
                            "folder", "user_avatars",
                            "public_id", "user_" + login + "_" + System.currentTimeMillis(),
                            "overwrite", false,
                            "resource_type", "image"
                    ));

            String imageUrl = (String) uploadResult.get("secure_url");
            System.out.println("Image uploaded to Cloudinary: " + imageUrl);
            return imageUrl;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error uploading to Cloudinary: " + e.getMessage());
            return "";
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
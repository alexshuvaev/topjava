package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.setAuthUserId;

public class UserServlet extends HttpServlet {
    private final Logger log = getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String signedUser = request.getParameter("user");

        switch (signedUser == null ? "" : signedUser) {
            case "1":
            case "2":
                setAuthUserId(Integer.parseInt(signedUser));
                log.debug("redirect to index.html");
                response.sendRedirect(request.getContextPath());
                break;
            default:
                log.debug("forward to users");
                request.getRequestDispatcher("/users.jsp").forward(request, response);
        }

    }
}

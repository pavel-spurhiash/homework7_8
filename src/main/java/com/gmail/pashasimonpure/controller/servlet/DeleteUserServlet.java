package com.gmail.pashasimonpure.controller.servlet;

import com.gmail.pashasimonpure.controller.util.ParseUtil;
import com.gmail.pashasimonpure.service.UserService;
import com.gmail.pashasimonpure.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class DeleteUserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("DeleteUserServlet: post");

        Integer id = ParseUtil.tryParseInt(req.getParameter("id"));

        if (id != null) {

            if (userService.deleteUser(id) == true) {

                logger.info("User#" + id + " successfully  deleted");
                req.setAttribute("msg", "User#" + id + " successfully deleted");
                getServletContext().getRequestDispatcher("/Message.jsp").forward(req, resp);

            } else {

                logger.error("Delete user error, check logs.");
                resp.sendError(500, "Delete user error, check logs."); //500 - server error

            }

        } else {

            logger.error("Incorrect parameter format.");
            resp.sendError(400, "Incorrect parameter format."); //400 - bad request
            return;

        }
    }
}
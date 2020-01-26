package com.gmail.pashasimonpure.controller.servlet;

import com.gmail.pashasimonpure.controller.util.ParseUtil;
import com.gmail.pashasimonpure.service.UserService;
import com.gmail.pashasimonpure.service.impl.UserServiceImpl;
import com.gmail.pashasimonpure.service.model.UserInformationDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class EditUserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("EditListServlet: post");

        Integer id = ParseUtil.tryParseInt(req.getParameter("id"));

        String address = ParseUtil.tryParseStr(req.getParameter("address"));
        String telephone = ParseUtil.tryParseStr(req.getParameter("telephone"));

        if (id != null && (address != null || telephone != null)) {

            UserInformationDTO userInfo = new UserInformationDTO();
            userInfo.setId(id);

            if (address != null) {
                userInfo.setAddress(address);
            }
            if (telephone != null) {
                userInfo.setTelephone(telephone);
            }

            if (userService.updateInfo(userInfo) == false) {

                logger.error("Add user info error, check logs.");
                resp.sendError(500, "Add user info error, check logs."); //500 - server error
                return;
            }

            req.setAttribute("msg", "User#" + id + " information successfully updated.");
            getServletContext().getRequestDispatcher("/Message.jsp").forward(req, resp);

        } else {

            logger.error("Incorrect parameter format.");
            resp.sendError(400, "Incorrect parameter format."); //400 - bad request
            return;

        }

    }
}
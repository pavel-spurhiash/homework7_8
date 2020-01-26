package com.gmail.pashasimonpure.controller.servlet;

import com.gmail.pashasimonpure.controller.util.ParseUtil;
import com.gmail.pashasimonpure.service.UserService;
import com.gmail.pashasimonpure.service.impl.UserServiceImpl;
import com.gmail.pashasimonpure.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class NewUserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("NewUserServlet:" + " method doPost()");

        String userName = ParseUtil.tryParseStr(req.getParameter("name"));
        String password = ParseUtil.tryParseStr(req.getParameter("password"));
        Boolean isActive = Boolean.parseBoolean(req.getParameter("isActive"));
        Integer age = ParseUtil.tryParseInt(req.getParameter("age"));
        String address = ParseUtil.tryParseStr(req.getParameter("address"));
        String telephone = ParseUtil.tryParseStr(req.getParameter("telephone"));

        if (userName != null && password != null && isActive != null && age != null) {

            UserDTO user = new UserDTO();
            user.setName(userName);
            user.setPassword(password);
            user.setActive(isActive);
            user.setAge(age);

            if (address != null) {
                user.setAddress(address);
            }
            if (telephone != null) {
                user.setTelephone(telephone);

            }

            if( userService.add(user)==false){

                logger.error("Create user error, check logs.");
                resp.sendError(500, "Create user error, check logs."); //500 - server error
                return;
            }

            req.setAttribute("msg", "User " + userName + " successfully created.");
            getServletContext().getRequestDispatcher("/Message.jsp").forward(req, resp);

        } else {

            logger.error("Incorrect parameter format.");
            resp.sendError(400, "Incorrect parameter format."); //400 - bad request
            return;

        }

    }
}
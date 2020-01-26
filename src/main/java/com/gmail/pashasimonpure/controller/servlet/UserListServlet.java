package com.gmail.pashasimonpure.controller.servlet;

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
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class UserListServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("UserListServlet: get");

        List<UserDTO> users = userService.findAll(); //get from service

        if (users.size() == 0) {
            logger.info("No users in database.");

            req.setAttribute("msg", "No users in database.");
            getServletContext().getRequestDispatcher("/Message.jsp").forward(req, resp);
            return;
        }

        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='css/table.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h2>User List</h2>");

            out.println("<table >");

            out.println(" <tr>");
            out.println("<td>User ID</td>");
            out.println("<td>User Name</td>");
            out.println("<td>Password</td>");
            out.println("<td>Is Active</td>");
            out.println("<td>Age</td>");
            out.println("<td>Address</td>");
            out.println("<td>Telephone</td>");
            out.println("</tr>");

            //users fields output:
            for(UserDTO user : users){
                out.println("<tr>");
                out.println("<td>"+user.getId()+"</td>");
                out.println("<td>"+user.getName()+"</td>");
                out.println("<td>"+user.getPassword()+"</td>");
                out.println("<td>"+user.getActive()+"</td>");
                out.println("<td>"+user.getAge()+"</td>");

                out.println("<td>"+user.getAddress()+"</td>");
                out.println("<td>"+user.getTelephone()+"</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</body></html>");
        }

    }

}
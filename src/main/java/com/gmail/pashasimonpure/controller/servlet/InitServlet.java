package com.gmail.pashasimonpure.controller.servlet;

import com.gmail.pashasimonpure.service.UserService;
import com.gmail.pashasimonpure.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;

import java.lang.invoke.MethodHandles;

public class InitServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void init() {

        logger.debug("initialization.");

        userService.drop();
        userService.init();
    }

}

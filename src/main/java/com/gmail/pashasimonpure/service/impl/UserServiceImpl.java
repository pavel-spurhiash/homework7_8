package com.gmail.pashasimonpure.service.impl;

import com.gmail.pashasimonpure.repository.ConnectionRepository;
import com.gmail.pashasimonpure.repository.UserInformationRepository;
import com.gmail.pashasimonpure.repository.UserRepository;
import com.gmail.pashasimonpure.repository.impl.ConnectionRepositoryImpl;
import com.gmail.pashasimonpure.repository.impl.UserInformationRepositoryImpl;
import com.gmail.pashasimonpure.repository.impl.UserRepositoryImpl;
import com.gmail.pashasimonpure.repository.model.User;
import com.gmail.pashasimonpure.repository.model.UserInformation;
import com.gmail.pashasimonpure.service.UserService;
import com.gmail.pashasimonpure.service.model.UserDTO;
import com.gmail.pashasimonpure.service.model.UserInformationDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private UserInformationRepository userInformationRepository = UserInformationRepositoryImpl.getInstance();

    private static UserService instance;

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();

        }
        return instance;
    }

    @Override
    public boolean init() {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                userRepository.createTable(connection);
                userInformationRepository.createTable(connection);

                connection.commit();
                return true;

            } catch (SQLException e) {

                connection.rollback();
                logger.error(e.getMessage(), e);
                return false;

            }
        } catch (SQLException e) {

            logger.error(e.getMessage(), e);
            return false;

        }

    }

    @Override
    public boolean drop() {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                userInformationRepository.dropTable(connection);
                userRepository.dropTable(connection);

                connection.commit();
                return true;

            } catch (SQLException e) {

                connection.rollback();
                logger.error(e.getMessage(), e);
                return false;

            }
        } catch (SQLException e) {

            logger.error(e.getMessage(), e);
            return false;

        }

    }

    @Override
    public boolean deleteUser(Integer id) {

        Boolean success = false;

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {

                try {
                    userInformationRepository.delete(connection, id);
                } catch (SQLException e) {
                    logger.debug("deleteUser: no user information to delete.");
                }
                userRepository.delete(connection, id);

                connection.commit();
                success = true;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return success;
    }

    @Override
    public boolean add(UserDTO userDTO) {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                User user = new User();

                user.setActive(userDTO.getActive());
                user.setAge(userDTO.getAge());
                user.setName(userDTO.getName());
                user.setPassword(userDTO.getPassword());

                //returns user with id field:
                user = userRepository.add(connection, user);

                if (userDTO.getTelephone() != null || userDTO.getAddress() != null) {

                    UserInformation userInfo = new UserInformation();
                    userInfo.setId(user.getId());
                    userInfo.setAddress(userDTO.getAddress());
                    userInfo.setTelephone(userDTO.getTelephone());

                    userInformationRepository.add(connection, userInfo);
                }

                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean updateInfo(UserInformationDTO userInfoDTO) {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                UserInformation userInfo = new UserInformation();
                userInfo.setId(userInfoDTO.getId());
                userInfo.setAddress(userInfoDTO.getAddress());
                userInfo.setTelephone(userInfoDTO.getTelephone());


                try {
                    //check if exists:
                    userInformationRepository.get(connection, userInfo.getId());
                } catch (SQLException e) {
                    userInformationRepository.add(connection, userInfo);
                    connection.commit();
                    return true;
                }

                userInformationRepository.update(connection, userInfo);

                connection.commit();
                return true;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                return false;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<UserDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                List<UserDTO> usersDTO = new ArrayList<>();

                //get users info map:
                List<UserInformation> usersInfo = userInformationRepository.findAll(connection);
                Map<Integer, UserInformation> usersInfoMap = new HashMap<>();
                for (UserInformation userInfo : usersInfo) {
                    usersInfoMap.put(userInfo.getId(), userInfo);
                }

                //get users list
                List<User> users = userRepository.findAll(connection);

                for (User user : users) {

                    UserDTO userDTO = new UserDTO();

                    userDTO.setId(user.getId());
                    userDTO.setActive(user.getActive());
                    userDTO.setAge(user.getAge());
                    userDTO.setName(user.getName());
                    userDTO.setPassword(user.getPassword());

                    if (usersInfoMap.get(user.getId()) != null) {

                        userDTO.setAddress(usersInfoMap.get(user.getId()).getAddress());
                        userDTO.setTelephone(usersInfoMap.get(user.getId()).getTelephone());

                    }

                    usersDTO.add(userDTO);
                }

                connection.commit();
                return usersDTO;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

}
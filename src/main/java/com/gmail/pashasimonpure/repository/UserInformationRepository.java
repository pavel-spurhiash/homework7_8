package com.gmail.pashasimonpure.repository;

import com.gmail.pashasimonpure.repository.model.UserInformation;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserInformationRepository extends GeneralRepository<UserInformation> {

    UserInformation get(Connection connection, Integer id) throws SQLException;

    void update(Connection connection, UserInformation userInfo) throws SQLException;

}

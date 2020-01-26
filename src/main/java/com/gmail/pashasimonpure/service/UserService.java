package com.gmail.pashasimonpure.service;

import com.gmail.pashasimonpure.service.model.UserDTO;
import com.gmail.pashasimonpure.service.model.UserInformationDTO;

import java.util.List;

public interface UserService {

    boolean add(UserDTO userDTO);

    boolean updateInfo(UserInformationDTO userDTO);

    boolean deleteUser(Integer id);

    List<UserDTO> findAll();

    boolean init();

    boolean drop();

}
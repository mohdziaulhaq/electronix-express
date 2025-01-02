package com.zia.electronix.express.services;

import com.zia.electronix.express.dtos.PageableResponse;
import com.zia.electronix.express.dtos.UserDto;
import com.zia.electronix.express.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto, String userId);

    //delete
    void deleteUser(String userId);

    //get all users
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortOrder);

    //get single user by id
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

}

package com.zia.electronix.express.services.impl;

import com.zia.electronix.express.dtos.UserDto;
import com.zia.electronix.express.entities.User;
import com.zia.electronix.express.services.UserService;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public UserDto createUser(UserDto userDto) {

        String userId = UUID.randomUUID().toString();

        //dto to entity
        User user = dtoToEntity(userDto);

        //entity to dto
        UserDto returnDto = entityToDto(user);

        return returnDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public UserDto getUser(String userId) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        return null;
    }

    User dtoToEntity(UserDto dto){
        User user = User.builder().userId(dto.getUserId()).
                name(dto.getName()).
                password(dto.getPassword()).
                email(dto.getEmail()).
                gender(dto.getGender()).build();
        return user;

    }

    UserDto entityToDto(User savedUser){
        UserDto userDto = UserDto.builder().userId(savedUser.getUserId()).
                name(savedUser.getName()).
                password(savedUser.getPassword()).
                email(savedUser.getEmail()).
                gender(savedUser.getGender()).build();
        return userDto;

    }
}

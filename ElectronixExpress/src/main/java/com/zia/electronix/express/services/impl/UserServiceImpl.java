package com.zia.electronix.express.services.impl;

import com.zia.electronix.express.dtos.UserDto;
import com.zia.electronix.express.entities.User;
import com.zia.electronix.express.exception.ResourceNotFoundException;
import com.zia.electronix.express.repositories.UserRepository;
import com.zia.electronix.express.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto to entity
        User user = dtoToEntity(userDto);
        User savedUser = repository.save(user);
        //entity to dto
        UserDto returnDto = entityToDto(savedUser);

        return returnDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = repository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        repository.save(user);

        UserDto updatedDto = entityToDto(user);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = repository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        repository.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers(int pageNumber, int pageSize, String sortField, String sortDirection) {

        Sort sort = (sortDirection.equalsIgnoreCase("desc"))? (Sort.by(sortField).descending()) : (Sort.by(sortField).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<User> page = repository.findAll(pageable);
        List<User> users = page.getContent();



        //List<User> users = repository.findAll();
        List<UserDto> usersDtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return usersDtoList;
    }

    @Override
    public UserDto getUserById(String userId) {

        User user = repository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given ID"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user = repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email"));

        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {

        List<User> users = repository.findByNameContaining(keyword);
        List<UserDto> userDtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    private User dtoToEntity(UserDto dto){
//        User user = User.builder().userId(dto.getUserId()).
//                name(dto.getName()).
//                password(dto.getPassword()).
//                email(dto.getEmail()).
//                gender(dto.getGender()).build();
//        return user;

        return modelMapper.map(dto, User.class);

    }

    private UserDto entityToDto(User savedUser){
//        UserDto userdto = UserDto.builder().userId(savedUser.getUserId()).
//                name(savedUser.getName()).
//                password(savedUser.getPassword()).
//                email(savedUser.getEmail()).
//                gender(savedUser.getGender()).build();
//        return userdto;

        return modelMapper.map(savedUser, UserDto.class);
    }
}

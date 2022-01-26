package com.budgety.api.service.impl;

import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.UserDto;
import com.budgety.api.payload.UserUpdateRequest;
import com.budgety.api.repository.UserRepository;
import com.budgety.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto getUser(String sub) {
        User user = userRepository.findBySub(sub).orElseThrow(()->new IllegalArgumentException("User not found with sub "+ sub));
        return mapToDto(user);
    }

    public User getUserEntity(String sub){
        return userRepository.findBySub(sub).orElseThrow(()->new IllegalArgumentException("User not found with sub "+ sub));
    }

    @Override
    public boolean userExists(String sub) {
        return userRepository.existsBySub(sub);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapToEntity(userDto);
        User newUser = userRepository.save(user);
        return this.mapToDto(newUser);
    }

    @Override
    public UserDto updateUserByPrincipal(Principal principal, UserUpdateRequest userUpdateRequest) {
        User oldUser = userRepository.findBySub(principal.getName()).orElseThrow(()->new ResourceNotFoundException("user", "sub", principal.getName()));
        oldUser.setEmail(userUpdateRequest.getEmail());
        oldUser.setConfirmed(userUpdateRequest.isConfirmed());
        User updatedUser = userRepository.save(oldUser);
        return mapToDto(updatedUser);
    }

    public UserDto mapToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    public User mapToEntity(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

}

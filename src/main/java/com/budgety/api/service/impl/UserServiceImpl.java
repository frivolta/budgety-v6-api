package com.budgety.api.service.impl;

import com.budgety.api.entity.Profile;
import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.user.UserDto;
import com.budgety.api.payload.user.UserUpdateRequest;
import com.budgety.api.repositories.UserRepository;
import com.budgety.api.service.CategoryService;
import com.budgety.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           CategoryService categoryService) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
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
    public User getUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found with sub "+ id.toString()));
    }

    @Override
    public boolean userExists(String sub) {
        return userRepository.existsBySub(sub);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Profile profile = new Profile();
        profile.setStartingAmount(new BigDecimal(0));
        profile.setCurrency("$");
        User user = mapToEntity(userDto);
        user.setProfile(profile);
        User newUser = userRepository.saveAndFlush(user);
        categoryService.generateDefaultForUser(newUser);
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

package com.budgety.api.service.impl;

import com.budgety.api.entity.Profile;
import com.budgety.api.entity.User;
import com.budgety.api.exceptions.ResourceNotFoundException;
import com.budgety.api.payload.profile.ProfileDto;
import com.budgety.api.repositories.ProfileRepository;
import com.budgety.api.service.ProfileService;
import com.budgety.api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private ProfileRepository profileRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, UserService userService, ModelMapper modelMapper) {
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfileDto createProfile(Long userId, ProfileDto profileDto) {
        if(profileRepository.existsByUserId(userId)){
            throw new IllegalArgumentException("User already has a profile");
        }
        User user = userService.getUserEntityById(userId);
        Profile profile = mapToEntity(profileDto);
        profile.setUser(user);
        Profile newProfile = profileRepository.save(profile);
        return mapToDto(newProfile);
    }

    @Override
    public ProfileDto getProfileById(Long userId) {
        System.out.println("Called with"+ userId);
        Profile profile =  profileRepository.findProfileByUserId(userId).orElseThrow(
                ()-> new ResourceNotFoundException("Profile", "with user id", userId.toString())
        );
        return mapToDto(profile);
    }

    @Override
    public ProfileDto updateProfileById(Long userId, ProfileDto profileDto) {
        Profile oldProfile =  profileRepository.findProfileByUserId(userId).orElseThrow(
                ()-> new ResourceNotFoundException("Profile", "with user id", userId.toString())
        );
        oldProfile.setStartingAmount(profileDto.getStartingAmount());
        oldProfile.setCurrency(profileDto.getCurrency());
        Profile updatedProfile = profileRepository.save(oldProfile);
        return mapToDto(updatedProfile);
    }

    @Override
    public boolean deleteProfileById(Long userId) {
        Profile oldProfile =  profileRepository.findProfileByUserId(userId).orElseThrow(
                ()-> new ResourceNotFoundException("Profile", "with user id", userId.toString())
        );
        profileRepository.delete(oldProfile);
        return true;
    }

    public Profile mapToEntity(ProfileDto profileDto) {
        return modelMapper.map(profileDto, Profile.class);
    }

    public ProfileDto mapToDto(Profile profile) {
        return modelMapper.map(profile, ProfileDto.class);
    }
}

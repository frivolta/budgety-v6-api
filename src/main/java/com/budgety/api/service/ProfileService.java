package com.budgety.api.service;

import com.budgety.api.payload.profile.ProfileDto;

public interface ProfileService {
    ProfileDto createProfile(Long userId, ProfileDto profileDto);
    ProfileDto getProfileById(Long userId);
    ProfileDto updateProfileById(Long userId, ProfileDto profileDto);
    boolean deleteProfileById(Long userId);
}

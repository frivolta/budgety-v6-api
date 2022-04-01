package com.budgety.api.controller;

import com.budgety.api.payload.profile.ProfileDeleteRequest;
import com.budgety.api.payload.profile.ProfileDto;
import com.budgety.api.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/{userId}/profile")
public class ProfileController {
    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping()
    public ResponseEntity<ProfileDto> createProfile(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileDto profileDto
    ){
        ProfileDto profile = profileService.createProfile(userId, profileDto);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ProfileDto> getProfile(
            @PathVariable Long userId
    ){
        ProfileDto profile = profileService.getProfileById(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ProfileDeleteRequest> deleteProfile(
            @PathVariable Long userId
    ){
        profileService.deleteProfileById(userId);
        ProfileDeleteRequest response = new ProfileDeleteRequest();
        response.setOk(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProfileDto> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileDto profileDto
    ){
        ProfileDto profile = profileService.updateProfileById(userId, profileDto);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}

package com.rev.service;

import com.rev.dao.CreatorProfileDAO;
import com.rev.model.AccountType;
import com.rev.model.CreatorProfile;
import com.rev.model.Profile;
import com.rev.model.User;
import com.rev.util.Session;

public class CreatorProfileService {

    private CreatorProfileDAO creatorProfileDAO = new CreatorProfileDAO();
    private ProfileService profileService = new ProfileService();

    public CreatorProfile viewMyCreatorProfile() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null ||
            currentUser.getAccountType() != AccountType.CREATOR) {
            return null;
        }

        CreatorProfile creatorProfile =
                creatorProfileDAO.getByUserId(currentUser.getUserId());

        if (creatorProfile == null) {
            return null;
        }

        Profile baseProfile = profileService.viewMyProfile();

        if (baseProfile != null) {
            creatorProfile.setName(baseProfile.getName());
            creatorProfile.setBio(baseProfile.getBio());
            creatorProfile.setLocation(baseProfile.getLocation());
            creatorProfile.setWebsite(baseProfile.getWebsite());
        }

        return creatorProfile;
    }

    public boolean updateMyCreatorProfile(CreatorProfile profile) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null ||
            currentUser.getAccountType() != AccountType.CREATOR) {
            return false;
        }

        profile.setUserId(currentUser.getUserId());

        Profile baseProfile = new Profile();
        baseProfile.setUserId(currentUser.getUserId());
        baseProfile.setName(profile.getName());
        baseProfile.setBio(profile.getBio());
        baseProfile.setLocation(profile.getLocation());
        baseProfile.setWebsite(profile.getWebsite());

        profileService.updateMyProfile(baseProfile);

        return creatorProfileDAO.update(profile);
    }
}

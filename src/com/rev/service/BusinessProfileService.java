package com.rev.service;

import com.rev.dao.BusinessProfileDAO;
import com.rev.model.AccountType;
import com.rev.model.BusinessProfile;
import com.rev.model.Profile;
import com.rev.model.User;
import com.rev.util.Session;

public class BusinessProfileService {

    private BusinessProfileDAO businessProfileDAO = new BusinessProfileDAO();
    private ProfileService profileService = new ProfileService();

    public BusinessProfile viewMyBusinessProfile() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null ||
            currentUser.getAccountType() != AccountType.BUSINESS) {
            return null;
        }

        BusinessProfile businessProfile =
                businessProfileDAO.getByUserId(currentUser.getUserId());

        if (businessProfile == null) {
            return null;
        }

        Profile baseProfile = profileService.viewMyProfile();

        if (baseProfile != null) {
            businessProfile.setName(baseProfile.getName());
            businessProfile.setBio(baseProfile.getBio());
            businessProfile.setLocation(baseProfile.getLocation());
            businessProfile.setWebsite(baseProfile.getWebsite());
        }

        return businessProfile;
    }

    public boolean updateMyBusinessProfile(BusinessProfile profile) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null ||
            currentUser.getAccountType() != AccountType.BUSINESS) {
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

        return businessProfileDAO.update(profile);
    }
}

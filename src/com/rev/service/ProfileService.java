package com.rev.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rev.dao.ProfileDAO;
import com.rev.model.Profile;
import com.rev.model.User;
import com.rev.util.Session;

public class ProfileService {

    private static final Logger logger =
            LogManager.getLogger(ProfileService.class);

    private ProfileDAO profileDAO = new ProfileDAO();

    public boolean createEmptyProfile(int userId) {
        return profileDAO.createEmptyProfile(userId);
    }

    public Profile viewMyProfile() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return null;
        }

        return profileDAO.getProfileByUserId(currentUser.getUserId());
    }

    public boolean updateMyProfile(Profile profile) {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        if (profile.getUserId() != currentUser.getUserId()) {
            logger.warn("Unauthorized profile update attempt");
            return false;
        }

        return profileDAO.updateProfile(profile);
    }

    public Profile viewProfileByUserId(int userId) {
        return profileDAO.getProfileByUserId(userId);
    }

	public Profile getProfileByUserId(int userId) {
		// TODO Auto-generated method stub
		return profileDAO.getProfileByUserId(userId);
	}
}

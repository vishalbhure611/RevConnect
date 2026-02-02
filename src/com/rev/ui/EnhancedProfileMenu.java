package com.rev.ui;

import com.rev.model.AccountType;
import com.rev.model.User;
import com.rev.util.Session;

public class EnhancedProfileMenu {

    public void show() {

        User currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            System.out.println("Please login first");
            return;
        }

        if (currentUser.getAccountType() == AccountType.CREATOR) {
            new CreatorProfileMenu().show();
        } 
        else if (currentUser.getAccountType() == AccountType.BUSINESS) {
            new BusinessProfileMenu().show();
        } 
        else {
            System.out.println("Enhanced profile is not available for personal accounts");
        }
    }
}

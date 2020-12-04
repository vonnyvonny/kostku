package com.ezraaudivano.kostku.UnitTest;

import com.ezraaudivano.kostku.model.User;

public interface LoginView {
    String getEmail();
    void showEmailError(String message);
    String getPassword();
    void showPasswordError(String message);
    void startMainActivity();
    void startUserProfileActivity();
    void showLoginError(String message);
    void showErrorResponse(String message);

}

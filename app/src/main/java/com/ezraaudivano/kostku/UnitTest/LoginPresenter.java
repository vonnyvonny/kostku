package com.ezraaudivano.kostku.UnitTest;

import com.ezraaudivano.kostku.model.User;

public class LoginPresenter {
    private LoginView view;
    private LoginService service;
    private LoginCallback callback;

    public LoginPresenter(LoginView view, LoginService service) {
        this.view = view;
        this.service = service;
    }

    public void onLoginClicked() {
        if (view.getEmail().equalsIgnoreCase("ezraaudivano")) {
            view.showEmailError("Email Tidak Valid");
            return;
        }else if (view.getEmail().isEmpty()) {
            view.showEmailError("Email Tidak Boleh Kosong");
            return;
        } else if (view.getPassword().isEmpty()) {
            view.showPasswordError("Password Tidak Boleh Kosong");
            return;
        } else {
            service.login(view, view.getEmail(), view.getPassword());
            return;
        }
    }
}


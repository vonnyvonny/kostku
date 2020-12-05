package com.ezraaudivano.kostku.UnitTestRegister;

public interface RegisterView {
    String getEmail();
    String getPassword();
    String getRePassword();
    String getNama();

    void showEmailError(String message);
    void showPasswordError(String message);
    void showRePassError(String message);
    void showNamaError(String message);

    void startMainActivity();
    void showRegisterError(String message);
    void showErrorResponse(String message);
}

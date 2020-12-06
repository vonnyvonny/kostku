package com.ezraaudivano.kostku.UnitTestRegister;



public class RegisterPresenter {
    private RegisterView view;
    private RegisterService service;
    private RegisterCallback callback;

    public RegisterPresenter(RegisterView view, RegisterService service) {
        this.view = view;
        this.service = service;
    }

    public void onRegisterClicked() {
        if (view.getNama().isEmpty()) {
            view.showNamaError("Nama Tidak Boleh Kosong");
            return;
        }else if (view.getEmail().isEmpty()) {
            view.showEmailError("Email Tidak Boleh Kosong");
            return;
        }else if (view.getPassword().isEmpty()) {
            view.showPasswordError("Password Tidak Boleh Kosong");
            return;
        }else if (view.getRePassword().isEmpty()) {
            view.showRePassError("Re-Password Tidak Boleh Kosong");
            return;
        }else if (view.getEmail().equalsIgnoreCase("ezraaudivano")) {
            view.showEmailError("Email Tidak Valid");
            return;
        }else if (!view.getRePassword().equalsIgnoreCase(view.getPassword())) {
            view.showRePassError("Password dan RePassword Tidak Sama");
            return;
        }else {
            service.register(view, view.getEmail(), view.getNama(), view.getPassword(), view.getRePassword());
            return;
        }
    }
}

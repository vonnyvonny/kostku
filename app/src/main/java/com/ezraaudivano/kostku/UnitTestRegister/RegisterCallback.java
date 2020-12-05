package com.ezraaudivano.kostku.UnitTestRegister;

import com.ezraaudivano.kostku.model.User;

public interface RegisterCallback {
    void onSuccess(boolean value, User user);
    void onError();
}

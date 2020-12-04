package com.ezraaudivano.kostku.UnitTest;

import com.ezraaudivano.kostku.model.User;

public interface LoginCallback {
    void onSuccess(boolean value, User user);
    void onError();

}

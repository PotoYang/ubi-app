package com.chh.yinbao.view;

import com.chh.yinbao.User;

/**
 * Created by potoyang on 2017/8/7.
 */

public interface LoginView extends IView {

    void loginSuccess(User data);

    void loginError(String msg);
}

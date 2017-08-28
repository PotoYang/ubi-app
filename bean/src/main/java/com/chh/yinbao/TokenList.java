package com.chh.yinbao;

import java.io.Serializable;

/**
 * Created by potoyang on 2017/8/7.
 */

public class TokenList implements Serializable {

    private Token account;

    public Token getAccount() {
        return account;
    }

    public void setAccount(Token account) {
        this.account = account;
    }
}

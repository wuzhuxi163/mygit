package com.qf.wzx.service;

import com.qf.wzx.entity.Account;

import java.sql.SQLException;

public interface AccountService {
    Account doLogin(Account account);

    String getBalanceByCardNo(String cardno);

    String transfer(String fromAccount, String toAccount, double money);

    void changePassword(String cardno, String password) throws SQLException;
}

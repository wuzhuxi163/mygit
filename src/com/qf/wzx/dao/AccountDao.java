package com.qf.wzx.dao;

import com.qf.wzx.entity.Account;

import java.sql.SQLException;

public interface AccountDao {
    Account getAccountByCardno(String cardNo);

    void transfer(String fromAccount, String toAccount, double money) throws Exception;

    void changePassword(String cardno, String password) throws SQLException;
}

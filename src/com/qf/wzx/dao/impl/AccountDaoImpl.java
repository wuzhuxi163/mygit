package com.qf.wzx.dao.impl;

import com.qf.wzx.dao.AccountDao;
import com.qf.wzx.entity.Account;
import com.qf.wzx.util.DbUtils;

import java.sql.*;
import java.text.SimpleDateFormat;

public class AccountDaoImpl implements AccountDao {
    @Override
    public Account getAccountByCardno(String cardNo) {
        Connection connection = DbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT\n" +
                    "\tcardno AS cardno,\n" +
                    "\tPASSWORD AS PASSWORD,\n" +
                    "\taccount_balance AS accountBalance,\n" +
                    "STATUS AS STATUS \n" +
                    "FROM\n" +
                    "\taccount \n" +
                    "WHERE\n" +
                    "\tcardno = ?");
            preparedStatement.setObject(1, cardNo);
            ResultSet rs = preparedStatement.executeQuery();
            Account account = new Account();
            while (rs.next()) {
                account.setCardno(rs.getString("cardno"));
                account.setPassword(rs.getString("password"));
                account.setAccountBalance(rs.getDouble("accountBalance"));
            }
            return account;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void transfer(String fromAccount, String toAccount, double money) throws Exception {
        Connection connection = DbUtils.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("update account set account_balance = account_balance-" + money + " where cardno =" + fromAccount);
        statement.executeUpdate("update account set account_balance = account_balance+" + money + " where cardno =" + toAccount);
        transferLog(fromAccount, toAccount, money, connection);
        connection.commit();
    }

    @Override
    public void changePassword(String cardno, String password) throws SQLException {
        Connection connection = DbUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(" update account set password = ? where cardno =?");
        preparedStatement.setObject(1,password);
        preparedStatement.setObject(2,cardno);
        preparedStatement.executeUpdate();
    }


    private void transferLog(String fromAccount, String toAccount, double money, Connection connection) throws Exception {

        //支出用户日志
        PreparedStatement prepPost = connection.prepareStatement("insert into TRANSACTION_record(Cardno,expense,Transaction_date) values(?,?,curdate())\n");
        prepPost.setObject(1, fromAccount);
        prepPost.setObject(2, money);
        prepPost.executeUpdate();
        //收入用户日志
        PreparedStatement prepReceive = connection.prepareStatement("insert into TRANSACTION_record(Cardno,income,Transaction_date) values(?,?,curdate())\n");
        prepReceive.setObject(1, toAccount);
        prepReceive.setObject(2, money);
        prepReceive.executeUpdate();
    }



}

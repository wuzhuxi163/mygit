package com.qf.wzx.dao.impl;

import com.qf.wzx.dao.TransactionDao;
import com.qf.wzx.entity.Account;
import com.qf.wzx.entity.Transaction;
import com.qf.wzx.util.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl  implements TransactionDao {

    @Override
    public List<Transaction> getLog(String startDate, String endDate) {
        Connection connection = DbUtils.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT\n" +
                    "\tcardno AS cardno,\n" +
                    "\tTRANSACTION_date AS transactionDate,\n" +
                    "\texpense AS expense,\n" +
                    "\tincome AS income \n" +
                    "FROM\n" +
                    "\tTRANSACTION_record \n" +
                    "WHERE\n" +
                    "\tTRANSACTION_date BETWEEN ? \n" +
                    "\tAND ?\n" +
                    " ");
            preparedStatement.setObject(1,startDate);
            preparedStatement.setObject(2, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            List<Transaction> transactions = new ArrayList<>();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setCardno(rs.getString("cardno"));
                transaction.setExpense(rs.getString("expense"));
                transaction.setIncome(rs.getString("income"));
                transaction.setTransactionDate(rs.getString("transactionDate"));
                transactions.add(transaction);

            }
            return transactions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

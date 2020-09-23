package com.qf.wzx.dao;

import com.qf.wzx.entity.Transaction;

import java.util.List;

public interface TransactionDao {

    List<Transaction> getLog(String startDate, String endDate);
}

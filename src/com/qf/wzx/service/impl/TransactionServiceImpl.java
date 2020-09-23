package com.qf.wzx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qf.wzx.dao.TransactionDao;
import com.qf.wzx.dao.impl.TransactionDaoImpl;
import com.qf.wzx.entity.Transaction;
import com.qf.wzx.service.TransactionService;

import java.util.List;

public class TransactionServiceImpl  implements TransactionService {
    TransactionDao dao = new TransactionDaoImpl();
    @Override
    public String getLog(String startDate, String endDate) {
        List<Transaction> logList = dao.getLog(startDate,endDate);
        return JSONObject.toJSONString(logList);
    }
}

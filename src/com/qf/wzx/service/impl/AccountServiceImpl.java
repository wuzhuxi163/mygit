package com.qf.wzx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qf.wzx.dao.AccountDao;
import com.qf.wzx.dao.impl.AccountDaoImpl;
import com.qf.wzx.entity.Account;
import com.qf.wzx.entity.Result;
import com.qf.wzx.service.AccountService;

import java.sql.SQLException;

public class AccountServiceImpl implements AccountService {
    AccountDao dao = new AccountDaoImpl();

    @Override
    public Account doLogin(Account account) {
        Account localAccount= dao.getAccountByCardno(account.getCardno());
        if(null!=localAccount){
            if (localAccount.getPassword().equals(account.getPassword())){
                return localAccount;
            }
        }
        return null;
    }

    @Override
    public String getBalanceByCardNo(String cardno) {
        Account localAccount= dao.getAccountByCardno(cardno);
        String balance = localAccount.getAccountBalance().toString();
        return balance;

    }

    @Override
    public String transfer(String fromAccount, String toAccount, double money) {
        //创建数据返回对象
        Result result = new Result();
        //检查收钱用户是否存在 如果不存在返回 响应类型json串
        Account receive  =dao.getAccountByCardno(toAccount);
        if (receive==null){
            result.setCode("404");
            result.setMessage("收款用户不存在");
            return JSONObject.toJSONString(result);
        }

        //检查金额是否足够转账
        Account post = dao.getAccountByCardno(fromAccount);
        if(money>post.getAccountBalance()){
            result.setCode("500");
            result.setMessage("金额不足");
            return JSONObject.toJSONString(result);
        }
        try{
            dao.transfer(fromAccount,  toAccount, money);
            result.setCode("200");
            result.setMessage("成功转账"+money+"元");
            return JSONObject.toJSONString(result);
        }catch (Exception e){
            result.setCode("505");
            result.setMessage("转账异常");
            return JSONObject.toJSONString(result);
        }

    }


    @Override
    public void changePassword(String cardno, String password) throws SQLException {
        dao.changePassword(cardno,password);
    }
}

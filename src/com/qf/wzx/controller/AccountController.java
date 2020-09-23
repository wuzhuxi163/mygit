package com.qf.wzx.controller;

import com.qf.wzx.entity.Account;
import com.qf.wzx.service.AccountService;
import com.qf.wzx.service.impl.AccountServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/account", loadOnStartup = 1)
public class AccountController extends BaseController {
    AccountService service = new AccountServiceImpl();

    //登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Account account = new Account();
        account.setCardno(request.getParameter("cardno"));
        account.setPassword(request.getParameter("password"));
        account = service.doLogin(account);
        if (null != account) {
            request.getSession().setAttribute("account", account);
            request.getRequestDispatcher("/main.html").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath()+"/login.html");
        }
    }

    //查询余额
    public void balance(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String cardno = account.getCardno();
        String balance = service.getBalanceByCardNo(cardno);
        response.getOutputStream().write(balance.getBytes());
    }

    //转账功能
    public void transfer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String toAccount = request.getParameter("toAccount");
        double money = Double.parseDouble(request.getParameter("money"));
        Account account = (Account) request.getSession().getAttribute("account");
        //TODO 1。验证收钱用户是否存在 2.检查金额是否足够转账
        String fromAccount = account.getCardno();
        String json = service.transfer(fromAccount, toAccount, money);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
    }

    //检查旧密码是否正确
    public void checkOldPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String password = account.getPassword();
        String oldPassword = request.getParameter("password");
        String status ="0";
        if(password.equals(oldPassword)){
            status="1";
        }
        response.getWriter().write(status);
    }

    public void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String password = request.getParameter("password");
        try{
            service.changePassword(account.getCardno(),password);
            request.getSession().removeAttribute("account");
            response.getWriter().write("1");
        }catch (Exception e){
            e.printStackTrace();
            response.getWriter().write("0");
        }

    }

}

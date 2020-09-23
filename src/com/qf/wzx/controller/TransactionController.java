package com.qf.wzx.controller;

import com.qf.wzx.entity.Transaction;
import com.qf.wzx.service.TransactionService;
import com.qf.wzx.service.impl.TransactionServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/transaction")
public class TransactionController extends BaseController {
    TransactionService service = new TransactionServiceImpl();
    //查询日志
    public void log(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String startDate = request.getParameter("startDate");
        String endDate =request.getParameter("endDate");
        String json =service.getLog(startDate,endDate);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
    }
}

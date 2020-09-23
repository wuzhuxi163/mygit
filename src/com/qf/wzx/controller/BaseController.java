package com.qf.wzx.controller;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseController extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            Class clazz = this.getClass();
            Object object = clazz.newInstance();
            String  methodName = req.getParameter("method");
            Method method =clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(object, (HttpServletRequest) req,(HttpServletResponse) res);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}

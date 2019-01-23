package com.test.bll;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HttpHandlerContext {
		HttpServletRequest _request;
		HttpServletResponse _response;
	public  HttpHandlerContext(HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
			this._request=request;
			this._response=response;
			System.out.println("调用超类HttpHandlerContext");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	protected void output(Object result){
		PrintWriter out;
		try {
			out = this._response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



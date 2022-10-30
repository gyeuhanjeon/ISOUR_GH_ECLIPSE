package com.ISOUR.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.ISOUR.Common.*;
import com.ISOUR.DAO.*;


@WebServlet("/MemberOut")
public class MemberOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Common.corsResSet(response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Common.corsResSet(response);
		StringBuffer sb = Common.reqStringBuff(request);
		JSONObject jsonObj = Common.getJsonObj(sb);
		String getId = (String)jsonObj.get("id");
		String getPwd = (String)jsonObj.get("pwd");
		MemberDAO dao = new MemberDAO();
		boolean isRegister = dao.logingCheck(getId, getPwd);
		if(isRegister) {
		dao.regidOut(getId);//isNotReg = TRUE 가입안된 경우e
		}
		PrintWriter out = response.getWriter();
		JSONObject resJson = new JSONObject();
		
		if(isRegister) resJson.put("result", "OK");
		else resJson.put("result", "NOK");
		out.print(resJson);
	}

}
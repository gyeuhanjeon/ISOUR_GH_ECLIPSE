package com.ISOUR.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ISOUR.Common.Common;
import com.ISOUR.DAO.MemberDAO;


@SuppressWarnings("unused")
@WebServlet("/MemberOut")
public class MemberOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
       
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Common.corsResSet(response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Common.corsResSet(response);
		StringBuffer sb = Common.reqStringBuff(request);
		JSONObject jsonObj = Common.getJsonObj(sb);
		
		String getId = (String)jsonObj.get("id");
		
		System.out.println("ID뭐냐" + getId);
		
		MemberDAO dao = new MemberDAO();
		boolean isRegister = dao.MemOutCheck(getId);
		
		PrintWriter out = response.getWriter();
		JSONObject resJson = new JSONObject();
		System.out.println("여기까지 와라....Out : " + isRegister);
		if(isRegister) resJson.put("result", "OK");  // result = Key / OK = value
		else resJson.put("result", "NOK");
		out.print(resJson);

	}
}

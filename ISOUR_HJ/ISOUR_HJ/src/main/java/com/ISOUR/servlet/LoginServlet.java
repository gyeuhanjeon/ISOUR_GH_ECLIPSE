package com.ISOUR.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import com.ISOUR.Common.Common;
import com.ISOUR.DAO.MemberDAO;


@SuppressWarnings("serial")
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Common.corsResSet(response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 깨짐 방지를 위해서 설정
		request.setCharacterEncoding("utf-8");
		// CORS 접근 허용
		Common.corsResSet(response);

		// 요청 메시지 받기
		StringBuffer sb = Common.reqStringBuff(request);
		
		// 요청 받은 메시지 JSON 파싱
		JSONObject jsonObj = Common.getJsonObj(sb);
		
		String getId = (String)jsonObj.get("id");
		String getPwd = (String)jsonObj.get("pwd");
		
		System.out.println("log-아이디 받았다 : " + getId);
		
		
		MemberDAO dao = new MemberDAO();
		boolean isRegister = dao.logingCheck(getId, getPwd);
		
		System.out.println("여기여기여기여기 : " + isRegister);
		
		PrintWriter out = response.getWriter();
		JSONObject resJson = new JSONObject();
		if(isRegister) resJson.put("result", "OK");  // result = Key / OK = value
		else resJson.put("result", "NOK");
		out.print(resJson);
		
	}
 
}

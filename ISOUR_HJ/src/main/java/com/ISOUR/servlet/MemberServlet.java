package com.ISOUR.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.ISOUR.VO.MemberVO;


@SuppressWarnings("serial")
@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {


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
		String reqCmd = (String)jsonObj.get("cmd");
		PrintWriter out = response.getWriter();
		
		
		if(!reqCmd.equals("MemberInfo")) {
			JSONObject resJson = new JSONObject();
			resJson.put("result", "멤버서블렛 NOT OK");
			out.print(resJson);
			return;
		}
		
		MemberDAO dao = new MemberDAO();
		List<MemberVO> list = dao.memberSelect();
		
		JSONArray memberArray = new JSONArray();
		
		for(MemberVO e : list) {
			JSONObject memberInfo = new JSONObject();
			memberInfo.put("id", e.getId());
			memberInfo.put("name", e.getName());
			memberInfo.put("gender", e.getGender());
//			DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd");
//			String dateToStr = dateFormat.format(e.getBirth());   // 시간을 문자열로 형변환
			memberInfo.put("birth",  e.getBirth());
			memberInfo.put("region1",  e.getRegion1());
			memberInfo.put("region2",  e.getRegion2());
			memberArray.add(memberInfo);
		}
		System.out.println(memberArray);
		out.print(memberArray);
	}
}



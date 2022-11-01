package com.user.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coders.controller.Action;
import com.coders.controller.ActionForward;
import com.coders.model.UserDAO;

public class CodersProfileModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		String id = (String)session.getAttribute("userId");
		String profile = request.getParameter("profile").trim();
		
		UserDAO dao = UserDAO.getInstance();
		
		int check = dao.userProfileModify(id, profile);
		
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			
			out.println("<script>");
			out.println("location.href='profile.do'");
			out.println("</script>");

		}else {
			out.println("<script>");
			out.println("alert('자기소개 수정에 실패하였습니다.')");
			out.println("history.back()");
			out.println("</script>");
			
		}
		
		return null;
	}

}

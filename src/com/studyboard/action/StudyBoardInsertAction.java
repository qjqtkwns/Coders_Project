package com.studyboard.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coders.controller.Action;
import com.coders.controller.ActionForward;
import com.coders.model.StudyBoardDAO;
import com.coders.model.StudyBoardDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class StudyBoardInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		StudyBoardDTO dto = new StudyBoardDTO();

		String saveFolder = "D:\\git\\Coders_Project\\WebContent\\study_upload";

		int fileSize = 20 * 1024 * 1024; // 20MB

		MultipartRequest multi = new MultipartRequest(request, saveFolder, fileSize, "UTF-8"); 
		
		String study_writer = multi.getParameter("study_writer").trim();
		String study_title = multi.getParameter("study_title").trim();
		String study_cont = multi.getParameter("study_cont").trim();
		int study_people = Integer.parseInt(multi.getParameter("study_people").trim());
		String study_start = multi.getParameter("study_start").trim();
		String study_end = multi.getParameter("study_end").trim();
		
		String study_file = multi.getFilesystemName("study_file");
		
		
		dto.setStudy_writer(study_writer);
		dto.setStudy_title(study_title);
		dto.setStudy_cont(study_cont);
		dto.setStudy_people(study_people);
		dto.setStudy_start(study_start);
		dto.setStudy_end(study_end);
		
		dto.setStudy_file(study_file);
		
		StudyBoardDAO dao = StudyBoardDAO.getInstance();
		int res = dao.insertStudy(dto);
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		
		if(res>0) {
			forward.setRedirect(true);
			forward.setPath("studyBoard_list.do");
		}else {
			out.println("<script> alert('게시물 등록에 실패하였습니다.'); history.back(); </script>");
		}
		
		
		return forward;
	}

}

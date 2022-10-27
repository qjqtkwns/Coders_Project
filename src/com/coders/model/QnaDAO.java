package com.coders.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class QnaDAO {
	
	Connection con = null;  // DB와 연동하는 객체
	PreparedStatement pstmt = null;  // DB에 SQL문을 전송하는 객체 
	ResultSet rs = null;  // SQL문을 실행한 후에 결과 값을 가지고 있는 객체
	String sql = null;  // Query문을 저장할 변수
	
	private static QnaDAO instance;
	
	private QnaDAO() {} // 기본 생성자
	
	// 3단계 : 기본 생성자 대신 싱글턴 객체를 return 해주는 getInstance()라는 메서드를 생성해
	//		  해당 getInstance()라는 메서드를 외부에서 접근할 수 있도록 만든다.
	
	public static QnaDAO getInstance() {
		if(instance == null) {
			instance = new QnaDAO();
		}
		
		return instance; // 주소값을 반환
	}
	
	public void openConn() {
	
		try {
			
			// 1단계 : JNDI 서버 객체 생성
			Context ctx = new InitialContext(); //java.naming의 context
			
			// 2단계 : lookup() 메서드를 이용하여 매칭되는 커넥션 찾기 
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/myoracle"); 
												// "java:comp.env/context.xml에서 선언한 name"
			// 3단계 : DataSource 객체를 이용하여 커넥션을 하나 가져온다.
			con = ds.getConnection();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
	}// openConn()메서드의 end
	
	// DB에 연결된 자원을 종료하는 메서드
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} // closeConn()메서드의 end
	
	
	
	// 페이징 처리
		public int getQnaCount() {
		
			int count = 0;
			
			try {
				openConn();
				sql = "select count(*) from qna";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					count = rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeConn(rs, pstmt, con);
			}
			return count;
		} // getBoardCount()메서드의 end

		
	
	// QnA 메인페이지 출력
	public List<QnaDTO> getQnaList(int page, int rowsize) {
		
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		
		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize -1);
				
		// 해당 페이지에서 마지막 번호
		int endNo = (page * rowsize);

		try {
			openConn();
			
			sql = "select * from (select row_number() over(order by qna_date desc) qnum, q.* from qna q) where qnum >=? and qnum <= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setQna_num(rs.getInt("qna_num"));
				dto.setQna_writer(rs.getString("qna_writer"));
				dto.setQna_title(rs.getString("qna_title"));
				dto.setQna_cont(rs.getString("qna_cont"));
				dto.setQna_date(rs.getString("qna_date"));
				dto.setQna_update(rs.getString("qna_update"));
				dto.setQna_hit(rs.getInt("qna_hit"));
				dto.setQna_tag(rs.getString("qna_tag"));
				dto.setQna_code(rs.getString("qna_code"));
				dto.setQna_reply(rs.getInt("qna_reply"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	
	
	// 조회수순으로 정렬하는 메서드
	public List<QnaDTO> getQnaViewList(int page, int rowsize) {
		
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		
		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize -1);
				
		// 해당 페이지에서 마지막 번호
		int endNo = (page * rowsize);

		try {
			openConn();
			
			sql = "select * from (select row_number() over(order by qna_hit desc) qnum, q.* from qna q) where qnum >=? and qnum <= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setQna_num(rs.getInt("qna_num"));
				dto.setQna_writer(rs.getString("qna_writer"));
				dto.setQna_title(rs.getString("qna_title"));
				dto.setQna_cont(rs.getString("qna_cont"));
				dto.setQna_date(rs.getString("qna_date"));
				dto.setQna_update(rs.getString("qna_update"));
				dto.setQna_hit(rs.getInt("qna_hit"));
				dto.setQna_tag(rs.getString("qna_tag"));
				dto.setQna_code(rs.getString("qna_code"));
				dto.setQna_reply(rs.getInt("qna_reply"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	
	// qna게시판을 답변수대로 정렬하는 메서드
	public List<QnaDTO> qnaCList(int page, int rowsize){
		
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		
		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize -1);
				
		// 해당 페이지에서 마지막 번호
		int endNo = (page * rowsize);

		try {
			openConn();
			
			sql = "select * from (select row_number() over(order by qna_reply desc) qnum, q.* from qna q) where qnum >=? and qnum <= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setQna_num(rs.getInt("qna_num"));
				dto.setQna_writer(rs.getString("qna_writer"));
				dto.setQna_title(rs.getString("qna_title"));
				dto.setQna_cont(rs.getString("qna_cont"));
				dto.setQna_date(rs.getString("qna_date"));
				dto.setQna_update(rs.getString("qna_update"));
				dto.setQna_hit(rs.getInt("qna_hit"));
				dto.setQna_tag(rs.getString("qna_tag"));
				dto.setQna_code(rs.getString("qna_code"));
				dto.setQna_reply(rs.getInt("qna_reply"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	
	// qna게시판에서 선택한 코드 언어가 들어가는 태그만 출력하는 메서드
	public List<QnaDTO> codeSortList(int page, int rowsize, String codeName) {
		
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		
		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize -1);
				
		// 해당 페이지에서 마지막 번호
		int endNo = (page * rowsize);
		
		try {
			openConn();
			sql = "select * from (select row_number() over(order by qna_date desc) qnum, "
					+ "q.* from qna q) where qnum >=? and qnum <= ? and qna_tag = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			pstmt.setString(3, codeName);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setQna_num(rs.getInt("qna_num"));
				dto.setQna_writer(rs.getString("qna_writer"));
				dto.setQna_title(rs.getString("qna_title"));
				dto.setQna_cont(rs.getString("qna_cont"));
				dto.setQna_date(rs.getString("qna_date"));
				dto.setQna_update(rs.getString("qna_update"));
				dto.setQna_hit(rs.getInt("qna_hit"));
				dto.setQna_tag(rs.getString("qna_tag"));
				dto.setQna_code(rs.getString("qna_code"));
				dto.setQna_reply(rs.getInt("qna_reply"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	
	// qna 게시판에 글 쓰기 하는 메서드
	public int insertQna(QnaDTO dto) {
		
		int result = 0, count = 0;
		
		try {
			openConn();
			
			sql = "select max(qna_num) from qna";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1) + 1;
			}
			
			sql = "insert into qna values(?,?,?,?,sysdate,'',?,0,?,?,default)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setString(2, dto.getQna_writer());
			pstmt.setString(3, dto.getQna_title());
			pstmt.setString(4, dto.getQna_cont());
			pstmt.setString(5, dto.getQna_file());
			pstmt.setString(6, dto.getQna_tag());
			pstmt.setString(7, dto.getQna_code());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
	
	
	
	
	// qna게시판에서 검색된 게시물의 수
	public int searchListCount(String keyword) {
		
		int count = 0;
		
		try {
			openConn();
			
			sql = "select count(*) from qna where qna_title like ? or qna_cont like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setString(2, "%"+keyword+"%");
			rs=pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return count;
	}
	
	
	// qna 게시판에서 검색된 게시글을 출력하는 메서드
	public List<QnaDTO> searchQnaList(String keyword, int page, int rowsize){
		
			List<QnaDTO> list = new ArrayList<QnaDTO>();
			
			// 해당 페이지에서 시작 번호
			int startNo = (page * rowsize) - (rowsize -1);
			
			// 해당 페이지에서 마지막 번호
			int endNo = (page * rowsize);
			
			openConn();
			
				try {
					sql = "select * from (select row_number() over(order by qna_date desc) qnum, q.* from qna q) where qnum >= ? and qnum <= ? and qna_title like ? or qna_cont like ?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+keyword+"%");
					pstmt.setString(2, "%"+keyword+"%");
					pstmt.setInt(3, startNo);
					pstmt.setInt(4, endNo);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						QnaDTO dto = new QnaDTO();
						dto.setQna_num(rs.getInt("qna_num"));
						dto.setQna_writer(rs.getString("qna_writer"));
						dto.setQna_title(rs.getString("qna_title"));
						dto.setQna_cont(rs.getString("qna_cont"));
						dto.setQna_date(rs.getString("qna_date"));
						dto.setQna_update(rs.getString("qna_update"));
						dto.setQna_hit(rs.getInt("qna_hit"));
						dto.setQna_tag(rs.getString("qna_tag"));
						dto.setQna_code(rs.getString("qna_code"));
						dto.setQna_reply(rs.getInt("qna_reply"));
						
						list.add(dto);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					closeConn(rs, pstmt, con);
				}
				return list;
	}
	
	// 조회수 증가 메서드
	public void updateHit(int no) {
	
		try {
			openConn();
			
			sql = "update qna set qna_hit = qna_hit + 1 where qna_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
	}
		
	
	// qna 세부정보 조회 메소드
		 public QnaDTO getQnaContent(int no) {
			 
			 QnaDTO dto = null;
			
			 try {
				openConn();
				 
				sql = "select * from qna where qna_num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, no);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					
					dto = new QnaDTO();
					
					dto.setQna_num(rs.getInt("qna_num"));
					dto.setQna_writer(rs.getString("qna_writer"));
					dto.setQna_title(rs.getString("qna_title"));
					dto.setQna_cont(rs.getString("qna_cont"));
					dto.setQna_date(rs.getString("qna_date"));
					dto.setQna_update(rs.getString("qna_update"));
					dto.setQna_hit(rs.getInt("qna_hit"));
					dto.setQna_tag(rs.getString("qna_tag"));
					dto.setQna_code(rs.getString("qna_code"));
					dto.setQna_code(rs.getString("qna_code"));
					dto.setQna_reply(rs.getInt("qna_reply"));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeConn(rs, pstmt, con);
			}
			  return dto;
		 } //getqnacontent end

		
		 
		 //qna 게시글 업데이트 메소드
		 public int updateQna(QnaDTO dto) {
			 
			 int result = 0;
			 
			 try {
				 
				openConn();
				
				sql = "select * from qna where qna_num = ? ";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, dto.getQna_num());
				rs = pstmt.executeQuery();
				 
				if(rs.next()) {
					
					sql = "update qna set qna_writer = ?, qna_title = ?, qna_cont = ?, qna_update = sysdate, qna_file = ?, qna_tag = ? where qna_num = ?";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, dto.getQna_writer());
					pstmt.setString(2, dto.getQna_title());
					pstmt.setString(3, dto.getQna_cont());
					pstmt.setString(4, dto.getQna_file());
					pstmt.setString(5, dto.getQna_tag());
					pstmt.setInt(6, dto.getQna_num());
					
					result = pstmt.executeUpdate();
					
				} else {
					
					result = -1;
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
			 	return result;
		 } //updateqna end
		 
		 
		//qna게시글 삭제 메소드
		 public int deleteQna(int no){
			 
			 int result = 0;
				
				try {
					openConn();
					
					sql = "delete from qna where qna_num = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, no);
					
					result = pstmt.executeUpdate();
					
					sql = "update qna set qna_num = qna_num-1 where qna_num > ?";
					
					pstmt.setInt(1, no);
					
					pstmt.executeUpdate();
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					closeConn(rs, pstmt, con);
				}
					return result;
			 
		 }//delete end
		 

		//게시글 삭제시 번호 재작업
			
			 public void updateQnaNum(int no){
				
				 try {
					 
						openConn();
						 
						sql = "update qna set qna_num = qna_num - 1 where qna_num > ?";
						pstmt = con.prepareStatement(sql);
						
						pstmt.setInt(1, no);
						pstmt.executeUpdate();
							
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						closeConn(rs, pstmt, con);
					}
			 } 
		 
		 
		 
	//-----------------------------------------------------------------------------------------------------	 
		 
	
		 //페이지에 해당하는 댓글 리스트를 조회하는 메소드
		 public String getQnacommentList (int no){
			 
			 String result = "";
			 
			 try {
				 
				openConn();
				 
				sql = "select * from qna_comment where qna_num = ? order by qcomment_date desc";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, no);
				rs = pstmt.executeQuery();
				
				result += "<comments>";
				
				while (rs.next()) {
					result += "<comment>";
					result += "<qcomment_num>" + rs.getInt("qcomment_num") + "</qcomment_num>";
					result += "<qna_num>" + rs.getInt("qna_num") + "</qna_num>";
					result += "<qcomment_writer>" + rs.getString("qcomment_writer") + "</qcomment_writer>";
					result += "<qcomment_cont>" + rs.getString("qcomment_cont") + "</qcomment_cont>";
					result += "<qcomment_date>" + rs.getString("qcomment_date") + "</qcomment_date>";
					result += "<qcomment_update>" + rs.getString("qcomment_update") + "</qcomment_update>";
					result += "<qcomment_good>" + rs.getInt("qcomment_good") + "</qcomment_good>";
					result += "<qcomment_file>" + rs.getString("qcomment_file") + "</qcomment_file>";
					result += "</comment>";
				}
				
				result += "</comments>";
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
			 	return result;
		 } //getqnacomment end
		 
		 

		 //댓글 등록 메소드
		 public int commentInsert(QnaCommentDTO dto) {
			 
			 int result = 0, count = 0; 
			 
				try {
					
					openConn();
				
					sql = "select max(qcomment_num) from qna_comment";
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						count = rs.getInt(1) + 1;
					}
					
					sql = "insert into qna_comment values(?, ?, ?, ?, sysdate, '', 0, ?)";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setInt(1, count);
					pstmt.setInt(2, dto.getQna_num());
					pstmt.setString(3, dto.getQcomment_writer());
					pstmt.setString(4, dto.getQcomment_cont());
					pstmt.setString(5, dto.getQcommnet_file());

					result = pstmt.executeUpdate();
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					closeConn(rs, pstmt, con);
				}
				return result;
		 } //commentinsert end
		 
		 
		 public int deleteQnaComment(int no) {
			 
			 
			 int result = 0;
				
				try {
					openConn();
					
					sql = "delete from qna_comment where qcomment_num = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, no);
					
					result = pstmt.executeUpdate();
					
					sql = "update qna_comment set qcomment_num = qcomment_num-1 where qcomment_num > ?";
					
					pstmt.setInt(1, no);
					
					pstmt.executeUpdate();
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					closeConn(rs, pstmt, con);
				}
					return result;
			 
		 }
	
}

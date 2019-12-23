package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.UserVO;

public class UserDAO {
	// 신규회원
		public static int getUserRegister(UserVO avo) throws Exception {

			String dml = "insert into userTbl "
					+ "(userId, userName, userGender, userBirth, userAdult, userPhone, userHomePhone, userEmail, userSignDay, userImage, userStatus, userPw)"
					+ " values " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // 12개, DB순서대로

			Connection con = null;
			PreparedStatement pstmt = null; // 문장을 준비한다 = 보안을 위한것.
			int count = 0;

			try {
				// DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
				con = DBUtil.getConnection();

				// 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, avo.getUserId()); // 1번 물음표에다가 값 넣는것이다. 스트링으로
				pstmt.setString(2, avo.getUserName());
				pstmt.setString(3, avo.getUserGender());
				pstmt.setString(4, avo.getUserBirth());
				pstmt.setString(5, avo.getUserAdult());
				pstmt.setString(6, avo.getUserPhone());
				pstmt.setString(7, avo.getUserHomePhone());
				pstmt.setString(8, avo.getUserEmail());
				pstmt.setString(9, avo.getUserSignday());
				pstmt.setString(10, avo.getUserImage());// 이미지
				pstmt.setString(11, avo.getUserStatus());
				pstmt.setString(12, avo.getUserPw());

				count = pstmt.executeUpdate(); // executeUpdate: 번개를 누르는 것과 같은 기능 (mysql워크벤치에서) . 몇문장 실행했는지 리턴

			} catch (SQLException e) {
				System.out.println("e=[" + e + "]");
			} catch (Exception e) {
				System.out.println("e=[" + e + "]");
			} finally {
				try {
					// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제 = 자원반납
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
				}
			}
			return count;
		}
	   

		// 검색기능(select * from table명 where name like '%홍길%')
		// ID 찾기 검색 기능
		public static String getUserId(String userName, String userEmail) {
			String dml = "select userId from userTbl where userName=? and userEmail=?";
			String resultString = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, userName);
				pstmt.setString(2, userEmail);
				// 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
				// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
				// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
				rs = pstmt.executeQuery();

				while (rs.next()) {
					resultString = rs.getString(1);
				}
				if (resultString == null) {
					return resultString;
				}
			} catch (SQLException | ClassNotFoundException e) {
				CommonFunc.alertDisplay(1, "검색 오류 발생", "ID를 찾지 못하였습니다.", "다시 시도해주세요");
				System.out.println("getUserId 부분 오류 발생");
				e.printStackTrace();
			} finally {
				try {
					// CLOSE DataBase psmt object
					// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
					// 반드시 있으면 닫자
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, " ㅇ", " ", " ");
				}
			}
			return resultString;
		}
		
		// PW 검색 기능
		public static String getUserPW(String userName, String userEmail, String userId) {
			String dml = "select userPw from userTbl where userName=? and userEmail=? and userId=?";
			String resultString = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				// 첫번째 물음표 자리 치 시켜주는 작업
				pstmt.setString(1, userName);
				pstmt.setString(2, userEmail);
				pstmt.setString(3, userId);
				// 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
				// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
				// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
				rs = pstmt.executeQuery();

				while (rs.next()) {
					resultString = rs.getString(1);
				}
				if (resultString == null) {
					return resultString;
				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, " ", " ", " ");
				}
			}
			return resultString;
		}
	   
		// 중복확인 아이디 찾기
		public static String getUserIdDuplicateCheck(String userId) {
			String dml = "select userId from userTbl where userId=?";
			String resultString = null;
			Connection con = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				psmt = con.prepareStatement(dml);
				// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
				// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
				psmt.setString(1, userId);
				rs = psmt.executeQuery();
				while (rs.next()) {
					resultString = rs.getString(1);
					// System.out.println("test1"+resultString);
				}
				if (resultString == null) {
					// System.out.println("test2"+resultString);
					return resultString;
				}
			} catch (SQLException | ClassNotFoundException e) {
				CommonFunc.alertDisplay(1, "DB연결 오류", "연결실패", "다시 시도하십시오.");
			} finally {
				try {
					if (psmt != null)
						psmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, "연결오류 발생", "연결실패", "다시 시도하십시오.");
				}
			}
			return resultString;
		}
		// 회원정보수정
		public static UserVO updateUser(UserVO userVO) throws Exception {
			// 접속한 회원의 아이디 얻기
			LoginController lc = new LoginController();
			String currentUserId = lc.getTxtUserId();

			String dml = "update userTbl set userPhone=?, userHomePhone=?, userPw=? where userId = ?";
			Connection con = null;
			PreparedStatement pstmt = null;
			int i = 0;
			try {
				// DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
				con = DBUtil.getConnection();

				// 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
				pstmt = con.prepareStatement(dml); // 보안을 위해
				pstmt.setString(1, userVO.getUserPhone());
				pstmt.setString(2, userVO.getUserHomePhone());
				pstmt.setString(3, userVO.getUserPw());
				pstmt.setString(4, currentUserId);

				// SQL문을 수행후 처리 결과를 얻어옴
				i = pstmt.executeUpdate(); // ==번개, 몇 문장을 실행했는지 리턴함
				if (i == 1) {
					CommonFunc.alertDisplay(1, "수정완료", "수정완료되었습니다", "회원정보수정이 완료되었습니다.");
				} else {
					CommonFunc.alertDisplay(1, "수정실패", "수정 실패하였습니다.", "다시 시도해주세요.");
					return null;
				}

			} catch (SQLException e) {
				System.out.println("e=[" + e + "]");
				CommonFunc.alertDisplay(1, "데이터베이스연결오류", "연결오류발생", "데이터 삽입에 문제가 발생했습니다.");
			} catch (Exception e) {
				System.out.println("e=[" + e + "]");
				CommonFunc.alertDisplay(1, "데이터베이스연결오류", "연결오류발생", "데이터 삽입에 문제가 발생했습니다.");
			} finally {
				try {
					// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
				}
			}
			return userVO;
		}
		// 로그인 관련
		////////////////////////////
		// [WHERE ID=?] userTbl에 존재하는 id인지 확인
		public static int checkStudentId(String studentID) {
			StringBuffer checkTchID = new StringBuffer("select count(*) from userTbl where userId = ? ");
			int resultCount = 0;
			Connection con = null;
			PreparedStatement psmt = null;

			ResultSet rs = null;
			try {
				System.out.println(studentID);
				con = DBUtil.getConnection();
				psmt = con.prepareStatement(checkTchID.toString());
				// 매치시키기
				psmt.setString(1, studentID);

				// 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
				// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
				// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
				rs = psmt.executeQuery();

				while (rs.next()) {
					System.out.println(rs.getInt(1));
					resultCount = rs.getInt(1);
				}
				if (resultCount == 0) {
					CommonFunc.alertDisplay(1, "존재하지 않는 아이디", "존재하지 않는 아이디입니다.", "다시 시도해주세요.");
					// AdminController.callAlert("LOGIN 실패 : 존재하지 않는 아이디 입니다.");
					return resultCount;
				}

			} catch (SQLException | ClassNotFoundException e) {
				// AdminController.callAlert("login 실패 : StudentDAO");
				CommonFunc.alertDisplay(1, "로그인 실패", "로그인 실패", "다시 시도해주세요.");
				System.out.println("checkStudentId 부분 오류 발생");
				e.printStackTrace();
			} finally {
				try {
					// CLOSE DataBase psmt object
					// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
					// 반드시 있으면 닫아라.
					if (psmt != null)
						psmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, "자원 닫기 실패", "자원 닫기 실패", "자원 닫기 실패 : psmt & con (데이터 자원) 닫는 데에 문제 발생하였습니다.");
				}
			}

			return resultCount;
		}

		// 로그인한 회원 정보 가져오기
		public static ArrayList<UserVO> getCurrentUserInfo(String curUserId) {
			ArrayList<UserVO> list = new ArrayList<UserVO>();
			String dml = "select * from userTbl where userId=?";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			UserVO userVO = null;

			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, curUserId);
				// 첫번째 물음표 자리 = 첫번째 매칭
				rs = pstmt.executeQuery();
				while (rs.next()) {
					userVO = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
							rs.getString(11), rs.getString(12));
					list.add(userVO);
				}
			} catch (SQLException se) {
				System.out.println(se);
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException se) {
				}
			}
			return list;
		}

		// [WHERE ID=?] userTBL에 존재하는 PW인지 확인
		public static int checkPW(String studentID, String pw) {
			StringBuffer checkStuID = new StringBuffer("select count(*) from userTbl where userPw = ? and userId= ? ");
			int resultCount = 0;
			Connection con = null;
			PreparedStatement psmt = null;

			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				psmt = con.prepareStatement(checkStuID.toString());
				psmt.setString(1, pw);
				psmt.setString(2, studentID);

				// 실제 데이터를 연결한 쿼리문 실행하라 데이터 베이스에게 명령(번개문)
				// executeQuery -> 쿼리문 실행해서 결과를 *!가져올때!* 사용하는 번개문
				// executeUpdate-> 쿼리문 실행해서 결과를 *!가지고 갈때!* 사용하는 번개문
				rs = psmt.executeQuery();

				while (rs.next()) {
					System.out.println(rs.getInt(1));
					resultCount = rs.getInt(1);
				}
				if (resultCount == 0) {
					CommonFunc.alertDisplay(1, "LOGIN 실패", "LOGIN 실패 ", "아이디와 패스워드가 일치하지 않습니다");
					return resultCount;
				}

			} catch (SQLException | ClassNotFoundException e) {
				CommonFunc.alertDisplay(1, "로그인 실패", "로그인 실패하였습닙다.", "checkPW 부분 오류가 발생하였습니다.");
				e.printStackTrace();
			} finally {
				try {
					// CLOSE DataBase psmt object
					// 제일 먼저 불렀던 것을 제일 나중에 닫는다.
					// 반드시 있으면 닫아라.
					if (psmt != null)
						psmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, "자원 닫기 실패 실패", "자원 닫기 실패 실패", "자원 닫기 실패 실패하였습니다.");
				}
			}
			return resultCount;
		}
		
	// 전체 리스트
	// 데이터를 가져오는 부분(모든 내용)
	public ArrayList<UserVO> getUserTotal() {
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		String dml = "select userId, userName, userGender, userBirth, userAdult, userPhone, userHomePhone, userEmail, userSignDay, userImage, userStatus from userTbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체
		UserVO userVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // 레코드셋
			while (rs.next()) {
				userVO = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
				list.add(userVO);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}

	// 회원 삭제 기능(delete)
	public void getUserDelete(String userId) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "delete from userTbl where userId = ?";	// where 조건 꼭 써야됨
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userId);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(3, "사용자 삭제", "사용자 삭제 완료", "사용자 삭제 성공");
			} else {
				CommonFunc.alertDisplay(3, "사용자 삭제", "사용자 삭제 실패", "사용자 삭제 실패");
			}

		} catch (SQLException e) {
			System.out.println("getUserDelete=[" + e + "]");
		} catch (Exception e) {
			System.out.println("getUserDelete=[" + e + "]");
		} finally {
			try {
				// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

	}

	// 회원 검색기능(select * from table명 where name like '%홍길%')
	public ArrayList<UserVO> getUserSearch(String name) throws Exception {
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		String dml = "select userId, userName, userGender, userBirth, userAdult, userPhone, userHomePhone, userEmail, userSignDay, userImage, userStatus from userTbl where userName like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO retval = null;
		String name2 = "%"+name+"%";
//		System.out.println("name2 = "+name2);	//디버깅
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();	
			while (rs.next()) {
				retval = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
				list.add(retval);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}
	
	// enterNo(int) 가져오기(foreignKey 활용)
	public int getEnterNo(String userId) {
		String dml = "select c.enterNo from chargetbl c where c.userId=? and userStartDate = curdate() order by c.userStartTime desc limit 1";
		int enterNo = 0;
		// -- select 가지고올데이터
		// -- from 포맅키가 있는 테이블 (자식?외부인)
		// -- inner join 부모테이블
		// -- on 포린키테이블.같은데이터 = 부모.같은 데이터 where 조건= '조건데이타';

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체
//		UserOrderVO orderVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery(); // 레코드셋
			while (rs.next()) {
				enterNo = rs.getInt(1);
//				enterNo = orderVO.getEnterNo();
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return enterNo;
	}
}

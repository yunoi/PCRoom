package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import model.ChargeVO;

public class ChargeDAO {

	// 기존회원 dml
	//update chargetbl set userStartTime = curtime(), prepaidMoney = 1000, userStartDate =curdate(), userAvailableTime = userAvailableTime + 3600 where userId = 'ggg' and (select u.userSignDay from userTbl u where u.usersignday= '2019-10-18' and u.userid = 'ggg') limit 1;
	
	//신규가입회원 시간충전
	
	
	// 기존 충전된 시간 불러오기
	public int userRemainTime(String userId) {
		String dml = "select userAvailableTime from chargetbl c where userId = ? order by userStartDate DESC limit 1";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체
		System.out.println(userId);
		int remainTime = 0;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery(); // 레코드셋
			while (rs.next()) {
				remainTime=rs.getInt(1);
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
		return remainTime;
	}
	
		// 처음가입 처음충전
	public void getTodayFirstCharge(ChargeVO cvo) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "insert into chargetbl (enterNo, userStartTime, prepaidMoney, userid, userStartDate, seatNo, userAvailableTime) values (null, curtime(), ?, ?, curdate(), '0', ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println(cvo.getUserId()+"유저아이디");
		System.out.println(cvo.getPrepaidMoney()+"선불요금");
		System.out.println(cvo.getUserAvailableTime()+"시간");
		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 학생 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, cvo.getPrepaidMoney());
			pstmt.setString(2, cvo.getUserId());
			pstmt.setInt(3, cvo.getUserAvailableTime());

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "요금 충전", cvo.getUserId() + "님" +cvo.getPrepaidMoney()+ "****요금 충전 완료", "시간이 충전되었습니다.");
			} else {
				CommonFunc.alertDisplay(1, "요금 충전", "오류", "요금 정보 수정을 실패하였습니다.");
	
			}

		} catch (SQLException e) {
			System.out.println("getTodayFirstCharge=[" + e + "]");
		} catch (Exception e) {
			System.out.println("getTodayFirstCharge=[" + e + "]");
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

		// 사용자가 로그인하면 사용자의 좌석, 로그인 시간, 로그인 날짜 정보를 저장한다.
	public void userLoginInfoSave(int enterNo) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "update chargeTbl set " + "userStartTime= curtime(), userStartDate=curdate(), seatNo=?" + " where enterNo = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ChargeVO cvo = new ChargeVO();
		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 학생 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, cvo.getSeatNo());
			pstmt.setInt(2, enterNo);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "환영합니다.", cvo.getUserId() + "님", cvo.getSeatNo()+"번 좌석에서 로그인하셨습니다.");
			} else {
				CommonFunc.alertDisplay(1, "오류", "오류", "정상이용이 불가합니다.");
	
			}

		} catch (SQLException e) {
			System.out.println("userLoginInfoSave=[" + e + "]");
		} catch (Exception e) {
			System.out.println("userLoginInfoSave=[" + e + "]");
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
		
	// 요금정보 전체 저장
		public int getChargeRegister(String enterNo) throws Exception {
			String dml = "insert into chargeTbl "
					+ "(enterNo, userStartTime, prepaidMoney, userId, userStartDate, seatNo, userAvailableTime) " + " values "
					+ "(null, ?, ?, ?, ?, ?, ?)"; // 6개, DB순서대로
			
			Connection con = null;
			PreparedStatement pstmt = null; // 문장을 준비한다 = 보안을 위한것.
			int count = 0;
			ChargeVO cvo = new ChargeVO();
			try {
				// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
				con = DBUtil.getConnection();

				// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
				pstmt = con.prepareStatement(dml); // 외워 (패턴)
				pstmt.setString(1, cvo.getUserStartTime());
				pstmt.setInt(2, cvo.getPrepaidMoney());
				pstmt.setString(3, cvo.getUserId());
				pstmt.setString(4, cvo.getUserStartDate());
				pstmt.setString(5, cvo.getSeatNo());
				pstmt.setInt(6, cvo.getUserAvailableTime());

				count = pstmt.executeUpdate(); // executeUpdate: 번개를 누르는 것과 같은 기능 (mysql워크벤치에서) . 몇문장 실행했는지 리턴

			} catch (SQLException e) {
				System.out.println("getChargeRegister=[" + e + "]");
			} catch (Exception e) {
				System.out.println("getChargeRegister=[" + e + "]");
			} finally {
				try {
					// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제 = 자원반납
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
				}
			}
			return count;
		}
	// 사용자가 로그인하면 사용자의 모든 정보를 가져온다.
	public ArrayList<ChargeVO> getUserInfo(String userId) {
		ArrayList<ChargeVO> list = new ArrayList<ChargeVO>();
		String dml = "select * from chargeTbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체
		ChargeVO chargeVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // 레코드셋
			while (rs.next()) {
				chargeVO = new ChargeVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7));
				list.add(chargeVO);
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



	// 로그인 아이디를 받아서 선불요금을 가져온다.
	public int selectLabelNameTime(String userId) {
		Vector<Object> vector = new Vector<Object>();
		String dml = "select c.prepaidMoney from chargetbl c where c.userId = ? and c.userStartDate=curdate() order by c.userStartTime desc limit 1";
		Connection con = null;
		PreparedStatement pstmt = null;
		int prepaidMoney=0;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				prepaidMoney=rs.getInt(1);
			}
		} catch (SQLException e1) {
			System.out.println("selectLabelNameTime = [" + e1 + "]");
		} catch (Exception e2) {
			System.out.println("selectLabelNameTime = [" + e2 + "]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException e1) {
			}
		}

		return prepaidMoney;
	}
	// 금액 더하기
	// String dml = "update chargeTbl set prepaidMoney = + ?" // 원래 있던 금액에 추가금 더하기
	// + " where enterNo = ?";
	
	
	// 유저가 로그인한 좌석 번호 저장
	public void saveSeatNo(String userId, String currentSeatNo) {
		String dml = "update chargeTbl set seatNo= ? where userId = ? and userStartDate=curdate() order by userStartTime desc limit 1";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 학생 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, currentSeatNo);
			pstmt.setString(2, userId);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "좌석번호 저장", "좌석번호 저장","좌석번호 저장성공" );
			} else {
				CommonFunc.alertDisplay(1, "좌석번호 저장", "오류", "좌석번호 저장 실패");
			}

		} catch (SQLException e) {
			System.out.println("saveSeatNo=[" + e + "]");
		} catch (Exception e) {
			System.out.println("saveSeatNo=[" + e + "]");
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
	

	// 로그인한 유저의 좌석번호 가져오기
public String getLoginUserSeatNo(String userId) {
	String dml = "select seatNo from chargetbl where userId = ? and userStartDate=curdate() order by userStartTime desc limit 1";
	Connection con = null;
	PreparedStatement pstmt = null;
	String seatNo=null;
	
	try {
		con = DBUtil.getConnection();
		pstmt = con.prepareStatement(dml);
		pstmt.setString(1, userId);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			seatNo=rs.getString(1);
		}
	} catch (SQLException e1) {
		System.out.println("selectLabelNameTime = [" + e1 + "]");
	} catch (Exception e2) {
		System.out.println("selectLabelNameTime = [" + e2 + "]");
	} finally {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}

		} catch (SQLException e1) {
		}
	}

	return seatNo;
}

}


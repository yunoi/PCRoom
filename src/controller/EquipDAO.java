package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.EquipVO;

public class EquipDAO {
	// ① 신규 학생 점수 등록
	// 데이터를 입력하는 부분(insert)
	public int getEquipRegister(EquipVO evo) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		// 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into equipTbl " + "(equipNo, equipCategory, equipName, asInfo, equipStatus,seatNo)"
				+ " values " + "(?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null; // 문장을 준비한다 = 보안을 위한것.
		int count = 0;

		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml); // 외워 (패턴)
			pstmt.setString(1, evo.getEquipNo()); // 1번 물음표에다가 값 넣는것이다. 스트링으로
			pstmt.setString(2, evo.getEquipCategory());
			pstmt.setString(3, evo.getEquipName());
			pstmt.setString(4, evo.getAsInfo());
			pstmt.setString(5, evo.getEquipStatus());
			pstmt.setString(6, evo.getSeatNo());

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			count = pstmt.executeUpdate(); // executeUpdate: 번개를 누르는 것과 같은 기능 (mysql워크벤치에서) . 몇문장 실행했는지 리턴

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
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

	// 장비 전체 리스트
	// 데이터를 가져오는 부분(모든 내용)
	public ArrayList<EquipVO> getEquipTotal() {
		ArrayList<EquipVO> list = new ArrayList<EquipVO>();
		String dml = "select * from equipTbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체
		EquipVO equipVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // 레코드셋
			while (rs.next()) {
				equipVO = new EquipVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
				list.add(equipVO);
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

	// 수정기능 (update table명 set 필드명=수정내용 where 조건내용)
	// 선택한 장비의 정보 수정
	public EquipVO getEquipUpdate(EquipVO evo, String equipNo) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "update equipTbl set " + " equipNo=?, equipCategory=?, equipName=?, asInfo=?, "
				+ "equipStatus=?, seatNo=?" + "where equipNo = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 학생 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, evo.getEquipNo());
			pstmt.setString(2, evo.getEquipCategory());
			pstmt.setString(3, evo.getEquipName());
			pstmt.setString(4, evo.getAsInfo());
			pstmt.setString(5, evo.getEquipStatus());
			pstmt.setString(6, evo.getSeatNo());
			pstmt.setString(7, evo.getEquipNo());

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "설비 정보 수정", evo.getEquipNo() + " " + evo.getEquipName() + "의 정보 수정 성공",
						"정보 수정이 완료되었습니다.");
			} else {
				CommonFunc.alertDisplay(1, "설비 정보 수정", evo.getEquipNo() + " " + evo.getEquipName() + "의 정보 수정 실패",
						"정보 수정을 실패하였습니다.");
				return null;
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
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
		return evo;
	}

	// 시설 삭제 기능(delete)
	public void getEquipDelete(String equipNo) throws Exception {
		// ② 데이터 처리를 위한 SQL 문
		String dml = "delete from equipTbl where equipNo = ?"; // where 조건 꼭 써야됨
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, equipNo);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(3, "장비 삭제", "장비 삭제 완료", "장비 삭제 성공");
			} else {
				CommonFunc.alertDisplay(3, "장비 삭제", "장비 삭제 실패", "장비 삭제 실패");
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
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

	// 직원 검색기능(select * from table명 where name like '%홍길%')
	// ⑦ 직원의 name을 입력받아 정보 조회
	public ArrayList<EquipVO> getEquipSearch(String equipName) throws Exception {
		ArrayList<EquipVO> list = new ArrayList<EquipVO>();
		String dml = "select * from equipTbl where equipName like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EquipVO retval = null;
		String equipName2 = "%" + equipName + "%";
//			System.out.println("name2 = "+name2);	//디버깅
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, equipName2);
			rs = pstmt.executeQuery(); // 항상 결과값은 레코드셋으로 가져온다.
			while (rs.next()) {
				retval = new EquipVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
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

	// 설비 갯수 새로고침

	public ArrayList<Integer> getEquipAmountCheck() throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String dml = "SELECT COUNT(IF (equipCategory = '모니터', 1, null))," + 
				" COUNT(IF (equipCategory = '본체', 1, null))," + 
				" COUNT(IF (equipCategory = '키보드', 1, null))," +
				" COUNT(IF (equipCategory = '스피커', 1, null))," +
				" COUNT(IF (equipCategory = '헤드셋', 1, null))," +
				" COUNT(IF (equipCategory = '마우스', 1, null))" +
				" FROM equiptbl";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		ArrayList<Integer> retval = null;
//			System.out.println("name2 = "+name2);	//디버깅
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // 항상 결과값은 레코드셋으로 가져온다.

			while (rs.next()) {
				list.add(rs.getInt(1)); //모니터
				list.add(rs.getInt(2)); // 본체
				list.add(rs.getInt(3)); // 키보드
				list.add(rs.getInt(4)); // 스피커
				list.add(rs.getInt(5)); // 헤드셋
				list.add(rs.getInt(6)); // 마우스
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
	
	// 좌석 상세 장비 리스트
	public ArrayList<String> getEquipSeatInfo(String seatNo) {
		ArrayList<String> list = new ArrayList<String>();
		String dml = "select equipName from equipTbl where seatNo = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, seatNo);
			
			rs = pstmt.executeQuery(); // 레코드셋
			while (rs.next()) {
				
				list.add(rs.getString(1));
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
}

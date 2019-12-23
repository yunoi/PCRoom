package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.IncomeVO;

public class IncomeDAO {

	// 오늘 하루 총 방문자 수
	public int getDayUserCount(String string) {
		String dml = "select count(enterNo) from chargetbl where userStartDate=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, string);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e + "총 방문자 수 오류");
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
		return count;
	}

	// 현재 회원 수
	public int getCurrentMemberCount() {
		String dml = "select count(userId) from usertbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	// 신규 가입 회원 수
	public int getNewMemberCount(String string) {
		String dml = "select count(userId) from usertbl where userSignDay=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, string);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e + "신규 가입자 수 오류");
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
		return count;
	}

	// 일 피씨, 상품, 총매출 가져오기
	// 일 피씨 매출
	public int getDayPCIncomeInfo(String string) {
		String dml = "select dayPCIncome from incomeTbl where incomeDay = dayofmonth(?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int todayPCIncome = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, string);
			rs = pstmt.executeQuery(); 
			while (rs.next()) {
				todayPCIncome = rs.getInt(1);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e + "현재 PC 매출 오류");
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
		return todayPCIncome;
	}

	// 일 상품 매출
	public int getDayItemIncomeInfo(String string) {
		String dml = "select dayItemIncome from incomeTbl where incomeDay = dayofmonth(?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int todayItemIncome = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, string);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				todayItemIncome = rs.getInt(1);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e + "현재 상품 매출 오류");
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
		return todayItemIncome;
	}

	// 일 총 매출
	public int getDayTotalIncomeInfo(String string) {
		String dml = "select dayTotalIncome from incomeTbl where incomeDay = dayofmonth(?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int todayTotalIncome = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, string);
			rs = pstmt.executeQuery(); 
			while (rs.next()) {
				todayTotalIncome = rs.getInt(1);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e + "현재 총 매출 오류");
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
		return todayTotalIncome;
	}

	// 해당 달 피씨, 상품, 총매출 전체 데이터 가져오기
	public ArrayList<IncomeVO> getIncomeTotal(String year, String month) {
		ArrayList<IncomeVO> list = new ArrayList<IncomeVO>();
		String dml = "select * from incomeTbl where incomeYear = ? and incomeMonth = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		IncomeVO incomeVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, year);
			pstmt.setString(2, month);
			rs = pstmt.executeQuery(); // 레코드셋
			while (rs.next()) {
				incomeVO = new IncomeVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getInt(6));
				list.add(incomeVO);
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

	// 연 pc 매출
	public int getYearPCIncomeInfo(String year) {
		String dml = "select sum(dayPCIncome) from incomeTbl where incomeYear = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int yearPCIncome = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, year);
			rs = pstmt.executeQuery(); 
			while (rs.next()) {
				yearPCIncome = rs.getInt(1);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e + "연 PC 매출 오류");
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
		return yearPCIncome;
	}
	// 연 상품 매출
		public int getYearItemIncomeInfo(String year) {
			String dml = "select sum(dayItemIncome) from incomeTbl where incomeYear = ?";
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int yearItemIncome = 0;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, year);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					yearItemIncome = rs.getInt(1);
				}
			} catch (SQLException se) {
				System.out.println(se);
			} catch (Exception e) {
				System.out.println(e + "연 상품 매출 오류");
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
			return yearItemIncome;
		}
		// 연 총 매출
		public int getYearTotalIncomeInfo(String year) {
			String dml = "select sum(dayTotalIncome) from incomeTbl where incomeYear = ?";
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int yearTotalIncome = 0;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, year);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					yearTotalIncome = rs.getInt(1);
				}
			} catch (SQLException se) {
				System.out.println(se);
			} catch (Exception e) {
				System.out.println(e + "연 총 매출 오류");
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
			return yearTotalIncome;
		}

}

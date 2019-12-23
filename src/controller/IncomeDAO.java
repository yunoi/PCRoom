package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.IncomeVO;

public class IncomeDAO {

	// ���� �Ϸ� �� �湮�� ��
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
			System.out.println(e + "�� �湮�� �� ����");
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

	// ���� ȸ�� ��
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

	// �ű� ���� ȸ�� ��
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
			System.out.println(e + "�ű� ������ �� ����");
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

	// �� �Ǿ�, ��ǰ, �Ѹ��� ��������
	// �� �Ǿ� ����
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
			System.out.println(e + "���� PC ���� ����");
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

	// �� ��ǰ ����
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
			System.out.println(e + "���� ��ǰ ���� ����");
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

	// �� �� ����
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
			System.out.println(e + "���� �� ���� ����");
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

	// �ش� �� �Ǿ�, ��ǰ, �Ѹ��� ��ü ������ ��������
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
			rs = pstmt.executeQuery(); // ���ڵ��
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

	// �� pc ����
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
			System.out.println(e + "�� PC ���� ����");
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
	// �� ��ǰ ����
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
				System.out.println(e + "�� ��ǰ ���� ����");
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
		// �� �� ����
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
				System.out.println(e + "�� �� ���� ����");
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

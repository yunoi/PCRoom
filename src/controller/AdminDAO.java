package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.AdminVO;

public class AdminDAO {
	// �� �ű� �л� ���� ���
	// �����͸� �Է��ϴ� �κ�(insert)
	public int getAdminRegister(AdminVO avo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into adminTbl "
				+ "(adminId, adminName, adminGender, adminBirth, adminPhone, adminAddress, adminEmail, adminWorkingTime, adminLevel, adminCleanArea, adminPw)" + " values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null; // ������ �غ��Ѵ� = ������ ���Ѱ�.
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // �ܿ� (����)
			pstmt.setString(1, avo.getAdminId()); // 1�� ����ǥ���ٰ� �� �ִ°��̴�. ��Ʈ������
			pstmt.setString(2, avo.getAdminName());
			pstmt.setString(3, avo.getAdminGender());
			pstmt.setString(4, avo.getAdminBirth());
			pstmt.setString(5, avo.getAdminPhone());
			pstmt.setString(6, avo.getAdminAddress());
			pstmt.setString(7, avo.getAdminEmail());
			pstmt.setString(8, avo.getAdminWorkingTime());
			pstmt.setString(9, avo.getAdminLevel());
			pstmt.setString(10, avo.getAdminCleanArea());
			pstmt.setString(11, avo.getAdminPw());

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // executeUpdate: ������ ������ �Ͱ� ���� ��� (mysql��ũ��ġ����) . ��� �����ߴ��� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ���� = �ڿ��ݳ�
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	// ��ü ����Ʈ
	// �����͸� �������� �κ�(��� ����)
	public ArrayList<AdminVO> getAdminTotal() {
		ArrayList<AdminVO> list = new ArrayList<AdminVO>();
		String dml = "select * from adminTbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		AdminVO adminVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // ���ڵ��
			while (rs.next()) {
				adminVO = new AdminVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
				list.add(adminVO);
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
	
	// ������� (update table�� set �ʵ��=�������� where ���ǳ���)
	// ������ ������ ���� ����
	public AdminVO getAdminUpdate(AdminVO avo, String id) throws Exception {
		// �� ������ ó���� ���� SQL ��
		//adminId, adminName, adminGender, adminBirth, adminPhone, adminAddress, 
		//adminEmail, adminWorkingTime, adminLevel, adminCleanArea, adminPw
		String dml = "update adminTbl set " + " adminId=?, adminName=?, adminGender=?, adminBirth=?, "
				+ "adminPhone=?, adminAddress=?, adminEmail=?, adminWorkingTime=?, adminLevel=?, adminCleanArea=?, adminPw=?"
				+ "where adminId = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ �л� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, avo.getAdminId());
			pstmt.setString(2, avo.getAdminName());
			pstmt.setString(3, avo.getAdminGender());
			pstmt.setString(4, avo.getAdminBirth());
			pstmt.setString(5, avo.getAdminPhone());
			pstmt.setString(6, avo.getAdminAddress());
			pstmt.setString(7, avo.getAdminEmail());
			pstmt.setString(8, avo.getAdminWorkingTime());
			pstmt.setString(9, avo.getAdminLevel());
			pstmt.setString(10, avo.getAdminCleanArea());
			pstmt.setString(11, avo.getAdminPw());
			pstmt.setString(12, avo.getAdminId());

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "���� ���� ����", avo.getAdminName() + "���� ���� ���� �Ϸ�", "���� ������ �Ϸ�Ǿ����ϴ�.");
			} else {
				CommonFunc.alertDisplay(1, "���� ���� ����", avo.getAdminName() + "���� ���� ���� ����", "���� ������ �����Ͽ����ϴ�.");
				return null;
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return avo;
	}

	// ���� ���� ���(delete)
	public void getAdminDelete(String id) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from adminTbl where adminId = ?";	// where ���� �� ��ߵ�
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, id);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(3, "���� ����", "���� ���� �Ϸ�", "���� ���� ����");
			} else {
				CommonFunc.alertDisplay(3, "���� ����", "���� ���� ����", "���� ���� ����");
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

	}

	// ���� �˻����(select * from table�� where name like '%ȫ��%')
	// �� ������ name�� �Է¹޾� ���� ��ȸ
	public ArrayList<AdminVO> getAdminSearch(String adminName) throws Exception {
		ArrayList<AdminVO> list = new ArrayList<AdminVO>();
		String dml = "select * from adminTbl where adminName like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminVO retval = null;
		String adminName2 = "%"+adminName+"%";
//		System.out.println("name2 = "+name2);	//�����
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, adminName2);
			rs = pstmt.executeQuery();	// �׻� ������� ���ڵ������ �����´�.
			while (rs.next()) {
				retval = new AdminVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
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
}

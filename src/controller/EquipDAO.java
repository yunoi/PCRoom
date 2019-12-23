package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.EquipVO;

public class EquipDAO {
	// �� �ű� �л� ���� ���
	// �����͸� �Է��ϴ� �κ�(insert)
	public int getEquipRegister(EquipVO evo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into equipTbl " + "(equipNo, equipCategory, equipName, asInfo, equipStatus,seatNo)"
				+ " values " + "(?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null; // ������ �غ��Ѵ� = ������ ���Ѱ�.
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // �ܿ� (����)
			pstmt.setString(1, evo.getEquipNo()); // 1�� ����ǥ���ٰ� �� �ִ°��̴�. ��Ʈ������
			pstmt.setString(2, evo.getEquipCategory());
			pstmt.setString(3, evo.getEquipName());
			pstmt.setString(4, evo.getAsInfo());
			pstmt.setString(5, evo.getEquipStatus());
			pstmt.setString(6, evo.getSeatNo());

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

	// ��� ��ü ����Ʈ
	// �����͸� �������� �κ�(��� ����)
	public ArrayList<EquipVO> getEquipTotal() {
		ArrayList<EquipVO> list = new ArrayList<EquipVO>();
		String dml = "select * from equipTbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		EquipVO equipVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // ���ڵ��
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

	// ������� (update table�� set �ʵ��=�������� where ���ǳ���)
	// ������ ����� ���� ����
	public EquipVO getEquipUpdate(EquipVO evo, String equipNo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update equipTbl set " + " equipNo=?, equipCategory=?, equipName=?, asInfo=?, "
				+ "equipStatus=?, seatNo=?" + "where equipNo = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ �л� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, evo.getEquipNo());
			pstmt.setString(2, evo.getEquipCategory());
			pstmt.setString(3, evo.getEquipName());
			pstmt.setString(4, evo.getAsInfo());
			pstmt.setString(5, evo.getEquipStatus());
			pstmt.setString(6, evo.getSeatNo());
			pstmt.setString(7, evo.getEquipNo());

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "���� ���� ����", evo.getEquipNo() + " " + evo.getEquipName() + "�� ���� ���� ����",
						"���� ������ �Ϸ�Ǿ����ϴ�.");
			} else {
				CommonFunc.alertDisplay(1, "���� ���� ����", evo.getEquipNo() + " " + evo.getEquipName() + "�� ���� ���� ����",
						"���� ������ �����Ͽ����ϴ�.");
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
		return evo;
	}

	// �ü� ���� ���(delete)
	public void getEquipDelete(String equipNo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from equipTbl where equipNo = ?"; // where ���� �� ��ߵ�
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, equipNo);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(3, "��� ����", "��� ���� �Ϸ�", "��� ���� ����");
			} else {
				CommonFunc.alertDisplay(3, "��� ����", "��� ���� ����", "��� ���� ����");
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
	public ArrayList<EquipVO> getEquipSearch(String equipName) throws Exception {
		ArrayList<EquipVO> list = new ArrayList<EquipVO>();
		String dml = "select * from equipTbl where equipName like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EquipVO retval = null;
		String equipName2 = "%" + equipName + "%";
//			System.out.println("name2 = "+name2);	//�����
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, equipName2);
			rs = pstmt.executeQuery(); // �׻� ������� ���ڵ������ �����´�.
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

	// ���� ���� ���ΰ�ħ

	public ArrayList<Integer> getEquipAmountCheck() throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String dml = "SELECT COUNT(IF (equipCategory = '�����', 1, null))," + 
				" COUNT(IF (equipCategory = '��ü', 1, null))," + 
				" COUNT(IF (equipCategory = 'Ű����', 1, null))," +
				" COUNT(IF (equipCategory = '����Ŀ', 1, null))," +
				" COUNT(IF (equipCategory = '����', 1, null))," +
				" COUNT(IF (equipCategory = '���콺', 1, null))" +
				" FROM equiptbl";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		ArrayList<Integer> retval = null;
//			System.out.println("name2 = "+name2);	//�����
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // �׻� ������� ���ڵ������ �����´�.

			while (rs.next()) {
				list.add(rs.getInt(1)); //�����
				list.add(rs.getInt(2)); // ��ü
				list.add(rs.getInt(3)); // Ű����
				list.add(rs.getInt(4)); // ����Ŀ
				list.add(rs.getInt(5)); // ����
				list.add(rs.getInt(6)); // ���콺
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
	
	// �¼� �� ��� ����Ʈ
	public ArrayList<String> getEquipSeatInfo(String seatNo) {
		ArrayList<String> list = new ArrayList<String>();
		String dml = "select equipName from equipTbl where seatNo = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, seatNo);
			
			rs = pstmt.executeQuery(); // ���ڵ��
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

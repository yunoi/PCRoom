package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ItemVO;

public class ItemDAO {
	// �� �ű� �л� ���� ���
	// �����͸� �Է��ϴ� �κ�(insert)
public int getItemRegister(ItemVO ivo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into itemTbl "
				+ "(itemCode, itemCategory, itemName, itemPrice, itemStockGarage, itemStockDisplay, itemTotalStock, itemImg)"
				+ " values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null; // ������ �غ��Ѵ� = ������ ���Ѱ�.
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // �ܿ� (����)
			pstmt.setString(1, ivo.getItemCode()); // 1�� ����ǥ���ٰ� �� �ִ°��̴�. ��Ʈ������
			pstmt.setString(2, ivo.getItemCategory());
			pstmt.setString(3, ivo.getItemName());
			pstmt.setInt(4, ivo.getItemPrice());
			pstmt.setInt(5, ivo.getItemStockGarage());
			pstmt.setInt(6, ivo.getItemStockDisplay());
			pstmt.setInt(7, ivo.getItemTotalStock());
			pstmt.setString(8, ivo.getItemImg());

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
	public ArrayList<ItemVO> getItemTotal() {
		ArrayList<ItemVO> list = new ArrayList<ItemVO>();
		String dml = "select * from itemTbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		ItemVO itemVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // ���ڵ��
			while (rs.next()) {
				itemVO = new ItemVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getString(8));
				list.add(itemVO);
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
	public ItemVO getItemUpdate(ItemVO ivo, String itemCode) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update itemTbl set "+"itemCode=?, itemCategory=?, itemName=?, itemPrice=?, itemStockGarage=?, itemStockDisplay=?, itemTotalStock=?, itemImg=?"
				+ " where itemCode=?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ �л� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, ivo.getItemCode());
			pstmt.setString(2, ivo.getItemCategory());
			pstmt.setString(3, ivo.getItemName());
			pstmt.setInt(4, ivo.getItemPrice());
			pstmt.setInt(5, ivo.getItemStockGarage());
			pstmt.setInt(6, ivo.getItemStockDisplay());
			pstmt.setInt(7, ivo.getItemTotalStock());
			pstmt.setString(8, ivo.getItemImg());
			pstmt.setString(9, itemCode);
		
			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "��ǰ �� ��� ���� ����", ivo.getItemName() + "�� ���� ���� ����", "���� ������ �Ϸ�Ǿ����ϴ�.");
			} else {
				CommonFunc.alertDisplay(1, "��ǰ �� ��� ���� ����", ivo.getItemName() + "�� ���� ���� ����", "���� ������ �����Ͽ����ϴ�.");
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
		return ivo;
	}

	// �ü� ���� ���(delete)
	public void getItemDelete(String itemCode) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from itemTbl where itemCode = ?";	// where ���� �� ��ߵ�
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, itemCode);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();
			if (i == 1) {
				CommonFunc.alertDisplay(3, "��ǰ ����", "��ǰ ���� �Ϸ�", "��ǰ ���� ����");
			} else {
//				CommonFunc.alertDisplay(3, "��ǰ ����", "��ǰ ���� ����", "��ǰ ���� ����");
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
	public ArrayList<ItemVO> getItemSearch(String itemName) throws Exception {
		ArrayList<ItemVO> list = new ArrayList<ItemVO>();
		String dml = "select * from itemTbl where itemName like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemVO retval = null;
		String itemName2 = "%"+itemName+"%";
//		System.out.println("name2 = "+name2);	//�����
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, itemName2);
			rs = pstmt.executeQuery();	// �׻� ������� ���ڵ������ �����´�.
			while (rs.next()) {
				retval = new ItemVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7));
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

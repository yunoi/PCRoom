package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.UserOrderVO;

public class UserOrderDAO {
	// �ֹ����
	public int getOrderRegiste(UserOrderVO ovo, int enterNo) throws Exception {
		// ������ ó���� ���� SQL ��
		// �ش�� �ʵ� orderNo�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into ordertbl "
				+ "(orderNo, enterNo, itemCode, itemOrderAmount, orderPrice, orderDate, orderStatus)" + " values "
				+ "(null, ?, ?, ?, ?, curdate(), ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �Է¹��� �ֹ� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ����
			pstmt.setInt(1, ovo.getEnterNo());
			pstmt.setString(2, ovo.getItemCode());
			pstmt.setInt(3, ovo.getItemOrderAmount());
			pstmt.setInt(4, ovo.getOrderPrice());
			pstmt.setString(5, ovo.getOrderStatus());

			// SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // ==����, �� ������ �����ߴ��� ������

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	// ��ǰ ��ü ����Ʈ
	// �����͸� �������� �κ�(��� ����)
	public ArrayList<UserOrderVO> getOrderTotal() {
		ArrayList<UserOrderVO> list = new ArrayList<UserOrderVO>();
		String dml = "select * from ordertbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü

		UserOrderVO orderVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // ���ڵ��
			while (rs.next()) {
				orderVO = new UserOrderVO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6), rs.getString(7));
				list.add(orderVO);
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

	// �ֹ� ����
	public static void getOrderDelete(int orderNo) {
		String dml = "delete from orderTbl where orderNo = ?"; // where ���� �� ��ߵ�
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			//  DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			//  SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, orderNo);

			//  SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(3, "�ֹ� ����", "�ֹ� ���� �Ϸ�", "�ش� ��ǰ�� �����Ͽ����ϴ�.");
			} else {
				CommonFunc.alertDisplay(3, "�ֹ� ����", "�ֹ� ���� ����", "Ȯ�� �� �ٽ� �õ��� �ּ���.");
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

	// �ֹ��� ��ǰ�̸�(itemName) ��������(foreignKey Ȱ��)
	public ArrayList<UserOrderVO> getOrderList() {
		ArrayList<UserOrderVO> list = new ArrayList<UserOrderVO>();
		String dml = "select o.orderNo, i.itemName, o.itemOrderAmount, o.orderPrice from ordertbl o INNER join itemtbl i on o.itemCode = i.itemCode";
		// -- select ������õ�����
		// -- from ����Ű�� �ִ� ���̺� (�ڽ�?�ܺ���)
		// -- inner join �θ����̺�
		// -- on ����Ű���̺�.���������� = �θ�.���� ������ where ����= '���ǵ���Ÿ';

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		UserOrderVO orderVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // ���ڵ��
			while (rs.next()) {
				orderVO = new UserOrderVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
				list.add(orderVO);
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

	// enterNo(int) ��������(foreignKey Ȱ��)
//	public int getEnterNo() {
//		String dml = "select c.enterNo from chargetbl c INNER join ordertbl o on c.enterNo = o.enterNo";
//		int enterNo = 0;
//		// -- select ������õ�����
//		// -- from ����Ű�� �ִ� ���̺� (�ڽ�?�ܺ���)
//		// -- inner join �θ����̺�
//		// -- on ����Ű���̺�.���������� = �θ�.���� ������ where ����= '���ǵ���Ÿ';
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
////		UserOrderVO orderVO = null;
//
//		try {
//			con = DBUtil.getConnection();
//			pstmt = con.prepareStatement(dml);
//			rs = pstmt.executeQuery(); // ���ڵ��
//			while (rs.next()) {
//				enterNo = rs.getInt(1);
////				enterNo = orderVO.getEnterNo();
//			}
//		} catch (SQLException se) {
//			System.out.println(se);
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException se) {
//			}
//		}
//		return enterNo;
//	}

	// ���ݱ��� �ֹ��� ��ǰ���� ������ ��������(select orderPrice from ordertbl)
	public int getTotalOrderMoneySum(int enterNo) throws Exception {
		String dml = "Select sum(orderPrice) from ordertbl group by enterNo=?";
		int sum = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, enterNo);
			rs = pstmt.executeQuery(); // ���ڵ��, ����� ������ ��
			while (rs.next()) {
				sum = rs.getInt(1);
			}
			if (sum == 0) {
				return sum;
			}
		} catch (SQLException se) {
			System.out.println(se + "getTotalOrderMoneySum���� ������ �߻�");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println(e + "getTotalOrderMoneySum���� ������ �߻�");
			e.printStackTrace();
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
		return sum;
	}
}
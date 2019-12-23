package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.OrderVO;

public class OrderDAO {

	// �ֹ� ��ü ����Ʈ ��������
	public ArrayList<OrderVO> getOrderTotal() {
		ArrayList<OrderVO> list = new ArrayList<OrderVO>();
		String dml = "select o.orderNo, o.orderDate, c.seatno, c.userID, u.userName, i.itemName, o.itemOrderAmount, o.orderPrice, o.orderStatus" + 
				" from ordertbl o, chargetbl c, usertbl u, itemtbl i " + 
				"where o.enterno = c.enterno and o.itemCode = i.itemCode and c.userId=u.userId";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		OrderVO orderVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // ���ڵ��
			while (rs.next()) {
				orderVO = new OrderVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9));
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

	// �ֹ� ���� ����
	public OrderVO getOrderStatusUpdate(OrderVO ovo, int orderNo) {
		String dml = "update orderTbl set " + "orderStatus=?"
				+ " where orderNo = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ �л� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, ovo.getOrderStatus());
			pstmt.setInt(2, orderNo);
		
			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "�ֹ� ���� ����", orderNo+ "���� �ֹ� ó�� �Ϸ�", "�ֹ� ó���� �Ϸ�Ǿ����ϴ�..");
			} else {
				CommonFunc.alertDisplay(1, "�ֹ� ���� ����", orderNo+ "���� �ֹ����� ���� ���� ����", "���� ������ �����Ͽ����ϴ�.");
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
		return ovo;
	}

	// �ֹ� ����
	public void getOrderDelete(int orderNo) {
		String dml = "delete from orderTbl where orderNo = ?";	// where ���� �� ��ߵ�
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, orderNo);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(3, "�ֹ� ����", "�ֹ� ���� �Ϸ�", "����ڿ��� �ȳ� �ٶ��ϴ�.");
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

}

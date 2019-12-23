package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.OrderVO;

public class OrderDAO {

	// 주문 전체 리스트 가져오기
	public ArrayList<OrderVO> getOrderTotal() {
		ArrayList<OrderVO> list = new ArrayList<OrderVO>();
		String dml = "select o.orderNo, o.orderDate, c.seatno, c.userID, u.userName, i.itemName, o.itemOrderAmount, o.orderPrice, o.orderStatus" + 
				" from ordertbl o, chargetbl c, usertbl u, itemtbl i " + 
				"where o.enterno = c.enterno and o.itemCode = i.itemCode and c.userId=u.userId";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체
		OrderVO orderVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // 레코드셋
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

	// 주문 상태 변경
	public OrderVO getOrderStatusUpdate(OrderVO ovo, int orderNo) {
		String dml = "update orderTbl set " + "orderStatus=?"
				+ " where orderNo = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 수정한 학생 정보를 수정하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, ovo.getOrderStatus());
			pstmt.setInt(2, orderNo);
		
			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "주문 정보 수정", orderNo+ "번의 주문 처리 완료", "주문 처리가 완료되었습니다..");
			} else {
				CommonFunc.alertDisplay(1, "주문 정보 수정", orderNo+ "번의 주문상태 정보 수정 실패", "정보 수정을 실패하였습니다.");
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
		return ovo;
	}

	// 주문 삭제
	public void getOrderDelete(int orderNo) {
		String dml = "delete from orderTbl where orderNo = ?";	// where 조건 꼭 써야됨
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, orderNo);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(3, "주문 삭제", "주문 삭제 완료", "사용자에게 안내 바랍니다.");
			} else {
				CommonFunc.alertDisplay(3, "주문 삭제", "주문 삭제 실패", "확인 후 다시 시도해 주세요.");
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

}

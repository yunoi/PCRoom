package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.UserOrderVO;

public class UserOrderDAO {
	// 주문등록
	public int getOrderRegiste(UserOrderVO ovo, int enterNo) throws Exception {
		// 데이터 처리를 위한 SQL 문
		// 해당된 필드 orderNo부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
		String dml = "insert into ordertbl "
				+ "(orderNo, enterNo, itemCode, itemOrderAmount, orderPrice, orderDate, orderStatus)" + " values "
				+ "(null, ?, ?, ?, ?, curdate(), ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 입력받은 주문 정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml); // 보안을 위해
			pstmt.setInt(1, ovo.getEnterNo());
			pstmt.setString(2, ovo.getItemCode());
			pstmt.setInt(3, ovo.getItemOrderAmount());
			pstmt.setInt(4, ovo.getOrderPrice());
			pstmt.setString(5, ovo.getOrderStatus());

			// SQL문을 수행후 처리 결과를 얻어옴
			count = pstmt.executeUpdate(); // ==번개, 몇 문장을 실행했는지 리턴함

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	// 상품 전체 리스트
	// 데이터를 가져오는 부분(모든 내용)
	public ArrayList<UserOrderVO> getOrderTotal() {
		ArrayList<UserOrderVO> list = new ArrayList<UserOrderVO>();
		String dml = "select * from ordertbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체

		UserOrderVO orderVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // 레코드셋
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

	// 주문 삭제
	public static void getOrderDelete(int orderNo) {
		String dml = "delete from orderTbl where orderNo = ?"; // where 조건 꼭 써야됨
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			//  DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			//  SQL문을 수행후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, orderNo);

			//  SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(3, "주문 삭제", "주문 삭제 완료", "해당 상품을 삭제하였습니다.");
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

	// 주문한 상품이름(itemName) 가져오기(foreignKey 활용)
	public ArrayList<UserOrderVO> getOrderList() {
		ArrayList<UserOrderVO> list = new ArrayList<UserOrderVO>();
		String dml = "select o.orderNo, i.itemName, o.itemOrderAmount, o.orderPrice from ordertbl o INNER join itemtbl i on o.itemCode = i.itemCode";
		// -- select 가지고올데이터
		// -- from 포맅키가 있는 테이블 (자식?외부인)
		// -- inner join 부모테이블
		// -- on 포린키테이블.같은데이터 = 부모.같은 데이터 where 조건= '조건데이타';

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체
		UserOrderVO orderVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // 레코드셋
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

	// enterNo(int) 가져오기(foreignKey 활용)
//	public int getEnterNo() {
//		String dml = "select c.enterNo from chargetbl c INNER join ordertbl o on c.enterNo = o.enterNo";
//		int enterNo = 0;
//		// -- select 가지고올데이터
//		// -- from 포맅키가 있는 테이블 (자식?외부인)
//		// -- inner join 부모테이블
//		// -- on 포린키테이블.같은데이터 = 부모.같은 데이터 where 조건= '조건데이타';
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null; // 데이터베이스 값을 임시로 저장하는 장소 제공하는 객체
////		UserOrderVO orderVO = null;
//
//		try {
//			con = DBUtil.getConnection();
//			pstmt = con.prepareStatement(dml);
//			rs = pstmt.executeQuery(); // 레코드셋
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

	// 지금까지 주문한 상품들의 가격을 가져오기(select orderPrice from ordertbl)
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
			rs = pstmt.executeQuery(); // 레코드셋, 결과를 가지고 옴
			while (rs.next()) {
				sum = rs.getInt(1);
			}
			if (sum == 0) {
				return sum;
			}
		} catch (SQLException se) {
			System.out.println(se + "getTotalOrderMoneySum에서 오류가 발생");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println(e + "getTotalOrderMoneySum에서 오류가 발생");
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
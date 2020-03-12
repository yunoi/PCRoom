package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import model.ChargeVO;

public class ChargeDAO {

	// ����ȸ�� dml
	//update chargetbl set userStartTime = curtime(), prepaidMoney = 1000, userStartDate =curdate(), userAvailableTime = userAvailableTime + 3600 where userId = 'ggg' and (select u.userSignDay from userTbl u where u.usersignday= '2019-10-18' and u.userid = 'ggg') limit 1;
	
	//�ű԰���ȸ�� �ð�����
	
	
	// ���� ������ �ð� �ҷ�����
	public int userRemainTime(String userId) {
		String dml = "select userAvailableTime from chargetbl c where userId = ? order by userStartDate DESC limit 1";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		System.out.println(userId);
		int remainTime = 0;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery(); // ���ڵ��
			while (rs.next()) {
				remainTime=rs.getInt(1);
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
		return remainTime;
	}
	
		// ó������ ó������
	public void getTodayFirstCharge(ChargeVO cvo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "insert into chargetbl (enterNo, userStartTime, prepaidMoney, userid, userStartDate, seatNo, userAvailableTime) values (null, curtime(), ?, ?, curdate(), '0', ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println(cvo.getUserId()+"�������̵�");
		System.out.println(cvo.getPrepaidMoney()+"���ҿ��");
		System.out.println(cvo.getUserAvailableTime()+"�ð�");
		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ �л� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, cvo.getPrepaidMoney());
			pstmt.setString(2, cvo.getUserId());
			pstmt.setInt(3, cvo.getUserAvailableTime());

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "��� ����", cvo.getUserId() + "��" +cvo.getPrepaidMoney()+ "****��� ���� �Ϸ�", "�ð��� �����Ǿ����ϴ�.");
			} else {
				CommonFunc.alertDisplay(1, "��� ����", "����", "��� ���� ������ �����Ͽ����ϴ�.");
	
			}

		} catch (SQLException e) {
			System.out.println("getTodayFirstCharge=[" + e + "]");
		} catch (Exception e) {
			System.out.println("getTodayFirstCharge=[" + e + "]");
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

		// ����ڰ� �α����ϸ� ������� �¼�, �α��� �ð�, �α��� ��¥ ������ �����Ѵ�.
	public void userLoginInfoSave(int enterNo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "update chargeTbl set " + "userStartTime= curtime(), userStartDate=curdate(), seatNo=?" + " where enterNo = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ChargeVO cvo = new ChargeVO();
		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ �л� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, cvo.getSeatNo());
			pstmt.setInt(2, enterNo);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "ȯ���մϴ�.", cvo.getUserId() + "��", cvo.getSeatNo()+"�� �¼����� �α����ϼ̽��ϴ�.");
			} else {
				CommonFunc.alertDisplay(1, "����", "����", "�����̿��� �Ұ��մϴ�.");
	
			}

		} catch (SQLException e) {
			System.out.println("userLoginInfoSave=[" + e + "]");
		} catch (Exception e) {
			System.out.println("userLoginInfoSave=[" + e + "]");
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
		
	// ������� ��ü ����
		public int getChargeRegister(String enterNo) throws Exception {
			String dml = "insert into chargeTbl "
					+ "(enterNo, userStartTime, prepaidMoney, userId, userStartDate, seatNo, userAvailableTime) " + " values "
					+ "(null, ?, ?, ?, ?, ?, ?)"; // 6��, DB�������
			
			Connection con = null;
			PreparedStatement pstmt = null; // ������ �غ��Ѵ� = ������ ���Ѱ�.
			int count = 0;
			ChargeVO cvo = new ChargeVO();
			try {
				// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
				con = DBUtil.getConnection();

				// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
				pstmt = con.prepareStatement(dml); // �ܿ� (����)
				pstmt.setString(1, cvo.getUserStartTime());
				pstmt.setInt(2, cvo.getPrepaidMoney());
				pstmt.setString(3, cvo.getUserId());
				pstmt.setString(4, cvo.getUserStartDate());
				pstmt.setString(5, cvo.getSeatNo());
				pstmt.setInt(6, cvo.getUserAvailableTime());

				count = pstmt.executeUpdate(); // executeUpdate: ������ ������ �Ͱ� ���� ��� (mysql��ũ��ġ����) . ��� �����ߴ��� ����

			} catch (SQLException e) {
				System.out.println("getChargeRegister=[" + e + "]");
			} catch (Exception e) {
				System.out.println("getChargeRegister=[" + e + "]");
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
	// ����ڰ� �α����ϸ� ������� ��� ������ �����´�.
	public ArrayList<ChargeVO> getUserInfo(String userId) {
		ArrayList<ChargeVO> list = new ArrayList<ChargeVO>();
		String dml = "select * from chargeTbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		ChargeVO chargeVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // ���ڵ��
			while (rs.next()) {
				chargeVO = new ChargeVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7));
				list.add(chargeVO);
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



	// �α��� ���̵� �޾Ƽ� ���ҿ���� �����´�.
	public int selectLabelNameTime(String userId) {
		Vector<Object> vector = new Vector<Object>();
		String dml = "select c.prepaidMoney from chargetbl c where c.userId = ? and c.userStartDate=curdate() order by c.userStartTime desc limit 1";
		Connection con = null;
		PreparedStatement pstmt = null;
		int prepaidMoney=0;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				prepaidMoney=rs.getInt(1);
			}
		} catch (SQLException e1) {
			System.out.println("selectLabelNameTime = [" + e1 + "]");
		} catch (Exception e2) {
			System.out.println("selectLabelNameTime = [" + e2 + "]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException e1) {
			}
		}

		return prepaidMoney;
	}
	// �ݾ� ���ϱ�
	// String dml = "update chargeTbl set prepaidMoney = + ?" // ���� �ִ� �ݾ׿� �߰��� ���ϱ�
	// + " where enterNo = ?";
	
	
	// ������ �α����� �¼� ��ȣ ����
	public void saveSeatNo(String userId, String currentSeatNo) {
		String dml = "update chargeTbl set seatNo= ? where userId = ? and userStartDate=curdate() order by userStartTime desc limit 1";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� ������ �л� ������ �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, currentSeatNo);
			pstmt.setString(2, userId);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(1, "�¼���ȣ ����", "�¼���ȣ ����","�¼���ȣ ���强��" );
			} else {
				CommonFunc.alertDisplay(1, "�¼���ȣ ����", "����", "�¼���ȣ ���� ����");
			}

		} catch (SQLException e) {
			System.out.println("saveSeatNo=[" + e + "]");
		} catch (Exception e) {
			System.out.println("saveSeatNo=[" + e + "]");
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
	

	// �α����� ������ �¼���ȣ ��������
public String getLoginUserSeatNo(String userId) {
	String dml = "select seatNo from chargetbl where userId = ? and userStartDate=curdate() order by userStartTime desc limit 1";
	Connection con = null;
	PreparedStatement pstmt = null;
	String seatNo=null;
	
	try {
		con = DBUtil.getConnection();
		pstmt = con.prepareStatement(dml);
		pstmt.setString(1, userId);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			seatNo=rs.getString(1);
		}
	} catch (SQLException e1) {
		System.out.println("selectLabelNameTime = [" + e1 + "]");
	} catch (Exception e2) {
		System.out.println("selectLabelNameTime = [" + e2 + "]");
	} finally {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}

		} catch (SQLException e1) {
		}
	}

	return seatNo;
}

}


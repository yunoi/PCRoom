package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.UserVO;

public class UserDAO {
	// �ű�ȸ��
		public static int getUserRegister(UserVO avo) throws Exception {

			String dml = "insert into userTbl "
					+ "(userId, userName, userGender, userBirth, userAdult, userPhone, userHomePhone, userEmail, userSignDay, userImage, userStatus, userPw)"
					+ " values " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // 12��, DB�������

			Connection con = null;
			PreparedStatement pstmt = null; // ������ �غ��Ѵ� = ������ ���Ѱ�.
			int count = 0;

			try {
				// DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
				con = DBUtil.getConnection();

				// �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, avo.getUserId()); // 1�� ����ǥ���ٰ� �� �ִ°��̴�. ��Ʈ������
				pstmt.setString(2, avo.getUserName());
				pstmt.setString(3, avo.getUserGender());
				pstmt.setString(4, avo.getUserBirth());
				pstmt.setString(5, avo.getUserAdult());
				pstmt.setString(6, avo.getUserPhone());
				pstmt.setString(7, avo.getUserHomePhone());
				pstmt.setString(8, avo.getUserEmail());
				pstmt.setString(9, avo.getUserSignday());
				pstmt.setString(10, avo.getUserImage());// �̹���
				pstmt.setString(11, avo.getUserStatus());
				pstmt.setString(12, avo.getUserPw());

				count = pstmt.executeUpdate(); // executeUpdate: ������ ������ �Ͱ� ���� ��� (mysql��ũ��ġ����) . ��� �����ߴ��� ����

			} catch (SQLException e) {
				System.out.println("e=[" + e + "]");
			} catch (Exception e) {
				System.out.println("e=[" + e + "]");
			} finally {
				try {
					// �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ���� = �ڿ��ݳ�
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
				}
			}
			return count;
		}
	   

		// �˻����(select * from table�� where name like '%ȫ��%')
		// ID ã�� �˻� ���
		public static String getUserId(String userName, String userEmail) {
			String dml = "select userId from userTbl where userName=? and userEmail=?";
			String resultString = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, userName);
				pstmt.setString(2, userEmail);
				// ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
				// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
				// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
				rs = pstmt.executeQuery();

				while (rs.next()) {
					resultString = rs.getString(1);
				}
				if (resultString == null) {
					return resultString;
				}
			} catch (SQLException | ClassNotFoundException e) {
				CommonFunc.alertDisplay(1, "�˻� ���� �߻�", "ID�� ã�� ���Ͽ����ϴ�.", "�ٽ� �õ����ּ���");
				System.out.println("getUserId �κ� ���� �߻�");
				e.printStackTrace();
			} finally {
				try {
					// CLOSE DataBase psmt object
					// ���� ���� �ҷ��� ���� ���� ���߿� �ݴ´�.
					// �ݵ�� ������ ����
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, " ��", " ", " ");
				}
			}
			return resultString;
		}
		
		// PW �˻� ���
		public static String getUserPW(String userName, String userEmail, String userId) {
			String dml = "select userPw from userTbl where userName=? and userEmail=? and userId=?";
			String resultString = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				// ù��° ����ǥ �ڸ� ġ �����ִ� �۾�
				pstmt.setString(1, userName);
				pstmt.setString(2, userEmail);
				pstmt.setString(3, userId);
				// ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
				// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
				// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
				rs = pstmt.executeQuery();

				while (rs.next()) {
					resultString = rs.getString(1);
				}
				if (resultString == null) {
					return resultString;
				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, " ", " ", " ");
				}
			}
			return resultString;
		}
	   
		// �ߺ�Ȯ�� ���̵� ã��
		public static String getUserIdDuplicateCheck(String userId) {
			String dml = "select userId from userTbl where userId=?";
			String resultString = null;
			Connection con = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				psmt = con.prepareStatement(dml);
				// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
				// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
				psmt.setString(1, userId);
				rs = psmt.executeQuery();
				while (rs.next()) {
					resultString = rs.getString(1);
					// System.out.println("test1"+resultString);
				}
				if (resultString == null) {
					// System.out.println("test2"+resultString);
					return resultString;
				}
			} catch (SQLException | ClassNotFoundException e) {
				CommonFunc.alertDisplay(1, "DB���� ����", "�������", "�ٽ� �õ��Ͻʽÿ�.");
			} finally {
				try {
					if (psmt != null)
						psmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, "������� �߻�", "�������", "�ٽ� �õ��Ͻʽÿ�.");
				}
			}
			return resultString;
		}
		// ȸ����������
		public static UserVO updateUser(UserVO userVO) throws Exception {
			// ������ ȸ���� ���̵� ���
			LoginController lc = new LoginController();
			String currentUserId = lc.getTxtUserId();

			String dml = "update userTbl set userPhone=?, userHomePhone=?, userPw=? where userId = ?";
			Connection con = null;
			PreparedStatement pstmt = null;
			int i = 0;
			try {
				// DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
				con = DBUtil.getConnection();

				// �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
				pstmt = con.prepareStatement(dml); // ������ ����
				pstmt.setString(1, userVO.getUserPhone());
				pstmt.setString(2, userVO.getUserHomePhone());
				pstmt.setString(3, userVO.getUserPw());
				pstmt.setString(4, currentUserId);

				// SQL���� ������ ó�� ����� ����
				i = pstmt.executeUpdate(); // ==����, �� ������ �����ߴ��� ������
				if (i == 1) {
					CommonFunc.alertDisplay(1, "�����Ϸ�", "�����Ϸ�Ǿ����ϴ�", "ȸ������������ �Ϸ�Ǿ����ϴ�.");
				} else {
					CommonFunc.alertDisplay(1, "��������", "���� �����Ͽ����ϴ�.", "�ٽ� �õ����ּ���.");
					return null;
				}

			} catch (SQLException e) {
				System.out.println("e=[" + e + "]");
				CommonFunc.alertDisplay(1, "�����ͺ��̽��������", "��������߻�", "������ ���Կ� ������ �߻��߽��ϴ�.");
			} catch (Exception e) {
				System.out.println("e=[" + e + "]");
				CommonFunc.alertDisplay(1, "�����ͺ��̽��������", "��������߻�", "������ ���Կ� ������ �߻��߽��ϴ�.");
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
			return userVO;
		}
		// �α��� ����
		////////////////////////////
		// [WHERE ID=?] userTbl�� �����ϴ� id���� Ȯ��
		public static int checkStudentId(String studentID) {
			StringBuffer checkTchID = new StringBuffer("select count(*) from userTbl where userId = ? ");
			int resultCount = 0;
			Connection con = null;
			PreparedStatement psmt = null;

			ResultSet rs = null;
			try {
				System.out.println(studentID);
				con = DBUtil.getConnection();
				psmt = con.prepareStatement(checkTchID.toString());
				// ��ġ��Ű��
				psmt.setString(1, studentID);

				// ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
				// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
				// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
				rs = psmt.executeQuery();

				while (rs.next()) {
					System.out.println(rs.getInt(1));
					resultCount = rs.getInt(1);
				}
				if (resultCount == 0) {
					CommonFunc.alertDisplay(1, "�������� �ʴ� ���̵�", "�������� �ʴ� ���̵��Դϴ�.", "�ٽ� �õ����ּ���.");
					// AdminController.callAlert("LOGIN ���� : �������� �ʴ� ���̵� �Դϴ�.");
					return resultCount;
				}

			} catch (SQLException | ClassNotFoundException e) {
				// AdminController.callAlert("login ���� : StudentDAO");
				CommonFunc.alertDisplay(1, "�α��� ����", "�α��� ����", "�ٽ� �õ����ּ���.");
				System.out.println("checkStudentId �κ� ���� �߻�");
				e.printStackTrace();
			} finally {
				try {
					// CLOSE DataBase psmt object
					// ���� ���� �ҷ��� ���� ���� ���߿� �ݴ´�.
					// �ݵ�� ������ �ݾƶ�.
					if (psmt != null)
						psmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, "�ڿ� �ݱ� ����", "�ڿ� �ݱ� ����", "�ڿ� �ݱ� ���� : psmt & con (������ �ڿ�) �ݴ� ���� ���� �߻��Ͽ����ϴ�.");
				}
			}

			return resultCount;
		}

		// �α����� ȸ�� ���� ��������
		public static ArrayList<UserVO> getCurrentUserInfo(String curUserId) {
			ArrayList<UserVO> list = new ArrayList<UserVO>();
			String dml = "select * from userTbl where userId=?";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			UserVO userVO = null;

			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, curUserId);
				// ù��° ����ǥ �ڸ� = ù��° ��Ī
				rs = pstmt.executeQuery();
				while (rs.next()) {
					userVO = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
							rs.getString(11), rs.getString(12));
					list.add(userVO);
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

		// [WHERE ID=?] userTBL�� �����ϴ� PW���� Ȯ��
		public static int checkPW(String studentID, String pw) {
			StringBuffer checkStuID = new StringBuffer("select count(*) from userTbl where userPw = ? and userId= ? ");
			int resultCount = 0;
			Connection con = null;
			PreparedStatement psmt = null;

			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				psmt = con.prepareStatement(checkStuID.toString());
				psmt.setString(1, pw);
				psmt.setString(2, studentID);

				// ���� �����͸� ������ ������ �����϶� ������ ���̽����� ���(������)
				// executeQuery -> ������ �����ؼ� ����� *!�����ö�!* ����ϴ� ������
				// executeUpdate-> ������ �����ؼ� ����� *!������ ����!* ����ϴ� ������
				rs = psmt.executeQuery();

				while (rs.next()) {
					System.out.println(rs.getInt(1));
					resultCount = rs.getInt(1);
				}
				if (resultCount == 0) {
					CommonFunc.alertDisplay(1, "LOGIN ����", "LOGIN ���� ", "���̵�� �н����尡 ��ġ���� �ʽ��ϴ�");
					return resultCount;
				}

			} catch (SQLException | ClassNotFoundException e) {
				CommonFunc.alertDisplay(1, "�α��� ����", "�α��� �����Ͽ����մ�.", "checkPW �κ� ������ �߻��Ͽ����ϴ�.");
				e.printStackTrace();
			} finally {
				try {
					// CLOSE DataBase psmt object
					// ���� ���� �ҷ��� ���� ���� ���߿� �ݴ´�.
					// �ݵ�� ������ �ݾƶ�.
					if (psmt != null)
						psmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					CommonFunc.alertDisplay(1, "�ڿ� �ݱ� ���� ����", "�ڿ� �ݱ� ���� ����", "�ڿ� �ݱ� ���� �����Ͽ����ϴ�.");
				}
			}
			return resultCount;
		}
		
	// ��ü ����Ʈ
	// �����͸� �������� �κ�(��� ����)
	public ArrayList<UserVO> getUserTotal() {
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		String dml = "select userId, userName, userGender, userBirth, userAdult, userPhone, userHomePhone, userEmail, userSignDay, userImage, userStatus from userTbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		UserVO userVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // ���ڵ��
			while (rs.next()) {
				userVO = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
				list.add(userVO);
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

	// ȸ�� ���� ���(delete)
	public void getUserDelete(String userId) throws Exception {
		// �� ������ ó���� ���� SQL ��
		String dml = "delete from userTbl where userId = ?";	// where ���� �� ��ߵ�
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� SQL���� ������ ó�� ����� ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userId);

			// �� SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				CommonFunc.alertDisplay(3, "����� ����", "����� ���� �Ϸ�", "����� ���� ����");
			} else {
				CommonFunc.alertDisplay(3, "����� ����", "����� ���� ����", "����� ���� ����");
			}

		} catch (SQLException e) {
			System.out.println("getUserDelete=[" + e + "]");
		} catch (Exception e) {
			System.out.println("getUserDelete=[" + e + "]");
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

	// ȸ�� �˻����(select * from table�� where name like '%ȫ��%')
	public ArrayList<UserVO> getUserSearch(String name) throws Exception {
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		String dml = "select userId, userName, userGender, userBirth, userAdult, userPhone, userHomePhone, userEmail, userSignDay, userImage, userStatus from userTbl where userName like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO retval = null;
		String name2 = "%"+name+"%";
//		System.out.println("name2 = "+name2);	//�����
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name2);
			rs = pstmt.executeQuery();	
			while (rs.next()) {
				retval = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
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
	
	// enterNo(int) ��������(foreignKey Ȱ��)
	public int getEnterNo(String userId) {
		String dml = "select c.enterNo from chargetbl c where c.userId=? and userStartDate = curdate() order by c.userStartTime desc limit 1";
		int enterNo = 0;
		// -- select ������õ�����
		// -- from ����Ű�� �ִ� ���̺� (�ڽ�?�ܺ���)
		// -- inner join �θ����̺�
		// -- on ����Ű���̺�.���������� = �θ�.���� ������ where ����= '���ǵ���Ÿ';

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
//		UserOrderVO orderVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery(); // ���ڵ��
			while (rs.next()) {
				enterNo = rs.getInt(1);
//				enterNo = orderVO.getEnterNo();
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
		return enterNo;
	}
}

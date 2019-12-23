package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


// �ϴ� ��
// 1. ����̹��� �����Ѵ�.
// 2. ���̵�� �н������ myspl �����ͺ��̽��� ���� ��û�Ѵ�. -> db�����ϴ� ��ü��������(db�ּҸ� �˰��ִ� �����ִ�) �ش�.
public class DBUtil {
	// 1. ����̹��� ����
	private static String driver = "com.mysql.jdbc.Driver";
	// 2. �����ͺ��̽� url ���� (url: ��ġ�� ��ġ)
	private static String url = "jdbc:mysql://localhost/PCRoomDB";	// ���� ip ������ �ű�� �� �� ����. ip/���ϸ�
	
	// 3. �Լ�: ����̹��� �����ϰ� �����ͺ��̽��� �����ϴ� ����.
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 1. ����̹� ���� (�ڹٿ� mysql ������ �������ִ� ����) (�ε�)
		Class.forName(driver);
		// 2. �����ͺ��̽� ����
		Connection con = DriverManager.getConnection(url, "root", "123456");
		return con;		//mysql������ �� �ִ� ��ü(��������) ����
	}

}

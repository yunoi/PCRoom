package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


// 하는 일
// 1. 드라이버를 적재한다.
// 2. 아이디와 패스워드로 myspl 데이터베이스에 연결 요청한다. -> db연결하는 객체참조변수(db주소를 알고있는 갖고있는) 준다.
public class DBUtil {
	// 1. 드라이버명 저장
	private static String driver = "com.mysql.jdbc.Driver";
	// 2. 데이터베이스 url 저장 (url: 설치된 위치)
	private static String url = "jdbc:mysql://localhost/PCRoomDB";	// 상대방 ip 적으면 거기로 들어갈 수 있음. ip/파일명
	
	// 3. 함수: 드라이버를 적재하고 데이터베이스를 연결하는 역할.
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 1. 드라이버 적재 (자바와 mysql 서버와 연결해주는 역할) (로드)
		Class.forName(driver);
		// 2. 데이터베이스 연결
		Connection con = DriverManager.getConnection(url, "root", "123456");
		return con;		//mysql접근할 수 있는 객체(참조변수) 리턴
	}

}

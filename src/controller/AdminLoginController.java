package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLoginController implements Initializable {
	@FXML
	private TextField txtAdminId;
	@FXML
	private PasswordField txtAdminPw;
	@FXML
	private Button btnAdminStart;
	
	public static String currentAdminId;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnAdminStart.setOnAction(event -> {
			handlerBtnAdminStartAction(event);
		});
		
		// 테스트용으로 작업해놓음
		txtAdminId.setText("admin");
		txtAdminPw.setText("1234");
	}

	// 버튼 확인을 눌렀을 때 이벤트 처리
	public void handlerBtnAdminStartAction(ActionEvent event) {
		// 1. 아이디 패스워드가 입력안되었을 때 경고창을 준다.
		currentAdminId = txtAdminId.getText();
		if (txtAdminId.getText().equals("") || txtAdminPw.getText().equals("")) {
			CommonFunc.alertDisplay(1, "로그인 실패", "아이디 혹은 패스워드 미입력", "다시 입력해 주세요.");
			
			// 2. 아이디 패스워드가 올바르게 입력되었을 때.
		} else if (txtAdminId.getText().equals("admin") && txtAdminPw.getText().equals("1234")) {
			// 뭔가 원하는 작업을 해야한다.
			// 로그인 완료되었으면 다음 메인창으로 이동한다. (현재창을 닫고 이동하는것)
			Parent mainView = null;
			Stage mainStage = null;
			try {
				mainView = FXMLLoader.load(getClass().getResource("/view/admin_main.fxml"));
				Scene scene = new Scene(mainView);
				mainStage = new Stage();
				mainStage.setTitle("안녕하세요! 외유내강PC방 관리자님 환영합니다.");
				mainStage.setScene(scene);
				mainStage.setResizable(false);
				// 현재스테이지(기존창)를 닫고 새로운 창을 연다.
				((Stage) btnAdminStart.getScene().getWindow()).close();
				mainStage.show();
				
			} catch (IOException e) {
				CommonFunc.alertDisplay(1, "메인창 부르기 실패", "메인창을 불러올 수 없습니다.", e.toString());
			}
		
		} else {
			CommonFunc.alertDisplay(1, "로그인 실패", "아이디와 패스워드 불일치", "다시 입력해 주세요.");
		}		
	}
	
}

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import controller.CommonFunc;
import controller.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class IDPWSearchController implements Initializable {

	@FXML
	private TextField txtIdName;
	@FXML
	private TextField txtIdEmail;
	@FXML
	private Button btnIdFind;
	@FXML
	private TextField txtPwName;
	@FXML
	private TextField txtPwId;
	@FXML
	private TextField txtPwEmail;
	@FXML
	private Button btnPwFind;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnIdFind.setOnAction(event -> handlerBtnIdFindAction(event));
		btnPwFind.setOnAction(event -> handlerBtnPwFindAction(event));
	}

//ID찾기 버튼
	private void handlerBtnIdFindAction(ActionEvent event) {
		String foundID = null;
		String userName = txtIdName.getText();
		String userEmail = txtIdEmail.getText();
		try {
			foundID = UserDAO.getUserId(userName, userEmail);
			if (foundID == null) {
				CommonFunc.alertDisplay(1, "ID 찾기 실패", "확인요망", "다시 입력해주세요.");
				return;
			} else {
				CommonFunc.alertDisplay(1, "ID 찾기 성공", "ID를 찾았습니다.", "회원님의 ID는 " + foundID + " 입니다.");
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "DB연결오류", "확인요망", "다시 시도해주세요.");
		}
	}

//PW 찾기 버튼
	private void handlerBtnPwFindAction(ActionEvent event) {
		String foundPW = null;
		String userName = txtPwName.getText();
		String userEmail = txtPwEmail.getText();
		String userId = txtPwId.getText();

		try {
			foundPW = UserDAO.getUserPW(userName, userEmail, userId);
			if (foundPW == null) {
				CommonFunc.alertDisplay(1, "PW 찾기 실패", "확인요망", "다시 입력해주세요.");
				return;
			} else {
				CommonFunc.alertDisplay(1, "PW 찾기 성공", "PW를 찾았습니다.", "회원님의 PW는 " + foundPW + " 입니다.");
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "DB연결오류", "확인요망", "다시 시도해주세요.");
		}
	}

}

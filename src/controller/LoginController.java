package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private TextField txtUserId;
	@FXML
	private PasswordField txtUserPw;
	@FXML
	private Button btnUserStart;
	@FXML
	private Button btnIdPwSearch;
	@FXML
	private Button btnSignIn;
	@FXML
	private Button btnExit;
	@FXML
	private ComboBox<String> cbxSeatNo;
	public static String getCurrentUserID;
	public static String getCurrentSeatNo;
	String userId;
	String userSeatNo;

	public String getTxtUserId() {
		userId = getCurrentUserID;
		return userId;
	}

	public int getUserSeatNo() {
		userSeatNo = getCurrentSeatNo;
		return Integer.parseInt(userSeatNo);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbxSeatNo.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
		btnUserStart.setOnAction(event -> handlerBtnLoginAction(event));
		btnExit.setOnAction(event -> handlerBtnCancelAction(event));
		btnIdPwSearch.setOnAction(event -> handlerBtnIdPwSearchAction(event));
		btnSignIn.setOnAction(event -> handlerBtnbtnSignInAction(event));

		// 테스트 속도 위해 미리 적어놓음
//		txtUserId.setText("ggg");
//		txtUserPw.setText("1111");
	}

	// 회원가입창
	public void handlerBtnbtnSignInAction(ActionEvent event) {
		Parent mainView = null;
		Stage signStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/SignIn.fxml"));
			Scene scene = new Scene(mainView);
			signStage = new Stage();
			signStage.setScene(scene);
			signStage.setTitle("외유내강 PC방 회원가입");
			signStage.setScene(scene);
			signStage.setResizable(false);
			signStage.show();

		} catch (IOException e) {
			CommonFunc.alertDisplay(5, "회원가입창 부르기 오류", "회원가입창을 불러오지 못했습니다.", "다시 시도해주세요.");
		}
	}

	// 아이디 패스워드 찾기
	public void handlerBtnIdPwSearchAction(ActionEvent event) {
		Parent searchView = null;
		Stage stage = null;
		try {
			searchView = FXMLLoader.load(getClass().getResource("/view/IDPWSearch.fxml"));
			Scene scene = new Scene(searchView);
			stage = new Stage();
			stage.setTitle("ID, PW찾기");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "ID/PW창 부르기 오류", "확인요망", "다시 시도해주세요.");
		}
	}

	// 나가기
	public void handlerBtnCancelAction(ActionEvent event) {
		Platform.exit();
	}

	// 버튼 사용시작 이벤트 처리
	public void handlerBtnLoginAction(ActionEvent event) {

		getCurrentSeatNo = cbxSeatNo.getValue();
		if (getCurrentSeatNo == null) {
			CommonFunc.alertDisplay(1, "로그인 실패", "좌석번호를 선택해 주세요.", "좌석번호를 선택해 주세요.");
			return;
		} else {
//			ChargeDAO chargeDAO = new ChargeDAO();
//			chargeDAO.
		}
		String id = txtUserId.getText();
		getCurrentUserID = txtUserId.getText();
//		System.out.println(getCurrentUserID);
		String sPW = txtUserPw.getText();
		// DAO 만들고
		int existID = UserDAO.checkStudentId(id);
		int existPW = 0;
		if (existID == 0) {
			CommonFunc.alertDisplay(1, "로그인 실패", "존재하지 않는 아이디입니다.", "다시 확인하십시오.");
			return;
		} else {
			// DAO 만들고 다시하기
			existPW = UserDAO.checkPW(id, sPW);
		}
		if (existPW == 0) {
			return;
		} else {
			// existPW=StudentDAO.checkPW(id,sPW);
		}

		// enterNo얻기
		UserDAO udo = new UserDAO();
		System.out.println("접속 enterNo= " + udo.getEnterNo(getCurrentUserID));
		ChargeDAO cdo = new  ChargeDAO();
		cdo.saveSeatNo(getCurrentUserID, getCurrentSeatNo);

		Parent mainView = null;
		Stage mainStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/UserMain.fxml"));
			Scene scene = new Scene(mainView);
			mainStage = new Stage();
			mainStage.setScene(scene);
			mainStage.setScene(scene);
			mainStage.setTitle("사용자 메인");
			mainStage.setResizable(false);
			mainStage.show();

			// 현재의 스테이지를 닫고 새로운 창을 연다
			((Stage) btnUserStart.getScene().getWindow()).close();
			// AdminController.callAlert("[성공] : 화면 전환에 성공 ");
		} catch (Exception e) {

		} // end of handlerBtnLoginActoion
	}
}

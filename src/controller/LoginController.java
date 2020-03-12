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

		// �׽�Ʈ �ӵ� ���� �̸� �������
//		txtUserId.setText("ggg");
//		txtUserPw.setText("1111");
	}

	// ȸ������â
	public void handlerBtnbtnSignInAction(ActionEvent event) {
		Parent mainView = null;
		Stage signStage = null;
		try {
			mainView = FXMLLoader.load(getClass().getResource("/view/SignIn.fxml"));
			Scene scene = new Scene(mainView);
			signStage = new Stage();
			signStage.setScene(scene);
			signStage.setTitle("�������� PC�� ȸ������");
			signStage.setScene(scene);
			signStage.setResizable(false);
			signStage.show();

		} catch (IOException e) {
			CommonFunc.alertDisplay(5, "ȸ������â �θ��� ����", "ȸ������â�� �ҷ����� ���߽��ϴ�.", "�ٽ� �õ����ּ���.");
		}
	}

	// ���̵� �н����� ã��
	public void handlerBtnIdPwSearchAction(ActionEvent event) {
		Parent searchView = null;
		Stage stage = null;
		try {
			searchView = FXMLLoader.load(getClass().getResource("/view/IDPWSearch.fxml"));
			Scene scene = new Scene(searchView);
			stage = new Stage();
			stage.setTitle("ID, PWã��");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "ID/PWâ �θ��� ����", "Ȯ�ο��", "�ٽ� �õ����ּ���.");
		}
	}

	// ������
	public void handlerBtnCancelAction(ActionEvent event) {
		Platform.exit();
	}

	// ��ư ������ �̺�Ʈ ó��
	public void handlerBtnLoginAction(ActionEvent event) {

		getCurrentSeatNo = cbxSeatNo.getValue();
		if (getCurrentSeatNo == null) {
			CommonFunc.alertDisplay(1, "�α��� ����", "�¼���ȣ�� ������ �ּ���.", "�¼���ȣ�� ������ �ּ���.");
			return;
		} else {
//			ChargeDAO chargeDAO = new ChargeDAO();
//			chargeDAO.
		}
		String id = txtUserId.getText();
		getCurrentUserID = txtUserId.getText();
//		System.out.println(getCurrentUserID);
		String sPW = txtUserPw.getText();
		// DAO �����
		int existID = UserDAO.checkStudentId(id);
		int existPW = 0;
		if (existID == 0) {
			CommonFunc.alertDisplay(1, "�α��� ����", "�������� �ʴ� ���̵��Դϴ�.", "�ٽ� Ȯ���Ͻʽÿ�.");
			return;
		} else {
			// DAO ����� �ٽ��ϱ�
			existPW = UserDAO.checkPW(id, sPW);
		}
		if (existPW == 0) {
			return;
		} else {
			// existPW=StudentDAO.checkPW(id,sPW);
		}

		// enterNo���
		UserDAO udo = new UserDAO();
		System.out.println("���� enterNo= " + udo.getEnterNo(getCurrentUserID));
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
			mainStage.setTitle("����� ����");
			mainStage.setResizable(false);
			mainStage.show();

			// ������ ���������� �ݰ� ���ο� â�� ����
			((Stage) btnUserStart.getScene().getWindow()).close();
			// AdminController.callAlert("[����] : ȭ�� ��ȯ�� ���� ");
		} catch (Exception e) {

		} // end of handlerBtnLoginActoion
	}
}

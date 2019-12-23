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

//IDã�� ��ư
	private void handlerBtnIdFindAction(ActionEvent event) {
		String foundID = null;
		String userName = txtIdName.getText();
		String userEmail = txtIdEmail.getText();
		try {
			foundID = UserDAO.getUserId(userName, userEmail);
			if (foundID == null) {
				CommonFunc.alertDisplay(1, "ID ã�� ����", "Ȯ�ο��", "�ٽ� �Է����ּ���.");
				return;
			} else {
				CommonFunc.alertDisplay(1, "ID ã�� ����", "ID�� ã�ҽ��ϴ�.", "ȸ������ ID�� " + foundID + " �Դϴ�.");
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "DB�������", "Ȯ�ο��", "�ٽ� �õ����ּ���.");
		}
	}

//PW ã�� ��ư
	private void handlerBtnPwFindAction(ActionEvent event) {
		String foundPW = null;
		String userName = txtPwName.getText();
		String userEmail = txtPwEmail.getText();
		String userId = txtPwId.getText();

		try {
			foundPW = UserDAO.getUserPW(userName, userEmail, userId);
			if (foundPW == null) {
				CommonFunc.alertDisplay(1, "PW ã�� ����", "Ȯ�ο��", "�ٽ� �Է����ּ���.");
				return;
			} else {
				CommonFunc.alertDisplay(1, "PW ã�� ����", "PW�� ã�ҽ��ϴ�.", "ȸ������ PW�� " + foundPW + " �Դϴ�.");
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "DB�������", "Ȯ�ο��", "�ٽ� �õ����ּ���.");
		}
	}

}

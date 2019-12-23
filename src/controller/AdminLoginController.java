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
		
		// �׽�Ʈ������ �۾��س���
		txtAdminId.setText("admin");
		txtAdminPw.setText("1234");
	}

	// ��ư Ȯ���� ������ �� �̺�Ʈ ó��
	public void handlerBtnAdminStartAction(ActionEvent event) {
		// 1. ���̵� �н����尡 �Է¾ȵǾ��� �� ���â�� �ش�.
		currentAdminId = txtAdminId.getText();
		if (txtAdminId.getText().equals("") || txtAdminPw.getText().equals("")) {
			CommonFunc.alertDisplay(1, "�α��� ����", "���̵� Ȥ�� �н����� ���Է�", "�ٽ� �Է��� �ּ���.");
			
			// 2. ���̵� �н����尡 �ùٸ��� �ԷµǾ��� ��.
		} else if (txtAdminId.getText().equals("admin") && txtAdminPw.getText().equals("1234")) {
			// ���� ���ϴ� �۾��� �ؾ��Ѵ�.
			// �α��� �Ϸ�Ǿ����� ���� ����â���� �̵��Ѵ�. (����â�� �ݰ� �̵��ϴ°�)
			Parent mainView = null;
			Stage mainStage = null;
			try {
				mainView = FXMLLoader.load(getClass().getResource("/view/admin_main.fxml"));
				Scene scene = new Scene(mainView);
				mainStage = new Stage();
				mainStage.setTitle("�ȳ��ϼ���! ��������PC�� �����ڴ� ȯ���մϴ�.");
				mainStage.setScene(scene);
				mainStage.setResizable(false);
				// ���罺������(����â)�� �ݰ� ���ο� â�� ����.
				((Stage) btnAdminStart.getScene().getWindow()).close();
				mainStage.show();
				
			} catch (IOException e) {
				CommonFunc.alertDisplay(1, "����â �θ��� ����", "����â�� �ҷ��� �� �����ϴ�.", e.toString());
			}
		
		} else {
			CommonFunc.alertDisplay(1, "�α��� ����", "���̵�� �н����� ����ġ", "�ٽ� �Է��� �ּ���.");
		}		
	}
	
}

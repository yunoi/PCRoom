package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.UserVO;

public class UserMainController implements Initializable {

	@FXML
	private ImageView imgView;
	@FXML
	private Button btnOrder;
	@FXML
	private Button btnUserLogout;
	@FXML
	private Button btnUserInfoEdit;
	@FXML
	private Button btnSeatChange;
	@FXML
	private Button btnDetailCharge;
	@FXML
	private Label lbSeatNo;
	@FXML
	private Label lbAvailableTime;
	@FXML
	private Label lbPrepaidMoney;
	@FXML
	private Button btnInfo;

	//// 2019-10-22 ȸ�������� ���� ���� ȸ�����Է�Ʈ��Ʈ�ѷ��� �����̸��� ������
	@FXML
	TextField txtSignInName;
	@FXML
	TextField txtSignInId;
	@FXML
	PasswordField txtSignInPw;
	@FXML
	TextField txtSignInEmail;
	@FXML
	TextField txtSignInCellPhone;
	@FXML
	TextField txtSignInHomePhone;
	@FXML
	PasswordField txtSignInPwConfirm;
	@FXML
	Button btnIdDuplicate;
	@FXML
	Button btnSignInOk;
	@FXML
	Label onUserName;
	@FXML
	RadioButton rbUserFemale;
	@FXML
	RadioButton rbUserMale;
	@FXML
	RadioButton rbAdult;
	@FXML
	RadioButton rbChild;
	@FXML
	ComboBox<String> cbxUserBirthYear;
	@FXML
	ComboBox<String> cbxUserBirthMonth;
	@FXML
	ComboBox<String> cbxUserBirthDay;
	@FXML
	ComboBox<String> cbxSignInEmail;
	@FXML
	ImageView imgIcon;
	@FXML
	private ToggleGroup userGenderGP;
	@FXML
	private ToggleGroup userAgeGP;
	private UserDAO userDAO = null;
	private ObservableList<UserVO> selectUser;
	// ������Ű��ú���
	private int time;
	LoginController lc = new LoginController();
	String currentUserId = lc.getTxtUserId();
	int currentSeatNo = lc.getUserSeatNo();
	private boolean logout = true;
	private int countdownSeconds;
	private int prepaidMoney;
	private int i;

	private Socket socket;
	private String id;
	private String count;
	private PrintWriter os;
	private BufferedReader in;

	// �̹��� ���� ���
	private String localUrl = "";
	private Image localImage;
	private File selectedFile = null;
	private String selectFileName = ""; // �̹��� ���ϸ�
	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/images");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		connect(); // ������ ����

		btnOrder.setOnAction(event -> handlerBtnOrderAction(event));
		btnUserLogout.setOnAction(event -> handlerBtnUserLogoutAction(event));
		btnSeatChange.setOnAction(event -> handlerBtnSeatChangeAction(event));
		btnDetailCharge.setOnAction(event -> handlerBtnDetailChargeAction(event));
		btnInfo.setOnAction(event -> handlerBtnInfoAction(event));
		imageSave(file);

		onUserName.setText(currentUserId); // ���� �̿���
		lbSeatNo.setText(String.valueOf(currentSeatNo)); // ���� �¼�
	}

	public void connect() {
		ChargeDAO chargeDAO = new ChargeDAO();
		prepaidMoney = chargeDAO.selectLabelNameTime(currentUserId);
	

		try {
			socket = new Socket("127.0.0.1", 9999);
			os = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("������ �����Ͽ����ϴ�.");
		} catch (IOException e1) {
			System.out.println("�����������");
			e1.printStackTrace();
		}
//		prepaidMoney = 2000; // �����

		// ���� �ð� ǥ��
		if (prepaidMoney == 1000) {
			countdownSeconds = 3600;
		} else if (prepaidMoney == 2000) {
			countdownSeconds = 7200;
		} else if (prepaidMoney == 3000) {
			countdownSeconds = 10800;
		} else if (prepaidMoney == 5000) {
			countdownSeconds = 18000;
		} else if (prepaidMoney == 20000) {
			countdownSeconds = 86400;
		} 
		Thread t1 = new Thread() {
			@Override
			public void run() {
				try {
					os.println("NAME " + currentUserId);
					os.flush();
					while (logout) {
						// int countdownSeconds = hour * 3600 + minute * 60 + second;
						if (countdownSeconds == 0) {
							CommonFunc.alertDisplay(1, "����", "X", "X");
						} else {
							if (currentUserId == null) {
								CommonFunc.alertDisplay(1, "����", "X", "X");
							} else {
								for (i = countdownSeconds; i >= 0; i--) {

									Thread.sleep(1000);

									Platform.runLater(() -> {
										lbAvailableTime.setText(
												String.valueOf(i / 3600 + ":" + i % 3600 / 60 + ":" + i % 3600 % 60));
										lbPrepaidMoney.setText(prepaidMoney + "��");
									});
								}
							}
						}
					} // while
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t1.start();
	}// connect

	// �̹��� ���� �޼ҵ�
	private String imageSave(File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// �̹��� ���ϸ� ����
			fileName = "User" + System.currentTimeMillis() + "_" + file.getName();
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));

			// ������ �̹��� ���� InputStream�� �������� �̸����� ���� -1
			while ((data = bis.read()) != -1) {
				bos.write(data);
				bos.flush();
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}
		return fileName;

	}

	// ȸ����������
	private void handlerBtnInfoAction(ActionEvent event) {
		try {
			Parent editRoot = null;
			editRoot = FXMLLoader.load(getClass().getResource("/view/UserInfo.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnOrder.getScene().getWindow());
			stageDialog.setTitle("ȸ����������");

			// 21��(textField 5��, passwordField 2��, Button 2��, ImageView 1��, ToggleGroup 2��)
			// RadioButton 4��, ComboBox 4��, Lable 1��
			TextField txtSignInNameEdit = (TextField) editRoot.lookup("#txtSignInName");
			TextField txtSignInIdEdit = (TextField) editRoot.lookup("#txtSignInId");
			TextField txtSignInEmailEdit = (TextField) editRoot.lookup("#txtSignInEmail");
			TextField txtSignInCellPhoneEdit = (TextField) editRoot.lookup("#txtSignInCellPhone");
			TextField txtSignInHomePhoneEdit = (TextField) editRoot.lookup("#txtSignInHomePhone");
			PasswordField txtSignInPwEdit = (PasswordField) editRoot.lookup("#txtSignInPw");
			PasswordField txtSignInPwConfirmEdit = (PasswordField) editRoot.lookup("#txtSignInPwConfirm");
			Button btnIdDuplicateEdit = (Button) editRoot.lookup("#btnIdDuplicate");
			Button btnSignInOkEdit = (Button) editRoot.lookup("#btnSignInOk");
			ImageView imgIconEdit = (ImageView) editRoot.lookup("#imgIcon");
			Label lbPwConditionMatchEdit = (Label) editRoot.lookup("#lbPwConditionMatch");
			// ��۱׷�2���� ���ϰ�
			RadioButton rbUserFemaleEdit = (RadioButton) editRoot.lookup("#rbUserFemale");
			RadioButton rbUserMaleEdit = (RadioButton) editRoot.lookup("#rbUserMale");
			RadioButton rbAdultEdit = (RadioButton) editRoot.lookup("#rbAdult");
			RadioButton rbChildEdit = (RadioButton) editRoot.lookup("#rbChild");
			ComboBox<String> cbxUserBirthYearEdit = (ComboBox<String>) editRoot.lookup("#cbxUserBirthYear");
			ComboBox<String> cbxUserBirthMonthEdit = (ComboBox<String>) editRoot.lookup("#cbxUserBirthMonth");
			ComboBox<String> cbxUserBirthDayEdit = (ComboBox<String>) editRoot.lookup("#cbxUserBirthDay");
			ComboBox<String> cbxSignInEmailEdit = (ComboBox<String>) editRoot.lookup("#cbxSignInEmail");

			// �����Ұ�
			txtSignInIdEdit.setDisable(true);
			txtSignInNameEdit.setDisable(true);
			btnIdDuplicateEdit.setDisable(true);
			rbUserFemaleEdit.setDisable(true);
			rbUserMaleEdit.setDisable(true);
			cbxUserBirthYearEdit.setDisable(true);
			cbxUserBirthMonthEdit.setDisable(true);
			cbxUserBirthDayEdit.setDisable(true);
			rbAdultEdit.setDisable(true);
			rbChildEdit.setDisable(true);
			txtSignInEmailEdit.setDisable(true);
			cbxSignInEmailEdit.setDisable(true);

			txtSignInPwEdit.setDisable(false);
			txtSignInPwConfirmEdit.setDisable(false);

			txtSignInHomePhoneEdit.setDisable(false);
			txtSignInCellPhoneEdit.setDisable(false);

			cbxSignInEmailEdit.setItems(
					FXCollections.observableArrayList("gmail.com", "naver.com", "daum.net", "hotmail.com", "nate.com"));

			// ���� ������ ����� ���̵� ��������
			txtSignInIdEdit.setText(currentUserId);
			// �ش� ���̵��� ���� ��������
			ArrayList<UserVO> list = null;
			userDAO = new UserDAO();
			list = UserDAO.getCurrentUserInfo(currentUserId);
			if (list == null) {
				CommonFunc.alertDisplay(1, "�α����� ȸ�� ����Ʈ��������", "DB�������� ����", "���� ���");
				return;
			} else {
				for (UserVO uvo : list) {
					System.out.println(uvo.toString()+"�׽�Ʈ��");
				}
			}
			// �̹���
			String fileName = list.get(0).getUserSignday();
			selectedFile = new File("C:/images/" + fileName);
			if (selectedFile != null) {
				// �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
				localImage = new Image(localUrl, false);
				imgIconEdit.setImage(localImage);
				imgIconEdit.setFitHeight(150);
				imgIconEdit.setFitWidth(150);
			} else {
				imageViewInit();
			}
			// �̸�
			txtSignInNameEdit.setText(list.get(0).getUserName());
			// ���̵�
			txtSignInIdEdit.setText(list.get(0).getUserId());
			// ����
			if (list.get(0).getUserGender().equals("����")) {
				rbUserFemaleEdit.setSelected(true);
			} else if (list.get(0).getUserGender().equals("����")) {
				rbUserMaleEdit.setSelected(true);
			}
			// ���ο���
			if (list.get(0).getUserAdult().equals("��")) {
				rbAdultEdit.setSelected(true);
			} else if (list.get(0).getUserAdult().equals("�ƴϿ�")) {
				rbChildEdit.setSelected(true);
			}
			// �޴���
			txtSignInCellPhoneEdit.setText(list.get(0).getUserPhone());
			// ������ȭ
			txtSignInHomePhoneEdit.setText(list.get(0).getUserHomePhone());
			// ������� �޺��ڽ�
			ArrayList<String> birthList = new ArrayList<String>();
			for (int years = 1920; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
				birthList.add(years + "");
			}
			cbxUserBirthYearEdit.setItems(FXCollections.observableArrayList(birthList));
			cbxUserBirthMonthEdit.setItems(
					FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
			cbxUserBirthDayEdit.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9",
					"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
					"26", "27", "28", "29", "30", "31")); // �����ڷ� �ٷ� �־����
			String birth = list.get(0).getUserBirth();
			String year = birth.split("-")[0];
			String month = birth.split("-")[1];
			String day = birth.split("-")[2];
			cbxUserBirthYearEdit.setValue(year);
			cbxUserBirthMonthEdit.setValue(month);
			cbxUserBirthDayEdit.setValue(day);
			// �̸���
			cbxSignInEmailEdit.setItems(
					FXCollections.observableArrayList("gmail.com", "naver.com", "daum.net", "hotmail.com", "nate.com"));
			txtSignInEmailEdit.setText(list.get(0).getUserEmail());
			String eMail = list.get(0).getUserEmail();
			int idx2 = eMail.indexOf("@");
			String front = eMail.substring(0, idx2);
			String back = eMail.substring(idx2 + 1);
			txtSignInEmailEdit.setText(front);
			cbxSignInEmailEdit.setValue(back);

			// ��ư ����Ȯ�� �̺�Ʈ ó��
			btnSignInOkEdit.setOnAction(e3 -> {
				try {
					if (txtSignInPwEdit.getText().equals("") || txtSignInPwConfirmEdit.getText().equals("")
							|| txtSignInCellPhoneEdit.getText().equals("")) {
						CommonFunc.alertDisplay(1, "��������", "�ʼ��׸� ���Է�", "�����Ϸ��� PW�� �Է��Ͻÿ�.");
					} else if (!txtSignInPwConfirmEdit.getText().equals(txtSignInPwEdit.getText())) {
						lbPwConditionMatchEdit.setText("������ ��й�ȣ�� �Է����ּ���.");
						lbPwConditionMatchEdit.setStyle("-fx-text-fill: red");
						CommonFunc.alertDisplay(1, "PW ����", "PW ����ġ ����", "PW�� �����ϰ� �Է��Ͻÿ�.");
					} else {
						// ������ ���� �����ϱ�
						// ���� ������ ���̵� ���
						LoginController lc = new LoginController();
						String currentUserId = lc.getTxtUserId();
						UserVO uvo = new UserVO(txtSignInCellPhoneEdit.getText(), txtSignInHomePhoneEdit.getText(),txtSignInPwEdit.getText());
						UserDAO userDAO = new UserDAO();
						UserVO userVO = UserDAO.updateUser(uvo);
					} // end of else
				} catch (Exception e) {
					stageDialog.close();
				}
			});
			Scene scene = new Scene(editRoot);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e1) {
			CommonFunc.alertDisplay(1, "���� ����", "������ ���� ����", "�������ʵ带 Ȯ���� �ּ���." + e1.getStackTrace());
		}
	}

	// �󼼿�ݹ�ư
	private void handlerBtnDetailChargeAction(ActionEvent event) {
		Parent searchView = null;
		Stage stage = null;
		try {
			searchView = FXMLLoader.load(getClass().getResource("/view/DetailCharge.fxml"));
			Scene scene = new Scene(searchView);
			stage = new Stage();
			stage.setTitle("�󼼿��");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(3, "�󼼿��â ����", "â�� �ҷ��� �� �����ϴ�.", e.toString());
		}
	}

	// �ڸ��̵���ư
	private void handlerBtnSeatChangeAction(ActionEvent event) {
		CommonFunc.alertDisplay(2, "�ڸ��̵�", "", "������� �� �ٸ� PC���� �α����ϼ���.");
	}

	// ��������ư
	private void handlerBtnUserLogoutAction(ActionEvent event) {
		os.println("EXIT " + currentUserId);
		os.flush();
		logout = false;
		if (logout == false) {
			try {
				os.close();
				socket.close();
				System.out.println("�������� " + socket.isConnected());
				System.out.println("���ϴ����� " + socket.isClosed());

			} catch (IOException e) {
				e.getStackTrace();
			} finally {
				logout = true;
			}
		}

		((Stage) btnUserLogout.getScene().getWindow()).close();
		Parent searchView = null;
		Stage stage = null;
		try {
			searchView = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(searchView);
			stage = new Stage();
			stage.setTitle("�α���");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "�α���â �θ��� ����", "�α���â�� �ҷ��� �� �����ϴ�.", "�ٽ� �õ��Ͻʽÿ�.");
		}
	}

	// ��ǰ�ֹ���ư
	private void handlerBtnOrderAction(ActionEvent event) {
		Parent searchView = null;
		Stage stage = null;
		try {
			searchView = FXMLLoader.load(getClass().getResource("/view/ItemOrder.fxml"));
			Scene scene = new Scene(searchView);
			stage = new Stage();
			stage.setTitle("��ǰ�ֹ�â");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(3, "��ǰ�ֹ�â ����", "��ǰ�ֹ�â�� �ҷ��� �� �����ϴ�.", "�ٽ� �õ����ּ���.");
		}
	}

	// �⺻ �̹��� ����
	public void imageViewInit() {
		localUrl = "/images/profile.png";
		localImage = new Image(localUrl, false);
		imgIcon.setImage(localImage);
	}

}

package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.UserVO;

public class SignInController implements Initializable {

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
	Button btnImgRegister;
	@FXML
	Button btnSignInOk;
	@FXML
	Button btnSignInExit;
	@FXML
	Label lbPwCondition;
	@FXML
	Label lbPwConditionMatch;
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
	// �̹��� ���� ���
	private String localUrl = "";
	private Image localImage;
	private File selectedFile = null;
	private String selectFileName = ""; // �̹��� ���ϸ�
	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/images");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;
	Boolean userStatus = false;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnIdDuplicate.setOnAction(event -> handlerBtnIdDuplicateAction(event));
		btnSignInOk.setOnAction(event -> handlerBtnSignInOkAction(event));
		btnImgRegister.setOnAction(event -> handlerBtnImgRegisterAction(event));
		btnSignInExit.setOnAction(event -> handlerBtnSignInExitAction(event));
		txtSignInPwConfirm.setOnKeyPressed(e -> handlertxtSignInPwConfirm(e));
		txtSignInPwConfirm.setOnMouseClicked(e -> lbPwConditionMatch.setText(null));
		imageViewInit();
		// ������� ����
		birthInit();
		// ��ȭ��ȣ ��������
		cellFormat();
		imageSave(file);
		// �ּ� �޺��ڽ�
		cbxSignInEmail.setItems(
				FXCollections.observableArrayList("gmail.com", "naver.com", "daum.net", "hotmail.com", "nate.com"));
	}

	// �н����� ��ġ ����
	private void handlertxtSignInPwConfirm(KeyEvent e) {
		if (txtSignInPwConfirm.getText().equals(txtSignInPw.getText())) {
			lbPwConditionMatch.setText("��й�ȣ�� ��ġ�մϴ�");
			lbPwConditionMatch.setStyle("-fx-text-fill: green");
		} else if (!txtSignInPwConfirm.getText().equals(txtSignInPw.getText())) {
			lbPwConditionMatch.setText("������ ��й�ȣ�� �Է����ּ���.");
			// CommonFunc.alertDisplay(1, "PW ����", "PW ����ġ ����", "PW�� �����ϰ� �Է��Ͻÿ�.");
			lbPwConditionMatch.setStyle("-fx-text-fill: red");
		}
	}

	// ��ȭ��ȣ ��������
	private void cellFormat() {
		DecimalFormat format = new DecimalFormat("##############");

		txtSignInCellPhone.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 12) {
				return null;
			} else {
				return event;
			}
		}));

		txtSignInHomePhone.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 16) {
				return null;
			} else {
				return event;
			}
		}));

	}

	// ������� ����
	private void birthInit() {
		// �� �޺��ڽ�����
		cbxUserBirthDay.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31"));
		// �� �޺��ڽ�����
		cbxUserBirthMonth.setItems(
				FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
		// �⵵ �޺��ڽ�����
		ArrayList<String> yearss = new ArrayList<String>();
		for (int years = 1900; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
			yearss.add(years + "");
		}
		cbxUserBirthYear.setItems(FXCollections.observableArrayList(yearss));
	}

	// ��ҹ�ư
	private void handlerBtnSignInExitAction(ActionEvent event) {
		Platform.exit();
	}

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

	// Ȯ�ι�ư, ���ǿ���Ȯ��
	private void handlerBtnSignInOkAction(ActionEvent event) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LLLL-dd");
		String formattedString = localDate.format(formatter);

		userStatus = true;

		// �ʼ��Է��׸� ���Է½� ����
		if (txtSignInId.getText().isEmpty() || txtSignInName.getText().isEmpty() || txtSignInPw.getText().isEmpty()
				|| txtSignInPwConfirm.getText().isEmpty() || txtSignInCellPhone.getText().isEmpty()
				|| cbxUserBirthYear.getValue().isEmpty() || cbxUserBirthMonth.getValue().isEmpty()
				|| cbxUserBirthDay.getValue().isEmpty() || cbxSignInEmail.getValue().isEmpty()
				|| txtSignInEmail.getText().isEmpty()) {
			CommonFunc.alertDisplay(1, "�ʼ��׸� ���Է�", "�ʼ��׸��Է��ʿ�", "�ʼ��׸��� ��� �Է��Ͻÿ�.");
		} else {
			// ȸ������ ������ �Ϻ��ϸ� ���� ����â���� �̵��Ѵ�.
			// ����â ������ UserVO, UserDAO�� ������ �ְ� �̰� ����Ÿ���̽��� �����ؼ� ���� �ֱ�
			String userNameInfo = txtSignInName.getText();
			String userIdInfo = txtSignInId.getText();
			String userPwInfo = txtSignInPw.getText();
			String userGenderInfo = "";
			if (rbUserFemale.isSelected()) {
				userGenderInfo = rbUserFemale.getText();
			} else if (rbUserMale.isSelected()) {
				userGenderInfo = rbUserMale.getText();
			}
			String userPhoneInfo = txtSignInCellPhone.getText();
			String userHomePhoneInfo = txtSignInHomePhone.getText();
			if (userHomePhoneInfo == null) {
				userHomePhoneInfo = "";
			}
			String userBirthInfo = cbxUserBirthYear.getValue() + "-" + cbxUserBirthMonth.getValue() + "-"
					+ cbxUserBirthDay.getValue();
			String userEmailInfo = null;
			if (txtSignInEmail == null) {
				userEmailInfo = "";
			} else {
				userEmailInfo = txtSignInEmail.getText() + "@" + cbxSignInEmail.getValue();
			}
			String userAdultInfo = "";
			if (rbAdult.isSelected()) {
				userAdultInfo = rbAdult.getText();
			} else if (rbChild.isSelected()) {
				userAdultInfo = rbChild.getText();
			}
			String userSignDay = formattedString;
			String userStatusInfo = "";
			if (this.userStatus == true) {
				userStatusInfo = "ON";
			} else {
				userStatusInfo = "OFFLINE";
			}
			// �̹�������//////////////////////
			File dirMake = new File(dirSave.getAbsolutePath());
			// �̹��� ���� ���� ����
			if (!dirMake.exists()) {
				dirMake.mkdir();
			}
			// �̹��� ���� ����
			String fileName = imageSave(selectedFile);
			System.out.println("imageFileName= " + fileName);

			UserVO uvo = new UserVO(userNameInfo, userIdInfo, userPwInfo, userGenderInfo, userPhoneInfo,
					userHomePhoneInfo, userBirthInfo, userEmailInfo, userAdultInfo, userSignDay, userStatusInfo,
					fileName);

			// DB �θ��� ���
			UserDAO userDAO = new UserDAO();
			int count = 0;
			// �����ͺ��̽� ���̺� ���� �Է��ϴ� ����
			try {
				count = UserDAO.getUserRegister(uvo);
			} catch (Exception e1) {
				CommonFunc.alertDisplay(1, "SignInController ����", "handlerBtnSignInOkAction ����", e1.toString());
			}
			 ((Stage) btnSignInOk.getScene().getWindow()).close();

			/*
			 * Parent mainView = null; Stage mainStage = null; try { mainView =
			 * FXMLLoader.load(getClass().getResource("/view/Login.fxml")); Scene scene =
			 * new Scene(mainView); mainStage = new Stage(); mainStage.setScene(scene);
			 * mainStage.setScene(scene); mainStage.setTitle("����� ����");
			 * mainStage.setResizable(false); mainStage.show(); // ������ ���������� �ݰ� ���ο� â�� ����
			 * ((Stage) btnSignInOk.getScene().getWindow()).close(); throw new
			 * Exception("ȸ�����ԿϷ�"); } catch (Exception e) { // CommonFunc.alertDisplay(1,
			 * "����â �θ��� ����", "����â �θ��⸦ �����Ͽ����ϴ�.", "����â�� �� �� �����ϴ�.");
			 * CommonFunc.alertDisplay(1, "ȸ�� ���� ����", "ȸ������ ���ԵǾ����ϴ�.", "ȯ���մϴ�!"); }
			 */
		}

	}

	// �⺻ �̹��� ����
	public void imageViewInit() {
		localUrl = "/images/person.png";
		localImage = new Image(localUrl, false);
		imgIcon.setImage(localImage);
	}

	// ����� �̹��� ��� ��ư
	private void handlerBtnImgRegisterAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnSignInOk.getScene().getWindow());
			if (selectedFile != null) {
				// �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e2) {
			CommonFunc.alertDisplay(5, "�̹��� ��� ���� �߻�", "�߸��� ������ URL�߻�", "�ٽ� �õ��Ͻʽÿ�.");
		}
		localImage = new Image(localUrl, false);
		imgIcon.setImage(localImage);
		imgIcon.setFitHeight(150);
		imgIcon.setFitWidth(150);
		btnSignInOk.setDisable(false);

		if (selectedFile != null) {
			selectFileName = selectedFile.getName();
		}
	}

	// ���̵� �ߺ�Ȯ��
	private void handlerBtnIdDuplicateAction(ActionEvent event) {
		try {
			String udo = UserDAO.getUserIdDuplicateCheck(txtSignInId.getText());
			if (txtSignInId.getText().equals("")) {
				CommonFunc.alertDisplay(1, "���̵� �Է¹ٶ��ϴ�.", "���̵� ����.", "���̵� �Է��ϼ���.");
			} else if (udo.equals(txtSignInId.getText())) {
				CommonFunc.alertDisplay(1, "���̵� �Է¿���", "�̹� ������� ���̵��Դϴ�.", "�ٸ� ���̵� �Է����ּ���");
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "���̵� ��������", "��� ������ ���̵��Դϴ�.", "ȸ�������� ��� �����ϼ���.");
		}
	}
}

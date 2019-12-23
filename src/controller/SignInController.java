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
	// 이미지 파일 경로
	private String localUrl = "";
	private Image localImage;
	private File selectedFile = null;
	private String selectFileName = ""; // 이미지 파일명
	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C:/images");
	// 이미지 불러올 파일을 저장할 파일 객체 선언
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
		// 생년월일 세팅
		birthInit();
		// 전화번호 숫자형식
		cellFormat();
		imageSave(file);
		// 주소 콤보박스
		cbxSignInEmail.setItems(
				FXCollections.observableArrayList("gmail.com", "naver.com", "daum.net", "hotmail.com", "nate.com"));
	}

	// 패스워드 일치 여부
	private void handlertxtSignInPwConfirm(KeyEvent e) {
		if (txtSignInPwConfirm.getText().equals(txtSignInPw.getText())) {
			lbPwConditionMatch.setText("비밀번호가 일치합니다");
			lbPwConditionMatch.setStyle("-fx-text-fill: green");
		} else if (!txtSignInPwConfirm.getText().equals(txtSignInPw.getText())) {
			lbPwConditionMatch.setText("동일한 비밀번호를 입력해주세요.");
			// CommonFunc.alertDisplay(1, "PW 오류", "PW 불일치 오류", "PW를 동일하게 입력하시오.");
			lbPwConditionMatch.setStyle("-fx-text-fill: red");
		}
	}

	// 전화번호 숫자형식
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

	// 생년월일 세팅
	private void birthInit() {
		// 일 콤보박스세팅
		cbxUserBirthDay.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31"));
		// 월 콤보박스세팅
		cbxUserBirthMonth.setItems(
				FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
		// 년도 콤보박스세팅
		ArrayList<String> yearss = new ArrayList<String>();
		for (int years = 1900; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
			yearss.add(years + "");
		}
		cbxUserBirthYear.setItems(FXCollections.observableArrayList(yearss));
	}

	// 취소버튼
	private void handlerBtnSignInExitAction(ActionEvent event) {
		Platform.exit();
	}

	// 이미지 저장 메소드
	private String imageSave(File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// 이미지 파일명 생성
			fileName = "User" + System.currentTimeMillis() + "_" + file.getName();
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));

			// 선택한 이미지 파일 InputStream의 마지막에 이르렀을 경우는 -1
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

	// 확인버튼, 조건여부확인
	private void handlerBtnSignInOkAction(ActionEvent event) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LLLL-dd");
		String formattedString = localDate.format(formatter);

		userStatus = true;

		// 필수입력항목 미입력시 오류
		if (txtSignInId.getText().isEmpty() || txtSignInName.getText().isEmpty() || txtSignInPw.getText().isEmpty()
				|| txtSignInPwConfirm.getText().isEmpty() || txtSignInCellPhone.getText().isEmpty()
				|| cbxUserBirthYear.getValue().isEmpty() || cbxUserBirthMonth.getValue().isEmpty()
				|| cbxUserBirthDay.getValue().isEmpty() || cbxSignInEmail.getValue().isEmpty()
				|| txtSignInEmail.getText().isEmpty()) {
			CommonFunc.alertDisplay(1, "필수항목 미입력", "필수항목입력필요", "필수항목을 모두 입력하시오.");
		} else {
			// 회원가입 조건이 완벽하면 다음 메인창으로 이동한다.
			// 메인창 연결후 UserVO, UserDAO에 정보를 넣고 이걸 데이타베이스에 연결해서 정보 넣기
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
			// 이미지저장//////////////////////
			File dirMake = new File(dirSave.getAbsolutePath());
			// 이미지 저장 폴더 생성
			if (!dirMake.exists()) {
				dirMake.mkdir();
			}
			// 이미지 파일 저장
			String fileName = imageSave(selectedFile);
			System.out.println("imageFileName= " + fileName);

			UserVO uvo = new UserVO(userNameInfo, userIdInfo, userPwInfo, userGenderInfo, userPhoneInfo,
					userHomePhoneInfo, userBirthInfo, userEmailInfo, userAdultInfo, userSignDay, userStatusInfo,
					fileName);

			// DB 부르는 명령
			UserDAO userDAO = new UserDAO();
			int count = 0;
			// 데이터베이스 테이블에 값을 입력하는 순간
			try {
				count = UserDAO.getUserRegister(uvo);
			} catch (Exception e1) {
				CommonFunc.alertDisplay(1, "SignInController 오류", "handlerBtnSignInOkAction 오류", e1.toString());
			}
			 ((Stage) btnSignInOk.getScene().getWindow()).close();

			/*
			 * Parent mainView = null; Stage mainStage = null; try { mainView =
			 * FXMLLoader.load(getClass().getResource("/view/Login.fxml")); Scene scene =
			 * new Scene(mainView); mainStage = new Stage(); mainStage.setScene(scene);
			 * mainStage.setScene(scene); mainStage.setTitle("사용자 메인");
			 * mainStage.setResizable(false); mainStage.show(); // 현재의 스테이지를 닫고 새로운 창을 연다
			 * ((Stage) btnSignInOk.getScene().getWindow()).close(); throw new
			 * Exception("회원가입완료"); } catch (Exception e) { // CommonFunc.alertDisplay(1,
			 * "메인창 부르기 오류", "메인창 부르기를 실패하였습니다.", "메인창을 열 수 없습니다.");
			 * CommonFunc.alertDisplay(1, "회원 가입 성공", "회원으로 가입되었습니다.", "환영합니다!"); }
			 */
		}

	}

	// 기본 이미지 세팅
	public void imageViewInit() {
		localUrl = "/images/person.png";
		localImage = new Image(localUrl, false);
		imgIcon.setImage(localImage);
	}

	// 사용자 이미지 등록 버튼
	private void handlerBtnImgRegisterAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnSignInOk.getScene().getWindow());
			if (selectedFile != null) {
				// 이미지 파일 경로
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e2) {
			CommonFunc.alertDisplay(5, "이미지 등록 오류 발생", "잘못된 형식의 URL발생", "다시 시도하십시오.");
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

	// 아이디 중복확인
	private void handlerBtnIdDuplicateAction(ActionEvent event) {
		try {
			String udo = UserDAO.getUserIdDuplicateCheck(txtSignInId.getText());
			if (txtSignInId.getText().equals("")) {
				CommonFunc.alertDisplay(1, "아이디 입력바랍니다.", "아이디 공백.", "아이디를 입력하세요.");
			} else if (udo.equals(txtSignInId.getText())) {
				CommonFunc.alertDisplay(1, "아이디 입력오류", "이미 사용중인 아이디입니다.", "다른 아이디를 입력해주세요");
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "아이디 생성가능", "사용 가능한 아이디입니다.", "회원가입을 계속 진행하세요.");
		}
	}
}

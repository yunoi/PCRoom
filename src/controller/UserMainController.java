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

	//// 2019-10-22 회원정보를 보기 위해 회원가입루트컨트롤러의 변수이름들 가져옴
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
	// 소켓통신관련변수
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

	// 이미지 파일 경로
	private String localUrl = "";
	private Image localImage;
	private File selectedFile = null;
	private String selectFileName = ""; // 이미지 파일명
	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C:/images");
	// 이미지 불러올 파일을 저장할 파일 객체 선언
	private File file = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		connect(); // 서버와 연결

		btnOrder.setOnAction(event -> handlerBtnOrderAction(event));
		btnUserLogout.setOnAction(event -> handlerBtnUserLogoutAction(event));
		btnSeatChange.setOnAction(event -> handlerBtnSeatChangeAction(event));
		btnDetailCharge.setOnAction(event -> handlerBtnDetailChargeAction(event));
		btnInfo.setOnAction(event -> handlerBtnInfoAction(event));
		imageSave(file);

		onUserName.setText(currentUserId); // 현재 이용자
		lbSeatNo.setText(String.valueOf(currentSeatNo)); // 현재 좌석
	}

	public void connect() {
		ChargeDAO chargeDAO = new ChargeDAO();
		prepaidMoney = chargeDAO.selectLabelNameTime(currentUserId);
	

		try {
			socket = new Socket("127.0.0.1", 9999);
			os = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("서버에 연결하였습니다.");
		} catch (IOException e1) {
			System.out.println("서버연결오류");
			e1.printStackTrace();
		}
//		prepaidMoney = 2000; // 디버깅

		// 남은 시간 표시
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
							CommonFunc.alertDisplay(1, "오류", "X", "X");
						} else {
							if (currentUserId == null) {
								CommonFunc.alertDisplay(1, "오류", "X", "X");
							} else {
								for (i = countdownSeconds; i >= 0; i--) {

									Thread.sleep(1000);

									Platform.runLater(() -> {
										lbAvailableTime.setText(
												String.valueOf(i / 3600 + ":" + i % 3600 / 60 + ":" + i % 3600 % 60));
										lbPrepaidMoney.setText(prepaidMoney + "원");
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

	// 회원정보수정
	private void handlerBtnInfoAction(ActionEvent event) {
		try {
			Parent editRoot = null;
			editRoot = FXMLLoader.load(getClass().getResource("/view/UserInfo.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnOrder.getScene().getWindow());
			stageDialog.setTitle("회원정보수정");

			// 21개(textField 5개, passwordField 2개, Button 2개, ImageView 1개, ToggleGroup 2개)
			// RadioButton 4개, ComboBox 4개, Lable 1개
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
			// 토글그룹2개는 안하고
			RadioButton rbUserFemaleEdit = (RadioButton) editRoot.lookup("#rbUserFemale");
			RadioButton rbUserMaleEdit = (RadioButton) editRoot.lookup("#rbUserMale");
			RadioButton rbAdultEdit = (RadioButton) editRoot.lookup("#rbAdult");
			RadioButton rbChildEdit = (RadioButton) editRoot.lookup("#rbChild");
			ComboBox<String> cbxUserBirthYearEdit = (ComboBox<String>) editRoot.lookup("#cbxUserBirthYear");
			ComboBox<String> cbxUserBirthMonthEdit = (ComboBox<String>) editRoot.lookup("#cbxUserBirthMonth");
			ComboBox<String> cbxUserBirthDayEdit = (ComboBox<String>) editRoot.lookup("#cbxUserBirthDay");
			ComboBox<String> cbxSignInEmailEdit = (ComboBox<String>) editRoot.lookup("#cbxSignInEmail");

			// 수정불가
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

			// 현재 접속한 사용자 아이디 가져오기
			txtSignInIdEdit.setText(currentUserId);
			// 해당 아이디의 정보 가져오기
			ArrayList<UserVO> list = null;
			userDAO = new UserDAO();
			list = UserDAO.getCurrentUserInfo(currentUserId);
			if (list == null) {
				CommonFunc.alertDisplay(1, "로그인한 회원 리스트가져오기", "DB가져오기 오류", "점검 요망");
				return;
			} else {
				for (UserVO uvo : list) {
					System.out.println(uvo.toString()+"테스트중");
				}
			}
			// 이미지
			String fileName = list.get(0).getUserSignday();
			selectedFile = new File("C:/images/" + fileName);
			if (selectedFile != null) {
				// 이미지 파일 경로
				localUrl = selectedFile.toURI().toURL().toString();
				localImage = new Image(localUrl, false);
				imgIconEdit.setImage(localImage);
				imgIconEdit.setFitHeight(150);
				imgIconEdit.setFitWidth(150);
			} else {
				imageViewInit();
			}
			// 이름
			txtSignInNameEdit.setText(list.get(0).getUserName());
			// 아이디
			txtSignInIdEdit.setText(list.get(0).getUserId());
			// 성별
			if (list.get(0).getUserGender().equals("여자")) {
				rbUserFemaleEdit.setSelected(true);
			} else if (list.get(0).getUserGender().equals("남자")) {
				rbUserMaleEdit.setSelected(true);
			}
			// 성인여부
			if (list.get(0).getUserAdult().equals("예")) {
				rbAdultEdit.setSelected(true);
			} else if (list.get(0).getUserAdult().equals("아니오")) {
				rbChildEdit.setSelected(true);
			}
			// 휴대폰
			txtSignInCellPhoneEdit.setText(list.get(0).getUserPhone());
			// 자택전화
			txtSignInHomePhoneEdit.setText(list.get(0).getUserHomePhone());
			// 생년월일 콤보박스
			ArrayList<String> birthList = new ArrayList<String>();
			for (int years = 1920; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
				birthList.add(years + "");
			}
			cbxUserBirthYearEdit.setItems(FXCollections.observableArrayList(birthList));
			cbxUserBirthMonthEdit.setItems(
					FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
			cbxUserBirthDayEdit.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9",
					"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
					"26", "27", "28", "29", "30", "31")); // 생성자로 바로 넣어버림
			String birth = list.get(0).getUserBirth();
			String year = birth.split("-")[0];
			String month = birth.split("-")[1];
			String day = birth.split("-")[2];
			cbxUserBirthYearEdit.setValue(year);
			cbxUserBirthMonthEdit.setValue(month);
			cbxUserBirthDayEdit.setValue(day);
			// 이메일
			cbxSignInEmailEdit.setItems(
					FXCollections.observableArrayList("gmail.com", "naver.com", "daum.net", "hotmail.com", "nate.com"));
			txtSignInEmailEdit.setText(list.get(0).getUserEmail());
			String eMail = list.get(0).getUserEmail();
			int idx2 = eMail.indexOf("@");
			String front = eMail.substring(0, idx2);
			String back = eMail.substring(idx2 + 1);
			txtSignInEmailEdit.setText(front);
			cbxSignInEmailEdit.setValue(back);

			// 버튼 수정확인 이벤트 처리
			btnSignInOkEdit.setOnAction(e3 -> {
				try {
					if (txtSignInPwEdit.getText().equals("") || txtSignInPwConfirmEdit.getText().equals("")
							|| txtSignInCellPhoneEdit.getText().equals("")) {
						CommonFunc.alertDisplay(1, "수정오류", "필수항목 미입력", "수정하려는 PW를 입력하시오.");
					} else if (!txtSignInPwConfirmEdit.getText().equals(txtSignInPwEdit.getText())) {
						lbPwConditionMatchEdit.setText("동일한 비밀번호를 입력해주세요.");
						lbPwConditionMatchEdit.setStyle("-fx-text-fill: red");
						CommonFunc.alertDisplay(1, "PW 오류", "PW 불일치 오류", "PW를 동일하게 입력하시오.");
					} else {
						// 수정한 정보 저장하기
						// 현재 접속자 아이디 얻기
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
			CommonFunc.alertDisplay(1, "수정 실패", "데이터 수정 실패", "데이터필드를 확인해 주세요." + e1.getStackTrace());
		}
	}

	// 상세요금버튼
	private void handlerBtnDetailChargeAction(ActionEvent event) {
		Parent searchView = null;
		Stage stage = null;
		try {
			searchView = FXMLLoader.load(getClass().getResource("/view/DetailCharge.fxml"));
			Scene scene = new Scene(searchView);
			stage = new Stage();
			stage.setTitle("상세요금");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(3, "상세요금창 오류", "창을 불러올 수 없습니다.", e.toString());
		}
	}

	// 자리이동버튼
	private void handlerBtnSeatChangeAction(ActionEvent event) {
		CommonFunc.alertDisplay(2, "자리이동", "", "사용종료 후 다른 PC에서 로그인하세요.");
	}

	// 사용종료버튼
	private void handlerBtnUserLogoutAction(ActionEvent event) {
		os.println("EXIT " + currentUserId);
		os.flush();
		logout = false;
		if (logout == false) {
			try {
				os.close();
				socket.close();
				System.out.println("연결끊겼니 " + socket.isConnected());
				System.out.println("소켓닫혔니 " + socket.isClosed());

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
			stage.setTitle("로그인");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(1, "로그인창 부르기 오류", "로그인창을 불러올 수 없습니다.", "다시 시도하십시오.");
		}
	}

	// 상품주문버튼
	private void handlerBtnOrderAction(ActionEvent event) {
		Parent searchView = null;
		Stage stage = null;
		try {
			searchView = FXMLLoader.load(getClass().getResource("/view/ItemOrder.fxml"));
			Scene scene = new Scene(searchView);
			stage = new Stage();
			stage.setTitle("상품주문창");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch (IOException e) {
			CommonFunc.alertDisplay(3, "상품주문창 오류", "상품주문창을 불러올 수 없습니다.", "다시 시도해주세요.");
		}
	}

	// 기본 이미지 셋팅
	public void imageViewInit() {
		localUrl = "/images/profile.png";
		localImage = new Image(localUrl, false);
		imgIcon.setImage(localImage);
	}

}

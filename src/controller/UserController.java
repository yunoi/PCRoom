package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.AdminVO;
import model.ChargeVO;
import model.UserVO;

public class UserController implements Initializable {
	@FXML
	private TableView<UserVO> tableViewUserDB;
	@FXML
	private Button btnCharge;
	@FXML
	private Button btnUserDelete;
	@FXML
	private Button btnUserSearch;
	@FXML
	private TextField txtUserSearch;
	private ObservableList<UserVO> userData;
	private int selectedUserIndex;
	private ObservableList<UserVO> selectedUser;

	private boolean editDelete = false;
	private LocalTime t;
	private int hour;
	private int minute;
	private int second;
	private int i;
	private int countdownSeconds;
	private ChargeDAO chargeDAO; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 회원 정보 테이블 세팅
		userTableViewSettings();
		// 회원 삭제
		btnUserDelete.setOnAction(event -> {
			handlerUserDeleteAction(event);
		});

		// 유저 선택 후 요금 충전
		tableViewUserDB.setOnMouseClicked(event -> {
			handlerUserChargeAction(event);
		});
		// 회원 검색
		btnUserSearch.setOnAction(event -> {
			handlerUserSearchAction(event);
		});
	}

	// 회원 테이블 관리
	public void userTableViewSettings() {
		userData = FXCollections.observableArrayList();
		tableViewUserDB.setEditable(true);

		//
		TableColumn colUserName = new TableColumn("아이디");
		colUserName.setPrefWidth(100);
		colUserName.setStyle("-fx-alignment: CENTER;");
		colUserName.setCellValueFactory(new PropertyValueFactory<>("userId"));
		//
		TableColumn colUserId = new TableColumn("이름");
		colUserId.setPrefWidth(120);
		colUserId.setStyle("-fx-alignment: CENTER;");
		colUserId.setCellValueFactory(new PropertyValueFactory<>("userName"));
		//
		TableColumn colUserGender = new TableColumn("성별");
		colUserGender.setPrefWidth(100);
		colUserGender.setStyle("-fx-alignment: CENTER;");
		colUserGender.setCellValueFactory(new PropertyValueFactory<>("userGender"));
		//
		TableColumn colUserBirth = new TableColumn("생년월일");
		colUserBirth.setPrefWidth(150);
		colUserBirth.setStyle("-fx-alignment: CENTER;");
		colUserBirth.setCellValueFactory(new PropertyValueFactory<>("userBirth"));
		//
		TableColumn colUserAdult = new TableColumn("성인여부");
		colUserAdult.setPrefWidth(150);
		colUserAdult.setStyle("-fx-alignment: CENTER;");
		colUserAdult.setCellValueFactory(new PropertyValueFactory<>("userAdult"));
		//
		TableColumn colUserPhone = new TableColumn("휴대폰");
		colUserPhone.setPrefWidth(150);
		colUserPhone.setStyle("-fx-alignment: CENTER;");
		colUserPhone.setCellValueFactory(new PropertyValueFactory<>("userPhone"));
		//
		TableColumn colUserHomePhone = new TableColumn("자택전화");
		colUserHomePhone.setPrefWidth(150);
		colUserHomePhone.setStyle("-fx-alignment: CENTER;");
		colUserHomePhone.setCellValueFactory(new PropertyValueFactory<>("userHomePhone"));
		// ----
		TableColumn colUserEmail = new TableColumn("이메일");
		colUserEmail.setPrefWidth(180);
		colUserEmail.setStyle("-fx-alignment: CENTER;");
		colUserEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		//
		TableColumn colUserSignday = new TableColumn("가입일");
		colUserSignday.setPrefWidth(100);
		colUserSignday.setStyle("-fx-alignment: CENTER;");
		colUserSignday.setCellValueFactory(new PropertyValueFactory<>("userSignday"));
		//
		TableColumn colUserImage = new TableColumn("이미지파일");
		colUserImage.setPrefWidth(100);
		colUserImage.setStyle("-fx-alignment: CENTER;");
		colUserImage.setCellValueFactory(new PropertyValueFactory<>("userImage"));
		//
		TableColumn colUserStatus = new TableColumn("이용상태");
		colUserStatus.setPrefWidth(100);
		colUserStatus.setStyle("-fx-alignment: CENTER;");
		colUserStatus.setCellValueFactory(new PropertyValueFactory<>("userStatus"));

		// 테이블설정 컬럼 객체들을 테이블뷰에 리스트 추가 및 항목 추가한다.
		tableViewUserDB.setItems(userData);
		tableViewUserDB.getColumns().addAll(colUserName, colUserId, colUserGender, colUserPhone, colUserEmail,
				colUserBirth, colUserAdult, colUserHomePhone, colUserSignday, colUserStatus, colUserImage);

		totalUserList();
	}

	// 회원 검색
	public void handlerUserSearchAction(ActionEvent event) {
		try {
			ArrayList<UserVO> list = new ArrayList<UserVO>();
			UserDAO userDAO = new UserDAO();
			list = userDAO.getUserSearch(txtUserSearch.getText());
//				System.out.println("list.size = " +list.size() );	// 디버깅부분
			if (list == null) {
				throw new Exception("검색 오류");
			}
			userData.removeAll(userData);
			for (UserVO uvo : list) {
				userData.add(uvo);
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "검색 결과", "검색 결과 없음", "검색하신 이름이 존재하지 않습니다." + e.toString());
		}
	}

	// 요금 충전
	private void handlerUserChargeAction(MouseEvent event) {
		try {
			editDelete = true;

			selectedUserIndex = tableViewUserDB.getSelectionModel().getSelectedIndex();
			selectedUser = tableViewUserDB.getSelectionModel().getSelectedItems();

			btnCharge.setOnAction(e -> {
				Parent charge;
				try {
					charge = FXMLLoader.load(getClass().getResource("/view/admin_charge.fxml"));

					Stage stageDialog = new Stage(StageStyle.UTILITY);
					stageDialog.initModality(Modality.WINDOW_MODAL);
					stageDialog.initOwner(btnCharge.getScene().getWindow());
					stageDialog.setTitle("요금충전");

					ComboBox cbxCharge = (ComboBox) charge.lookup("#cbxCharge");
					Button btnOk = (Button) charge.lookup("#btnOk");
					cbxCharge.setItems(FXCollections.observableArrayList("1시간 1,000원", "2시간 2,000원", "3시간 3,000원",
							"5시간 5,000원", "24시간(정액) 20,000원"));

					btnOk.setOnAction(e2 -> {
						try {
							String charged = cbxCharge.getValue().toString();
							int idx = charged.indexOf(" ");
							String paidMoney = charged.substring(idx + 1);
							SimpleDateFormat sdt = new SimpleDateFormat("HH:mm:ss");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date time = new Date();
							Date date = new Date();

							String time1 = sdt.format(time);
							String date1 = sdf.format(date);
							// 남은시간 가져오기
							chargeDAO = new ChargeDAO();
							int remainTime = chargeDAO.userRemainTime(selectedUser.get(0).getUserName());
							
							// 처음가입 최초충전
							if (paidMoney.equals("1,000원")) {
								int prepaidMoney = 1000;
								countdownSeconds = 3600;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
//								System.out.println(selectedUser.get(0).getUserName());
//								System.out.println(prepaidMoney);
//								System.out.println(countdownSeconds);
							} else if (paidMoney.equals("2,000원")) {
								int prepaidMoney = 2000;
								countdownSeconds = 7200;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
							} else if (paidMoney.equals("3,000원")) {
								int prepaidMoney = 3000;
								countdownSeconds = 10800;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
							} else if (paidMoney.equals("5,000원")) {
								int prepaidMoney = 5000;
								countdownSeconds = 18000;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
							} else if (paidMoney.equals("20,000원")) {
								int prepaidMoney = 20000;
								countdownSeconds = 86400;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
							}
							 // 추가충전
//							if (paidMoney.equals("1,000원")) {
//								int prepaidMoney = 1000;
//								countdownSeconds = 3600;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // 엔터넘버 테스트
//							} else if (paidMoney.equals("2,000원")) {
//								int prepaidMoney = 2000;
//								countdownSeconds = 7200;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // 엔터넘버 테스트
//							} else if (paidMoney.equals("3,000원")) {
//								int prepaidMoney = 3000;
//								countdownSeconds = 10800;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // 엔터넘버 테스트
//							} else if (paidMoney.equals("5,000원")) {
//								int prepaidMoney = 5000;
//								countdownSeconds = 18000;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // 엔터넘버 테스트
//							} else if (paidMoney.equals("20,000원")) {
//								int prepaidMoney = 20000;
//								countdownSeconds = 86400;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // 엔터넘버 테스트
//							}

						} catch (Exception e1) {
							CommonFunc.alertDisplay(1, "저장 실패", "요금 충전 실패", "다시 확인해 주세요." + e1.getStackTrace());
							}
						stageDialog.close();
					});

					Scene scene = new Scene(charge);
					stageDialog.setScene(scene);
					stageDialog.setResizable(false);
					stageDialog.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		} catch (Exception e) {
			editDelete = false;
		}

	}

	// 전체 사용자 데이터베이스 불러와서 표시
	public void totalUserList() {
		ArrayList<UserVO> list = null;
		UserDAO userDAO = new UserDAO();
		UserVO userVO = null;
		list = userDAO.getUserTotal();
		if (list == null) {
			CommonFunc.alertDisplay(1, "경고", "DB 가져오기 오류", "다시 한 번 점검해 주세요.");
			return;
		} else {
			for (int i = 0; i < list.size(); i++) {
				userVO = list.get(i);
				userData.add(userVO);
			}
		}
	}

	// 회원 삭제
	public void handlerUserDeleteAction(ActionEvent event) {
		try {
			editDelete = true;

			selectedUserIndex = tableViewUserDB.getSelectionModel().getSelectedIndex();
			selectedUser = tableViewUserDB.getSelectionModel().getSelectedItems();

			UserDAO userDAO = new UserDAO();
			userDAO.getUserDelete(selectedUser.get(0).getUserId());
			userData.removeAll(userData);
			totalUserList();
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "삭제 오류", "삭제오류", "다시 시도하시기 바랍니다." + e.toString());
		}
		editDelete = false;
	}

}

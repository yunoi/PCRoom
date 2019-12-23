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
		// ȸ�� ���� ���̺� ����
		userTableViewSettings();
		// ȸ�� ����
		btnUserDelete.setOnAction(event -> {
			handlerUserDeleteAction(event);
		});

		// ���� ���� �� ��� ����
		tableViewUserDB.setOnMouseClicked(event -> {
			handlerUserChargeAction(event);
		});
		// ȸ�� �˻�
		btnUserSearch.setOnAction(event -> {
			handlerUserSearchAction(event);
		});
	}

	// ȸ�� ���̺� ����
	public void userTableViewSettings() {
		userData = FXCollections.observableArrayList();
		tableViewUserDB.setEditable(true);

		//
		TableColumn colUserName = new TableColumn("���̵�");
		colUserName.setPrefWidth(100);
		colUserName.setStyle("-fx-alignment: CENTER;");
		colUserName.setCellValueFactory(new PropertyValueFactory<>("userId"));
		//
		TableColumn colUserId = new TableColumn("�̸�");
		colUserId.setPrefWidth(120);
		colUserId.setStyle("-fx-alignment: CENTER;");
		colUserId.setCellValueFactory(new PropertyValueFactory<>("userName"));
		//
		TableColumn colUserGender = new TableColumn("����");
		colUserGender.setPrefWidth(100);
		colUserGender.setStyle("-fx-alignment: CENTER;");
		colUserGender.setCellValueFactory(new PropertyValueFactory<>("userGender"));
		//
		TableColumn colUserBirth = new TableColumn("�������");
		colUserBirth.setPrefWidth(150);
		colUserBirth.setStyle("-fx-alignment: CENTER;");
		colUserBirth.setCellValueFactory(new PropertyValueFactory<>("userBirth"));
		//
		TableColumn colUserAdult = new TableColumn("���ο���");
		colUserAdult.setPrefWidth(150);
		colUserAdult.setStyle("-fx-alignment: CENTER;");
		colUserAdult.setCellValueFactory(new PropertyValueFactory<>("userAdult"));
		//
		TableColumn colUserPhone = new TableColumn("�޴���");
		colUserPhone.setPrefWidth(150);
		colUserPhone.setStyle("-fx-alignment: CENTER;");
		colUserPhone.setCellValueFactory(new PropertyValueFactory<>("userPhone"));
		//
		TableColumn colUserHomePhone = new TableColumn("������ȭ");
		colUserHomePhone.setPrefWidth(150);
		colUserHomePhone.setStyle("-fx-alignment: CENTER;");
		colUserHomePhone.setCellValueFactory(new PropertyValueFactory<>("userHomePhone"));
		// ----
		TableColumn colUserEmail = new TableColumn("�̸���");
		colUserEmail.setPrefWidth(180);
		colUserEmail.setStyle("-fx-alignment: CENTER;");
		colUserEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		//
		TableColumn colUserSignday = new TableColumn("������");
		colUserSignday.setPrefWidth(100);
		colUserSignday.setStyle("-fx-alignment: CENTER;");
		colUserSignday.setCellValueFactory(new PropertyValueFactory<>("userSignday"));
		//
		TableColumn colUserImage = new TableColumn("�̹�������");
		colUserImage.setPrefWidth(100);
		colUserImage.setStyle("-fx-alignment: CENTER;");
		colUserImage.setCellValueFactory(new PropertyValueFactory<>("userImage"));
		//
		TableColumn colUserStatus = new TableColumn("�̿����");
		colUserStatus.setPrefWidth(100);
		colUserStatus.setStyle("-fx-alignment: CENTER;");
		colUserStatus.setCellValueFactory(new PropertyValueFactory<>("userStatus"));

		// ���̺��� �÷� ��ü���� ���̺�信 ����Ʈ �߰� �� �׸� �߰��Ѵ�.
		tableViewUserDB.setItems(userData);
		tableViewUserDB.getColumns().addAll(colUserName, colUserId, colUserGender, colUserPhone, colUserEmail,
				colUserBirth, colUserAdult, colUserHomePhone, colUserSignday, colUserStatus, colUserImage);

		totalUserList();
	}

	// ȸ�� �˻�
	public void handlerUserSearchAction(ActionEvent event) {
		try {
			ArrayList<UserVO> list = new ArrayList<UserVO>();
			UserDAO userDAO = new UserDAO();
			list = userDAO.getUserSearch(txtUserSearch.getText());
//				System.out.println("list.size = " +list.size() );	// �����κ�
			if (list == null) {
				throw new Exception("�˻� ����");
			}
			userData.removeAll(userData);
			for (UserVO uvo : list) {
				userData.add(uvo);
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "�˻� ���", "�˻� ��� ����", "�˻��Ͻ� �̸��� �������� �ʽ��ϴ�." + e.toString());
		}
	}

	// ��� ����
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
					stageDialog.setTitle("�������");

					ComboBox cbxCharge = (ComboBox) charge.lookup("#cbxCharge");
					Button btnOk = (Button) charge.lookup("#btnOk");
					cbxCharge.setItems(FXCollections.observableArrayList("1�ð� 1,000��", "2�ð� 2,000��", "3�ð� 3,000��",
							"5�ð� 5,000��", "24�ð�(����) 20,000��"));

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
							// �����ð� ��������
							chargeDAO = new ChargeDAO();
							int remainTime = chargeDAO.userRemainTime(selectedUser.get(0).getUserName());
							
							// ó������ ��������
							if (paidMoney.equals("1,000��")) {
								int prepaidMoney = 1000;
								countdownSeconds = 3600;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
//								System.out.println(selectedUser.get(0).getUserName());
//								System.out.println(prepaidMoney);
//								System.out.println(countdownSeconds);
							} else if (paidMoney.equals("2,000��")) {
								int prepaidMoney = 2000;
								countdownSeconds = 7200;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
							} else if (paidMoney.equals("3,000��")) {
								int prepaidMoney = 3000;
								countdownSeconds = 10800;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
							} else if (paidMoney.equals("5,000��")) {
								int prepaidMoney = 5000;
								countdownSeconds = 18000;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
							} else if (paidMoney.equals("20,000��")) {
								int prepaidMoney = 20000;
								countdownSeconds = 86400;
								ChargeVO cvo = new ChargeVO(prepaidMoney,selectedUser.get(0).getUserId(),countdownSeconds);
								chargeDAO.getTodayFirstCharge(cvo);
							}
							 // �߰�����
//							if (paidMoney.equals("1,000��")) {
//								int prepaidMoney = 1000;
//								countdownSeconds = 3600;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // ���ͳѹ� �׽�Ʈ
//							} else if (paidMoney.equals("2,000��")) {
//								int prepaidMoney = 2000;
//								countdownSeconds = 7200;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // ���ͳѹ� �׽�Ʈ
//							} else if (paidMoney.equals("3,000��")) {
//								int prepaidMoney = 3000;
//								countdownSeconds = 10800;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // ���ͳѹ� �׽�Ʈ
//							} else if (paidMoney.equals("5,000��")) {
//								int prepaidMoney = 5000;
//								countdownSeconds = 18000;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // ���ͳѹ� �׽�Ʈ
//							} else if (paidMoney.equals("20,000��")) {
//								int prepaidMoney = 20000;
//								countdownSeconds = 86400;
//								ChargeVO cvo = new ChargeVO(prepaidMoney,countdownSeconds);
//								ChargeDAO chargeDAO = new ChargeDAO();
//								chargeDAO.getChargeUpdate(cvo, 2); // ���ͳѹ� �׽�Ʈ
//							}

						} catch (Exception e1) {
							CommonFunc.alertDisplay(1, "���� ����", "��� ���� ����", "�ٽ� Ȯ���� �ּ���." + e1.getStackTrace());
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

	// ��ü ����� �����ͺ��̽� �ҷ��ͼ� ǥ��
	public void totalUserList() {
		ArrayList<UserVO> list = null;
		UserDAO userDAO = new UserDAO();
		UserVO userVO = null;
		list = userDAO.getUserTotal();
		if (list == null) {
			CommonFunc.alertDisplay(1, "���", "DB �������� ����", "�ٽ� �� �� ������ �ּ���.");
			return;
		} else {
			for (int i = 0; i < list.size(); i++) {
				userVO = list.get(i);
				userData.add(userVO);
			}
		}
	}

	// ȸ�� ����
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
			CommonFunc.alertDisplay(1, "���� ����", "��������", "�ٽ� �õ��Ͻñ� �ٶ��ϴ�." + e.toString());
		}
		editDelete = false;
	}

}

package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.AdminVO;
import model.EquipVO;
import model.ItemVO;

public class AdminMenuController implements Initializable {

	// ��������
	@FXML
	private TableView<AdminVO> tableViewAdminDB;
	private TextField txtAdminName;
	private TextField txtAdminId;
	private PasswordField txtAdminPw;
	private PasswordField txtAdminPwConfirm;
	private RadioButton rbFemale;
	private RadioButton rbMale;
	private ComboBox<String> cbxAdminBirthYear;
	private ComboBox<String> cbxAdminBirthMonth;
	private ComboBox<String> cbxAdminBirthDay;
	private TextField txtAdminCellPhone;
	private TextField txtAdminAddress1;
	private TextField txtAdminAddress2;
	private TextField txtAdminEmail;
	private ComboBox<String> cbxAdminEmail;
	private ComboBox<String> cbxWorkingTime;
	private ComboBox<String> cbxCleanArea;
	private RadioButton rbManager;
	private RadioButton rbBoss;
	@FXML
	private AnchorPane ancAdminInit;
	@FXML
	private Button btnAdminAdd;
	@FXML
	private Button btnAdminDelete;
	@FXML
	private Button btnAdminInfoEdit;
	@FXML
	private Button btnAdminSearch;
	@FXML
	private TextField txtAdminSearch;
	private ObservableList<AdminVO> adminData;
	private int selectedAdminIndex;
	private ObservableList<AdminVO> selectedAdmin;
	private int idNumber = 1; // �������̵��ڵ�����ī����

	// �ü� ����
	@FXML
	private TableView<EquipVO> tableViewEquipDB;
	@FXML
	private Label lbMonitorCount;
	@FXML
	private Label lbComCount;
	@FXML
	private Label lbKeyboardCount;
	@FXML
	private Label lbMouseCount;
	@FXML
	private Label lbSpeakerCount;
	@FXML
	private Label lbHeadsetCount;
	@FXML
	private Button btnEquipAdd;
	@FXML
	private Button btnEquipEdit;
	@FXML
	private Button btnEquipDelete;
	@FXML
	private Button btnEquipSearch;
	@FXML
	private Button btnRefresh;
	@FXML
	private TextField txtEquipSearch;
	private ObservableList<EquipVO> equipData;
	private ObservableList<EquipVO> selectedEquip;
	private int selectedEquipIndex;

	// ������
	@FXML
	private TableView<ItemVO> tableViewItemDB;
	@FXML
	private TextField txtItemCode;
	@FXML
	private TextField txtItemCategory;
	@FXML
	private TextField txtItemName;
	@FXML
	private TextField txtItemPrice;
	@FXML
	private TextField txtImg;
	@FXML
	private TextField txtItemStockGarage;
	@FXML
	private TextField txtItemStockDisplay;
	@FXML
	private TextField txtItemTotalStock;
	@FXML
	private TextField txtItemSearch;
	@FXML
	private Button btnInit;
	@FXML
	private Button btnImg;
	@FXML
	private Button btnNewItemAdd;
	@FXML
	private Button btnItemDelete;
	@FXML
	private Button btnCal;
	@FXML
	private Button btnItemEdit;
	@FXML
	private Button btnItemAdd;
	@FXML
	private Button btnItemSearch;
	private ObservableList<ItemVO> itemData;
	private ObservableList<ItemVO> selectedItem;
	private int selectedItemIndex;

	// ��ǰ �̹��� ����
	@FXML
	private ImageView imgItemImg;
	private String selectFileName = ""; // �̹��� ���ϸ�
	private String localUrl = ""; // �̹��� ���� ���
	private Image localImage;
	private File selectedFile = null;
	// �̹��� ó��
	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:\\javaTest\\PCRoomFXMVC\\src\\images");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;

	boolean editDelete = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ���� ���̺� ����
		adminTableViewSettings();
		// ���̺�信�� ���ڵ带 ������ �� �߻��Ǵ� �̺�Ʈ ó�� ���
		tableViewAdminDB.setOnMousePressed(event -> {
			handlerTableViewPressAction(event);
		});
		// ���� ��� ȭ��
		btnAdminAdd.setOnAction(event -> {
			handlerAdminAddAction(event);
		});
		// ���� ����
		btnAdminDelete.setOnAction(event -> {
			handlerAdminDeleteAction(event);
		});
		// ���� �˻�
		btnAdminSearch.setOnAction(event -> {
			handlerAdminSearchAction(event);
		});
		/////////////////////////////////////////////////
		// �ü� ���̺� ����
		equipTableViewSettings();
		// �ü� ���
		btnEquipAdd.setOnAction(event -> {
			handlerEquipAddAction(event);
		});
		// �ü� ���� ����
		btnEquipEdit.setOnAction(event -> {
			handlerEquipEditAction(event);
		});
		// ���� �˻�
		btnEquipSearch.setOnAction(event -> {
			handlerEquipSearchAction(event);
		});
		// ���� ����
		btnEquipDelete.setOnAction(event -> {
			handlerEquipDeleteAction(event);
		});
		// ���� ���� ���ΰ�ħ
		btnRefresh.setOnAction(event -> {
			handlerEquipAmounAction(event);
		});
		//////////////////////////////////////////////
		// ��� ����

		// ��ǰ ���̺� ����
		itemTableViewSettings();
		// ��ǰ���� �ؽ�Ʈ �ʵ� �ʱ� ����(��Ȱ��ȭ)
		// itemCode,itemCategory,itemName,itemPrice, itemStockGarage,
		// itemStockDisplay, itemTotalStock, itemImg: 8��
		itemTextFieldSetting(true, true, true, true, true, true, true, true);
		// �ʱ�ȭ��ư ��Ȱ��ȭ
		btnInit.setDisable(true);

		btnCal.setOnAction(event -> {
			try {
				int garage = Integer.parseInt(txtItemStockGarage.getText());
				int display = Integer.parseInt(txtItemStockDisplay.getText());
				txtItemTotalStock.setText(String.valueOf(garage + display));
				if (txtItemStockGarage.getText().equals("") || txtItemStockDisplay.getText().equals("")) {
					throw new Exception();
				}
			} catch (Exception e) {
				CommonFunc.alertDisplay(1, "��� ����", "��� ���� ����", "â������ ������� ��Ȯ�� �Է��� �ּ���.");
			}
		});

		// ��ǰ ���̺� �� ������ ���� + ����/����
		tableViewItemDB.setOnMouseClicked(event -> {
			handlerItemSelectAction(event);
		});
		// �� ��ǰ �߰�
		btnNewItemAdd.setOnAction(event -> {
			handlerNewItemAddAction(event);
		});
		// �̹����ʱ�ȭ �Լ�
		imageViewInit();
		// ��ǰ �̹��� ����
		btnImg.setOnAction(event -> {
			handlerItemImageEditAction(event);
		});
		// ��ǰ �˻�
		btnItemSearch.setOnAction(event -> {
			handlerItemSearchAction(event);
		});

	} // end of initialize

	// ��ǰ �˻�
	public void handlerItemSearchAction(ActionEvent event) {
		try {
			ArrayList<ItemVO> list = new ArrayList<ItemVO>();
			ItemDAO itemDAO = new ItemDAO();
			list = itemDAO.getItemSearch(txtItemSearch.getText());
//			System.out.println("list.size = " +list.size() );	// �����κ�
			if (list == null) {
				throw new Exception("�˻� ����");
			}
			itemData.removeAll(itemData);
			for (ItemVO ivo : list) {
				itemData.add(ivo);
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "�˻� ���", "�˻� ��� ����", "�˻��Ͻ� ��� �������� �ʽ��ϴ�." + e.toString());
		}
	}

	public void handlerItemImageEditAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(btnAdminAdd.getScene().getWindow());
			if (selectedFile != null) {
				// �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		localImage = new Image(localUrl, false);
		imgItemImg.setImage(localImage);
		imgItemImg.setFitHeight(100);
		imgItemImg.setFitWidth(100);
		
		if (selectedFile != null) {
			selectFileName = selectedFile.getName(); // �̸��� ������
			txtImg.setText(selectFileName);
		} 
	}

	// �� ��ǰ �߰�
	public void handlerNewItemAddAction(ActionEvent event) {
		try {
			Parent itemAdd = FXMLLoader.load(getClass().getResource("/view/new_Item_add.fxml"));

			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnAdminAdd.getScene().getWindow()); // ���� ��������
			stageDialog.setTitle("������ ���");

			// -------------------------------------------
			TextField txtItemCode = (TextField) itemAdd.lookup("#txtItemCode");
			TextField txtItemCategory = (TextField) itemAdd.lookup("#txtItemCategory");
			TextField txtItemName = (TextField) itemAdd.lookup("#txtItemName");
			TextField txtItemPrice = (TextField) itemAdd.lookup("#txtItemPrice");
			TextField txtItemStockGarage = (TextField) itemAdd.lookup("#txtItemStockGarage");
			TextField txtItemStockDisplay = (TextField) itemAdd.lookup("#txtItemStockDisplay");
			TextField txtItemTotalStock = (TextField) itemAdd.lookup("#txtItemTotalStock");

			Button btnImg = (Button) itemAdd.lookup("#btnImg");
			Button btnCal = (Button) itemAdd.lookup("#btnCal");
			Button btnExit = (Button) itemAdd.lookup("#btnExit");
			Button btnOk = (Button) itemAdd.lookup("#btnOk");

			ImageView imgViewItemImg = (ImageView) itemAdd.lookup("#imgViewItemImg");
			localUrl = "/images/default.png";
			localImage = new Image(localUrl, false);
			imgViewItemImg.setImage(localImage);
			
			txtItemTotalStock.setDisable(true);

			// ��Ϲ�ư ������ ��
			btnOk.setOnAction(event2 -> {
				try {
					File dirMake = new File(dirSave.getAbsolutePath());// ���������� ����� ������ ��ġ
					// �̹��� ���� ���� ����
					if (!dirMake.exists()) {
						dirMake.mkdir();
					}
					// �̹��� ���� ����
					String imgName = imageSave(selectedFile);

					if (txtItemCode.getText().equals("") || txtItemCategory.getText().equals("")
							|| txtItemName.getText().equals("") || txtItemPrice.getText().equals("")
							|| txtItemStockGarage.getText().equals("") || txtItemStockDisplay.getText().equals("")
							|| txtItemTotalStock.getText().equals("")) {
						throw new Exception("�ʼ� ������ ��� �Է��� �ּ���");
					} else {
						ItemVO ivo = new ItemVO(txtItemCode.getText(), txtItemCategory.getText(), txtItemName.getText(),
								Integer.parseInt(txtItemPrice.getText()),
								Integer.parseInt(txtItemStockGarage.getText()),
								Integer.parseInt(txtItemStockDisplay.getText()),
								Integer.parseInt(txtItemTotalStock.getText()), imgName);

						// �����Ͱ� ���̺�信 �������� ���� >> 2������
						if (editDelete == true) {
							itemData.remove(selectedItemIndex);
							itemData.add(selectedItemIndex, ivo);
							editDelete = false;
						} else {
							// DB �θ��� ���
							ItemDAO itemDAO = new ItemDAO();

							// �����ͺ��̽� ���̺� ���� �Է��ϴ� ����
							int count = itemDAO.getItemRegister(ivo);
							if (count != 0) {
								itemData.removeAll(itemData);
								totalItemList();
								// �̹����� �����ϱ� -> �̹����� �ʱ�ȭ����
								localUrl = "/images/default.png";
								localImage = new Image(localUrl, false);
								imgViewItemImg.setImage(localImage);
							} else {
								throw new Exception("�����ͺ��̽� ��� ����");
							}
						} // end of else
					}
				} catch (Exception e) {
					CommonFunc.alertDisplay(1, "��� ����", "������ ��� ����", "�������ʵ带 Ȯ���� �ּ���." + e.getStackTrace());
				}
				stageDialog.close();
			});
			// ��ҹ�ư ������ ��
			btnExit.setOnAction(e -> {
				stageDialog.close();
			});

			// �̹��� ���� ��ư
			btnImg.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
				try {
					selectedFile = fileChooser.showOpenDialog(btnOk.getScene().getWindow());
					if (selectedFile != null) {
						// �̹��� ���� ���
						localUrl = selectedFile.toURI().toURL().toString();
					}
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				localImage = new Image(localUrl, false);
				imgViewItemImg.setImage(localImage);
				imgViewItemImg.setFitHeight(100);
				imgViewItemImg.setFitWidth(100);

				if (selectedFile != null) {
					selectFileName = selectedFile.getName(); // �̸��� ������
				}
			});
			//��� ��� ��ư
			btnCal.setOnAction(e -> {
				try {
					int garage = Integer.parseInt(txtItemStockGarage.getText());
					int display = Integer.parseInt(txtItemStockDisplay.getText());
					txtItemTotalStock.setText(String.valueOf(garage + display));
					if (txtItemStockGarage.getText().equals("") || txtItemStockDisplay.getText().equals("")) {
						throw new Exception();
					}
				} catch (Exception e2) {
					CommonFunc.alertDisplay(1, "��� ����", "��� ���� ����", "â������ ������� ��Ȯ�� �Է��� �ּ���.");
				}
			});
			Scene scene = new Scene(itemAdd);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e1) {
			CommonFunc.alertDisplay(1, "���� ���� ����", "������ ��� ����", "������ �� �����ϴ�." + e1.toString());
		}
	} // end of newItemAdd

	// ��ǰ ���̺�� ����
	public void itemTableViewSettings() {
		itemData = FXCollections.observableArrayList();
		tableViewItemDB.setEditable(true);

		TableColumn colItemCode = new TableColumn("��ǰ��ȣ");
		colItemCode.setPrefWidth(120);
		colItemCode.setStyle("-fx-alignment: CENTER;");
		colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));

		TableColumn colItemCategory = new TableColumn("��ǰ�з�");
		colItemCategory.setPrefWidth(120);
		colItemCategory.setStyle("-fx-alignment: CENTER;");
		colItemCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

		TableColumn colItemName = new TableColumn("��ǰ��");
		colItemName.setPrefWidth(120);
		colItemName.setStyle("-fx-alignment: CENTER;");
		colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));

		TableColumn colItemPrice = new TableColumn("��ǰ����");
		colItemPrice.setPrefWidth(120);
		colItemPrice.setStyle("-fx-alignment: CENTER;");
		colItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

		TableColumn colItemStockGarage = new TableColumn("â�����");
		colItemStockGarage.setPrefWidth(120);
		colItemStockGarage.setStyle("-fx-alignment: CENTER;");
		colItemStockGarage.setCellValueFactory(new PropertyValueFactory<>("itemStockGarage"));

		TableColumn colItemStockDisplay = new TableColumn("�������");
		colItemStockDisplay.setPrefWidth(120);
		colItemStockDisplay.setStyle("-fx-alignment: CENTER;");
		colItemStockDisplay.setCellValueFactory(new PropertyValueFactory<>("itemStockDisplay"));

		TableColumn colItemTotalStock = new TableColumn("�����");
		colItemTotalStock.setPrefWidth(120);
		colItemTotalStock.setStyle("-fx-alignment: CENTER;");
		colItemTotalStock.setCellValueFactory(new PropertyValueFactory<>("itemTotalStock"));

		TableColumn colItemImg = new TableColumn("�̹�������");
		colItemImg.setPrefWidth(120);
		colItemImg.setStyle("-fx-alignment: CENTER;");
		colItemImg.setCellValueFactory(new PropertyValueFactory<>("itemImg"));

		tableViewItemDB.setItems(itemData);
		tableViewItemDB.getColumns().addAll(colItemCode, colItemCategory, colItemName, colItemPrice, colItemStockGarage,
				colItemStockDisplay, colItemTotalStock, colItemImg);

		totalItemList();
	}

	// ��ǰ ���̺� �� ������ ����

	public void handlerItemSelectAction(MouseEvent event) {
		// ������ �� ��ġ�� �ش�� ��ü�� �����´�.
		// + ����, ���� ���
		try {
			editDelete = true;

			selectedItemIndex = tableViewItemDB.getSelectionModel().getSelectedIndex();
			selectedItem = tableViewItemDB.getSelectionModel().getSelectedItems();
			itemTextFieldSetting(true, false, false, false, false, false, false, true);
			// ������ �� ���̺�信 �ִ� ���� �����ͼ� �Ʒ��� �������ʵ忡 ����ش�.
			txtItemCode.setText(selectedItem.get(0).getItemCode());
			txtItemCategory.setText(selectedItem.get(0).getItemCategory());
			txtItemName.setText(selectedItem.get(0).getItemName());
			txtItemPrice.setText(String.valueOf(selectedItem.get(0).getItemPrice()));
			txtItemStockGarage.setText(String.valueOf(selectedItem.get(0).getItemStockGarage()));
			txtItemStockDisplay.setText(String.valueOf(selectedItem.get(0).getItemStockDisplay()));
			txtItemTotalStock.setText(String.valueOf(selectedItem.get(0).getItemTotalStock()));
			txtImg.setText(selectedItem.get(0).getItemImg());

			String fileName = selectedItem.get(0).getItemImg();
			selectedFile = new File("C:/javaTest/PCRoomFXMVC/src/images/" + fileName);
			if (selectedFile != null) {
				// �̹������ϰ��
				localUrl = selectedFile.toURI().toURL().toString();
				localImage = new Image(localUrl, false);
				imgItemImg.setImage(localImage);
				imgItemImg.setFitHeight(100);
				imgItemImg.setFitWidth(100);
			} else {
				imageViewInit();
			}
			// ������ư
			btnItemEdit.setOnAction(event2 -> {
				try {
					File dirMake = new File(dirSave.getAbsolutePath());// ���������� ����� ������ ��ġ
					// �̹��� ���� ���� ����
					if (!dirMake.exists()) {
						dirMake.mkdir();
					}
					// �̹��� ���� ����
					String imgName = imageSave(selectedFile);
					
					ItemVO ivo = new ItemVO(txtItemCode.getText(), txtItemCategory.getText(), txtItemName.getText(),
							Integer.parseInt(txtItemPrice.getText()), Integer.parseInt(txtItemStockGarage.getText()),
							Integer.parseInt(txtItemStockDisplay.getText()),
							Integer.parseInt(txtItemTotalStock.getText()), imgName);
					
					ItemDAO itemDAO = new ItemDAO();
					ItemVO itemVO = itemDAO.getItemUpdate(ivo, selectedItem.get(0).getItemCode());

					// �����Ͱ� ���̺�信 �������� ���� >> 2������
					if (editDelete == true && itemVO != null) {
						itemData.remove(selectedItemIndex);
						itemData.add(selectedItemIndex, itemVO);
						editDelete = false;
					} else {
						throw new Exception("���� ����");
					}

				} catch (Exception e) {
					CommonFunc.alertDisplay(1, "���� ����", "������ ���� ����", "�������ʵ带 Ȯ���� �ּ���." + e.getStackTrace());
				} // end of try catch
			}); // end of Edit

			// ����
			btnItemDelete.setOnAction(event3 -> {
				try {
					ItemDAO itemDAO = new ItemDAO();
					itemDAO.getItemDelete(selectedItem.get(0).getItemCode());
					itemData.removeAll(itemData);
					totalItemList();
				} catch (Exception e) {
					CommonFunc.alertDisplay(1, "���� ����", "��������", "9�� �޼ҵ� Ȯ�� �ٶ�" + e.toString());
				}
				editDelete = false;
			});
		} catch (Exception e) {
			editDelete = false;
		} // end of try catch
	}// end of delete

	// ��ǰ���� �ؽ�Ʈ �ʵ� ����
	public void itemTextFieldSetting(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g,
			boolean h) {

		txtItemCode.setDisable(a);
		txtItemCategory.setDisable(b);
		txtItemName.setDisable(c);
		txtItemPrice.setDisable(d);
		txtItemStockGarage.setDisable(f);
		txtItemStockDisplay.setDisable(g);
		txtItemTotalStock.setDisable(h);
		txtImg.setDisable(e);
		btnInit.setDisable(false);

		// �ʱ�ȭ -> ��ǰ���� �ؽ�Ʈ �ʵ��� ��� ���� �����Ѵ�.
		btnInit.setOnAction(event -> {
			txtItemCode.setText(null);
			txtItemCategory.setText(null);
			txtItemName.setText(null);
			txtItemPrice.setText(null);
			txtItemStockGarage.setText(null);
			txtItemStockDisplay.setText(null);
			txtItemTotalStock.setText(null);
			txtImg.setText(null);
		});
	}

	/***********************************************************************/
	
	// ���� ���� ���ΰ�ħ
		public void handlerEquipAmounAction(ActionEvent event) {
			try {
			EquipDAO equipDAO = new EquipDAO();
			ArrayList<Integer> list = new ArrayList<Integer>();
				list = equipDAO.getEquipAmountCheck();
				
			lbMonitorCount.setText(String.valueOf(list.get(0)));
			lbComCount.setText(String.valueOf(list.get(1)));
			lbKeyboardCount.setText(String.valueOf(list.get(2)));
			lbSpeakerCount.setText(String.valueOf(list.get(3)));
			lbHeadsetCount.setText(String.valueOf(list.get(4)));
			lbMouseCount.setText(String.valueOf(list.get(5)));
			
			} catch (Exception e) {
				CommonFunc.alertDisplay(1, "�������� ����", "������ �������� ����", "�ٽ� �õ��� �ּ���.");
			}
		}
	
	// ���� ����
	public void handlerEquipDeleteAction(ActionEvent event) {
		try {
			EquipDAO equipDAO = new EquipDAO();
			equipDAO.getEquipDelete(selectedEquip.get(0).getEquipNo());
			equipData.removeAll(equipData);
			totalEquipList();
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "���� ����", "��������", "�ٽ� �õ��Ͻñ� �ٶ��ϴ�." + e.toString());
		}
		editDelete = false;
	}

	// ���� �˻�
	public void handlerEquipSearchAction(ActionEvent event) {
		try {
			ArrayList<EquipVO> list = new ArrayList<EquipVO>();
			EquipDAO equipDAO = new EquipDAO();
			list = equipDAO.getEquipSearch(txtEquipSearch.getText());
//			System.out.println("list.size = " +list.size() );	// �����κ�
			if (list == null) {
				throw new Exception("�˻� ����");
			}
			equipData.removeAll(equipData);
			for (EquipVO evo : list) {
				equipData.add(evo);
			}
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "�˻� ���", "�˻� ��� ����", "�˻��Ͻ� ��� �������� �ʽ��ϴ�." + e.toString());
		}
	}

	// �ü���������
	public void handlerEquipEditAction(ActionEvent event) {
		// ������ �� ��ġ�� �ش�� ��ü�� �����´�.
		try {
			editDelete = true;

			selectedEquipIndex = tableViewEquipDB.getSelectionModel().getSelectedIndex();
			selectedEquip = tableViewEquipDB.getSelectionModel().getSelectedItems();

			btnEquipEdit.setOnAction(event2 -> {
				// System.out.println("buttonPressed");
				Parent equipEdit;
				try {
					equipEdit = FXMLLoader.load(getClass().getResource("/view/equip_add.fxml"));

					Stage stageDialog = new Stage(StageStyle.UTILITY);
					stageDialog.initModality(Modality.WINDOW_MODAL);
					stageDialog.initOwner(tableViewAdminDB.getScene().getWindow()); // �ƹ� ��ü������ �ҷ��� �� �ִ�. ����������
					stageDialog.setTitle("��� ���� ����");

					TextField txtEquipNo = (TextField) equipEdit.lookup("#txtEquipNo");
					TextField txtEquipType = (TextField) equipEdit.lookup("#txtEquipType");
					TextField txtEquipName = (TextField) equipEdit.lookup("#txtEquipName");
					TextField txtASInfo = (TextField) equipEdit.lookup("#txtASInfo");
					ComboBox<String> cbxStatus = (ComboBox<String>) equipEdit.lookup("#cbxStatus");
					TextField txtSeatNo = (TextField) equipEdit.lookup("#txtSeatNo");
					Button btnOk = (Button) equipEdit.lookup("#btnOk");
					Button btnExit = (Button) equipEdit.lookup("#btnExit");
					cbxStatus.setItems(
							FXCollections.observableArrayList("�����̿���", "������", "�н�", "��ü����", "â��(�̿밡��)", "â��(����)"));

					// ������ �� ���̺�信 �ִ� ���� �����ͼ� ������ ���� �ʵ忡 ����ش�.
					txtEquipNo.setText(selectedEquip.get(0).getEquipNo());
					txtEquipType.setText(selectedEquip.get(0).getEquipCategory());
					txtEquipName.setText(selectedEquip.get(0).getEquipName());
					txtASInfo.setText(selectedEquip.get(0).getAsInfo());
					cbxStatus.setValue(selectedEquip.get(0).getEquipStatus());
					txtSeatNo.setText(selectedEquip.get(0).getSeatNo());

					// ����(��� ��ư)
					btnOk.setOnAction(e4 -> {
						try {
							EquipVO evo = new EquipVO(txtEquipNo.getText(), txtEquipType.getText(),
									txtEquipName.getText(), txtASInfo.getText(), cbxStatus.getValue(),
									txtSeatNo.getText());

							EquipDAO equipDAO = new EquipDAO();
							EquipVO equipVO = equipDAO.getEquipUpdate(evo, selectedEquip.get(0).getEquipNo());
							
							// �����Ͱ� ���̺�信 �������� ���� >> 2������
							if (editDelete == true && equipVO != null) {
								equipData.remove(selectedEquipIndex);
								equipData.add(selectedEquipIndex, equipVO);
								editDelete = false;
							} else {
								throw new Exception("���� ����");
							}

						} catch (Exception e) {
							CommonFunc.alertDisplay(1, "���� ����", "������ ���� ����", "�������ʵ带 Ȯ���� �ּ���." + e.getStackTrace());
						}
						stageDialog.close();
					});
					// ��ҹ�ư ������ ��
					btnExit.setOnAction(e3 -> {
						stageDialog.close();
					});

					Scene scene = new Scene(equipEdit);
					stageDialog.setScene(scene);
					stageDialog.setResizable(false);
					stageDialog.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			editDelete = false;
		}

	}

	// �ü� ���
	public void handlerEquipAddAction(ActionEvent event) {
		try {
			Parent equipAdd = FXMLLoader.load(getClass().getResource("/view/equip_add.fxml"));

			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnAdminAdd.getScene().getWindow()); // ���� ��������
			stageDialog.setTitle("��� ���");

			// -------------------------------------------
			TextField txtEquipNo = (TextField) equipAdd.lookup("#txtEquipNo");
			TextField txtEquipType = (TextField) equipAdd.lookup("#txtEquipType");
			TextField txtEquipName = (TextField) equipAdd.lookup("#txtEquipName");
			TextField txtASInfo = (TextField) equipAdd.lookup("#txtASInfo");
			ComboBox<String> cbxStatus = (ComboBox<String>) equipAdd.lookup("#cbxStatus");
			TextField txtSeatNo = (TextField) equipAdd.lookup("#txtSeatNo");
			Button btnOk = (Button) equipAdd.lookup("#btnOk");
			Button btnExit = (Button) equipAdd.lookup("#btnExit");

			cbxStatus.setItems(FXCollections.observableArrayList("�����̿���", "������", "�н�", "��ü����", "â��(�̿밡��)", "â��(����)"));

			// ��Ϲ�ư ������ ��
			btnOk.setOnAction(event2 -> {
				try {
					if (txtEquipNo.getText().equals("") || txtEquipType.getText().equals("")
							|| txtEquipName.getText().equals("") || cbxStatus.getValue().equals("")) {
						throw new Exception("�ʼ� ������ ��� �Է��� �ּ���");
					} else {
						EquipVO evo = new EquipVO(txtEquipNo.getText(), txtEquipType.getText(), txtEquipName.getText(),
								txtASInfo.getText(), cbxStatus.getValue(), txtSeatNo.getText());

						// �����Ͱ� ���̺�信 �������� ���� >> 2������
						if (editDelete == true) {
							equipData.remove(selectedEquipIndex);
							equipData.add(selectedEquipIndex, evo);
							editDelete = false;
						} else {
							// DB �θ��� ���
							EquipDAO equipDAO = new EquipDAO();

							// �����ͺ��̽� ���̺� ���� �Է��ϴ� ����
							int count = equipDAO.getEquipRegister(evo);
							if (count != 0) {
								equipData.removeAll(equipData);
								totalEquipList();
							} else {
								throw new Exception("�����ͺ��̽� ��� ����");
							}
						}
					}
				} catch (Exception e) {
					CommonFunc.alertDisplay(1, "��� ����", "������ ��� ����", "�������ʵ带 Ȯ���� �ּ���." + e.getStackTrace());
				}
				stageDialog.close();
			});
			// ��ҹ�ư ������ ��
			btnExit.setOnAction(e -> {
				stageDialog.close();
			});

			Scene scene = new Scene(equipAdd);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e1) {
			CommonFunc.alertDisplay(1, "���� ���� ����", "������ ��� ����", "������ �� �����ϴ�." + e1.toString());
		}

	}

	// �ü� ���̺� ����
	public void equipTableViewSettings() {
		equipData = FXCollections.observableArrayList();
		tableViewEquipDB.setEditable(true);

		TableColumn colEquipNo = new TableColumn("�����ȣ");
		colEquipNo.setPrefWidth(120);
		colEquipNo.setStyle("-fx-alignment: CENTER;");
		colEquipNo.setCellValueFactory(new PropertyValueFactory<>("equipNo"));

		TableColumn colEquipCategory = new TableColumn("����з�");
		colEquipCategory.setPrefWidth(120);
		colEquipCategory.setStyle("-fx-alignment: CENTER;");
		colEquipCategory.setCellValueFactory(new PropertyValueFactory<>("equipCategory"));

		TableColumn colEquipName = new TableColumn("�𵨸�");
		colEquipName.setPrefWidth(120);
		colEquipName.setStyle("-fx-alignment: CENTER;");
		colEquipName.setCellValueFactory(new PropertyValueFactory<>("equipName"));

		TableColumn colAsInfo = new TableColumn("A/S����");
		colAsInfo.setPrefWidth(120);
		colAsInfo.setStyle("-fx-alignment: CENTER;");
		colAsInfo.setCellValueFactory(new PropertyValueFactory<>("asInfo"));

		TableColumn colEquipStatus = new TableColumn("����/��ġ");
		colEquipStatus.setPrefWidth(120);
		colEquipStatus.setStyle("-fx-alignment: CENTER;");
		colEquipStatus.setCellValueFactory(new PropertyValueFactory<>("equipStatus"));

		TableColumn colSeatNo = new TableColumn("�¼���ȣ");
		colSeatNo.setPrefWidth(120);
		colSeatNo.setStyle("-fx-alignment: CENTER;");
		colSeatNo.setCellValueFactory(new PropertyValueFactory<>("seatNo"));

		tableViewEquipDB.setItems(equipData);
		tableViewEquipDB.getColumns().addAll(colEquipNo, colEquipCategory, colEquipName, colAsInfo, colEquipStatus,
				colSeatNo);

		totalEquipList();
	}

	// ���� ���̺� ����
	public void adminTableViewSettings() {
		adminData = FXCollections.observableArrayList();
		tableViewAdminDB.setEditable(true);

		TableColumn colAdminId = new TableColumn("����ID");
		colAdminId.setPrefWidth(120);
		colAdminId.setStyle("-fx-alignment: CENTER;");
		colAdminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));

		TableColumn colAdminName = new TableColumn("�̸�");
		colAdminName.setPrefWidth(100);
		colAdminName.setStyle("-fx-alignment: CENTER;");
		colAdminName.setCellValueFactory(new PropertyValueFactory<>("adminName"));

		TableColumn colAdminGender = new TableColumn("����");
		colAdminGender.setPrefWidth(100);
		colAdminGender.setStyle("-fx-alignment: CENTER;");
		colAdminGender.setCellValueFactory(new PropertyValueFactory<>("adminGender"));

		TableColumn colAdminBirth = new TableColumn("�������");
		colAdminBirth.setPrefWidth(150);
		colAdminBirth.setStyle("-fx-alignment: CENTER;");
		colAdminBirth.setCellValueFactory(new PropertyValueFactory<>("adminBirth"));

		TableColumn colAdminPhone = new TableColumn("�޴���");
		colAdminPhone.setPrefWidth(150);
		colAdminPhone.setCellValueFactory(new PropertyValueFactory<>("adminPhone"));

		TableColumn colAdminAddress = new TableColumn("�޴���");
		colAdminAddress.setPrefWidth(150);
		colAdminAddress.setCellValueFactory(new PropertyValueFactory<>("adminAddress"));

		TableColumn colAdminEmail = new TableColumn("�̸���");
		colAdminEmail.setPrefWidth(180);
		colAdminEmail.setStyle("-fx-alignment: CENTER;");
		colAdminEmail.setCellValueFactory(new PropertyValueFactory<>("adminEmail"));

		TableColumn colAdminWorkingTime = new TableColumn("�ٹ��ð�ǥ");
		colAdminWorkingTime.setPrefWidth(100);
		colAdminWorkingTime.setStyle("-fx-alignment: CENTER;");
		colAdminWorkingTime.setCellValueFactory(new PropertyValueFactory<>("adminWorkingTime"));

		TableColumn colAdminLevel = new TableColumn("�����ڱ���");
		colAdminLevel.setPrefWidth(100);
		colAdminLevel.setStyle("-fx-alignment: CENTER;");
		colAdminLevel.setCellValueFactory(new PropertyValueFactory<>("adminLevel"));

		TableColumn colAdminCleanArea = new TableColumn("û�ұ���");
		colAdminCleanArea.setPrefWidth(100);
		colAdminCleanArea.setStyle("-fx-alignment: CENTER;");
		colAdminCleanArea.setCellValueFactory(new PropertyValueFactory<>("adminCleanArea"));

		// ���̺��� �÷� ��ü���� ���̺�信 ����Ʈ �߰� �� �׸� �߰��Ѵ�.
		tableViewAdminDB.setItems(adminData);
		tableViewAdminDB.getColumns().addAll(colAdminId, colAdminName, colAdminGender, colAdminBirth, colAdminPhone,
				colAdminAddress, colAdminEmail, colAdminWorkingTime, colAdminLevel, colAdminCleanArea);

		totalList();
	}

	/************************************************************************/
	// ���� ���̺�並 ������ �� ���� ���
	public void handlerTableViewPressAction(MouseEvent event) {
		// ������ �� ��ġ�� �ش�� ��ü�� �����´�.
		try {
			editDelete = true;

			selectedAdminIndex = tableViewAdminDB.getSelectionModel().getSelectedIndex();
			selectedAdmin = tableViewAdminDB.getSelectionModel().getSelectedItems();

			btnAdminInfoEdit.setOnAction(event2 -> {
				// System.out.println("buttonPressed");
				Parent adminEdit;
				try {
					adminEdit = FXMLLoader.load(getClass().getResource("/view/admin_add.fxml"));

					Stage stageDialog = new Stage(StageStyle.UTILITY);
					stageDialog.initModality(Modality.WINDOW_MODAL);
					stageDialog.initOwner(tableViewAdminDB.getScene().getWindow()); // �ƹ� ��ü������ �ҷ��� �� �ִ�. ����������
					stageDialog.setTitle("������ ���� ����");

					TextField txtAdminNameEdit = (TextField) adminEdit.lookup("#txtAdminName");
					TextField txtAdminIdEdit = (TextField) adminEdit.lookup("#txtAdminId");
					PasswordField txtAdminPwEdit = (PasswordField) adminEdit.lookup("#txtAdminPw");
					PasswordField txtAdminPwConfirmEdit = (PasswordField) adminEdit.lookup("#txtAdminPwConfirm");
					RadioButton rbFemaleEdit = (RadioButton) adminEdit.lookup("#rbFemale");
					RadioButton rbMaleEdit = (RadioButton) adminEdit.lookup("#rbMale");
					ComboBox<String> cbxAdminBirthYearEdit = (ComboBox<String>) adminEdit.lookup("#cbxAdminBirthYear");
					ComboBox<String> cbxAdminBirthMonthEdit = (ComboBox<String>) adminEdit
							.lookup("#cbxAdminBirthMonth");
					ComboBox<String> cbxAdminBirthDayEdit = (ComboBox<String>) adminEdit.lookup("#cbxAdminBirthDay");
					TextField txtAdminCellPhoneEdit = (TextField) adminEdit.lookup("#txtAdminCellPhone");
					TextField txtAdminAddress1Edit = (TextField) adminEdit.lookup("#txtAdminAddress1");
					TextField txtAdminAddress2Edit = (TextField) adminEdit.lookup("#txtAdminAddress2");
					TextField txtAdminEmailEdit = (TextField) adminEdit.lookup("#txtAdminEmail");
					ComboBox<String> cbxAdminEmailEdit = (ComboBox<String>) adminEdit.lookup("#cbxAdminEmail");
					ComboBox<String> cbxWorkingTimeEdit = (ComboBox<String>) adminEdit.lookup("#cbxWorkingTime");
					ComboBox<String> cbxCleanAreaEdit = (ComboBox<String>) adminEdit.lookup("#cbxCleanArea");
					RadioButton rbManagerEdit = (RadioButton) adminEdit.lookup("#rbManager");
					RadioButton rbBossEdit = (RadioButton) adminEdit.lookup("#rbBoss");

					Button btnCreateId = (Button) adminEdit.lookup("#btnCreateId");
					Button btnExit = (Button) adminEdit.lookup("#btnExit");
					Button btnOk = (Button) adminEdit.lookup("#btnOk");

					// �⵵ �޺��ڽ�����
					ArrayList<String> list = new ArrayList<String>();
					for (int years = 1920; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
						list.add(years + "");
					}
					cbxAdminBirthYearEdit.setItems(FXCollections.observableArrayList(list));
					cbxAdminBirthMonthEdit.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
							"8", "9", "10", "11", "12"));
					cbxAdminBirthDayEdit.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
							"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
							"23", "24", "25", "26", "27", "28", "29", "30", "31")); // �����ڷ� �ٷ� �־����

					cbxAdminEmailEdit.setItems(FXCollections.observableArrayList("gmail.com", "naver.com", "daum.net",
							"hotmail.com", "nate.com"));
					cbxWorkingTimeEdit.setItems(FXCollections.observableArrayList("����(����)", "����(����)", "����(�߰�)",
							"�ָ�(����)", "�ָ�(����)", "�ָ�(�߰�)"));
					cbxCleanAreaEdit.setItems(FXCollections.observableArrayList("����", "ȭ���", "����", "������"));

					// ������ �� ���̺�信 �ִ� ���� �����ͼ� ������ ���� �ʵ忡 ����ش�.
					txtAdminNameEdit.setText(selectedAdmin.get(0).getAdminName());
					txtAdminIdEdit.setText(selectedAdmin.get(0).getAdminId());
					txtAdminPwEdit.setText(selectedAdmin.get(0).getAdminPw());
					txtAdminPwConfirmEdit.setText(selectedAdmin.get(0).getAdminPw());
					if (selectedAdmin.get(0).getAdminGender().equals("����")) {
						rbFemaleEdit.setSelected(true);
					} else {
						rbMaleEdit.setSelected(true);
					}
					String birth = selectedAdmin.get(0).getAdminBirth();
					String year = birth.split("-")[0];
					String month = birth.split("-")[1];
					String day = birth.split("-")[2];
					// System.out.println(year);
					// System.out.println(month);
					// System.out.println(day);
					cbxAdminBirthYearEdit.setValue(year);
					cbxAdminBirthMonthEdit.setValue(month);
					cbxAdminBirthDayEdit.setValue(day);
					txtAdminCellPhoneEdit.setText(selectedAdmin.get(0).getAdminPhone());
					String address = selectedAdmin.get(0).getAdminAddress();
					int idx1 = address.indexOf(" ");
					String address1 = address.substring(0, idx1);
					String address2 = address.substring(idx1 + 1);
					txtAdminAddress1Edit.setText(address1);
					txtAdminAddress2Edit.setText(address2);
					String eMail = selectedAdmin.get(0).getAdminEmail();
					int idx2 = eMail.indexOf("@");
					String front = eMail.substring(0, idx2);
					String back = eMail.substring(idx2 + 1);
					txtAdminEmailEdit.setText(front);
					cbxAdminEmailEdit.setValue(back);
					cbxWorkingTimeEdit.setValue(selectedAdmin.get(0).getAdminWorkingTime());
					cbxCleanAreaEdit.setValue(selectedAdmin.get(0).getAdminCleanArea());
					if (selectedAdmin.get(0).getAdminLevel().equals("����")) {
						rbBossEdit.setSelected(true);
					} else {
						rbManagerEdit.setSelected(true);
					}
					txtAdminNameEdit.setDisable(true);
					txtAdminIdEdit.setDisable(true);
					btnCreateId.setDisable(true);
					rbFemaleEdit.setDisable(true);
					rbMaleEdit.setDisable(true);

					// ����(��� ��ư)
					btnOk.setOnAction(e4 -> {
						try {
							String birthInfo = cbxAdminBirthYearEdit.getValue() + "-"
									+ cbxAdminBirthMonthEdit.getValue() + "-" + cbxAdminBirthDayEdit.getValue();
							String addressInfo = txtAdminAddress1Edit.getText() + " " + txtAdminAddress2Edit.getText();
							String eMailInfo = txtAdminEmailEdit.getText() + "@" + cbxAdminEmailEdit.getValue();
							String gender = "";
							String adminLevel = "";
							if (rbFemaleEdit.isSelected()) {
								gender = rbFemaleEdit.getText();
							} else if (rbMaleEdit.isSelected()) {
								gender = rbMaleEdit.getText();
							}
							if (rbManagerEdit.isSelected()) {
								adminLevel = rbManagerEdit.getText();
							} else if (rbBossEdit.isSelected()) {
								adminLevel = rbBossEdit.getText();
							}

							AdminVO avo = new AdminVO(txtAdminIdEdit.getText(), txtAdminNameEdit.getText(), gender,
									birthInfo, txtAdminCellPhoneEdit.getText(), addressInfo, eMailInfo,
									cbxWorkingTimeEdit.getValue(), adminLevel, cbxCleanAreaEdit.getValue(),
									txtAdminPwEdit.getText());

							AdminDAO adminDAO = new AdminDAO();
							AdminVO adminVO = adminDAO.getAdminUpdate(avo, selectedAdmin.get(0).getAdminId());

							// �����Ͱ� ���̺�信 �������� ���� >> 2������
							if (editDelete == true && adminVO != null) {
								adminData.remove(selectedAdminIndex);
								adminData.add(selectedAdminIndex, adminVO);
								editDelete = false;
							} else {
								throw new Exception("���� ����");
							}

						} catch (Exception e) {
							CommonFunc.alertDisplay(1, "���� ����", "������ ���� ����", "�������ʵ带 Ȯ���� �ּ���." + e.getStackTrace());
						}
						stageDialog.close();
					});
					// ��ҹ�ư ������ ��
					btnExit.setOnAction(e3 -> {
						stageDialog.close();
					});

					Scene scene = new Scene(adminEdit);
					stageDialog.setScene(scene);
					stageDialog.setResizable(false);
					stageDialog.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			editDelete = false;
		}

	}

	// ���� ���
	public void handlerAdminAddAction(ActionEvent event) {
		try {
			Parent adminAdd = FXMLLoader.load(getClass().getResource("/view/admin_add.fxml"));

			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			stageDialog.initOwner(btnAdminAdd.getScene().getWindow()); // ���� ��������
			stageDialog.setTitle("������ ���");

			// -------------------------------------------
			txtAdminName = (TextField) adminAdd.lookup("#txtAdminName");
			txtAdminId = (TextField) adminAdd.lookup("#txtAdminId");
			txtAdminPw = (PasswordField) adminAdd.lookup("#txtAdminPw");
			txtAdminPwConfirm = (PasswordField) adminAdd.lookup("#txtAdminPwConfirm");
			rbFemale = (RadioButton) adminAdd.lookup("#rbFemale");
			rbMale = (RadioButton) adminAdd.lookup("#rbMale");
			cbxAdminBirthYear = (ComboBox<String>) adminAdd.lookup("#cbxAdminBirthYear");
			cbxAdminBirthMonth = (ComboBox<String>) adminAdd.lookup("#cbxAdminBirthMonth");
			cbxAdminBirthDay = (ComboBox<String>) adminAdd.lookup("#cbxAdminBirthDay");
			txtAdminCellPhone = (TextField) adminAdd.lookup("#txtAdminCellPhone");
			txtAdminAddress1 = (TextField) adminAdd.lookup("#txtAdminAddress1");
			txtAdminAddress2 = (TextField) adminAdd.lookup("#txtAdminAddress2");
			txtAdminEmail = (TextField) adminAdd.lookup("#txtAdminEmail");
			cbxAdminEmail = (ComboBox<String>) adminAdd.lookup("#cbxAdminEmail");
			cbxWorkingTime = (ComboBox<String>) adminAdd.lookup("#cbxWorkingTime");
			cbxCleanArea = (ComboBox<String>) adminAdd.lookup("#cbxCleanArea");
			rbManager = (RadioButton) adminAdd.lookup("#rbManager");
			rbBoss = (RadioButton) adminAdd.lookup("#rbBoss");

			Button btnCreateId = (Button) adminAdd.lookup("#btnCreateId");
			Button btnExit = (Button) adminAdd.lookup("#btnExit");
			Button btnOk = (Button) adminAdd.lookup("#btnOk");

			ArrayList<String> birthList = new ArrayList<String>();
			for (int years = 1920; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
				birthList.add(years + "");
			}
			cbxAdminBirthYear.setItems(FXCollections.observableArrayList(birthList));
			cbxAdminBirthMonth.setItems(
					FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
			cbxAdminBirthDay.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9",
					"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
					"26", "27", "28", "29", "30", "31")); // �����ڷ� �ٷ� �־����

			cbxAdminEmail.setItems(
					FXCollections.observableArrayList("gmail.com", "naver.com", "daum.net", "hotmail.com", "nate.com"));
			cbxWorkingTime.setItems(
					FXCollections.observableArrayList("����(����)", "����(����)", "����(�߰�)", "�ָ�(����)", "�ָ�(����)", "�ָ�(�߰�)"));
			cbxCleanArea.setItems(FXCollections.observableArrayList("����", "ȭ���", "����", "������"));

			txtAdminId.setDisable(true);

			// ���̵������ư ������ ��
			btnCreateId.setOnAction(e2 -> {
				idNumber++;
				txtAdminId.setText("manager" + idNumber);
			});
			// ��Ϲ�ư ������ ��
			btnOk.setOnAction(event2 -> {
				try {
					if (txtAdminName.getText().equals("") || txtAdminId.getText().equals("")) {
						throw new Exception();
					} else {

						String birthInfo = cbxAdminBirthYear.getValue() + "-" + cbxAdminBirthMonth.getValue() + "-"
								+ cbxAdminBirthDay.getValue();
						String addressInfo = txtAdminAddress1.getText() + " " + txtAdminAddress2.getText();
						String eMailInfo = txtAdminEmail.getText() + "@" + cbxAdminEmail.getValue();
						String gender = "";
						String adminLevel = "";
						if (rbFemale.isSelected()) {
							gender = rbFemale.getText();
						} else if (rbMale.isSelected()) {
							gender = rbMale.getText();
						}
						if (rbManager.isSelected()) {
							adminLevel = rbManager.getText();
						} else if (rbBoss.isSelected()) {
							adminLevel = rbBoss.getText();
						}
						AdminVO avo = new AdminVO(txtAdminId.getText(), txtAdminName.getText(), gender, birthInfo,
								txtAdminCellPhone.getText(), addressInfo, eMailInfo, cbxWorkingTime.getValue(),
								adminLevel, cbxCleanArea.getValue(), txtAdminPw.getText());

						// �����Ͱ� ���̺�信 �������� ���� >> 2������
						if (editDelete == true) {
							adminData.remove(selectedAdminIndex);
							adminData.add(selectedAdminIndex, avo);
							editDelete = false;
						} else {
							// DB �θ��� ���
							AdminDAO adminDAO = new AdminDAO();

							// �����ͺ��̽� ���̺� ���� �Է��ϴ� ����
							int count = adminDAO.getAdminRegister(avo);
							if (count != 0) {
								adminData.removeAll(adminData);
								totalList();
							} else {
								throw new Exception("�����ͺ��̽� ��� ����");
							}
						}
					}
				} catch (Exception e) {
//					CommonFunc.alertDisplay(1, "��� ����", "������ ��� ����", "�������ʵ带 Ȯ���� �ּ���." + e.getStackTrace());
					CommonFunc.alertDisplay(1, "��� �Ϸ�", "������ ��� �Ϸ�", "ȯ���մϴ�.");
				}
				stageDialog.close();
			});
			// ��ҹ�ư ������ ��
			btnExit.setOnAction(e -> {
				stageDialog.close();
			});

			Scene scene = new Scene(adminAdd);
			stageDialog.setScene(scene);
			stageDialog.setResizable(false);
			stageDialog.show();
		} catch (IOException e1) {
			CommonFunc.alertDisplay(1, "���� ���� ����", "������ ��� ����", "������ �� �����ϴ�." + e1.toString());
		}
	}

	// �����˻�
	private void handlerAdminSearchAction(ActionEvent event) {
		try {
			ArrayList<AdminVO> list = new ArrayList<AdminVO>();
			AdminDAO adminDAO = new AdminDAO();
			list = adminDAO.getAdminSearch(txtAdminSearch.getText());
//				System.out.println("list.size = " +list.size() );	// �����κ�
			if (list == null) {
				throw new Exception("�˻� ����");
			}
			adminData.removeAll(adminData);
			for (AdminVO avo : list) {
				adminData.add(avo);
			}
			// �˻� �� �ٽ� ��ü ���������
			ancAdminInit.setOnMousePressed(e ->{
				totalList();
			});
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "�˻� ���", "�˻� ��� ����", "�˻��Ͻ� �̸��� �������� �ʽ��ϴ�." + e.toString());
		}
	}

	// ���� ����
	public void handlerAdminDeleteAction(ActionEvent event) {
		try {
			AdminDAO adminDAO = new AdminDAO();
			adminDAO.getAdminDelete(selectedAdmin.get(0).getAdminId());
			adminData.removeAll(adminData);
			totalList();
		} catch (Exception e) {
			CommonFunc.alertDisplay(1, "���� ����", "��������", "9�� �޼ҵ� Ȯ�� �ٶ�" + e.toString());
		}
		editDelete = false;
	}

	// ��ü ���� �����ͺ��̽� �ҷ�����
	public void totalList() {

		ArrayList<AdminVO> list = null;
		AdminDAO adminDAO = new AdminDAO();
		AdminVO adminVO = null;
		list = adminDAO.getAdminTotal();
		if (list == null) {
			CommonFunc.alertDisplay(1, "���", "DB �������� ����", "�ٽ� �� �� ������ �ּ���.");
			return;
		} else {
			for (int i = 0; i < list.size(); i++) {
				adminVO = list.get(i);
				adminData.add(adminVO);
			}
		}

	} // end of totalList

	/************************************************************************/
	// ��ü �ü� �����ͺ��̽� �ҷ�����
	public void totalEquipList() {

		ArrayList<EquipVO> list = null;
		EquipDAO equipDAO = new EquipDAO();
		EquipVO equipVO = null;
		list = equipDAO.getEquipTotal();
		if (list == null) {
			CommonFunc.alertDisplay(1, "���", "DB �������� ����", "�ٽ� �� �� ������ �ּ���.");
			return;
		} else {
			for (int i = 0; i < list.size(); i++) {
				equipVO = list.get(i);
				equipData.add(equipVO);
			}
		}

	} // end of totalList

	// ��ü ����ǰ �����ͺ��̽� �ҷ�����
	public void totalItemList() {
		ArrayList<ItemVO> list = null;
		ItemDAO itemDAO = new ItemDAO();
		ItemVO itemVO = null;
		list = itemDAO.getItemTotal();
		if (list == null) {
			CommonFunc.alertDisplay(1, "���", "DB �������� ����", "�ٽ� �� �� ������ �ּ���.");
			return;
		} else {
			for (int i = 0; i < list.size(); i++) {
				itemVO = list.get(i);
				itemData.add(itemVO);
			}
		}
	}

	// ��ǰ �̹��� ����
	public String imageSave(File imgFile) {
		// 1. ���� ��ü ��θ� �ָ� �ű⿡������ ���ϸ� �̾Ƽ�(inputstream) : file.getName() <- �浿.jpg
		// 2. �̸� ���� ���̰�
		// 3. ������ ������ ����(outputstream)
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// �̹��� ���ϸ� ����
			fileName = "item" + System.currentTimeMillis() + "_" + imgFile.getName();
			bis = new BufferedInputStream(new FileInputStream(imgFile));
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

	// 16. �⺻ �̹��� ����
	public void imageViewInit() {
		localUrl = "/images/default.png";
		localImage = new Image(localUrl, false);
		imgItemImg.setImage(localImage);
	}
}

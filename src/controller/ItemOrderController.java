package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.UserOrderVO;


public class ItemOrderController implements Initializable {

	@FXML
	Button btnMenuDelete;
	@FXML
	Button btnItemRegi;
	@FXML
	Button btnChItemTotalPrice;
	@FXML
	private TextField txtItemAmount;
	@FXML
	private TextField txtChItemTotalPrice;
	@FXML ComboBox<String> cbxMenuName;
	@FXML TableView<UserOrderVO> tableView;
	@FXML AnchorPane anchorpane;

	//���̺�並 �������� �� ��ġ���� ��ü���� ������ �� �ִ� ������ ����
	private int selectedIndex;
	private ObservableList<UserOrderVO> selectOrder;
	// ���̺�信 �����ֱ����ؼ� ����� ����Ÿ
	ObservableList<UserOrderVO> data;
	private UserOrderDAO orderDAO;
	int orderNo; //������ ���̺��� ������ �������� ��ȣ ����(������ ����?)
	int enterNo=0;
	UserOrderDAO od = new UserOrderDAO();
	UserDAO ud = new UserDAO();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//�޺��ڽ��� �޴� ����
		cbxMenuName.setItems(FXCollections.observableArrayList("�Ŷ��","�����","ġ����","�ʱ������","¥�İ�Ƽ","�����","��¡���","����Ĩ","����ٽ�","������","����","�Ҽ���","ȸ��������","�ֵ���","�ݶ�","���̴�","�������ֽ�","�Ƹ޸�ī��","��ȭ��","��������"));
		//���̺������(������ ���� ���� ����)
		tableViewSetting();
		// �� ó���� ���̺�信 �����ͺ��̽����� �о ���̺�並 �����´�.
		//�ֹ��ϱ�(���)��ư
		btnItemRegi.setOnAction(event -> handlerBtnItemRegiAction(event));
		// ������ ��ǰ�� �Ѱ���(���û�ǰ����*����)btnChItemTotalPrice�� ������ ��
		btnChItemTotalPrice.setOnAction(event -> handlerBtnChItemTotalPriceAction(event));
		// �޴�����
		btnMenuDelete.setOnAction(event -> handlerBtnMenuDeleteAction(event));
		// ���̺�信�� ������ �� �̺�Ʈ ó�� ���
		tableView.setOnMousePressed(e ->  handlerTableViewPressedAction(e)); 
		
		txtItemAmount.setText("0");
		btnItemRegi.setDisable(true);
		btnMenuDelete.setDisable(true);
		txtChItemTotalPrice.setDisable(true);
		totalOrderList();
		
		//enterNo ���

		enterNo=ud.getEnterNo(LoginController.getCurrentUserID);
	}
	
	//��ǰ�̸� ��������(foreign Key ���)
	private void totalOrderList() {
		ArrayList<UserOrderVO> list = null;
		UserOrderDAO orderDAO = new UserOrderDAO();
		UserOrderVO orderVO = null;
		list = orderDAO.getOrderList();
		if (list == null) {
			CommonFunc.alertDisplay(1, "�����۸���Ʈ��������", "DB�������� ����", "Ȯ�����ּ���.");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			orderVO = list.get(i);
			data.add(orderVO);
		}
	}

	//������ ��ǰ�� �Ѱ���(���û�ǰ����*����)btnChItemTotalPrice�� ������ ��
	private void handlerBtnChItemTotalPriceAction(ActionEvent event) {
		cbxMenuName.setDisable(true);
		btnItemRegi.setDisable(true);
		if(Integer.parseInt(txtItemAmount.getText())==0 || txtItemAmount.getText().trim()==null){
			CommonFunc.alertDisplay(1, "��ĭ ����", "��ĭ�����߻�", "�ֹ��Ͻ� ������ �Է����ּ���.");
			cbxMenuName.setDisable(false);
		}else {
			//�Ŷ��
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("�Ŷ��")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}
			//�����
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("�����")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*4000));
				txtChItemTotalPrice.setDisable(true);
			}//ġ����
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("ġ����")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//�ʱ������
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("�ʱ������")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//���İ�Ƽ
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("���İ�Ƽ")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//�����
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("�����")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2000));
				txtChItemTotalPrice.setDisable(true);
			}//��¡���
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("��¡���")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2500));
				txtChItemTotalPrice.setDisable(true);
			}//����Ĩ
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("����Ĩ")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//����ٽ�
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("����ٽ�")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//������
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("������")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//����
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("����")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*4000));
				txtChItemTotalPrice.setDisable(true);
			}//�Ҽ���
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("�Ҽ���")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2500));
				txtChItemTotalPrice.setDisable(true);
			}//ȸ��������
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("ȸ��������")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//�ֵ���
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("�ֵ���")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//�ݶ�
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("�ݶ�")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2000));
				txtChItemTotalPrice.setDisable(true);
			}//���̴�
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("���̴�")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2000));
				txtChItemTotalPrice.setDisable(true);
			}//�������ֽ�
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("�������ֽ�")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//�Ƹ޸�ī��
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("�Ƹ޸�ī��")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3500));
				txtChItemTotalPrice.setDisable(true);
			}//��ȭ��
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("��ȭ��")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2500));
				txtChItemTotalPrice.setDisable(true);
			}//��������
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("��������")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}		
		}
		btnItemRegi.setDisable(false);
	}

	// ���̺�信�� ������ �� �̺�Ʈ ó�� ���
	private void handlerTableViewPressedAction(MouseEvent e) {
		cbxMenuName.setDisable(true);
		txtItemAmount.setDisable(true);
		btnChItemTotalPrice.setDisable(true);
		txtChItemTotalPrice.setDisable(true);
		btnMenuDelete.setDisable(false);
		btnItemRegi.setDisable(true);
		
		selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		selectOrder = tableView.getSelectionModel().getSelectedItems();
		// ������ �� ���̺� �ִ� ���� �����ͼ� ������ �ʵ忡 ����ִ´�.
		txtItemAmount.setText(String.valueOf(selectOrder.get(0).getItemOrderAmount()));
		txtChItemTotalPrice.setText(String.valueOf(selectOrder.get(0).getOrderPrice()));
		cbxMenuName.setValue(selectOrder.get(0).getItemCode());	
		//anchorPane�κ� �������� �ٽ� �ֹ������ϰ�
		anchorpane.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				cbxMenuName.setDisable(false);
				txtItemAmount.setDisable(false);
				txtItemAmount.setText("0");
				btnChItemTotalPrice.setDisable(false);
				btnItemRegi.setDisable(false);
				btnMenuDelete.setDisable(true);
			}
		});
	}
	
	//�ֹ��ϱ�(���)��ư
	private void handlerBtnItemRegiAction(ActionEvent event) {
		// ������ ��ǰ�ֹ��ݾ��� �ִ��� Ȯ���Ѵ�.
		UserOrderVO ovo = null;
		//�����ؽ�Ʈ�� ��ǰ�ֹ��ݾ׹�ư ������ �ʾ��� ��� ���
		if (txtItemAmount.getText().equals("") || txtChItemTotalPrice.getText().equals("")) {
			CommonFunc.alertDisplay(1, "����", "�ʼ��׸� ���Է¿��� �߻�", "��ǰ�ֹ��ݾװ� ������ üũ�Ͻÿ�.");
		} else {
			if (cbxMenuName.getSelectionModel().getSelectedItem().equals("�Ŷ��")) {
				ovo = new UserOrderVO(orderNo, enterNo, "la01", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			}else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("�����")) {
				ovo = new UserOrderVO(orderNo, 1, "la02", Integer.parseInt(txtItemAmount.getText().trim()),
						4000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("ġ����")) {
				ovo = new UserOrderVO(orderNo, 1, "la03", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("�ʱ������")) {
				ovo = new UserOrderVO(orderNo, 1, "la04", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("¥�İ�Ƽ")) {
				ovo = new UserOrderVO(orderNo, 1, "la05", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("�����")) {
				ovo = new UserOrderVO(orderNo, 1, "sn01", Integer.parseInt(txtItemAmount.getText().trim()),
						2000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("��¡���")) {
				ovo = new UserOrderVO(orderNo, 1, "sn02", Integer.parseInt(txtItemAmount.getText().trim()),
						2500 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("����Ĩ")) {
				ovo = new UserOrderVO(orderNo, 1, "sn03", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("����ٽ�")) {
				ovo = new UserOrderVO(orderNo, 1, "sn04", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("������")) {
				ovo = new UserOrderVO(orderNo, 1, "sn05", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("����")) {
				ovo = new UserOrderVO(orderNo, 1, "fd01", Integer.parseInt(txtItemAmount.getText().trim()),
						4000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("�Ҽ���")) {
				ovo = new UserOrderVO(orderNo, 1, "fd02", Integer.parseInt(txtItemAmount.getText().trim()),
						2500 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("ȸ��������")) {
				ovo = new UserOrderVO(orderNo, 1, "fd03", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("�ֵ���")) {
				ovo = new UserOrderVO(orderNo, 1, "fd04", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("�ݶ�")) {
				ovo = new UserOrderVO(orderNo, 1, "vb01", Integer.parseInt(txtItemAmount.getText().trim()),
						2000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("���̴�")) {
				ovo = new UserOrderVO(orderNo, 1, "vb02", Integer.parseInt(txtItemAmount.getText().trim()),
						2000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("�������ֽ�")) {
				ovo = new UserOrderVO(orderNo, 1, "vb03", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("�Ƹ޸�ī��")) {
				ovo = new UserOrderVO(orderNo, 1, "vb04", Integer.parseInt(txtItemAmount.getText().trim()),
						3500 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("��ȭ��")) {
				ovo = new UserOrderVO(orderNo, 1, "vb05", Integer.parseInt(txtItemAmount.getText().trim()),
						2500 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("��������")) {
				ovo = new UserOrderVO(orderNo, 1, "vb06", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "�ֹ����");
			}
			orderDAO = new UserOrderDAO();
			// �����ͺ��̽� ���̺� �Է°��� �Է��ϴ� �Լ�.
			int count;
			try {
				count = orderDAO.getOrderRegiste(ovo, enterNo);
				if (count != 0) {
					data.removeAll(data);
					totalOrderList();
					txtItemAmount.clear();
					btnChItemTotalPrice.setDisable(false);
					txtChItemTotalPrice.clear();
					btnMenuDelete.setDisable(true);
					btnMenuDelete.setDisable(true);
					btnItemRegi.setDisable(true);
					
					//ordertbl���� orderprice�� �� ���� ȭ�� �����ֱ�
					//enterNo���
					UserOrderDAO od = new UserOrderDAO();
					ud.getEnterNo(LoginController.getCurrentUserID);
					//enterNo =? �� �ֹ��� ��ǰ�� ���� ���
					int total=od.getTotalOrderMoneySum(ud.getEnterNo(LoginController.getCurrentUserID));
					CommonFunc.alertDisplay(1, "��ǰ�ֹ�", "�ֹ��� �Ϸ�Ǿ����ϴ�", "��ø� ��ٷ��ּ���. \n���ݱ��� �ֹ��� �� ��ǰ�� ������ "+total+"�Դϴ�.");
					txtItemAmount.setText("0");
				} else {
					CommonFunc.alertDisplay(1, "DB ��� ����", "DB ��� ����", "��� ����");
				}
			} catch (Exception e) {
				CommonFunc.alertDisplay(1, "��ϰ��ÿ���", "�ֹ��� �����Ͽ����ϴ�.", "�ٽ� �õ����ּ���.");
				e.printStackTrace();
			}
			cbxMenuName.setDisable(false);
		}
	}

	//���̺������(������ ���� ���� ����) - �ǵ�������...., �� �� ȭ�鿡 ����Բ�
	private void tableViewSetting() {
		//  ���̺��� ���̸���Ʈ����
		data = FXCollections.observableArrayList();
		//  ���̺��� ���̺�並 �������ϰ� ����
		tableView.setEditable(false);
		//  �ؽ�Ʈ��(����) ���ڸ� �Է�
		DecimalFormat format = new DecimalFormat("###");
		// ���� �Է½� ���� ���� �̺�Ʈ ó��
		txtItemAmount.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 3) {
				return null;
			} else {
				return event;
			}
		}));
		//���̺��� �÷�����
		TableColumn colItemNo = new TableColumn("NO");
		colItemNo.setMaxWidth(150);
		colItemNo.setStyle("-fx-alignment: CENTER;");
		colItemNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));

		TableColumn colItemName = new TableColumn("��ǰ�̸�");
		colItemName.setMaxWidth(150);
		colItemName.setStyle("-fx-alignment: CENTER;");
		colItemName.setCellValueFactory(new PropertyValueFactory<>("orderList"));

		TableColumn colItemAmount = new TableColumn<>("�ֹ�����");
		colItemAmount.setMaxWidth(150);
		colItemAmount.setStyle("-fx-alignment: CENTER;");
		colItemAmount.setCellValueFactory(new PropertyValueFactory<>("itemOrderAmount"));

		TableColumn colItemPrice = new TableColumn("�ֹ� �ݾ�");
		colItemPrice.setMaxWidth(150);
		colItemPrice.setStyle("-fx-alignment: CENTER;");
		colItemPrice.setCellValueFactory(new PropertyValueFactory<>("orderPrice"));
		
	// ���̺��� �÷��� ��ü�� ���̺�信 ����Ʈ�߰� �� �׸��߰� (ȭ�鿡 show)
		tableView.setItems(data);
		tableView.getColumns().addAll(colItemNo, colItemName, colItemAmount, colItemPrice);
	}

	// �޴�����
	private void handlerBtnMenuDeleteAction(ActionEvent event) {
		try {
			UserOrderDAO.getOrderDelete(selectOrder.get(0).getOrderNo());
			data.removeAll(data);
			totalOrderList();
			cbxMenuName.setDisable(false);
			txtItemAmount.setDisable(false);
			btnChItemTotalPrice.setDisable(false);
			txtItemAmount.setDisable(false);
			btnChItemTotalPrice.setDisable(true);
			txtItemAmount.setText("0");
		} catch (Exception e1) {
			CommonFunc.alertDisplay(1, "���� ����", "������ �޴��� �����ϴ�.", "������ ǰ���� �������� �ʽ��ϴ�.");
		}
	}

}

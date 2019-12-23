package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.OrderVO;

public class OrderController implements Initializable{

	@FXML private TableView<OrderVO> tableViewOrderDB;
	@FXML private Button btnOrderCancel;
	@FXML private Button btnOrderDone;
	private ObservableList<OrderVO> orderData;
	private ObservableList<OrderVO> selectedOrder;
	private int selectedOrderIndex;
	private boolean editDelete = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// �ֹ� ���̺� ����
		orderTableViewSettings();
		// �ֹ� ó�� �Ϸ� �� ���
		tableViewOrderDB.setOnMousePressed(event -> {
			handlerOrderDoneAndCancel(event);
		});
		
	}// end of initialize

	// �ֹ� ó�� �Ϸ� �� ��� 
	private void handlerOrderDoneAndCancel(MouseEvent event) {
		editDelete = true;

		selectedOrderIndex = tableViewOrderDB.getSelectionModel().getSelectedIndex();
		selectedOrder = tableViewOrderDB.getSelectionModel().getSelectedItems();	
		
		btnOrderDone.setOnAction(e -> {
			try {
			OrderVO ovo = new OrderVO(selectedOrder.get(0).getOrderNo(), selectedOrder.get(0).getOrderDate(), 
					selectedOrder.get(0).getSeatNo(), selectedOrder.get(0).getOrderId(), 
					selectedOrder.get(0).getOrderUser(), selectedOrder.get(0).getOrderList(), 
					selectedOrder.get(0).getOrderAmount(), selectedOrder.get(0).getOrderPrice(), "�����Ϸ�");
			
			OrderDAO orderDAO = new OrderDAO();
			OrderVO orderVO = orderDAO.getOrderStatusUpdate(ovo, selectedOrder.get(0).getOrderNo());
			if (editDelete == true && orderVO != null) {
				orderData.remove(selectedOrderIndex);
				orderData.add(selectedOrderIndex, ovo);
				editDelete = false;
			} else {
				throw new Exception("���� ����");
			}
			} catch (Exception e2) {
				CommonFunc.alertDisplay(1, "���� ����", "������ ���� ����", "�������ʵ带 Ȯ���� �ּ���." + e2.getStackTrace());
			}
		}); // end of done
		
		btnOrderCancel.setOnAction(e3 ->{
			try {
				OrderDAO orderDAO = new OrderDAO();
				orderDAO.getOrderDelete(selectedOrder.get(0).getOrderNo());
				orderData.removeAll(orderData);
				totalOrderList();
			} catch (Exception e) {
				CommonFunc.alertDisplay(1, "���� ����", "��������", "9�� �޼ҵ� Ȯ�� �ٶ�" + e.toString());
			}
			editDelete = false;
		});
		
	}

	// �ֹ� ���̺� ����
	public void orderTableViewSettings() {
		orderData = FXCollections.observableArrayList();
		tableViewOrderDB.setEditable(true);

		TableColumn colOrderNo = new TableColumn("�ֹ���ȣ");
		colOrderNo.setPrefWidth(70);
		colOrderNo.setStyle("-fx-alignment: CENTER;");
		colOrderNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));

		TableColumn colOrderDate = new TableColumn("�ֹ��Ͻ�");
		colOrderDate.setPrefWidth(120);
		colOrderDate.setStyle("-fx-alignment: CENTER;");
		colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		
//		TableColumn colSeatNo = new TableColumn("�¼���ȣ");
//		colSeatNo.setPrefWidth(70);
//		colSeatNo.setStyle("-fx-alignment: CENTER;");
//		colSeatNo.setCellValueFactory(new PropertyValueFactory<>("seatNo"));
		
		TableColumn colOrderId = new TableColumn("�ֹ���ID");
		colOrderId.setPrefWidth(120);
		colOrderId.setStyle("-fx-alignment: CENTER;");
		colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		
		TableColumn colOrderUser = new TableColumn("�ֹ��ڸ�");
		colOrderUser.setPrefWidth(100);
		colOrderUser.setStyle("-fx-alignment: CENTER;");
		colOrderUser.setCellValueFactory(new PropertyValueFactory<>("orderUser"));
		
		TableColumn colOrderList = new TableColumn("�ֹ�����");
		colOrderList.setPrefWidth(120);
		colOrderList.setStyle("-fx-alignment: CENTER;");
		colOrderList.setCellValueFactory(new PropertyValueFactory<>("orderList"));
		
		TableColumn colOrderAmount = new TableColumn("�ֹ�����");
		colOrderAmount.setPrefWidth(70);
		colOrderAmount.setStyle("-fx-alignment: CENTER;");
		colOrderAmount.setCellValueFactory(new PropertyValueFactory<>("orderAmount"));
		
		TableColumn colOrderPrice= new TableColumn("�ֹ��ݾ�");
		colOrderPrice.setPrefWidth(120);
		colOrderPrice.setStyle("-fx-alignment: CENTER;");
		colOrderPrice.setCellValueFactory(new PropertyValueFactory<>("orderPrice"));
		
		TableColumn colOrderStatus = new TableColumn("�ֹ�����");
		colOrderStatus.setPrefWidth(120);
		colOrderStatus.setStyle("-fx-alignment: CENTER;");
		colOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
		
		tableViewOrderDB.setItems(orderData);
		tableViewOrderDB.getColumns().addAll(colOrderNo, colOrderDate, colOrderId, colOrderUser, colOrderList,
				colOrderAmount, colOrderPrice, colOrderStatus);

		totalOrderList();
	}
	
	// ��ü �ֹ� �����ͺ��̽� �ҷ�����
	private void totalOrderList() {
		ArrayList<OrderVO> list = null;
		OrderDAO orderDAO = new OrderDAO();
		OrderVO orderVO = null;
		list = orderDAO.getOrderTotal();
		if (list == null) {
			CommonFunc.alertDisplay(1, "���", "DB �������� ����", "�ٽ� �� �� ������ �ּ���.");
			return;
		} else {
			for (int i = 0; i < list.size(); i++) {
				orderVO = list.get(i);
				orderData.add(orderVO);
			}
		}
		
	}

}

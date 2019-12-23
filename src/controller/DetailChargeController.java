package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.CommonFunc;
import controller.LoginController;
import controller.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.OrderVO;
import model.UserVO;

public class DetailChargeController implements Initializable {

	@FXML
	Label lbDetailName;
	@FXML
	Label lbDetailId;
	@FXML
	Label lbDetailStartTime;
	@FXML
	Label lbDetailUsedTime;
	@FXML
	Label lbDetailAvailableTime;
	@FXML
	Label lbDetailItemOrdered;
	@FXML
	Label lbTotalDetailMoney;
	@FXML
	Button btnDetailOk;
	
	private UserDAO ud = new UserDAO(); 
	private UserOrderDAO od = new UserOrderDAO(); 

	// ���̺�並 �������� �� ��ġ���� ��ü���� ������ �� �ִ� ������ ����
	// ���̺�信 �����ֱ����ؼ� ����� ����Ÿ
	ObservableList<OrderVO> data;
	private UserOrderDAO orderDAO;

	// ���� ������ ������� ���̵� ��������
	LoginController lc = new LoginController();
	String currentUserId = lc.getTxtUserId();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnDetailOk.setOnAction(event -> handlerBtnDetailOkAction(event));
		// ���̵� setting
		lbDetailId.setText(currentUserId);
		// �̸� setting
		// �ش� ���̵��� ���� ��������
		ArrayList<UserVO> list = null;
		list = UserDAO.getCurrentUserInfo(currentUserId);
		if (list == null) {
			CommonFunc.alertDisplay(1, "�α����� ȸ�� ����Ʈ��������", "DB�������� ����", "���� ���");
			return;
		}
		lbDetailName.setText(list.get(0).getUserId());
		

		  // enterNo =? �� �ֹ��� ��ǰ�� ���� ��� 
		String sum = null; 
		try { sum = String.valueOf(od.getTotalOrderMoneySum(ud.getEnterNo(currentUserId)));
		  lbDetailItemOrdered.setText(sum); }catch(	Exception e){
		CommonFunc.alertDisplay(1, "�� �ֹ��ݾ� �������� ����", "�� �ֹ��ݾ� �������⸦ �����Ͽ����ϴ�.", "�ٽ� �õ����ּ���.");
		e.printStackTrace();
	}

	}

	// �󼼿�� ��ư ���� �ֹ��󼼺���â �ҷ�����
	public void handlerBtnDetailOrderViewAction(ActionEvent event) {
		try {
			Parent searchViewDetail;
			searchViewDetail = FXMLLoader.load(getClass().getResource("/view/OrderDetailCharge.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			// ����â �ҷ�����(����â �� �ƹ� �����̸� �������. �ı� �ϳ� �θ��� �� ��ü ���� �ּ� �����ִϱ�)
			stageDialog.initOwner(lbDetailStartTime.getScene().getWindow());
			stageDialog.setTitle("�ֹ��󼼺���â");
			Scene scene = new Scene(searchViewDetail);
			stageDialog.setScene(scene);
			stageDialog.setResizable(true);
			stageDialog.show();
			// ���� �ֹ��󼼺���â�� ��Ʈ�ѷ� ������ ���� ������ ã�������, ã���ִ¹���
			Button btnOrderDetailOk = (Button) searchViewDetail.lookup("#btnOrderDetailOk");
			Label lbOrderPrepaidMoney = (Label) searchViewDetail.lookup("#lbOrderPrepaidMoney");

	
			// enterNo =? �� �ֹ��� ��ǰ�� ���� ���
			String sum = null;
			try {
				sum = String.valueOf(od.getTotalOrderMoneySum(ud.getEnterNo(currentUserId)));
				lbOrderPrepaidMoney.setText(sum);
			} catch (Exception e) {
				CommonFunc.alertDisplay(1, "���ֹ��ݾ�ȭ�����", "â�� �ҷ��� �� �����ϴ�.", "�ٽ� �õ��ϼ���.");
				System.out.println("�󼼿�� ��ư ���� �ֹ��󼼺���â �ҷ����� �κ� �����߻�");
			}
			// �󼼿�ݹ�ư ���� �ֹ��󼼺���â �������ư
			btnOrderDetailOk.setOnAction(e6 -> {
				stageDialog.close();
			});
		} catch (IOException e) {
			CommonFunc.alertDisplay(3, "�󼼿��â ����", "â�� �ҷ��� �� �����ϴ�.", "�ٽ� �õ��ϼ���.");
		} catch (NullPointerException e1) {
			CommonFunc.alertDisplay(3, "�󼼿��â ����", "â�� �ҷ��� �� �����ϴ�.", "�ٽ� �õ��ϼ���.");
			e1.getStackTrace();
		}
	}

	// Ȯ�ι�ư
	public void handlerBtnDetailOkAction(ActionEvent event) {
		((Stage) btnDetailOk.getScene().getWindow()).close();
	}
}

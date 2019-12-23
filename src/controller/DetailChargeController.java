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

	// 테이블뷰를 선택했을 때 위치값과 객체값을 저장할 수 있는 변수를 선언
	// 테이블뷰에 보여주기위해서 저장된 데이타
	ObservableList<OrderVO> data;
	private UserOrderDAO orderDAO;

	// 현재 접속한 사용자의 아이디 가져오기
	LoginController lc = new LoginController();
	String currentUserId = lc.getTxtUserId();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnDetailOk.setOnAction(event -> handlerBtnDetailOkAction(event));
		// 아이디 setting
		lbDetailId.setText(currentUserId);
		// 이름 setting
		// 해당 아이디의 정보 가져오기
		ArrayList<UserVO> list = null;
		list = UserDAO.getCurrentUserInfo(currentUserId);
		if (list == null) {
			CommonFunc.alertDisplay(1, "로그인한 회원 리스트가져오기", "DB가져오기 오류", "점검 요망");
			return;
		}
		lbDetailName.setText(list.get(0).getUserId());
		

		  // enterNo =? 가 주문한 상품총 가격 얻기 
		String sum = null; 
		try { sum = String.valueOf(od.getTotalOrderMoneySum(ud.getEnterNo(currentUserId)));
		  lbDetailItemOrdered.setText(sum); }catch(	Exception e){
		CommonFunc.alertDisplay(1, "총 주문금액 가져오기 오류", "총 주문금액 가져오기를 실패하였습니다.", "다시 시도해주세요.");
		e.printStackTrace();
	}

	}

	// 상세요금 버튼 안의 주문상세보기창 불러오기
	public void handlerBtnDetailOrderViewAction(ActionEvent event) {
		try {
			Parent searchViewDetail;
			searchViewDetail = FXMLLoader.load(getClass().getResource("/view/OrderDetailCharge.fxml"));
			Stage stageDialog = new Stage(StageStyle.UTILITY);
			stageDialog.initModality(Modality.WINDOW_MODAL);
			// 주인창 불러오기(주인창 속 아무 변수이름 상관없음. 식구 하나 부르면 그 전체 가족 주소 돌려주니까)
			stageDialog.initOwner(lbDetailStartTime.getScene().getWindow());
			stageDialog.setTitle("주문상세보기창");
			Scene scene = new Scene(searchViewDetail);
			stageDialog.setScene(scene);
			stageDialog.setResizable(true);
			stageDialog.show();
			// 작은 주문상세보기창의 컨트롤러 연결이 없기 때문에 찾아줘야함, 찾아주는문구
			Button btnOrderDetailOk = (Button) searchViewDetail.lookup("#btnOrderDetailOk");
			Label lbOrderPrepaidMoney = (Label) searchViewDetail.lookup("#lbOrderPrepaidMoney");

	
			// enterNo =? 가 주문한 상품총 가격 얻기
			String sum = null;
			try {
				sum = String.valueOf(od.getTotalOrderMoneySum(ud.getEnterNo(currentUserId)));
				lbOrderPrepaidMoney.setText(sum);
			} catch (Exception e) {
				CommonFunc.alertDisplay(1, "총주문금액화면오류", "창을 불러올 수 없습니다.", "다시 시도하세요.");
				System.out.println("상세요금 버튼 안의 주문상세보기창 불러오기 부분 오류발생");
			}
			// 상세요금버튼 안의 주문상세보기창 나가기버튼
			btnOrderDetailOk.setOnAction(e6 -> {
				stageDialog.close();
			});
		} catch (IOException e) {
			CommonFunc.alertDisplay(3, "상세요금창 오류", "창을 불러올 수 없습니다.", "다시 시도하세요.");
		} catch (NullPointerException e1) {
			CommonFunc.alertDisplay(3, "상세요금창 오류", "창을 불러올 수 없습니다.", "다시 시도하세요.");
			e1.getStackTrace();
		}
	}

	// 확인버튼
	public void handlerBtnDetailOkAction(ActionEvent event) {
		((Stage) btnDetailOk.getScene().getWindow()).close();
	}
}

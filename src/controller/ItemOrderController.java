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

	//테이블뷰를 선택했을 때 위치값과 객체값을 저장할 수 있는 변수를 선언
	private int selectedIndex;
	private ObservableList<UserOrderVO> selectOrder;
	// 테이블뷰에 보여주기위해서 저장된 데이타
	ObservableList<UserOrderVO> data;
	private UserOrderDAO orderDAO;
	int orderNo; //삭제시 테이블에서 선택한 아이템의 번호 저장(아이템 순서?)
	int enterNo=0;
	UserOrderDAO od = new UserOrderDAO();
	UserDAO ud = new UserDAO();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//콤보박스에 메뉴 설정
		cbxMenuName.setItems(FXCollections.observableArrayList("신라면","진라면","치즈라면","너구리라면","짜파게티","새우깡","오징어땅콩","초코칩","쿠쿠다스","다이제","만두","소세지","회오리감자","핫도그","콜라","사이다","오렌지주스","아메리카노","쌍화탕","닥터페퍼"));
		//테이블설정기능(데이터 포맷 설정 포함)
		tableViewSetting();
		// 맨 처음에 테이블뷰에 데이터베이스값을 읽어서 테이블뷰를 가져온다.
		//주문하기(등록)버튼
		btnItemRegi.setOnAction(event -> handlerBtnItemRegiAction(event));
		// 선택한 상품의 총가격(선택상품가격*개수)btnChItemTotalPrice을 눌렀을 때
		btnChItemTotalPrice.setOnAction(event -> handlerBtnChItemTotalPriceAction(event));
		// 메뉴삭제
		btnMenuDelete.setOnAction(event -> handlerBtnMenuDeleteAction(event));
		// 테이블뷰에서 눌렀을 때 이벤트 처리 기능
		tableView.setOnMousePressed(e ->  handlerTableViewPressedAction(e)); 
		
		txtItemAmount.setText("0");
		btnItemRegi.setDisable(true);
		btnMenuDelete.setDisable(true);
		txtChItemTotalPrice.setDisable(true);
		totalOrderList();
		
		//enterNo 얻기

		enterNo=ud.getEnterNo(LoginController.getCurrentUserID);
	}
	
	//상품이름 가져오기(foreign Key 사용)
	private void totalOrderList() {
		ArrayList<UserOrderVO> list = null;
		UserOrderDAO orderDAO = new UserOrderDAO();
		UserOrderVO orderVO = null;
		list = orderDAO.getOrderList();
		if (list == null) {
			CommonFunc.alertDisplay(1, "아이템리스트가져오기", "DB가져오기 오류", "확인해주세요.");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			orderVO = list.get(i);
			data.add(orderVO);
		}
	}

	//선택한 상품의 총가격(선택상품가격*개수)btnChItemTotalPrice을 눌렀을 때
	private void handlerBtnChItemTotalPriceAction(ActionEvent event) {
		cbxMenuName.setDisable(true);
		btnItemRegi.setDisable(true);
		if(Integer.parseInt(txtItemAmount.getText())==0 || txtItemAmount.getText().trim()==null){
			CommonFunc.alertDisplay(1, "빈칸 오류", "빈칸오류발생", "주문하실 개수를 입력해주세요.");
			cbxMenuName.setDisable(false);
		}else {
			//신라면
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("신라면")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}
			//진라면
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("진라면")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*4000));
				txtChItemTotalPrice.setDisable(true);
			}//치즈라면
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("치즈라면")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//너구리라면
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("너구리라면")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//찌파게티
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("찌파게티")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//새우깡
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("새우깡")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2000));
				txtChItemTotalPrice.setDisable(true);
			}//오징어땅콩
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("오징어땅콩")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2500));
				txtChItemTotalPrice.setDisable(true);
			}//초코칩
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("초코칩")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//쿠쿠다스
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("쿠쿠다스")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//다이제
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("다이제")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//만두
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("만두")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*4000));
				txtChItemTotalPrice.setDisable(true);
			}//소세지
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("소세지")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2500));
				txtChItemTotalPrice.setDisable(true);
			}//회오리감자
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("회오리감자")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//핫도그
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("핫도그")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//콜라
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("콜라")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2000));
				txtChItemTotalPrice.setDisable(true);
			}//사이다
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("사이다")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2000));
				txtChItemTotalPrice.setDisable(true);
			}//오렌지주스
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("오렌지주스")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}//아메리카노
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("아메리카노")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3500));
				txtChItemTotalPrice.setDisable(true);
			}//쌍화탕
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("쌍화탕")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*2500));
				txtChItemTotalPrice.setDisable(true);
			}//닥터페퍼
			if(cbxMenuName.getSelectionModel().getSelectedItem().equals("닥터페퍼")) {
				int itemAmount =Integer.parseInt(txtItemAmount.getText().trim());
				txtChItemTotalPrice.setText(String.valueOf(itemAmount*3000));
				txtChItemTotalPrice.setDisable(true);
			}		
		}
		btnItemRegi.setDisable(false);
	}

	// 테이블뷰에서 눌렀을 때 이벤트 처리 기능
	private void handlerTableViewPressedAction(MouseEvent e) {
		cbxMenuName.setDisable(true);
		txtItemAmount.setDisable(true);
		btnChItemTotalPrice.setDisable(true);
		txtChItemTotalPrice.setDisable(true);
		btnMenuDelete.setDisable(false);
		btnItemRegi.setDisable(true);
		
		selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		selectOrder = tableView.getSelectionModel().getSelectedItems();
		// 눌렀을 때 테이블에 있는 값을 가져와서 데이터 필드에 집어넣는다.
		txtItemAmount.setText(String.valueOf(selectOrder.get(0).getItemOrderAmount()));
		txtChItemTotalPrice.setText(String.valueOf(selectOrder.get(0).getOrderPrice()));
		cbxMenuName.setValue(selectOrder.get(0).getItemCode());	
		//anchorPane부분 눌렀을때 다시 주문가능하게
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
	
	//주문하기(등록)버튼
	private void handlerBtnItemRegiAction(ActionEvent event) {
		// 개수와 상품주문금액이 있는지 확인한다.
		UserOrderVO ovo = null;
		//개수텍스트와 상품주문금액버튼 누르지 않았을 경우 경고
		if (txtItemAmount.getText().equals("") || txtChItemTotalPrice.getText().equals("")) {
			CommonFunc.alertDisplay(1, "오류", "필수항목 미입력오류 발생", "상품주문금액과 개수를 체크하시오.");
		} else {
			if (cbxMenuName.getSelectionModel().getSelectedItem().equals("신라면")) {
				ovo = new UserOrderVO(orderNo, enterNo, "la01", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			}else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("진라면")) {
				ovo = new UserOrderVO(orderNo, 1, "la02", Integer.parseInt(txtItemAmount.getText().trim()),
						4000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("치즈라면")) {
				ovo = new UserOrderVO(orderNo, 1, "la03", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("너구리라면")) {
				ovo = new UserOrderVO(orderNo, 1, "la04", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("짜파게티")) {
				ovo = new UserOrderVO(orderNo, 1, "la05", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("새우깡")) {
				ovo = new UserOrderVO(orderNo, 1, "sn01", Integer.parseInt(txtItemAmount.getText().trim()),
						2000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("오징어땅콩")) {
				ovo = new UserOrderVO(orderNo, 1, "sn02", Integer.parseInt(txtItemAmount.getText().trim()),
						2500 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("초코칩")) {
				ovo = new UserOrderVO(orderNo, 1, "sn03", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("쿠쿠다스")) {
				ovo = new UserOrderVO(orderNo, 1, "sn04", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("다이제")) {
				ovo = new UserOrderVO(orderNo, 1, "sn05", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("만두")) {
				ovo = new UserOrderVO(orderNo, 1, "fd01", Integer.parseInt(txtItemAmount.getText().trim()),
						4000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("소세지")) {
				ovo = new UserOrderVO(orderNo, 1, "fd02", Integer.parseInt(txtItemAmount.getText().trim()),
						2500 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("회오리감자")) {
				ovo = new UserOrderVO(orderNo, 1, "fd03", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("핫도그")) {
				ovo = new UserOrderVO(orderNo, 1, "fd04", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("콜라")) {
				ovo = new UserOrderVO(orderNo, 1, "vb01", Integer.parseInt(txtItemAmount.getText().trim()),
						2000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("사이다")) {
				ovo = new UserOrderVO(orderNo, 1, "vb02", Integer.parseInt(txtItemAmount.getText().trim()),
						2000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("오렌지주스")) {
				ovo = new UserOrderVO(orderNo, 1, "vb03", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("아메리카노")) {
				ovo = new UserOrderVO(orderNo, 1, "vb04", Integer.parseInt(txtItemAmount.getText().trim()),
						3500 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("쌍화탕")) {
				ovo = new UserOrderVO(orderNo, 1, "vb05", Integer.parseInt(txtItemAmount.getText().trim()),
						2500 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			} else if (cbxMenuName.getSelectionModel().getSelectedItem().equals("닥터페퍼")) {
				ovo = new UserOrderVO(orderNo, 1, "vb06", Integer.parseInt(txtItemAmount.getText().trim()),
						3000 * (Integer.parseInt(txtItemAmount.getText().trim())), "주문대기");
			}
			orderDAO = new UserOrderDAO();
			// 데이터베이스 테이블에 입력값을 입력하는 함수.
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
					
					//ordertbl에서 orderprice들 다 합한 화면 보여주기
					//enterNo얻기
					UserOrderDAO od = new UserOrderDAO();
					ud.getEnterNo(LoginController.getCurrentUserID);
					//enterNo =? 가 주문한 상품총 가격 얻기
					int total=od.getTotalOrderMoneySum(ud.getEnterNo(LoginController.getCurrentUserID));
					CommonFunc.alertDisplay(1, "상품주문", "주문이 완료되었습니다", "잠시만 기다려주세요. \n지금까지 주문한 총 상품의 가격은 "+total+"입니다.");
					txtItemAmount.setText("0");
				} else {
					CommonFunc.alertDisplay(1, "DB 등록 실패", "DB 등록 실패", "등록 오류");
				}
			} catch (Exception e) {
				CommonFunc.alertDisplay(1, "등록관련오류", "주문이 실패하였습니다.", "다시 시도해주세요.");
				e.printStackTrace();
			}
			cbxMenuName.setDisable(false);
		}
	}

	//테이블설정기능(데이터 포맷 설정 포함) - 건들지말자...., 맨 위 화면에 보기게끔
	private void tableViewSetting() {
		//  테이블설정 얼레이리스트설정
		data = FXCollections.observableArrayList();
		//  테이블설정 테이블뷰를 편집못하게 설정
		tableView.setEditable(false);
		//  텍스트라벨(개수) 숫자만 입력
		DecimalFormat format = new DecimalFormat("###");
		// 개수 입력시 길이 제한 이벤트 처리
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
		//테이블설정 컬럼셋팅
		TableColumn colItemNo = new TableColumn("NO");
		colItemNo.setMaxWidth(150);
		colItemNo.setStyle("-fx-alignment: CENTER;");
		colItemNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));

		TableColumn colItemName = new TableColumn("상품이름");
		colItemName.setMaxWidth(150);
		colItemName.setStyle("-fx-alignment: CENTER;");
		colItemName.setCellValueFactory(new PropertyValueFactory<>("orderList"));

		TableColumn colItemAmount = new TableColumn<>("주문개수");
		colItemAmount.setMaxWidth(150);
		colItemAmount.setStyle("-fx-alignment: CENTER;");
		colItemAmount.setCellValueFactory(new PropertyValueFactory<>("itemOrderAmount"));

		TableColumn colItemPrice = new TableColumn("주문 금액");
		colItemPrice.setMaxWidth(150);
		colItemPrice.setStyle("-fx-alignment: CENTER;");
		colItemPrice.setCellValueFactory(new PropertyValueFactory<>("orderPrice"));
		
	// 테이블설정 컬럼들 객체를 테이블뷰에 리스트추가 및 항목추가 (화면에 show)
		tableView.setItems(data);
		tableView.getColumns().addAll(colItemNo, colItemName, colItemAmount, colItemPrice);
	}

	// 메뉴삭제
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
			CommonFunc.alertDisplay(1, "삭제 오류", "삭제할 메뉴가 없습니다.", "삭제할 품목이 존재하지 않습니다.");
		}
	}

}

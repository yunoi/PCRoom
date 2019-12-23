package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.IncomeVO;

public class IncomeController implements Initializable {

	@FXML
	private Label lbTotalVisitor;
	@FXML
	private Label lbCurrentMember;
	@FXML
	private Label lbNewMember;
	@FXML
	private DatePicker selectDatePicker;
	@FXML
	private Label lbPCIncome;
	@FXML
	private Label lbItemIncome;
	@FXML
	private Label lbTotalIncome;
	@FXML
	private Label lbCurrentDate;
	@FXML
	private TableView<IncomeVO> tableViewMonthIncome;
	@FXML
	private Label lbMonth;
	@FXML
	private Label lbMonthYear;
	@FXML
	private Label lbRightArrow;
	@FXML
	private Label lbLeftArrow;
	@FXML
	private Label lbLeftArrowYear;
	@FXML
	private Label lbRightArrowYear;
	@FXML
	private Button btnTableView;
	@FXML
	private Label lbYear;
	@FXML
	private Button btnBarChartYear;
	@FXML
	private Label lbPCIncomeYear;
	@FXML
	private Label lbItemIncomeYear;
	@FXML
	private Label lbTotalIncomeYear;
	@FXML
	private Button btnPieChart;
	@FXML
	private Button btnLineChart;
	@FXML
	private Button btnBarChart;

	private IncomeDAO idao = new IncomeDAO();
	private LocalDate date;

	private ObservableList<IncomeVO> incomeData; // 테이블 뷰에 보여주기 위해서 저장된 데이터

	private int month;
	private int monthYear;
	private int year;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 일매출
		viewDayIncomeAction();
//		// 월매출
		viewMonthIncomeAction();
		// 연매출
		viewYearIncomeAction();
	}

	// 일매출
	public void viewDayIncomeAction() {
		lbCurrentDate.setText(date.now().toString()); // 현재날짜
		lbTotalVisitor.setText(String.valueOf(idao.getDayUserCount(lbCurrentDate.getText()))); // 총 방문자
		lbCurrentMember.setText(String.valueOf(idao.getCurrentMemberCount())); // 현재 총 가입자수
		lbNewMember.setText(String.valueOf(idao.getNewMemberCount(lbCurrentDate.getText()))); // 신규 가입자 수

		// 일 피씨매출
		lbPCIncome.setText(String.valueOf(idao.getDayPCIncomeInfo(lbCurrentDate.getText())));
		// 일 상품매출
		lbItemIncome.setText(String.valueOf(idao.getDayItemIncomeInfo(lbCurrentDate.getText())));
		// 일 총 매출
		lbTotalIncome.setText(String.valueOf(idao.getDayTotalIncomeInfo(lbCurrentDate.getText())));

		selectDatePicker.setOnAction(event -> {
			date = selectDatePicker.getValue(); // 로컬데이트 가져와서
			lbCurrentDate.setText("" + date); // 데이트값 찍어줌
			lbTotalVisitor.setText(String.valueOf(idao.getDayUserCount(lbCurrentDate.getText()))); // 총 방문자
			lbCurrentMember.setText(String.valueOf(idao.getCurrentMemberCount())); // 현재 총 가입자수
			lbNewMember.setText(String.valueOf(idao.getNewMemberCount(lbCurrentDate.getText()))); // 신규 가입자 수

			// 일 피씨매출
			lbPCIncome.setText(String.valueOf(idao.getDayPCIncomeInfo(lbCurrentDate.getText())));
			// 일 상품매출
			lbItemIncome.setText(String.valueOf(idao.getDayItemIncomeInfo(lbCurrentDate.getText())));
			// 일 총 매출
			lbTotalIncome.setText(String.valueOf(idao.getDayTotalIncomeInfo(lbCurrentDate.getText())));
		});
		btnPieChart.setOnAction(event -> {
			Parent pieChartRoot;
			try {
				pieChartRoot = FXMLLoader.load(getClass().getResource("/view/piechart.fxml"));
				Stage stage = new Stage(StageStyle.UTILITY);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(btnPieChart.getScene().getWindow());

				PieChart pieChart = (PieChart) pieChartRoot.lookup("#pieChart");
				// 그래프 그리기
				pieChart.setData(FXCollections.observableArrayList(
						new PieChart.Data("PC", Double.parseDouble(lbPCIncome.getText())),
						new PieChart.Data("상품", Double.parseDouble(lbItemIncome.getText()))));

				Scene scene = new Scene(pieChartRoot);
				stage.setScene(scene);
				stage.setTitle("오늘의 매출 차트");
				stage.setResizable(false);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	// 월매출
	public void viewMonthIncomeAction() {
		// 월 매출 테이블

		// 2. 테이블 설정 (arraylist로 설정)
		incomeData = FXCollections.observableArrayList();
		// 3, 테이블뷰를 편집못하게 설정
		tableViewMonthIncome.setEditable(false);

		// 5. 테이블설정에서 컬럼 세팅
		TableColumn colincomeYear = new TableColumn("년");
		colincomeYear.setMaxWidth(70);
		colincomeYear.setStyle("-fx-alignment: CENTER;");
		colincomeYear.setCellValueFactory(new PropertyValueFactory<>("incomeYear"));

		TableColumn colincomeMonth = new TableColumn("월");
		colincomeMonth.setMaxWidth(70);
		colincomeMonth.setStyle("-fx-alignment: CENTER;");
		colincomeMonth.setCellValueFactory(new PropertyValueFactory<>("incomeMonth"));

		TableColumn colincomeDay = new TableColumn("일");
		colincomeDay.setMaxWidth(70);
		colincomeDay.setStyle("-fx-alignment: CENTER;");
		colincomeDay.setCellValueFactory(new PropertyValueFactory<>("incomeDay"));

		TableColumn coldayPCIncome = new TableColumn("PC매출");
		coldayPCIncome.setMaxWidth(100);
		coldayPCIncome.setStyle("-fx-alignment: CENTER;");
		coldayPCIncome.setCellValueFactory(new PropertyValueFactory<>("dayPCIncome"));

		TableColumn coldayItemIncome = new TableColumn("상품매출");
		coldayItemIncome.setMaxWidth(100);
		coldayItemIncome.setStyle("-fx-alignment: CENTER;");
		coldayItemIncome.setCellValueFactory(new PropertyValueFactory<>("dayItemIncome"));

		TableColumn coldayTotalIncome = new TableColumn("총매출");
		coldayTotalIncome.setMaxWidth(100);
		coldayTotalIncome.setStyle("-fx-alignment: CENTER;");
		coldayTotalIncome.setCellValueFactory(new PropertyValueFactory<>("dayTotalIncome"));

		// 5. 테이블설정 컬럼 객체들을 테이블뷰에 리스트 추가 및 항목 추가한다.
		tableViewMonthIncome.setItems(incomeData);
		tableViewMonthIncome.getColumns().addAll(colincomeYear, colincomeMonth, colincomeDay, coldayPCIncome,
				coldayItemIncome, coldayTotalIncome);

		totalIncomeList();

		lbRightArrow.setOnMouseClicked(event -> {
			month = Integer.parseInt(lbMonth.getText());
			monthYear = Integer.parseInt(lbMonthYear.getText());
			month++;
			if (month == 13) {
				month = 1;
				monthYear++;
			}
			lbMonth.setText(String.valueOf(month));
			lbMonthYear.setText(String.valueOf(monthYear));
			totalIncomeList();
		});
		lbLeftArrow.setOnMouseClicked(event -> {
			month = Integer.parseInt(lbMonth.getText());
			monthYear = Integer.parseInt(lbMonthYear.getText());
			month--;
			if (month == 0) {
				month = 12;
				monthYear--;
			}
			lbMonth.setText(String.valueOf(month));
			lbMonthYear.setText(String.valueOf(monthYear));
			totalIncomeList();
		});

		btnLineChart.setOnAction(event -> {
			Parent lineChartRoot;
			try {
				lineChartRoot = FXMLLoader.load(getClass().getResource("/view/linechart.fxml"));
				Stage stage = new Stage(StageStyle.UTILITY);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(btnPieChart.getScene().getWindow());

				LineChart lineChart = (LineChart) lineChartRoot.lookup("#lineChart");
				// 그래프 그리기

				// pc 매출
				XYChart.Series lineMonthPCIncome = new XYChart.Series();
				lineMonthPCIncome.setName("PC매출"); // 차트의 라벨
				ObservableList PCIncomeList = FXCollections.observableArrayList();
				for (int i = 0; i < incomeData.size(); i++) {
					PCIncomeList.add(
							new XYChart.Data(incomeData.get(i).getIncomeDay(), incomeData.get(i).getDayPCIncome()));
				}
				lineMonthPCIncome.setData(PCIncomeList);
				lineChart.getData().add(lineMonthPCIncome);

				// 상품매출
				XYChart.Series lineMonthItemIncome = new XYChart.Series();
				lineMonthItemIncome.setName("상품매출"); // 차트의 라벨
				ObservableList ItemIncomeList = FXCollections.observableArrayList();
				for (int i = 0; i < incomeData.size(); i++) {
					ItemIncomeList.add(
							new XYChart.Data(incomeData.get(i).getIncomeDay(), incomeData.get(i).getDayItemIncome()));
				}
				lineMonthItemIncome.setData(ItemIncomeList);
				lineChart.getData().add(lineMonthItemIncome);

				// 총매출
				XYChart.Series lineMonthTotalIncome = new XYChart.Series();
				lineMonthTotalIncome.setName("월 총 매출"); // 차트의 라벨
				ObservableList totalIncomeList = FXCollections.observableArrayList();
				for (int i = 0; i < incomeData.size(); i++) {
					totalIncomeList.add(
							new XYChart.Data(incomeData.get(i).getIncomeDay(), incomeData.get(i).getDayTotalIncome()));
				}
				lineMonthTotalIncome.setData(totalIncomeList);
				lineChart.getData().add(lineMonthTotalIncome);

				Scene scene = new Scene(lineChartRoot);
				stage.setScene(scene);
				stage.setTitle("이번 달의 매출 차트");
				stage.setResizable(false);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	// 월 매출 테이블 전체 데이터 보기
	private void totalIncomeList() {
		ArrayList<IncomeVO> list = null;
		IncomeDAO incomeDAO = new IncomeDAO();
		IncomeVO incomeVO = null;
		month = Integer.parseInt(lbMonth.getText());
		monthYear = Integer.parseInt(lbMonthYear.getText());
		list = incomeDAO.getIncomeTotal(String.valueOf(monthYear), String.valueOf(month));
//		System.out.println(String.valueOf(monthYear)+" "+ String.valueOf(month));
		if (list == null) {
			CommonFunc.alertDisplay(1, "경고", "DB 가져오기 오류", "다시 한 번 점검해 주세요.");
			return;
		} else {
			incomeData.removeAll(incomeData);
			for (int i = 0; i < list.size(); i++) {
				incomeVO = list.get(i);
				incomeData.add(incomeVO);
			}
		}

	}

	// 연매출
	public void viewYearIncomeAction() {
		lbPCIncomeYear.setText(String.valueOf(idao.getYearPCIncomeInfo(lbYear.getText())));
		lbItemIncomeYear.setText(String.valueOf(idao.getYearItemIncomeInfo(lbYear.getText())));
		lbTotalIncomeYear.setText(String.valueOf(idao.getYearTotalIncomeInfo(lbYear.getText())));

		btnBarChart.setOnAction(event -> {
			Parent barChartRoot;
			try {
				barChartRoot = FXMLLoader.load(getClass().getResource("/view/barchart.fxml"));
				Stage stage = new Stage(StageStyle.UTILITY);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(btnPieChart.getScene().getWindow());

				BarChart barChart = (BarChart) barChartRoot.lookup("#barChart");
				// 그래프 그리기

				// pc 매출
				XYChart.Series barYearPCIncome = new XYChart.Series();
				barYearPCIncome.setName("PC매출"); // 차트의 라벨
				ObservableList PCIncomeList = FXCollections.observableArrayList();
//				for (int i = 0; i < incomeData.size(); i++) {
//					PCIncomeList.add(
//							new XYChart.Data(incomeData.get(i).getIncomeYear(), incomeData.get(i).getDayPCIncome()));
//				}
				PCIncomeList.add(new XYChart.Data(lbYear.getText(), Integer.parseInt(lbPCIncomeYear.getText())));
				barYearPCIncome.setData(PCIncomeList);
				barChart.getData().add(barYearPCIncome);

				// 상품매출
				XYChart.Series barYearItemIncome = new XYChart.Series();
				barYearItemIncome.setName("상품매출"); // 차트의 라벨
				ObservableList ItemIncomeList = FXCollections.observableArrayList();
//				for (int i = 0; i < incomeData.size(); i++) {
//					ItemIncomeList.add(
//							new XYChart.Data(incomeData.get(i).getIncomeYear(), incomeData.get(i).getDayItemIncome()));
//				}
				ItemIncomeList.add(new XYChart.Data(lbYear.getText(), Integer.parseInt(lbItemIncomeYear.getText())));
				barYearItemIncome.setData(ItemIncomeList);
				barChart.getData().add(barYearItemIncome);

				// 총매출
				XYChart.Series barYearTotalIncome = new XYChart.Series();
				barYearTotalIncome.setName("연 총 매출"); // 차트의 라벨
				ObservableList totalIncomeList = FXCollections.observableArrayList();
//				for (int i = 0; i < incomeData.size(); i++) {
//					totalIncomeList.add(
//							new XYChart.Data(incomeData.get(i).getIncomeYear(), incomeData.get(i).getDayTotalIncome()));
//				}
				totalIncomeList.add(new XYChart.Data(lbYear.getText(), Integer.parseInt(lbTotalIncomeYear.getText())));
				barYearTotalIncome.setData(totalIncomeList);
				barChart.getData().add(barYearTotalIncome);

				Scene scene = new Scene(barChartRoot);
				stage.setScene(scene);
				stage.setTitle("이번 해의 매출 차트");
				stage.setResizable(false);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		lbRightArrowYear.setOnMouseClicked(event -> {
			year = Integer.parseInt(lbYear.getText());
			year++;
			lbYear.setText(String.valueOf(year));

			lbPCIncomeYear.setText(String.valueOf(idao.getYearPCIncomeInfo(String.valueOf(year))));
			lbItemIncomeYear.setText(String.valueOf(idao.getYearItemIncomeInfo(String.valueOf(year))));
			lbTotalIncomeYear.setText(String.valueOf(idao.getYearTotalIncomeInfo(String.valueOf(year))));

		});
		lbLeftArrowYear.setOnMouseClicked(event -> {
			year = Integer.parseInt(lbYear.getText());
			year--;
			lbYear.setText(String.valueOf(year));

			lbPCIncomeYear.setText(String.valueOf(idao.getYearPCIncomeInfo(String.valueOf(year))));
			lbItemIncomeYear.setText(String.valueOf(idao.getYearItemIncomeInfo(String.valueOf(year))));
			lbTotalIncomeYear.setText(String.valueOf(idao.getYearTotalIncomeInfo(String.valueOf(year))));
		});

	}

}

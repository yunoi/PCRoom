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

	private ObservableList<IncomeVO> incomeData; // ���̺� �信 �����ֱ� ���ؼ� ����� ������

	private int month;
	private int monthYear;
	private int year;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// �ϸ���
		viewDayIncomeAction();
//		// ������
		viewMonthIncomeAction();
		// ������
		viewYearIncomeAction();
	}

	// �ϸ���
	public void viewDayIncomeAction() {
		lbCurrentDate.setText(date.now().toString()); // ���糯¥
		lbTotalVisitor.setText(String.valueOf(idao.getDayUserCount(lbCurrentDate.getText()))); // �� �湮��
		lbCurrentMember.setText(String.valueOf(idao.getCurrentMemberCount())); // ���� �� �����ڼ�
		lbNewMember.setText(String.valueOf(idao.getNewMemberCount(lbCurrentDate.getText()))); // �ű� ������ ��

		// �� �Ǿ�����
		lbPCIncome.setText(String.valueOf(idao.getDayPCIncomeInfo(lbCurrentDate.getText())));
		// �� ��ǰ����
		lbItemIncome.setText(String.valueOf(idao.getDayItemIncomeInfo(lbCurrentDate.getText())));
		// �� �� ����
		lbTotalIncome.setText(String.valueOf(idao.getDayTotalIncomeInfo(lbCurrentDate.getText())));

		selectDatePicker.setOnAction(event -> {
			date = selectDatePicker.getValue(); // ���õ���Ʈ �����ͼ�
			lbCurrentDate.setText("" + date); // ����Ʈ�� �����
			lbTotalVisitor.setText(String.valueOf(idao.getDayUserCount(lbCurrentDate.getText()))); // �� �湮��
			lbCurrentMember.setText(String.valueOf(idao.getCurrentMemberCount())); // ���� �� �����ڼ�
			lbNewMember.setText(String.valueOf(idao.getNewMemberCount(lbCurrentDate.getText()))); // �ű� ������ ��

			// �� �Ǿ�����
			lbPCIncome.setText(String.valueOf(idao.getDayPCIncomeInfo(lbCurrentDate.getText())));
			// �� ��ǰ����
			lbItemIncome.setText(String.valueOf(idao.getDayItemIncomeInfo(lbCurrentDate.getText())));
			// �� �� ����
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
				// �׷��� �׸���
				pieChart.setData(FXCollections.observableArrayList(
						new PieChart.Data("PC", Double.parseDouble(lbPCIncome.getText())),
						new PieChart.Data("��ǰ", Double.parseDouble(lbItemIncome.getText()))));

				Scene scene = new Scene(pieChartRoot);
				stage.setScene(scene);
				stage.setTitle("������ ���� ��Ʈ");
				stage.setResizable(false);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	// ������
	public void viewMonthIncomeAction() {
		// �� ���� ���̺�

		// 2. ���̺� ���� (arraylist�� ����)
		incomeData = FXCollections.observableArrayList();
		// 3, ���̺�並 �������ϰ� ����
		tableViewMonthIncome.setEditable(false);

		// 5. ���̺������� �÷� ����
		TableColumn colincomeYear = new TableColumn("��");
		colincomeYear.setMaxWidth(70);
		colincomeYear.setStyle("-fx-alignment: CENTER;");
		colincomeYear.setCellValueFactory(new PropertyValueFactory<>("incomeYear"));

		TableColumn colincomeMonth = new TableColumn("��");
		colincomeMonth.setMaxWidth(70);
		colincomeMonth.setStyle("-fx-alignment: CENTER;");
		colincomeMonth.setCellValueFactory(new PropertyValueFactory<>("incomeMonth"));

		TableColumn colincomeDay = new TableColumn("��");
		colincomeDay.setMaxWidth(70);
		colincomeDay.setStyle("-fx-alignment: CENTER;");
		colincomeDay.setCellValueFactory(new PropertyValueFactory<>("incomeDay"));

		TableColumn coldayPCIncome = new TableColumn("PC����");
		coldayPCIncome.setMaxWidth(100);
		coldayPCIncome.setStyle("-fx-alignment: CENTER;");
		coldayPCIncome.setCellValueFactory(new PropertyValueFactory<>("dayPCIncome"));

		TableColumn coldayItemIncome = new TableColumn("��ǰ����");
		coldayItemIncome.setMaxWidth(100);
		coldayItemIncome.setStyle("-fx-alignment: CENTER;");
		coldayItemIncome.setCellValueFactory(new PropertyValueFactory<>("dayItemIncome"));

		TableColumn coldayTotalIncome = new TableColumn("�Ѹ���");
		coldayTotalIncome.setMaxWidth(100);
		coldayTotalIncome.setStyle("-fx-alignment: CENTER;");
		coldayTotalIncome.setCellValueFactory(new PropertyValueFactory<>("dayTotalIncome"));

		// 5. ���̺��� �÷� ��ü���� ���̺�信 ����Ʈ �߰� �� �׸� �߰��Ѵ�.
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
				// �׷��� �׸���

				// pc ����
				XYChart.Series lineMonthPCIncome = new XYChart.Series();
				lineMonthPCIncome.setName("PC����"); // ��Ʈ�� ��
				ObservableList PCIncomeList = FXCollections.observableArrayList();
				for (int i = 0; i < incomeData.size(); i++) {
					PCIncomeList.add(
							new XYChart.Data(incomeData.get(i).getIncomeDay(), incomeData.get(i).getDayPCIncome()));
				}
				lineMonthPCIncome.setData(PCIncomeList);
				lineChart.getData().add(lineMonthPCIncome);

				// ��ǰ����
				XYChart.Series lineMonthItemIncome = new XYChart.Series();
				lineMonthItemIncome.setName("��ǰ����"); // ��Ʈ�� ��
				ObservableList ItemIncomeList = FXCollections.observableArrayList();
				for (int i = 0; i < incomeData.size(); i++) {
					ItemIncomeList.add(
							new XYChart.Data(incomeData.get(i).getIncomeDay(), incomeData.get(i).getDayItemIncome()));
				}
				lineMonthItemIncome.setData(ItemIncomeList);
				lineChart.getData().add(lineMonthItemIncome);

				// �Ѹ���
				XYChart.Series lineMonthTotalIncome = new XYChart.Series();
				lineMonthTotalIncome.setName("�� �� ����"); // ��Ʈ�� ��
				ObservableList totalIncomeList = FXCollections.observableArrayList();
				for (int i = 0; i < incomeData.size(); i++) {
					totalIncomeList.add(
							new XYChart.Data(incomeData.get(i).getIncomeDay(), incomeData.get(i).getDayTotalIncome()));
				}
				lineMonthTotalIncome.setData(totalIncomeList);
				lineChart.getData().add(lineMonthTotalIncome);

				Scene scene = new Scene(lineChartRoot);
				stage.setScene(scene);
				stage.setTitle("�̹� ���� ���� ��Ʈ");
				stage.setResizable(false);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	// �� ���� ���̺� ��ü ������ ����
	private void totalIncomeList() {
		ArrayList<IncomeVO> list = null;
		IncomeDAO incomeDAO = new IncomeDAO();
		IncomeVO incomeVO = null;
		month = Integer.parseInt(lbMonth.getText());
		monthYear = Integer.parseInt(lbMonthYear.getText());
		list = incomeDAO.getIncomeTotal(String.valueOf(monthYear), String.valueOf(month));
//		System.out.println(String.valueOf(monthYear)+" "+ String.valueOf(month));
		if (list == null) {
			CommonFunc.alertDisplay(1, "���", "DB �������� ����", "�ٽ� �� �� ������ �ּ���.");
			return;
		} else {
			incomeData.removeAll(incomeData);
			for (int i = 0; i < list.size(); i++) {
				incomeVO = list.get(i);
				incomeData.add(incomeVO);
			}
		}

	}

	// ������
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
				// �׷��� �׸���

				// pc ����
				XYChart.Series barYearPCIncome = new XYChart.Series();
				barYearPCIncome.setName("PC����"); // ��Ʈ�� ��
				ObservableList PCIncomeList = FXCollections.observableArrayList();
//				for (int i = 0; i < incomeData.size(); i++) {
//					PCIncomeList.add(
//							new XYChart.Data(incomeData.get(i).getIncomeYear(), incomeData.get(i).getDayPCIncome()));
//				}
				PCIncomeList.add(new XYChart.Data(lbYear.getText(), Integer.parseInt(lbPCIncomeYear.getText())));
				barYearPCIncome.setData(PCIncomeList);
				barChart.getData().add(barYearPCIncome);

				// ��ǰ����
				XYChart.Series barYearItemIncome = new XYChart.Series();
				barYearItemIncome.setName("��ǰ����"); // ��Ʈ�� ��
				ObservableList ItemIncomeList = FXCollections.observableArrayList();
//				for (int i = 0; i < incomeData.size(); i++) {
//					ItemIncomeList.add(
//							new XYChart.Data(incomeData.get(i).getIncomeYear(), incomeData.get(i).getDayItemIncome()));
//				}
				ItemIncomeList.add(new XYChart.Data(lbYear.getText(), Integer.parseInt(lbItemIncomeYear.getText())));
				barYearItemIncome.setData(ItemIncomeList);
				barChart.getData().add(barYearItemIncome);

				// �Ѹ���
				XYChart.Series barYearTotalIncome = new XYChart.Series();
				barYearTotalIncome.setName("�� �� ����"); // ��Ʈ�� ��
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
				stage.setTitle("�̹� ���� ���� ��Ʈ");
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

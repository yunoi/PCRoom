package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CommonFunc {
	// 경고창 처리 함수
	public static void alertDisplay(int type, String title, String headerText, String contentText) {
		Alert alert = null; // 정보창 주고싶으면 인포메이션, 컨퍼메이션:확신
		switch (type) {
		case 1:
			alert = new Alert(AlertType.WARNING);
			break;
		case 2:
			alert = new Alert(AlertType.CONFIRMATION);
			break;
		case 3:
			alert = new Alert(AlertType.INFORMATION);
			break;
		case 4:
			alert = new Alert(AlertType.NONE);
			break;
		case 5:
			alert = new Alert(AlertType.ERROR);
			break;
		}
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.setResizable(false);
		alert.showAndWait();
		return;
	}
}

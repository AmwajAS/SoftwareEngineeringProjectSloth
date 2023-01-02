package Alerts;

import javafx.scene.control.ButtonType;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;


public abstract class Alerts {


	public static Alert showAlert(AlertType type, String title, String message, ButtonType buttonType) {
		Alert alert = new Alert(type, message, buttonType);
		alert.setTitle(title);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.show();
		//alert.showAndWait();
		return alert;
	}



}

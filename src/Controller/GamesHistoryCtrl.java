package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import Model.Game;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class GamesHistoryCtrl implements Initializable{
	@FXML
    private ListView<Game> gamesList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		gamesList.getItems().clear();
		gamesList.getItems().addAll();
	}

}

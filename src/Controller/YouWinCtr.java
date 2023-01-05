package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class YouWinCtr implements Initializable {	
	@FXML
	private Text name;
	@FXML
	private Text newScore;
	@FXML
	private Button backBt;

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if(LoginController.getUser()==null) {
			System.out.println("no user!!error in import users");
			name.setText("Player Name: Error! ");
		}else{ 
			name.setText("Contgrats " +LoginController.getUser().getUsername() + " you win!");   //set the user name on the board
		}
		newScore.setText("-- "+BoardController.totalScore+" --");
	}
	
	public Button getBackBt() {
		return backBt;
	}

	public void setBackBt(Button backBt) {
		this.backBt = backBt;
	}




}
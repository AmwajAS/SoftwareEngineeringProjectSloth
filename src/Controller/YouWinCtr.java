package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class YouWinCtr implements Initializable {	
	@FXML
	private Text name;
	@FXML
	private Text newScore;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if(LoginController.getUser()==null) {
			System.out.println("no user!!error in import users");
			name.setText("Player Name: Error! ");
		}else{ 
			name.setText("Contgrats " +LoginController.getUser().getUsername() + " you win!");   //set the user name on the board
		}
		newScore.setText("--"+BoardController.totalScore+"--");
	}




}

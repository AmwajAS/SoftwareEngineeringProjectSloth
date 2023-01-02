package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserAnswerStatusController implements Initializable {

	@FXML
	private Text answerStatus;
	@FXML
	public static Button closeBt;
	public static Button getCloseBt() {
		return closeBt;
	}


	public static void setCloseBt(Button closeBt) {
		UserAnswerStatusController.closeBt = closeBt;
	}

	@FXML
	private ImageView imageStatus;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		try {
			showUser();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	public boolean showUser() throws FileNotFoundException {
		FileInputStream input;

		
		if(QuestionPopupController.isResult() == true) {
			answerStatus.setText("Your Answer is Correct");
			input = new FileInputStream("./src/images/6974-ai.png");
			Image img = new Image(input);
			imageStatus.setImage(img);

		}
		else {
			answerStatus.setText("Your Answer is Not Correct");
			input = new FileInputStream("./src/images/close.png");
			Image img = new Image(input);
			imageStatus.setImage(img);
		}
		
		
		return true;
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void close() {
		 QuestionPopupController.close();

	}
	

		
		
	
	
	
	

}

package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Alerts.Alerts;
import Model.Question;
import Utils.QuestionLevel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddQuestionController implements Initializable {

	@FXML
	private ComboBox<QuestionLevel> level;
	@FXML
	private ComboBox<Integer> correctAnswer;
	@FXML
	private TextArea qtext;
	@FXML
	private TextArea firstAnswer;
	@FXML
	private TextArea secondAnswer;
	@FXML
	private TextArea thirdAnswer;
	@FXML
	private TextArea fourthAnswer;
	@FXML
	private Button addBt;
	@FXML
	private Button clearBt;
	@FXML
	private Button back;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		level.getItems().clear();
		for (QuestionLevel ql : QuestionLevel.values()) {
			level.getItems().add(ql);
		}
		correctAnswer.getItems().addAll(1, 2, 3, 4);
	}

	@FXML
	public void addQuestion() {
        //defining variables to read the field from the screen.
		String question = null;
		String first = null;
		String second = null;
		String third = null;
		String fourth = null;
		QuestionLevel ql = null;
		int correct = 0;

		try {
	        //reading the fields from the screen.
			question = qtext.getText();
			first = firstAnswer.getText();
			second = secondAnswer.getText();
			third = thirdAnswer.getText();
			fourth = fourthAnswer.getText();

			ql = level.getSelectionModel().getSelectedItem();
			correct = correctAnswer.getSelectionModel().getSelectedItem();

		} catch (NullPointerException e) {
			// TODO: handle exception
			Alerts.showAlert(AlertType.WARNING, "Question Managments", "Plesae Fill All Fileds!", ButtonType.CLOSE);
		}

		String[] answers = { first, second, third, fourth };

		int levelCompare;

		//convert the question level from enum to int 
		if (ql == QuestionLevel.Easy) {
			levelCompare = 1;
		} else if (ql == QuestionLevel.Meduim) {
			levelCompare = 2;
		} else {
			levelCompare = 3;
		}

		//creating the new question object
		Question ques = new Question(question, answers, correct, levelCompare, "Sloth");
		try {
			//updating the questions list in the sysdata class
			if (Sysdata.getImportedQuestions().isEmpty()) {
				Sysdata.importQuestionsFromJSON();
			} else {
				Sysdata.getImportedQuestions().add(ques);
				Sysdata.exportQuestionsToJSON();
				clearning();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void clear() {
		clearning();
	}
/*
 * clearing all the fields after adding the question
 */
	public void clearning() {

		level.getSelectionModel().clearSelection();
		correctAnswer.getSelectionModel().clearSelection();
		if (qtext != null) {
			qtext.setText("");
		}
		if (firstAnswer != null) {
			firstAnswer.setText("");
		}
		if (secondAnswer != null) {
			secondAnswer.setText("");
		}
		if (thirdAnswer != null) {
			thirdAnswer.setText("");
		}
		if (fourthAnswer != null) {
			fourthAnswer.setText("");
		}
	}

	@FXML
	public void actionOnBack(ActionEvent event) throws IOException {
		// Close the current stage
		Stage currentStage = (Stage) back.getScene().getWindow();
		currentStage.close();
		// Starts a new stage
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/QuestionMangement.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sloth Chess Board");
		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(900);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
		FileInputStream input;
		try {
			input = new FileInputStream("./src/images/logo.png");
			Image img = new Image(input);
			primaryStage.getIcons().add(img); // icon
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

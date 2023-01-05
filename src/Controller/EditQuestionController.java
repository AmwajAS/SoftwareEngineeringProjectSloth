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

public class EditQuestionController implements Initializable {

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
	private Button editBt;
	@FXML
	private Button clearBt;
	@FXML
	private Button back;
	private Question qedit = QuestionMangController.getEditSelection(); // the selected question from the QuestionManagment view

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		qtext.setText(qedit.getQuestion());
		correctAnswer.getItems().addAll(1, 2, 3, 4);

		for (QuestionLevel ql : QuestionLevel.values()) {
			level.getItems().add(ql);
		}
         //showing the fourth answers to the selected question
		for (int i = 0; i < qedit.getAnswers().length; i++) {
			firstAnswer.setText(qedit.getAnswers()[0]);
			secondAnswer.setText(qedit.getAnswers()[1]);
			thirdAnswer.setText(qedit.getAnswers()[2]);
			fourthAnswer.setText(qedit.getAnswers()[3]);
		}
        //showing the question other data
		level.getSelectionModel().select(checkLevel(qedit.getLevel()));
		correctAnswer.getSelectionModel().select(qedit.getCorrect_ans());

	}

	@FXML
	public void editQuestion() {
        //defining variables to read the field from the screen.
		String questionEdited = null;
		String firstEdited = null;
		String secondEdited = null;
		String thirdEdited = null;
		String fourthEdited = null;
		QuestionLevel qlEdited = null;
		int correctEdited = 0;
		try {
	        //reading the fields from the screen.
			questionEdited = qtext.getText();
			firstEdited = firstAnswer.getText();
			secondEdited = secondAnswer.getText();
			thirdEdited = thirdAnswer.getText();
			fourthEdited = fourthAnswer.getText();

			qlEdited = level.getSelectionModel().getSelectedItem();
			correctEdited = correctAnswer.getSelectionModel().getSelectedItem();

		} catch (NullPointerException e) { //if any field of the fields is empty
			// TODO: handle exception
			Alerts.showAlert(AlertType.WARNING, "Question Managments", "Plesae Fill All Fileds!", ButtonType.CLOSE);
		}
		String[] answersEdited = { firstEdited, secondEdited, thirdEdited, fourthEdited };

		//comparing the previous question and the new question (before and after editing)
		if (((!qedit.getQuestion().equals(questionEdited)) || (qedit.getCorrect_ans() != correctEdited)
				|| (!qedit.getAnswers().equals(answersEdited) || checkLevel(qedit.getLevel()) != qlEdited))) {
			
         //creating a new question object
			Question question = new Question(questionEdited, answersEdited, correctEdited, checkLevelToInt(qlEdited),
					"Sloth");
			try {
				// updating the question list and writing to the Json file
				Sysdata.getImportedQuestions().remove(qedit);
				Sysdata.getImportedQuestions().add(question);
				Sysdata.exportQuestionsToJSON();
				clearning();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@FXML
	void clear() {
		clearning();
	}
/*
 * clear all data in the fields.
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

	/*
	 * converting the question level from int to QuestionLevel enum
	 */
	public QuestionLevel checkLevel(int level) {

		Question qedit = QuestionMangController.getEditSelection();
		QuestionLevel levelCompare;

		if (qedit.getLevel() == 1) {
			levelCompare = QuestionLevel.Easy;
		} else if (qedit.getLevel() == 2) {
			levelCompare = QuestionLevel.Meduim;
		} else {
			levelCompare = QuestionLevel.Hard;
		}
		return levelCompare;

	}

	/*
	 * converting the question level from QuestionLevel enum to int
	 */
	public int checkLevelToInt(QuestionLevel level) {

		int levelCompare;

		if (level == QuestionLevel.Easy) {
			levelCompare = 1;
		} else if (level == QuestionLevel.Meduim) {
			levelCompare = 2;
		} else {
			levelCompare = 3;
		}
		return levelCompare;

	}

	
	 @FXML
		public void actionOnBack(ActionEvent event) throws IOException {
		    // Close the current stage
		    Stage currentStage = (Stage) back.getScene().getWindow();
		    currentStage.close();
		    //Starts a new stage
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/View/QuestionMangement.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sloth - Question Mangement");
	        primaryStage.setResizable(false);
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

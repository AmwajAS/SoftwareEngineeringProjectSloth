package Controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import com.sun.media.sound.SoftJitterCorrector;
import com.sun.prism.paint.Color;

import Alerts.Alerts;
import Model.Game;
import Model.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuestionPopupController implements Initializable {

	@FXML
	private RadioButton firstAnswer;
	@FXML
	private RadioButton secondAnswer;
	@FXML
	private RadioButton thirdAnswer;
	@FXML
	private RadioButton forthAnswer;
	@FXML
	private Button checkAnswerBt;
	@FXML
	private Pane questionPane;

	@FXML
	private Text questionText;

	private ToggleGroup tg = new ToggleGroup();
	Question question;
	private Game game;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Sysdata.importQuestionsFromJSON();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Question> importedQuestions = Sysdata.getImportedQuestions();
		Random rand = new Random();
		int index = rand.nextInt(importedQuestions.size());
		question = importedQuestions.get(index);

		makeQuestion();

		firstAnswer.setToggleGroup(tg);
		secondAnswer.setToggleGroup(tg);
		thirdAnswer.setToggleGroup(tg);
		forthAnswer.setToggleGroup(tg);
	}

	public void makeQuestion() {
		if (question != null) {
			questionText.setText(question.getQuestion());

			for (int i = 0; i < question.getAnswers().length; i++) {
				firstAnswer.setText(question.getAnswers()[0]);
				secondAnswer.setText(question.getAnswers()[1]);
				thirdAnswer.setText(question.getAnswers()[2]);
				forthAnswer.setText(question.getAnswers()[3]);
			}

			if (question.getLevel() == 1) {
				questionPane.setStyle("-fx-background-color: white");
			} else if (question.getLevel() == 2) {
				questionPane.setStyle("-fx-background-color: yellow");
			} else {
				questionPane.setStyle("-fx-background-color: red");
			}
		}
	}

	// TODO we need to fix this function
	/*
	 * this Function checks if the user answered correct for the questions pop up. 
	 * the css for the question text changing based weather the user answered right or false.
	 * and showing an alert to give the user an information about his answers.
	 * 
	 */
	
	@FXML
	public void checkAnswer() {                           
		boolean isCorrect = true;
		RadioButton selectedRadioButton = (RadioButton) tg.getSelectedToggle();
		String userAnswer = selectedRadioButton.getText();
		System.out.println(userAnswer);

		if (question.getCorrect_ans() == 1){               // the question correct answer
			if(firstAnswer.isSelected()) {                 // the user answer
				firstAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				Alerts.showAlert(AlertType.CONFIRMATION, "Question!", "Answer Correct!!", ButtonType.OK);
				CalculateScore(isCorrect);

			}else {
				firstAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				selectedRadioButton.setStyle(("-fx-background-color: rgba(244, 67, 54, 0.5)"));
				CalculateScore(!isCorrect);

			}
		
		}
		if (question.getCorrect_ans() == 2){
			if(secondAnswer.isSelected()) {
				secondAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				Alerts.showAlert(AlertType.CONFIRMATION, "Question!", "Answer Correct!!", ButtonType.OK);
				CalculateScore(isCorrect);


			}else {
				secondAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				selectedRadioButton.setStyle(("-fx-background-color: rgba(244, 67, 54, 0.5)"));
				CalculateScore(!isCorrect);

			}
		
		}

		if (question.getCorrect_ans() == 3){
			if(thirdAnswer.isSelected()) {
				thirdAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				Alerts.showAlert(AlertType.CONFIRMATION, "Question!", "Answer Correct!!", ButtonType.OK);
				CalculateScore(isCorrect);
	


			}else {
				thirdAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				selectedRadioButton.setStyle(("-fx-background-color: rgba(244, 67, 54, 0.5)"));
				CalculateScore(!isCorrect);

			}
		
		}
		if (question.getCorrect_ans() == 4){
			if(forthAnswer.isSelected()) {
				forthAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				Alerts.showAlert(AlertType.CONFIRMATION, "Question!", "Answer Correct!!", ButtonType.OK);
				CalculateScore(isCorrect);

			}else {
				forthAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				selectedRadioButton.setStyle(("-fx-background-color: rgba(244, 67, 54, 0.5)"));
				CalculateScore(!isCorrect);

			}
		
		}

	

		
	}

/*
 * this function calculate the user score based on the user answer and the question level. 
 * each question level has his own formula to calculate the score.
 * 
 * 
 */
	public int CalculateScore(boolean isCorrect) {
		if (isCorrect) { // if the user answered correct

			if (question.getLevel() == 1) {  
				game.setScore(game.getScore() + 1);   

			} else if (question.getLevel() == 2) {
				game.setScore(game.getScore() + 2);

			} else if (question.getLevel() == 3) {
				game.setScore(game.getScore() + 3);
			}
		}else if(!isCorrect) {    // if the user answered incorrect
			if (question.getLevel() == 1) {
				game.setScore(game.getScore() - 1);

			} else if (question.getLevel() == 2) {
				game.setScore(game.getScore() - 2);

			} else if (question.getLevel() == 3) {
				game.setScore(game.getScore() - 3);

			}
		}
		//Stage stage = (Stage) checkAnswerBt.getScene().getWindow();
		//stage.close();
		return 0;

	}
	
	

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
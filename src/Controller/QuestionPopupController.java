package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import Alerts.Alerts;
import Model.Game;
import Model.Question;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//import jdk.internal.org.jline.reader.impl.history.DefaultHistory;

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

	public static boolean result;
	// public static Stage primaryStage = null;

	private ToggleGroup tg = new ToggleGroup();
	Question question;
	private Game game;
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

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
	 * the css for the question text changing based weather the user answered right
	 * or false. and showing an alert to give the user an information about his
	 * answers.
	 * 
	 */

	@FXML
	public void checkAnswer() throws IOException {
		boolean isCorrect = true;
		RadioButton selectedRadioButton = (RadioButton) tg.getSelectedToggle();
		String userAnswer = selectedRadioButton.getText();
		System.out.println(userAnswer);

		if (question.getCorrect_ans() == 1) { // the question correct answer
			if (firstAnswer.isSelected()) { // the user answer
				firstAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				// Alerts.showAlert(AlertType.CONFIRMATION, "Question!", "Answer Correct!!",
				// ButtonType.OK);
				isCorrect = true;
				CalculateScore(isCorrect);
			} else {
				firstAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				selectedRadioButton.setStyle(("-fx-background-color: rgba(244, 67, 54, 0.5)"));
				isCorrect = false;
				CalculateScore(isCorrect);
			}

		}
		if (question.getCorrect_ans() == 2) {
			if (secondAnswer.isSelected()) {
				secondAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				// Alerts.showAlert(AlertType.CONFIRMATION, "Question!", "Answer Correct!!",
				// ButtonType.OK);
				isCorrect = true;
				CalculateScore(isCorrect);

			} else {
				secondAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				selectedRadioButton.setStyle(("-fx-background-color: rgba(244, 67, 54, 0.5)"));
				isCorrect = false;
				CalculateScore(isCorrect);

			}
		}

		if (question.getCorrect_ans() == 3) {
			if (thirdAnswer.isSelected()) {
				thirdAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				// Alerts.showAlert(AlertType.CONFIRMATION, "Question!", "Answer Correct!!",
				// ButtonType.OK);
				isCorrect = true;
				CalculateScore(isCorrect);

			} else {
				thirdAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				selectedRadioButton.setStyle(("-fx-background-color: rgba(244, 67, 54, 0.5)"));
				isCorrect = false;
				CalculateScore(isCorrect);

			}

		}
		if (question.getCorrect_ans() == 4) {
			if (forthAnswer.isSelected()) {
				forthAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				// Alerts.showAlert(AlertType.CONFIRMATION, "Question!", "Answer Correct!!",
				// ButtonType.OK);
				isCorrect = true;
				CalculateScore(isCorrect);

			} else {
				forthAnswer.setStyle(("-fx-background-color: rgba(76, 175, 80, 0.7)"));
				selectedRadioButton.setStyle(("-fx-background-color: rgba(244, 67, 54, 0.5)"));
				isCorrect = false;
				CalculateScore(isCorrect);
			}
		}
		Image image;
		if (isCorrect) {
			image = new Image("/images/6974-ai.png");
		} else {
			image = new Image("/images/close.png");
		}
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		Alert alert = Alerts.showAlert(AlertType.INFORMATION, "Result", "Your Answer is " + isCorrect, ButtonType.OK);
		// Create an ImageView to display the image

		alert.setGraphic(imageView);
		// Close both windows
		Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
		okButton.addEventFilter(ActionEvent.ACTION, event -> {
			Stage popupStage = (Stage) checkAnswerBt.getScene().getWindow();
			Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
			popupStage.close();
			alertStage.close();
		});

	}

	/*
	 * this function calculate the user score based on the user answer and the
	 * question level. each question level has his own formula to calculate the
	 * score.
	 * 
	 * 
	 */
	public int CalculateScore(boolean isCorrect) throws IOException {
		if (isCorrect) { // if the user answered correct
			result = true;

			if (question.getLevel() == 1) {
				game.setScore(game.getScore() + 1);

			} else if (question.getLevel() == 2) {
				game.setScore(game.getScore() + 2);

			} else if (question.getLevel() == 3) {
				game.setScore(game.getScore() + 3);
			}
			// showResult();

		} else if (!isCorrect) { // if the user answered incorrect
			result = false;
			if (question.getLevel() == 1) {
				game.setScore(game.getScore() - 2);

			} else if (question.getLevel() == 2) {
				game.setScore(game.getScore() - 3);

			} else if (question.getLevel() == 3) {
				game.setScore(game.getScore() - 4);

			}
			// showResult();

		}

		// Stage stage = (Stage) checkAnswerBt.getScene().getWindow();
		// stage.close();
		return 0;

	}
	/*
	 * public void showResult() throws IOException { // Stage stage = (Stage)
	 * checkAnswerBt.getScene().getWindow(); // stage.close(); Stage primaryStage =
	 * new Stage(); Parent root; try { root =
	 * FXMLLoader.load(getClass().getResource("/View/UserAnswerStatus.fxml")); Scene
	 * scene = new Scene(root); primaryStage.setScene(scene);
	 * primaryStage.setTitle("Sloth Chess - Answer Status"); primaryStage.show(); }
	 * catch (IOException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); }
	 * 
	 * primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	 * 
	 * @Override public void handle(WindowEvent t) { Platform.exit();
	 * System.exit(0);
	 * 
	 * } }); FileInputStream input; try { input = new
	 * FileInputStream("./src/images/logo.png"); Image img = new Image(input);
	 * primaryStage.getIcons().add(img); // icon } catch (FileNotFoundException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */

	public static void close() { // this functions does'nt work, please check

		if (UserAnswerStatusController.getCloseBt().isPressed()) {
			Stage stage = (Stage) UserAnswerStatusController.getCloseBt().getScene().getWindow();
			stage.close();
		}

	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public static boolean isResult() {
		return result;
	}

	public static void setResult(boolean result) {
		QuestionPopupController.result = result;
	}

	public Button getCheckAnswerBt() {
		return checkAnswerBt;
	}
	/*
	 * public void setCheckAnswerBt(Button checkAnswerBt) {
	 * QuestionPopupController.checkAnswerBt = checkAnswerBt; }
	 */

}
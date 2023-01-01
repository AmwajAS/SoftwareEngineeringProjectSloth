package Controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import com.sun.media.sound.SoftJitterCorrector;
import com.sun.prism.paint.Color;

import Model.Game;
import Model.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
	@FXML
	public void checkAnswer() {
		boolean isCorrect = false;
		if ((firstAnswer.isSelected() && question.getCorrect_ans() == 1) || (secondAnswer.isSelected() && question.getCorrect_ans() == 2) 
				|| (thirdAnswer.isSelected() && question.getCorrect_ans() == 3) || (forthAnswer.isSelected() && question.getCorrect_ans() == 4)) {
			if(question.getLevel() == 1) {
				game.setScore(game.getScore()+1);

			}
			else if(question.getLevel() == 2) {
				game.setScore(game.getScore()+2);

			}
			else if(question.getLevel() == 3) {
				game.setScore(game.getScore()+3);
			}
			isCorrect =  true;
		}
		else if((firstAnswer.isSelected() && question.getCorrect_ans() != 1) || (secondAnswer.isSelected() && question.getCorrect_ans() != 2) 
				|| (thirdAnswer.isSelected() && question.getCorrect_ans() != 3) || (forthAnswer.isSelected() && question.getCorrect_ans() != 4)) {
			if(question.getLevel() == 1) {
				game.setScore(game.getScore()-1);


			}
			else if(question.getLevel() == 2) {
				game.setScore(game.getScore()-2);


			}
			else if(question.getLevel() == 3) {
				game.setScore(game.getScore()-3);


			}
			isCorrect= true;
		}
		if (isCorrect) {
	         Stage stage = (Stage) checkAnswerBt.getScene().getWindow();
	         stage.close();
	      }
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
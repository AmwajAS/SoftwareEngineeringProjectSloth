package Controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.media.sound.SoftJitterCorrector;
import com.sun.prism.paint.Color;

import Model.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			Sysdata.importQuestionsFromJSON();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Sysdata.getImportedQuestions());

		question = Sysdata.getImportedQuestions().get(8);  /////////////the random Questions

		makeQuestion();

		firstAnswer.setToggleGroup(tg);
		secondAnswer.setToggleGroup(tg);
		thirdAnswer.setToggleGroup(tg);
		forthAnswer.setToggleGroup(tg);

	}

	private void makeQuestion() {
		// TODO Auto-generated method stub

		if (question != null) {
			questionText.setText(question.getQuestion());

			for (int i = 0; i < question.getAnswers().length; i++) {
				firstAnswer.setText(question.getAnswers()[0]);
				secondAnswer.setText(question.getAnswers()[1]);
				thirdAnswer.setText(question.getAnswers()[2]);
				forthAnswer.setText(question.getAnswers()[3]);
			}

			if (question.getLevel() == 1) {
				questionPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5)");
			} else if (question.getLevel() == 2) {
				questionPane.setStyle("-fx-background-color: rgba(255, 235, 59, 0.5)");
			} else {
				questionPane.setStyle("-fx-background-color: rgba(244, 67, 54, 0.5)");
			}

		}
	}

	@FXML
	private boolean checkAnswer() {

		RadioButton selectedRadioButton = (RadioButton) tg.getSelectedToggle();
		String userAnswer = selectedRadioButton.getText();
		System.out.println(userAnswer);

		if (firstAnswer.isSelected() && question.getCorrect_ans() == 1) {
			System.out.println("yes1");
		}
		if (secondAnswer.isSelected() && question.getCorrect_ans() == 2) {
			System.out.println("yes2");
		}
		if (thirdAnswer.isSelected() && question.getCorrect_ans() == 3) {
			System.out.println("yes3");
			return true;
		}
		if (forthAnswer.isSelected() && question.getCorrect_ans() == 4) {
			System.out.println("yes4");
		}

		return false;
		// TODO Auto-generated method stub

	}

}

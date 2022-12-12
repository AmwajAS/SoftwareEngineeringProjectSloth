package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
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
	private Question qedit = QuestionMangController.getEditSelection();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		qtext.setText(qedit.getQuestion());
		correctAnswer.getItems().addAll(1, 2, 3, 4);

		for (QuestionLevel ql : QuestionLevel.values()) {
			level.getItems().add(ql);
		}

		for (int i = 0; i < qedit.getAnswers().length; i++) {
			firstAnswer.setText(qedit.getAnswers()[0]);
			secondAnswer.setText(qedit.getAnswers()[1]);
			thirdAnswer.setText(qedit.getAnswers()[2]);
			fourthAnswer.setText(qedit.getAnswers()[3]);
		}

		level.getSelectionModel().select(checkLevel(qedit.getLevel()));
		correctAnswer.getSelectionModel().select(qedit.getCorrect_ans());

	}

	@FXML
	public void editQuestion() {

		String questionEdited = null;
		String firstEdited = null;
		String secondEdited = null;
		String thirdEdited = null;
		String fourthEdited = null;
		QuestionLevel qlEdited = null;
		int correctEdited = 0;
		try {
			questionEdited = qtext.getText();
			firstEdited = firstAnswer.getText();
			secondEdited = secondAnswer.getText();
			thirdEdited = thirdAnswer.getText();
			fourthEdited = fourthAnswer.getText();

			qlEdited = level.getSelectionModel().getSelectedItem();
			correctEdited = correctAnswer.getSelectionModel().getSelectedItem();

		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.println("Plesae Fill All Fileds!");
		}
		String[] answersEdited = { firstEdited, secondEdited, thirdEdited, fourthEdited };

		if (((!qedit.getQuestion().equals(questionEdited)) || (qedit.getCorrect_ans() != correctEdited)
				|| (!qedit.getAnswers().equals(answersEdited) || checkLevel(qedit.getLevel()) != qlEdited))) {

			Question question = new Question(questionEdited, answersEdited, correctEdited, checkLevelToInt(qlEdited),
					"Sloth");
			try {
				// Sysdata.importQuestionsFromJSON();
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
		back.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

		});
		{
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/View/QuestionsManagement.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Questions Managment");
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.exit();
					System.exit(0);
				}
			});
		}
	}

}

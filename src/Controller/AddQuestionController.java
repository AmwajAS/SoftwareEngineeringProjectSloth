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

		String question = null;
		String first = null;
		String second = null;
		String third = null;
		String fourth = null;
		QuestionLevel ql = null;
		int correct = 0;

		try {
			question = qtext.getText();
			first = firstAnswer.getText();
			second = secondAnswer.getText();
			third = thirdAnswer.getText();
			fourth = fourthAnswer.getText();

			ql = level.getSelectionModel().getSelectedItem();
			correct = correctAnswer.getSelectionModel().getSelectedItem();

		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.println("Plesae Fill All Fileds!");
		}
		System.out.println(question);
		System.out.println(first);
		System.out.println(second);
		System.out.println(third);
		System.out.println(fourth);
		System.out.println(ql);
		System.out.println(correct);

		String[] answers = { first, second, third, fourth };

		int levelCompare;

		if (ql == QuestionLevel.Easy) {
			levelCompare = 1;
		} else if (ql == QuestionLevel.Meduim) {
			levelCompare = 2;
		} else {
			levelCompare = 3;
		}

		Question ques = new Question(question, answers, correct, levelCompare, "Sloth");
		try {
			Sysdata.importQuestionsFromJSON();
			Sysdata.getImportedQuestions().add(ques);
			Sysdata.exportQuestionsToJSON();
			clearning();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

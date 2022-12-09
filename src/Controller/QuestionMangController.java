package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Model.Question;
import Utils.QuestionLevel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class QuestionMangController implements Initializable {

	@FXML
	private TableView<Question> questionsTable;
	@FXML
	private TableColumn<Question, String> question;
	@FXML
	private TableColumn<Question, String> action;
	@FXML
	private Button search;
	@FXML
	private Button addBt;
	@FXML
	private PieChart chart;
	@FXML
	private ComboBox<QuestionLevel> diffSelect;
	@FXML
	Button clearBt;
	private ObservableList<Question> ObList;
	private ObservableList<Question> filterdList;
//	private ObservableList<String> ObPiechar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		loadData();
		ObList = FXCollections.observableList(Sysdata.getImportedQuestions());
		showTableContent();
		diffSelect.getItems().clear();
		for (QuestionLevel ql : QuestionLevel.values()) {
			diffSelect.getItems().add(ql);
		}
		loadChart();

	}

	public void loadData() {

		try {
			Sysdata.importQuestionsFromJSON();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showTableContent() {

		question.setCellValueFactory(new PropertyValueFactory<Question, String>("Question"));
		Callback<TableColumn<Question, String>, TableCell<Question, String>> cellFoctory = (
				TableColumn<Question, String> param) -> {
			final TableCell<Question, String> cell = new TableCell<Question, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// that cell created only on non-empty rows
					if (empty) {
						setGraphic(null);
						setText(null);

					} else {
					//	Button edit = new Button("edit");
						//Button delete = new Button("delete");
						FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
						deleteIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;");
						editIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;");
						HBox actBox = new HBox(editIcon, deleteIcon);
						HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
						HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
						setGraphic(actBox);
						setText(null);

					}
				}

			};
			return cell;
		};
		action.setCellFactory(cellFoctory);
		questionsTable.setItems(ObList);
	}

	@FXML
	public void filterByQuestionLevel(ActionEvent event) throws IOException {

		if (diffSelect.getSelectionModel().getSelectedItem() != null) {
			QuestionLevel filt = diffSelect.getSelectionModel().getSelectedItem();
			System.out.println(filt);
			int levelCompare;

			if (filt == QuestionLevel.Easy) {
				levelCompare = 1;
			} else if (filt == QuestionLevel.Meduim) {
				levelCompare = 2;
			} else {
				levelCompare = 3;
			}

			ArrayList<Question> result = new ArrayList<>();
			for (Question question : Sysdata.getImportedQuestions()) {
				if (question.getLevel() == levelCompare) {
					result.add(question);
					System.out.println(result);

				}
			}
		

		filterdList = FXCollections.observableList(result);
		question.setCellValueFactory(new PropertyValueFactory<Question, String>("Question"));
		Callback<TableColumn<Question, String>, TableCell<Question, String>> cellFoctory = (
				TableColumn<Question, String> param) -> {
			final TableCell<Question, String> cell = new TableCell<Question, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// that cell created only on non-empty rows
					if (empty) {
						setGraphic(null);
						setText(null);

					} else {
						//Button edit = new Button("edit");
						//Button delete = new Button("delete");
						FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
						deleteIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;");
						editIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;");
						HBox actBox = new HBox(editIcon, deleteIcon);
						HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
						HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
						setGraphic(actBox);
						setText(null);

					}
				}

			};
			return cell;
		};
		action.setCellFactory(cellFoctory);
		questionsTable.setItems(filterdList);

		}
	}

	public void loadChart() {

		int easyCounter = 0;
		int meduimCounter = 0;
		int hardCounter = 0;

		for (Question question : Sysdata.getImportedQuestions()) {
			if (question.getLevel() == 1) {
				easyCounter++;
			} else if (question.getLevel() == 2) {
				meduimCounter++;
			} else {
				hardCounter++;
			}
		}
		chart.setTitle("Questions By Levels");
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data(QuestionLevel.Easy.toString(), easyCounter),
				new PieChart.Data(QuestionLevel.Meduim.toString(), meduimCounter),
				new PieChart.Data(QuestionLevel.Hard.toString(), hardCounter));
		
		
		chart.setStartAngle(90);
		chart.setData(pieChartData);
		chart.setLegendVisible(true);
		chart.setLegendSide(Side.BOTTOM);
		chart.setLabelLineLength(10);
		
		
		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");
		for (final PieChart.Data data : chart.getData()) {
		 data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
		 new EventHandler<MouseEvent>() {
		 @Override public void handle(MouseEvent e) {
		 caption.setTranslateX(e.getSceneX());
		 caption.setTranslateY(e.getSceneY());
		 caption.setText(String.valueOf(data.getPieValue()) + "%");
		 }
		 });
		}
	}

	@FXML
	void clear() {
		clearning();
	}

	public void clearning() {
		diffSelect.getSelectionModel().clearSelection();
		showTableContent();

	}

}

package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Alerts.Alerts;
import Model.Question;
import Utils.QuestionLevel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class QuestionMangController implements Initializable {

	private ObservableList<Question> ObList;
	private ObservableList<Question> filterdList;
	FontAwesomeIconView deleteIcon;
	FontAwesomeIconView editIcon;

	@FXML
	private TableView<Question> questionsTable;
	@FXML
	private TableColumn<Question, Question> question;
	@FXML
	private TableColumn<Question, String> action;
	@FXML
	private Button search;
	@FXML
	private Button addBt;
	@FXML
	private Button exitBT;
	@FXML
	private PieChart chart;
	@FXML
	private ComboBox<QuestionLevel> diffSelect;
	@FXML
	private Button clearBt;
	@FXML
	private Button addMoreBt;
	@FXML
	private Button back;

	private static Question editSelection;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		// first we check if the question list from the json is empty
		if (Sysdata.getImportedQuestions().isEmpty()) {
			loadData();

		}
		ObList = FXCollections.observableList(Sysdata.getImportedQuestions());
		showTableContent();
		diffSelect.getItems().clear();
		for (QuestionLevel ql : QuestionLevel.values()) {
			diffSelect.getItems().add(ql);
		}
		loadChart();
//		try {
//			Sysdata.importUsersFromJSON();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(Sysdata.getThPlayers());

	}

	/*
	 * this method import the question list from the Json file and returns an array
	 * list with question imported.
	 * 
	 */
	public void loadData() {

		try {
			Sysdata.importQuestionsFromJSON();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * this method show all the imported question in a table, question column and
	 * action column that contains a delete and edit buttons.
	 */
	public void showTableContent() {

		question.setCellValueFactory(new PropertyValueFactory<Question, Question>("Question"));
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
						// the delete button -
						deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
						deleteIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;");
						editIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;");
						// deleting the selected question and update the questions list
						deleteIcon.setOnMouseClicked((MouseEvent event) -> {
							Question SelectedQuestion = questionsTable.getSelectionModel().getSelectedItem();
							Sysdata.getImportedQuestions().remove(SelectedQuestion);
							ObList.remove(SelectedQuestion);
							refreshTable();
							loadChart();
							try {
								Sysdata.exportQuestionsToJSON();
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							// showTableContent();

						});
						// the edit button -
						editIcon.setOnMouseClicked((MouseEvent event) -> {
                            //getting the selected question
							editSelection = questionsTable.getSelectionModel().getSelectedItem();
							// opening a new window for the edit
							Stage primaryStage = new Stage();
							Parent root;
							try {
								// Close the current stage
								Stage currentStage = (Stage) editIcon.getScene().getWindow();
								currentStage.close();
								// Starts a new stage
								root = FXMLLoader.load(getClass().getResource("/View/EditQuestion.fxml"));
								Scene scene = new Scene(root);
								primaryStage.setScene(scene);
								primaryStage.setTitle("Sloth Chess - Edit Question");
								primaryStage.setMinHeight(800);
								primaryStage.setMinWidth(900);
								primaryStage.show();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

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

						});
						
                        //putting the delete and the edit buttons in the table column
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

		action.setSortable(false);
		action.setCellFactory(cellFoctory);
		questionsTable.setItems(ObList);
	}

	/*
	 * refresh the table after deleting any question
	 */
	public void refreshTable() {
		ObList = FXCollections.observableList(Sysdata.getImportedQuestions());
		questionsTable.setItems(ObList);
	}
	
    /*
    * this functions used to filter the question by its level.
    */
	@FXML
	public void filterByQuestionLevel(ActionEvent event) throws IOException {

		if (diffSelect.getSelectionModel().getSelectedItem() != null) {
			QuestionLevel filt = diffSelect.getSelectionModel().getSelectedItem();
			System.out.println(filt);
			int levelCompare;

			//converting the enum to an int for the question level comparing
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
			questionsTable.setItems(filterdList);

		}else {
			Alerts.showAlert(AlertType.WARNING, "Question Managments", "Please Select a Question Diffecultly level", ButtonType.CLOSE);
		}

	}
/*
 * this method is one of the Design patterns, the chart will be updated as the questions will be updated.
 * this chart describes the number of each question in a specific level.
 */
	public void loadChart() {
  
		//counters for each level 
		int easyCounter = 0;
		int meduimCounter = 0;
		int hardCounter = 0;

		//counting the questions by level 
		for (Question question : Sysdata.getImportedQuestions()) {
			if (question.getLevel() == 1) {
				easyCounter++;
			} else if (question.getLevel() == 2) {
				meduimCounter++;
			} else {
				hardCounter++;
			}
		}
		//designing the chart
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
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					caption.setTranslateX(e.getSceneX());
					caption.setTranslateY(e.getSceneY());
					caption.setText(String.valueOf(data.getPieValue()) + "%");
				}
			});
		}
	}
/*
 * clear all data
 */
	@FXML
	void clear() {
		clearning();
	}
	/*
	 * clear the selection
	 */
	public void clearning() {
		diffSelect.getSelectionModel().clearSelection();
		refreshTable();
	}

	public static Question getEditSelection() {
		return editSelection;
	}

	public static void setEditSelection(Question editSelection) {
		QuestionMangController.editSelection = editSelection;
	}

	
	@FXML
	public void addMoreQuestions(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/AddQuestion.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
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

	@FXML
	public void exitGame(ActionEvent event) throws IOException {
		// Close the current stage
		Stage currentStage = (Stage) exitBT.getScene().getWindow();
		currentStage.close();
		// TODO: handle exception
		// Starts a new stage
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
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

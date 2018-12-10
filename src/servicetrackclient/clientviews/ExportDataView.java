package servicetrackclient.clientviews;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import servicetrackdirectories.DirectoryStructure;

public class ExportDataView extends AnchorPane implements BaseView{
	private DatePicker dateField;
	private RadioButton year;
	private RadioButton month;
	private ToggleGroup exportGroup;
	private Button closeButton;
	private Button exportButton;
	private HBox buttonPane;
	private VBox optionPane;
	private BorderPane mainPane;
	private Label title;
	private Scene scene;
	private FileChooser csvFile;
	private Alert dialog;

	@Override
	public void initializeView() {
		//initialize all variables
		title = new Label("Please select the year or month to export");
		dateField = new DatePicker();
		year = new RadioButton("Select a year");
		month = new RadioButton("Select a month");
		exportGroup = new ToggleGroup();
		exportButton = new Button("Export all data");
		closeButton = new Button ("Close");
		mainPane = new BorderPane();
		dialog = new Alert(AlertType.NONE);
		dialog.setTitle("Good Neighbor Alert");
		dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		
		year.setToggleGroup(exportGroup);
		month.setToggleGroup(exportGroup);
		
		
		optionPane = new VBox(10);
		optionPane.getChildren().addAll(year, month);
		optionPane.setAlignment(Pos.CENTER);
		
		buttonPane = new HBox(10);
		buttonPane.getChildren().addAll(exportButton, closeButton);
		buttonPane.setAlignment(Pos.CENTER_RIGHT);
		
		mainPane.setTop(title);
		mainPane.setRight(optionPane);
		mainPane.setCenter(dateField);
		mainPane.setBottom(buttonPane);
		//The default pattern will be the year.
		dateField.setConverter(new StringConverter<LocalDate>(){
			String pattern = "yyyy";
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		     {
		         dateField.setPromptText(pattern.toLowerCase());
		     }

		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		});
		
		csvFile = new FileChooser();
		csvFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
		csvFile.setTitle("Good Neighbor");
		csvFile.setInitialDirectory(new File(System.getProperty("user.home")));
		
		BorderPane.setAlignment(title, Pos.CENTER);
		BorderPane.setAlignment(dateField, Pos.CENTER);
		BorderPane.setAlignment(buttonPane, Pos.CENTER);
		BorderPane.setAlignment(optionPane, Pos.BOTTOM_CENTER);
		mainPane.setPadding(new Insets(10, 10, 10, 10));
		mainPane.setPrefWidth(500);
		mainPane.setPrefHeight(420);
		
		this.getChildren().add(mainPane);
		AnchorPane.setTopAnchor(mainPane, 0.0);
		AnchorPane.setLeftAnchor(mainPane, 0.0);
		AnchorPane.setBottomAnchor(mainPane, 0.0);
		AnchorPane.setRightAnchor(mainPane, 0.0);
		
		scene = new Scene(this, 500, 420);
		scene.getStylesheets().add("file:///" + new File(DirectoryStructure.getMainDir() + "Client\\css\\bootstrap3.css").getAbsolutePath().replace("\\", "/"));		
		
	}

	@Override
	public Scene getViewScene() {
		
		return null;
	}

	@Override
	public void clearView() {
		
	}
	public void showDialog(int code, String message) {
		if (code == -1)
			dialog.setAlertType(AlertType.ERROR);
		else
			dialog.setAlertType(AlertType.INFORMATION);
		
		dialog.setContentText(message);
		dialog.showAndWait();
	}
	public void setDateFormat(String pattern) {
		dateField.setConverter(new StringConverter<LocalDate>(){
			
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		     {
		         dateField.setPromptText(pattern.toLowerCase());
		     }

		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		});
	}
	public File selectSaveFile() {
		return csvFile.showSaveDialog(scene.getWindow());
	}
	public String getDate() {
		return dateField.getConverter().toString(dateField.getValue());
	}
	public void addYearRadioButtonListener(EventHandler<ActionEvent> event) {
		year.setOnAction(event);
	}
	public void addMonthRadioButtonListener(EventHandler<ActionEvent> event) {
		month.setOnAction(event);
	}
	public void closeButtonListener(EventHandler<ActionEvent> event) {
		closeButton.setOnAction(event);
	}
	public void exportButtonListener(EventHandler<ActionEvent> event) {
		exportButton.setOnAction(event);
	}
}

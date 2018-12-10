package servicetrackclient.controllers;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.scene.Scene;
import servicetrackclient.clientviews.ExportDataView;
import servicetrackclient.models.ExportDataModel;

public class ExportDataController implements BaseController {
	private ExportDataView exportDataView;
	private ExportDataModel exportDataModel;
	
	public ExportDataController() {
		exportDataView = new ExportDataView();
		exportDataModel = new ExportDataModel();
	}

	@SuppressWarnings("unused")
	@Override
	public void setupView() {
		//Initialize export data view
		exportDataView.initializeView();
		//Add listener to allow user to chose a month
		exportDataView.addMonthRadioButtonListener(event ->{
			exportDataView.setDateFormat("MMMM_yyyy");
		});
		//Add listener to allow user to chose a year
		exportDataView.addYearRadioButtonListener(event -> {
			exportDataView.setDateFormat("yyyy");
		});
		//Add listener to export client service data
		exportDataView.exportButtonListener(event -> {
			String date = exportDataView.getDate();
			System.out.println(date);
			int success = 0;
			try {
				success = exportDataModel.exportData(date);
			} catch (ClassNotFoundException ex) {
				exportDataView.showDialog(-1, "Operation failed");
			} catch (IOException ex) {
				exportDataView.showDialog(-1, "Error retrieving data from server.");
			}
			
			if(success == -1) {
				exportDataView.showDialog(success, exportDataModel.getMessage());
				return;
			}
			//Create the file
			File csvFile = exportDataView.selectSaveFile();
			byte[] fileBytes = null;
			//User presses cancel on the prompt.
			if(csvFile == null)
				return;
			fileBytes = exportDataModel.getExportFileBytes();
			//System.out.println(fileBytes.length);
			if(fileBytes == null) {
				exportDataView.showDialog(exportDataModel.getFlag(), exportDataModel.getMessage());
				return;
			}
			try {
				var csvFileOutput = new FileOutputStream(csvFile);
				var csvFileBufferedOutputStream = new BufferedOutputStream(csvFileOutput);
				csvFileBufferedOutputStream.write(fileBytes, 0, fileBytes.length);
				csvFileBufferedOutputStream.close();
				Desktop.getDesktop().open(csvFile);
			} catch (FileNotFoundException ex) {
				//Ignore file should be created, as pressing cancel on the dialog exits the function.
			} catch (IOException ex) {
				//Ignore as it will not be thrown considering we exit the function if fileBytes is null.
			}
		});
		//Add the listener for the close button
		exportDataView.closeButtonListener(event -> {
			//Close the window.
			MasterController.getMaster().fireEvent("C");
		});
		
	}

	@Override
	public Scene getViewScene() {
		return exportDataView.getScene();
	}

	@Override
	public void clearTheView() {
		// TODO Auto-generated method stub
		
	}

}

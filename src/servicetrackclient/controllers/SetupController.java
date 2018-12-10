package servicetrackclient.controllers;

import javafx.scene.Scene;
import servicetrackclient.clientviews.SetupView;
import servicetrackclient.models.SetupModel;
import servicetrackdirectories.DirectoryStructure;

public class SetupController implements BaseController{
private SetupModel setupModel;
private SetupView setupView;

public SetupController() {
	setupModel = new SetupModel();
	setupView = new SetupView();
}

@Override
public void setupView() {
	setupView.initializeView();
	setupView.addChooseDrectoryListener(event ->{
		setupView.chooseDir();
	});
	setupView.addCreateConfigButtonListener(event ->{
		setupModel.createConfigFile(setupView.getIP(), setupView.getDir());
		setupModel.initSettings();
		DirectoryStructure.createClientDirs();
		//TODO add method to model, so that I can download files online.
		MasterController.getMaster().fireEvent("O");
	});
	setupView.addCloseButtonListener(event ->{
		MasterController.getMaster().fireEvent("CP");
	});
	
}

@Override
public Scene getViewScene() {
	return setupView.getScene();
}

@Override
public void clearTheView() {
	// TODO Auto-generated method stub
	
}
}

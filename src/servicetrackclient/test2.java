package servicetrackclient;

import java.io.File;



import javafx.application.Application;
import javafx.stage.Stage;
import servicetrackclient.clientviews.LogInView;
import servicetrackclient.controllers.LogInController;
import servicetrackclient.controllers.MasterController;

public class test2 extends Application{

	public void start(Stage stage) throws Exception {
		
		MasterController control = MasterController.getMaster();
		control.setupAllViews();
		control.setPrimaryStage(stage);
		control.setUpPrimaryStage();
		//control.fireEvent("O");
		//control.startLogIn();
		control.showAdmin();
		//control.showCreateUser();
		
		
		
	}
	
	public static void main(String[] args) {
		
		Application.launch(args);
		
	}
}

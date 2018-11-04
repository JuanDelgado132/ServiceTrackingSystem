package servicetrackclient.controllers;

import javafx.scene.Scene;
import servicetrackclient.clientviews.CreateServiceView;
import servicetrackclient.models.CreateServiceModel;

public class CreateServiceController implements BaseController{
	private CreateServiceView createServiceView;
	private CreateServiceModel createServiceModel;
	
	public CreateServiceController() {
		createServiceView = new CreateServiceView();
		createServiceModel = new CreateServiceModel();
		
	}

	@Override
	public void setupView() {
		createServiceView.initializeView();
		createServiceView.addCreateServiceListener(event -> {
			String serviceName = createServiceView.getServiceName();
			String serviceDescription = createServiceView.getServiceDescription();
			String serviceDays = createServiceView.getServiceDays();
			String serviceTime = createServiceView.getServiceTime();
			int result = -1;
			if(serviceName.equals("") || serviceDescription.equals("")|| serviceDays.equals("") || serviceTime.equals("")){
				createServiceView.showDialog(-1, "All fields must be filled in.");
				return;
			}
			
			try {
				result = createServiceModel.createService(serviceName, serviceDescription, serviceDays, serviceTime);
			} catch (Exception e) {
				createServiceView.showDialog(-1, e.getMessage());
				return;
			}
			createServiceView.showDialog(result, createServiceModel.getMessage());
			createServiceView.clearView();
		});
		createServiceView.addCloseListener(event ->{
			createServiceView.clearView();
			MasterController.getMaster().fireEvent("C");
		});
		
	}

	@Override
	public Scene getViewScene() {
		return createServiceView.getScene();
	}

}

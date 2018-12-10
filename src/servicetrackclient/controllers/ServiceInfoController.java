package servicetrackclient.controllers;

import java.io.IOException;

import javafx.scene.Scene;
import servicetrackclient.clientviews.ServiceInfoView;
import servicetrackclient.models.ServiceInfoModel;
import servicetrackdata.Service;

public class ServiceInfoController implements BaseController{
	
	private ServiceInfoView view;
	private ServiceInfoModel model;
	
	public ServiceInfoController() {
		view = new ServiceInfoView();
		model = new ServiceInfoModel();
	}
	@Override
	public void setupView() {
		view.initializeView();
		view.addUpdateButtonListener(event -> {
			//1 for success or -1 for failure.
			int success = 0;
			if(view.getServiceName().equals("") || view.getServiceDays().equals("")|| view.getServiceTime().equals("")||view.getServiceDescription().equals("")) {
				view.showDialog(-1, "Make sure all fields are filled in.");
				return;
			}
			Service updatedService = new Service(view.getServiceID(), view.getServiceName(), view.getServiceDescription(), view.getServiceDays(), view.getServiceTime());
			//Returns a 1 for success for a -1 for failure
			
				try {
					success = model.updateService(updatedService);
				} catch (ClassNotFoundException e) {
					view.showDialog(-1, "The service class could not be loaded.");
					return;
				} catch (IOException e) {
					view.showDialog(-1, "Unable to establish client-server connection.");
					return;
				}
			
			
			
			view.showDialog(success, model.getMessage());
			
		});
		//Change service status.
		view.addDeactivateOrActivateButtonListener(event->{
			Service service = model.readServiceFile();
			
			//If status is 1, then we want to deactivate it, if it is not then we want to activate it.
			service.setActiveStatus(service.getActiveStatus() == 1 ? 0 : 1);
			int success = 0;
			try {
				success = model.changeActiveStatus(service);
			} catch (ClassNotFoundException e) {
				view.showDialog(-1, "The service class could not be loaded.");
				return;
			} catch (IOException e) {
				view.showDialog(-1, "Unable to establish client-server connection.");
				return;
			}
			view.showDialog(success, model.getMessage());
			//Delete old service file
			model.deleteOldFile();
			//Create new service file.
			model.writeServiceFile();
			view.clearView();
			setUpServiceInfo();
		});
		view.closeButtonListener(event ->{
			MasterController.getMaster().fireEvent("C");
		});
	}

	@Override
	public Scene getViewScene() {
		return view.getScene();
	}
	
	public void setUpServiceInfo() {
		Service requestedService = model.readServiceFile();
		view.setServiceID(requestedService.getServiceID());
		view.setServiceDays(requestedService.getDays());
		view.setServiceDescription(requestedService.getServiceDescription());
		view.setServiceName(requestedService.getServiceName());
		view.setServiceTime(requestedService.getTime());
		view.setStatus(requestedService.getActiveStatus());
	}
	@Override
	public void clearTheView() {
		view.clearView();
		
	}

}

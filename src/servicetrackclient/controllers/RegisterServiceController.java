package servicetrackclient.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Scene;
import servicetrackclient.clientviews.RegisterServiceView;
import servicetrackclient.models.RegisterServiceModel;
import servicetrackdata.Client;
import servicetrackdata.Service;

public class RegisterServiceController implements BaseController{
	private RegisterServiceView view;
	private RegisterServiceModel model;
	
	public RegisterServiceController() {
		view = new RegisterServiceView();
		model = new RegisterServiceModel();
	}

	@Override
	public void setupView() {
		view.initializeView();
		view.registerListener(event ->{
			Client client = model.readClient();
			HashMap<Integer, Service> services = model.readServices();
			ArrayList<Integer> servicesSelected = view.getSelectedServices();
			if(servicesSelected.size() == 0) {
				view.showDialog(-1, "No service was chosen.");
				return;
			}
			for(int i = 0; i < servicesSelected.size(); i++) {
				try {
					
					model.registerClient(client, services.get(servicesSelected.get(i)));
				} catch (Exception ex) {
					view.showDialog(-1, ex.getMessage());
					return;
				}
			}
			
			view.showDialog(1, "Success in registering all services.");
			view.clearView();
			MasterController.getMaster().fireEvent("C");
		});
		//Close Listener
		view.closeListener(event ->{
			MasterController.getMaster().fireEvent("C");
		});
		
		
	}

	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return view.getScene();
	}
	public void setViewInfo(){
		view.setServices(new ArrayList<Service>(model.readServices().values()));
		view.setSubTitle("Available Services For " + model.readClient().getFirstName());
	}

	@Override
	public void clearTheView() {
		view.clearView();
		
	}

}

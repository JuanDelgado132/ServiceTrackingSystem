package servicetrackclient.controllers;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import servicetrackclient.clientviews.ClientInfoView;
import servicetrackclient.models.ClientInfoModel;
import servicetrackdata.Client;
import servicetrackdata.Service;

public class ClientInfoController implements BaseController{
	
	private ClientInfoView clientInfoView;
	private ClientInfoModel clientModel;
	
	public ClientInfoController() {
		clientInfoView = new ClientInfoView();
		clientModel = new ClientInfoModel();
	}

	@Override
	public void setupView() {
		clientInfoView.initializeView();
		clientInfoView.addGenerateBarcodeListener(event ->{
			try {
				clientModel.createBarcodeFile(clientInfoView.selectSaveFile(), clientModel.readClient());
			} catch (BarcodeException e) {
				clientInfoView.showDialog(-1, e.getLocalizedMessage());
				return;
			} catch (OutputException e) {
				// TODO Auto-generated catch block
				clientInfoView.showDialog(-1, e.getLocalizedMessage());
				return;
			} catch (NullPointerException ex) {
				//This will be thrown when a user cancels the saving of the file. It is ignored.
				return;
			}
			
			clientInfoView.showDialog(1, "The file was created successuly.");
		});
		
		clientInfoView.addDeleteClientListener(event -> {
			Alert deleteConfirmAlert = clientInfoView.getDialogConfirmation(clientModel.readClient().toString());
			Optional<ButtonType> response = deleteConfirmAlert.showAndWait();
			//Check to see if a response is given. If user pressed ok delete client. If not return.
			if(response.isPresent() && response.get() == ButtonType.OK) {
				try {
					clientModel.deleteClient(clientModel.readClient());
				} catch (Exception e) {
					clientInfoView.showDialog(-1, "Client could not be deleted.");
					return;
				}
			}
			else if (response.isPresent() && response.get() == ButtonType.CANCEL)
				return;
			
			clientInfoView.showDialog(1, "Client has been deleted.");
			
			MasterController.getMaster().fireEvent("C");
			clientInfoView.clearView();
		});
		clientInfoView.addUserServiceListener(event ->{
			Client client = clientModel.readClient();
			Service serviceToUse = clientInfoView.getSelectedService();
			int success = -1;
			try {
				success = clientModel.userService(client, serviceToUse);
			} catch (UnknownHostException ex) {
				clientInfoView.showDialog(-1, "Could not connect to server.\n Ensure server ip is correct in config file. ");
			} catch (ClassNotFoundException e) {
				clientInfoView.showDialog(-1, "Could not load service or client class");
			} catch (IOException e) {
				clientInfoView.showDialog(-1, "Error processing operation.");
			}
			clientInfoView.showDialog(success, clientModel.getMessage());
		});
		//Update client info.
		clientInfoView.addUpdateClientListener(event -> {
			try {
				clientModel.updateClient(clientInfoView.getFirstName(), clientInfoView.getLastName(), clientInfoView.getID(), clientInfoView.getGender(), clientInfoView.getBirthDay(), clientInfoView.getComments());
			} catch (Exception e) {
				clientInfoView.showDialog(-1, e.getLocalizedMessage());
				e.printStackTrace();
				return;
			}
			//Delete old client file that contained the old data.
			clientModel.deleteClientFile();
			//Create the file again with the update data.
			clientModel.writeUpdatedClient();
			clientInfoView.clearView();
			setClientInfo();
			clientInfoView.showDialog(1, "Client has been successfully updated.");
		});
		
		clientInfoView.addCloseWindowListener(event -> {
			//clientModel.deleteClientFile();
			MasterController.getMaster().fireEvent("C");
			
		});
		
	}

	@Override
	public Scene getViewScene() {
		return clientInfoView.getScene();
	}
	
	public void setClientInfo() {
		Client client = clientModel.readClient();
		HashMap<Integer, Service> registeredServices = clientModel.readRegisteredServices();
		clientInfoView.setFirstName(client.getFirstName());
		clientInfoView.setLastName(client.getLastName());
		clientInfoView.setIDField(Integer.toString(client.getId()));
		clientInfoView.setGender(client.getGender());
		clientInfoView.setAge(Integer.toString(client.getAge()));
		clientInfoView.setBirthday(client.getBirthDay());
		clientInfoView.setComments(client.getComments());
		clientInfoView.setRegisteredService(registeredServices);
	}

	@Override
	public void clearTheView() {
		clientInfoView.clearView();
		
	}

}

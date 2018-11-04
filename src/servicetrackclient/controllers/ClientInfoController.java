package servicetrackclient.controllers;

import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import servicetrackclient.clientviews.ClientInfoView;
import servicetrackclient.models.ClientInfoModel;
import servicetrackdata.Client;

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
			clientInfoView.clearView();
		});
		
	}

	@Override
	public Scene getViewScene() {
		return clientInfoView.getViewScene();
	}
	
	public void setClientInfo() {
		Client client = clientModel.readClient();
		clientInfoView.setFirstName(client.getFirstName());
		clientInfoView.setLastName(client.getLastName());
		clientInfoView.setIDField(Integer.toString(client.getId()));
		clientInfoView.setGender(client.getGender());
		clientInfoView.setAge(Integer.toString(client.getAge()));
		clientInfoView.setBirthday(client.getBirthDay());
		clientInfoView.setComments(client.getComments());
	}

}

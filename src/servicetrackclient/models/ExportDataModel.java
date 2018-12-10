package servicetrackclient.models;

import java.io.IOException;

import servicetrackclient.ClientNetworkFunctions;

public class ExportDataModel {
	private ClientNetworkFunctions serverConnection;
	
	public ExportDataModel() {
		serverConnection = new ClientNetworkFunctions();
	}
	
	public int exportData(String requestedDate) throws IOException, ClassNotFoundException {
		serverConnection.addActionCode("EAD");
		serverConnection.setSelectedDate(requestedDate);
		
		try {
			serverConnection.establishConnection();
			serverConnection.sendPacketToServer();
			serverConnection.receivePacketFromServer();
		} catch (IOException ex) {
			throw ex;
		} catch (ClassNotFoundException ex) {
			throw ex;
		} finally {
			serverConnection.closeConnection();
		}
		
		return serverConnection.getErrorFlag();
	}
	public byte[] getExportFileBytes() {
		return serverConnection.getExportFile();
	}

	public String getMessage() {
		return serverConnection.message();
	}
	public int getFlag() {
		return serverConnection.getErrorFlag();
	}
	

}

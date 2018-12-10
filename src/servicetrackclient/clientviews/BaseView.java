package servicetrackclient.clientviews;

import javafx.scene.Scene;

public interface BaseView {

	/**
	 * Must initialize all User Controls.
	 * 
	 * @author juand
	 */
	public void initializeView();
	/**
	 * @deprecated you can already get the scene from the view itself from the node class.
	 * This will create a stage and show the view.
	 * @author juand
	 */
	public Scene getViewScene();
	/**
	 * Destroy the view once it is not needed anymore.
	 * 
	 * 
	 */
	public void clearView();
	
	
	//public void addListener(EventHandler<? extends Event> event);
}

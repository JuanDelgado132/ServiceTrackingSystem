package servicetrackclient.controllers;

import javafx.scene.Scene;

public interface  BaseController {
	/**
	 * We setup the scene and listeners for the view.
	 * @author juand
	 */
	public void setupView();
	/**
	 * 
	 * Returns the scene that this view is in. 
	 * @return
	 * @author juand
	 */
	public Scene getViewScene();
	/**
	 * Clear the controllers view.
	 * Used for when we close on request the data in the views are emptied.
	 * @author juand
	 */
	public void clearTheView();

}

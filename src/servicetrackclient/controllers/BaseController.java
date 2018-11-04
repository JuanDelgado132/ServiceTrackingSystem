package servicetrackclient.controllers;

import javafx.scene.Scene;

public interface  BaseController {
	/**
	 * We setup the scene and listeners for the view.
	 * @author juand
	 */
	public void setupView();
	/**
	 * Returns the scene that this view is in. 
	 * @return
	 * @author juand
	 */
	public Scene getViewScene();

}

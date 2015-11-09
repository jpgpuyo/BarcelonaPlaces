package com.jpuyo.barcelonaplaces.app.managers;

import android.content.Context;

import com.jpuyo.barcelonaplaces.app.database.managers.PlaceDetailManager;
import com.jpuyo.barcelonaplaces.app.ui.errors.ErrorManager;
import com.jpuyo.barcelonaplaces.app.ui.intents.IntentsManager;

/**
 * Class used for create different managers on the application
 */
public class ManagersFactory {

	private static ManagersFactory instance;
	private static PlaceDetailManager placeDetailManager;
	private static IntentsManager intentsManager;
	private static ErrorManager errorManager;

	public static void initInstance(Context context) {
		if (instance == null) {
			instance = new ManagersFactory(context);
		}
	}

	public static ManagersFactory getInstance() {
		return instance;
	}
	
	public static void destroy(){
		instance = null;
	}
	
	private ManagersFactory(Context context){
		createManagers(context);
	}
	
	private void createManagers(Context context){
		placeDetailManager = new PlaceDetailManager(context);
		intentsManager = IntentsManager.getInstance();
		errorManager = new ErrorManager(context);
	}

	public static PlaceDetailManager getPlaceDetailManager() {
		return placeDetailManager;
	}

	public static synchronized IntentsManager getIntentsManager() {
		return intentsManager;
	}

	public static ErrorManager getErrorManager() {
		return errorManager;
	}
}

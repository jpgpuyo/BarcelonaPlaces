package com.jpuyo.barcelonaplaces.app.database;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.PlaceDetails;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	private static final Class<?>[] classes = new Class[] {PlaceDetails.class};

	public static void main(String[] args) throws Exception {
		writeConfigFile("ormlite_config.txt", classes);
	}
}

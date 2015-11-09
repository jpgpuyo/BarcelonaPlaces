package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters;

/**
 * Interface created for format the retrofit field
 * as database field
 */
interface DatabaseFieldFormatter {
    /**
     * Return a appropiated value for <value>
     * @param value
     * @return
     */
    String format(String value);
}

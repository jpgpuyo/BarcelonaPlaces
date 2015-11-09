package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters;

/**
 * Class for format String field
 */
public class StringFieldFormatter implements DatabaseFieldFormatter {

    /**
     * Return the string without ?,without spaces,in lowercase
     * @param value
     * @return
     */
    @Override
    public String format(String value) {
        return value.replace("?","").replaceAll("\\s","").toLowerCase();
    }
}
package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.util;

/**
 * Utility methods for work with strings
 * that contains phone or fax numbers
 */
public class FaxOrPhoneUtils {

    /**
     * Return the phone number or fax number after the <prefix>
     *
     * @param prefix
     * @return
     */
    public static String extractPhoneOrFaxNumberAfterPrefix(String value, String prefix) {

        String normalizedValue = value;

        if(value.contains(prefix)){
            String[] valueSplitted = value.split(prefix);
            if(valueSplitted.length > 1){
                valueSplitted = valueSplitted[1].split("fax");
            }
            normalizedValue = valueSplitted[0].replaceAll("[^0-9;]","");
        }
        return "+" + normalizedValue;
    }

    /**
     * Return true if <value> has a valid format for Phone or Fax Number
     * @param value
     * @return
     */
    public static boolean hasValidFormatForPhoneOrFax(String value) {
        return android.util.Patterns.PHONE.matcher(value).matches();
    }
}

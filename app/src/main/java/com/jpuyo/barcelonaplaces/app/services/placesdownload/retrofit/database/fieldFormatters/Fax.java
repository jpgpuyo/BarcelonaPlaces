package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters;

import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.util.FaxOrPhoneUtils;

/**
 * Class for format Fax number field
 *
 * input: Fax:+93 123 45 67?
 * output: +931234567
 *
 * input: Tel:+93 123 45 67?
 * output: null
 *
 */
public class Fax implements DatabaseFieldFormatter {

    /**
     * Returns the formatted value for a fax number: only digits
     * @param value
     * @return
     */
    @Override
    public String format(String value) {
        StringFieldFormatter stringFieldFormatter = new StringFieldFormatter();
        String formattedValue = stringFieldFormatter.format(value);
        formattedValue = FaxOrPhoneUtils.extractPhoneOrFaxNumberAfterPrefix(formattedValue, "fax");

        if(FaxOrPhoneUtils.hasValidFormatForPhoneOrFax(formattedValue)){
            return formattedValue;
        }
        return null;
    }
}

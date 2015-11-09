package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters;

import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.util.FaxOrPhoneUtils;

/**
 * Class for format Phone number field
 *
 * input: Tel:+93 123 45 67?
 * output:+931234567
 *
 * input:  Fax:+93 123 45 67?
 * output: null
 */
public class Phone implements DatabaseFieldFormatter {

    /**
     * Returns the formatted value for a phone number: only digits
     * @param value
     * @return
     */
    @Override
    public String format(String value) {
        StringFieldFormatter stringFieldFormatter = new StringFieldFormatter();
        String formattedValue = stringFieldFormatter.format(value).replaceAll("tlf", "tel");
        formattedValue = FaxOrPhoneUtils.extractPhoneOrFaxNumberAfterPrefix(formattedValue, "tel");

        if(FaxOrPhoneUtils.hasValidFormatForPhoneOrFax(formattedValue)){
            return formattedValue;
        }
        return null;
    }
}

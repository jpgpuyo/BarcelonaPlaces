package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters;

/**
 * Class for format email field
 *
 * input: jpgpuyo@gmail.com
 * output: jpgpuyo@gmail.com
 *
 * input: jpgpuyo
 * output: null
 */
public class Email implements DatabaseFieldFormatter {

    @Override
    public String format(String value) {
        StringFieldFormatter stringFieldFormatter = new StringFieldFormatter();
        String formattedValue = stringFieldFormatter.format(value);

        boolean hasValidFormatForEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(formattedValue).matches();
        if(hasValidFormatForEmail) {
            return formattedValue;
        }
        return null;
    }
}

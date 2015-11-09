package com.jpuyo.barcelonaplaces.app.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class NotNullableTextView extends TextView implements CustomizableView {
    public NotNullableTextView(Context context) {
        super(context);
    }

    public NotNullableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NotNullableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set the textview with label and value
     * Only show textview if value is not null
     * @param label
     * @param value
     */
    public void setLabelAndValue(String label, String value) {

        if ((value != null)&&(!value.equalsIgnoreCase("null"))&&(!value.equalsIgnoreCase("undefined"))) {
            super.setText(label + value);
            setVisibility(VISIBLE);
        }else{
            setVisibility(GONE);
        }
    }
}

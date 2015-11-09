/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jpuyo.barcelonaplaces.app.database;


import android.test.AndroidTestCase;

import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.ApiProvider;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Email;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Fax;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Phone;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Transport;

public class TestFieldFormatter extends AndroidTestCase {

    public static final String LOG_TAG = TestFieldFormatter.class.getSimpleName();
    private ApiProvider apiProvider = new ApiProvider();

    public void testStringWithPhoneNumber(){
        String phoneNumber = "Tel:+93 123 45 67?";
        String newPhoneNumber = new Phone().format(phoneNumber);
        String newFaxNumber = new Fax().format(phoneNumber);

        assertEquals("Error: Incorrect phone number",
                newPhoneNumber, "+931234567");
        assertEquals("Error: Incorrect fax number",
                newFaxNumber, null);
    }

    public void testStringWithFax(){
        String phoneNumber = "Fax:+93 123 45 67?";
        String newPhoneNumber =  new Phone().format(phoneNumber);
        String newFaxNumber = new Fax().format(phoneNumber);

        assertEquals("Error: Incorrect phone number",
                newPhoneNumber,null);
        assertEquals("Error: Incorrect fax number",
                newFaxNumber, "+931234567");
    }

    public void testStringWithPhoneNumberAndFax(){
        String phoneNumber = "Tel.:+93 123 45 67?Fax.:+938765432";
        String newPhoneNumber = new Phone().format(phoneNumber);
        String newFaxNumber = new Fax().format(phoneNumber);

        assertEquals("Error: Incorrect phone number",
                newPhoneNumber, "+931234567");
        assertEquals("Error: Incorrect fax number",
                newFaxNumber, "+938765432");
    }

    public void testStringWithEmail(){
        String invalidEmail = "undefined";
        String validEmail = "info@casabatllo.cat";
        String newEmailIncorrect = new Email().format(invalidEmail);
        String newEmailCorrect = new Email().format(validEmail);

        assertEquals("Error: Invalid email",
                newEmailIncorrect, null);
        assertEquals("Error: Valid email",
                newEmailCorrect, "info@casabatllo.cat");
    }

    public void testTransport(){
        String invalidTransport = "metro:Passeig de gracia-L1";
        String newTransport = new Transport().format(invalidTransport);
        assertEquals("Error: Invalid transport",
                newTransport, null);

        String invalidTransportLine = "underground:Passeig de gracia-L10";
        newTransport = new Transport().format(invalidTransportLine);
        assertEquals("Error: Invalid transport line",
                newTransport, null);

        String validTransport = "Underground: Sant Pau / Dos de Maig - L5";
        newTransport = new Transport().format(validTransport);
        assertEquals("Error: Valid transport",
                newTransport, "underground:Sant Pau / Dos de Maig:L5");
    }
}

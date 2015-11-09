package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters;

/**
 * Class for format Transport field
 *
 * input:Underground: Sant Pau / Dos de Maig - L5
 * output:underground:Sant Pau / Dos de Maig:L5
 *
 * input:Sant Pau / Dos de Maig:L5
 * output: null
 *
 */
public class Transport implements DatabaseFieldFormatter {

    /**
     * Returns the formatted value that will have always
     * the next format on the input: <transportType>:<transportStation>-<transportLine>
     * @param value
     * @return
     */
    @Override
    public String format(String value) {

        StringFieldFormatter stringFieldFormatter = new StringFieldFormatter();
        String[] transportInfo = value.split(":");

        if(transportInfo.length > 1){
            String[] stationAndTransportLine = transportInfo[1].split("-");
            if(stationAndTransportLine.length > 1) {
                String transportType = stringFieldFormatter.format(transportInfo[0]);
                String station = stationAndTransportLine[0].trim();
                String transportLine = stringFieldFormatter.format(stationAndTransportLine[1]).toUpperCase();
                if(isValidTransport(transportType)&& isValidTransportLine(transportLine)) {
                    return transportType + ":" + station + ":" + transportLine;
                }
            }
        }
        return null;
    }

    /**
     * Return true if <transportType> is valid
     * @param transportType
     * @return
     */
    private boolean isValidTransport(String transportType){
        return transportType.matches("underground");
    }

    /**
     * Return true if <transportLine> is valid
     *
     * @param transportLine
     * @return
     */
    private boolean isValidTransportLine(String transportLine){
        return transportLine.matches("L1|L2|L3|L4|L5");
    }
}

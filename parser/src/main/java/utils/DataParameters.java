package utils;

import model.DataNode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import constants.DatabaseConstants;

public class DataParameters {

    public static String fileNameParser(String fileName) {
        String[] split = fileName.split("-");
        return split[1];
    }

    public static ArrayList<String> getCountriesOfInterests() {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("Canada");
        countries.add("Bangladesh");
        countries.add("Italy");
        countries.add("United States of America");
        countries.add("Australia");
        countries.add("Republic of Korea");

        return countries;
    }

    public static String getDataPath() {
        return "../data";
    }

    public static String[] modelToDataConverter(DataNode model) {

        ArrayList<String> result = new ArrayList<>();
        result.add(model.getCountryName());
        result.add(model.getDataTimeStamp());
        result.add(String.valueOf(model.getNumConfirmed()));
        result.add(String.valueOf(model.getNumRecentCase()));
        result.add(String.valueOf(model.getNumDeath()));
        result.add(String.valueOf(model.getRecentDeath()));
        result.add(String.valueOf(model.getModeOfTransmission()));
        

        String[] str = ArrayLisConverter(result);
        return str;
    }

	public static String[] ArrayLisConverter(ArrayList<String> result) {
		String[] str = new String[result.size()];
        for (int j = 0; j < result.size(); j++) {
            str[j] = result.get(j);
        }
		return str;
	}
    
    public static String[] modelToDataConverter(DataNode model, ArrayList<String> columns) {
    	
    	ArrayList<String> result = new ArrayList<>();
    	
    	if (columns.contains(DatabaseConstants.COUNTRY_NM)) {
    		result.add(model.getCountryName());
    	}
    	
    	if (columns.contains(DatabaseConstants.TIMESTAMP)) {
    		result.add(model.getDataTimeStamp());
    	}
    	
    	if (columns.contains(DatabaseConstants.CONFIRMED_NUM)) {
    		result.add(String.valueOf(model.getNumConfirmed()));
    	}
    	if (columns.contains(DatabaseConstants.RECENT_CASE)) {
    		result.add(String.valueOf(model.getNumRecentCase()));
    	}
    	if (columns.contains(DatabaseConstants.DEATH_NUM)) {
    		result.add(String.valueOf(model.getNumDeath()));
    	}
    	if (columns.contains(DatabaseConstants.RECENT_DEATH_NUM)) {
    		result.add(String.valueOf(model.getRecentDeath()));
    	}
    	if (columns.contains(DatabaseConstants.TRASNMISSION_MODE)) {
    		result.add(model.getModeOfTransmission());
    	}
    	
    	String[] str = ArrayLisConverter(result);
        return str;
    }
    
    public static java.sql.Date convertStrToDate(String date) {
    	
    	String parsedYear = date.substring(0,4);
		String parsedMonth = date.substring(4, 6);
		String parsedDate = date.substring(6);
		String formattedDate = parsedYear + "-" + parsedMonth + "-" + parsedDate;
		return java.sql.Date.valueOf(formattedDate);

    }
}

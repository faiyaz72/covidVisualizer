package utils;

import model.DataNode;

import java.util.ArrayList;

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

        return countries;
    }

    public static String getDataPath() {
        return "../data";
    }

    public static String[] modelToDataConverter(DataNode model) {

        ArrayList<String> result = new ArrayList<>();
        result.add(model.getCountryName());
        result.add(String.valueOf(model.getNumConfirmed()));
        result.add(String.valueOf(model.getNumRecentCase()));
        result.add(String.valueOf(model.getNumDeath()));
        result.add(String.valueOf(model.getRecentDeath()));
        result.add(String.valueOf(model.getModeOfTransmission()));
        result.add(model.getDataTimeStamp());

        String[] str = new String[result.size()];
        for (int j = 0; j < result.size(); j++) {
            str[j] = result.get(j);
        }
        return str;
    }
}

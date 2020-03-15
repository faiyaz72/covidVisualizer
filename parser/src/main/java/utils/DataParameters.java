package utils;

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

        return countries;
    }
}

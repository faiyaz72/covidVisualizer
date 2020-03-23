import Manager.ExportImportManager;
import constants.DatabaseConstants;
import constants.FileConstants;
import dataReaders.CSVDataReader;
import database.PSqlReader;
import interfaces.DatabaseReader;
import utils.DataFilter;
import utils.DataParameters;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	
    	CSVDataReader csvReader = new CSVDataReader();
    	DatabaseReader psql = new PSqlReader();
    	Connection connection = psql.connectDataBase("COVID", "postgres", "Xboxlive72");
    	ExportImportManager manager = new ExportImportManager(psql, connection, csvReader);
    	
    	DataFilter datafilter = new DataFilter();
    	datafilter.addCountry("United States of America");
    	datafilter.addCountry("Canada");
    	datafilter.addCountry("Republic of Korea");
    	datafilter.addCountry("Italy");
    	datafilter.addCountry("Australia");
    	datafilter.addCountry("Bangladesh");
    		
    	ArrayList<String> columns = new ArrayList<>();
    	
    	columns.add(DatabaseConstants.TIMESTAMP);
    	columns.add(DatabaseConstants.COUNTRY_NM);
    	columns.add(DatabaseConstants.CONFIRMED_NUM);
    	columns.add(DatabaseConstants.RECENT_CASE);
    	
//    	String countryNm = "Australia";
//    	
    	manager.exportSingleCountryData("Bangladesh", FileConstants.COUNTRY_TRENDS_DIRECTORY, columns);
//    	manager.importNewCountryData("Bangladesh", FileConstants.STORED_DATA_DIRECTORY);
        
        

    }
}

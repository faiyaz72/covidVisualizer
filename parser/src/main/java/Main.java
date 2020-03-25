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
    		
    	ArrayList<String> columns = new ArrayList<>();
    	
    	columns.add(DatabaseConstants.TIMESTAMP);
    	columns.add(DatabaseConstants.COUNTRY_NM);
    	columns.add(DatabaseConstants.CONFIRMED_NUM);
    	columns.add(DatabaseConstants.RECENT_CASE);
    	
//    	String countryNm = "Australia";
//    	
    	manager.exportSingleCountryData("Canada", FileConstants.COUNTRY_TRENDS_DIRECTORY, columns);
//    	manager.importNewCountryData("Pakistan", FileConstants.STORED_DATA_DIRECTORY);
//    	manager.updateExistingData(FileConstants.BULK_DATA_DIRECTORY);
    	
    	
        
        

    }
}

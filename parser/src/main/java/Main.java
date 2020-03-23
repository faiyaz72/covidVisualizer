import com.opencsv.exceptions.CsvValidationException;

import constants.DatabaseConstants;
import dataReaders.CSVDataReader;
import database.PSqlReader;
import interfaces.DataProcessor;
import interfaces.DatabaseReader;
import model.DataNode;
import utils.DataFilter;
import utils.DataParameters;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	
    	ArrayList<DataNode> datalist = new ArrayList<>();
    	CSVDataReader csvReader = new CSVDataReader();
    	DataFilter datafilter = new DataFilter();
    	datafilter.addCountry("United States of America");
    	datafilter.addCountry("Canada");
    	datafilter.addCountry("Republic of Korea");
    	datafilter.addCountry("Italy");
    	datafilter.addCountry("Australia");
    	datafilter.addCountry("Bangladesh");
    			
//    	DatabaseReader psql = new PSqlReader();
//    	Connection connection = psql.connectDataBase("COVID", "postgres", "Xboxlive72");
    	
    	
    	
    	
    	
    	DatabaseReader psql = new PSqlReader();
    	Connection connection = psql.connectDataBase("COVID", "postgres", "Xboxlive72");
    	String countryNm = "United States of America";
    	File file = new File("../countryTrends/" + countryNm +".csv");
    	ArrayList<String> columns = new ArrayList<>();
    	
    	columns.add(DatabaseConstants.TIMESTAMP);
    	columns.add(DatabaseConstants.COUNTRY_NM);
    	columns.add(DatabaseConstants.CONFIRMED_NUM);
    	columns.add(DatabaseConstants.RECENT_CASE);
    	
//    	psql.getAllData(connection, file);
    	psql.getCountryData(connection, file, countryNm, columns);
        
        

    }
}

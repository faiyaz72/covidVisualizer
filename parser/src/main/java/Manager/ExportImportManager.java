package Manager;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

import interfaces.DataProcessor;
import interfaces.DatabaseReader;
import model.DataNode;
import utils.DataFilter;

public class ExportImportManager {
	
	private DatabaseReader database;
	private Connection connection;
	private DataProcessor dataReader;
	
	public ExportImportManager(DatabaseReader database, Connection connection, DataProcessor dataReader) {
		this.database = database;
		this.connection = connection;
		this.dataReader = dataReader;
	}
	
	public void exportSingleCountryData(String countryNm, String directory, ArrayList<String> columns) {
		File file = new File(directory + "/" + countryNm +".csv");
		database.getCountryData(connection, file, countryNm, columns);
	}
	
	public void importNewDataFromDirectory(DataFilter filter, String directory) {
		try {
			ArrayList<DataNode> datalist = dataReader.readAllData(filter, directory);
			database.writeNewData(connection, datalist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void importNewCountryData(String countryNm, String directory) {
		DataFilter filter = new DataFilter();
		filter.addCountry(countryNm);
		importNewDataFromDirectory(filter, directory);
	}
	
	public void updateExistingData(String directory) {
		
		try {
			ArrayList<String> countryList = database.getExistingCountriesInDatabase(connection);
			DataFilter filter = new DataFilter();
			for (int i = 0; i < countryList.size(); i++) {
				filter.addCountry(countryList.get(i));
			}
			importNewDataFromDirectory(filter, directory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

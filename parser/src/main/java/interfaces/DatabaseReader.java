package interfaces;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DataNode;

public interface DatabaseReader {
	
	public Connection connectDataBase(String databaseNm, String userNm, String password);
	
	public void getCountryData(Connection databaseConnection, File destinationFile, String countryNm, ArrayList<String> colums);
	
	public void getAllData(Connection databaseConnection, File destinationFile);
	
	public void getMultipleCountryData();
	
	public void writeNewData(Connection databaseConnection, ArrayList<DataNode> dataList);

	public ArrayList<String> getExistingCountriesInDatabase(Connection databaseConnection);
}
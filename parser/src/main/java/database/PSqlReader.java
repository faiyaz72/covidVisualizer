package database;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

import constants.DatabaseConstants;
import interfaces.DatabaseReader;
import model.DataNode;
import utils.DataParameters;

public class PSqlReader implements DatabaseReader {

	@Override
	public Connection connectDataBase(String databaseNm, String userNm, String password) {
		Connection c = null;
		
		try {
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseNm, userNm, password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Opened database successfully");
		return c;
	}

	@Override
	public void getCountryData(Connection databaseConnection, File destinationFile, String countryNm, ArrayList<String> columns) {
		
		ArrayList<DataNode> datalist = new ArrayList<>();
		String sql = "select *\r\n" + 
				"from \"covidData\".\"CaseData\" c1 \r\n" + 
				"WHERE c1.\"CountryNm\" = '" + countryNm + "'\r\n" + 
				"order by c1.\"TimeStamp\" asc;";
		try {
			Statement statement = databaseConnection.createStatement();
			performQuery(databaseConnection, datalist, sql);
			FileWriter outputFile = new FileWriter(destinationFile);
			CSVWriter write = new CSVWriter(outputFile);
			String[] header = arrangeHeaders(columns);
			write.writeNext(header);
			for (DataNode dataNode : datalist) {
                String[] data = DataParameters.modelToDataConverter(dataNode, columns);
                write.writeNext(data);
            }
			write.close();
            System.out.println("Data successfully writen");
		} catch (Exception e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
		}
		
	}

	private String[] arrangeHeaders(ArrayList<String> columns) {
		ArrayList<String> result = new ArrayList<>();
    	
    	if (columns.contains(DatabaseConstants.COUNTRY_NM)) {
    		result.add(DatabaseConstants.COUNTRY_NM);
    	}
    	
    	if (columns.contains(DatabaseConstants.TIMESTAMP)) {
    		result.add(DatabaseConstants.TIMESTAMP);
    	}
    	
    	if (columns.contains(DatabaseConstants.CONFIRMED_NUM)) {
    		result.add(DatabaseConstants.CONFIRMED_NUM);
    	}
    	if (columns.contains(DatabaseConstants.RECENT_CASE)) {
    		result.add(DatabaseConstants.RECENT_CASE);
    	}
    	if (columns.contains(DatabaseConstants.DEATH_NUM)) {
    		result.add(DatabaseConstants.DEATH_NUM);
    	}
    	if (columns.contains(DatabaseConstants.RECENT_DEATH_NUM)) {
    		result.add(DatabaseConstants.RECENT_DEATH_NUM);
    	}
    	if (columns.contains(DatabaseConstants.TRASNMISSION_MODE)) {
    		result.add(DatabaseConstants.TRASNMISSION_MODE);
    	}
    	
		return DataParameters.ArrayLisConverter(result);
	}

	@Override
	public void getAllData(Connection databaseConnection, File destinationFile) {
		
		ArrayList<DataNode> datalist = new ArrayList<>();
		String sql = "select c1.\"CountryNm\",c1.\"TimeStamp\", c1.\"ConfirmedNm\", c1.\"RecentCaseNum\", c1.\"DeathNum\", c1.\"RecentDeathNum\", c1.\"TransmissionMode\"\r\n" + 
				"from \"covidData\".\"CaseData\" c1\r\n" + 
				"order by c1.\"CountryNm\", c1.\"TimeStamp\" asc;";
		try {
			performQuery(databaseConnection, datalist, sql);
			FileWriter outputFile = new FileWriter(destinationFile);
			CSVWriter write = new CSVWriter(outputFile);
			String[] header = {DatabaseConstants.COUNTRY_NM, DatabaseConstants.TIMESTAMP, DatabaseConstants.CONFIRMED_NUM, DatabaseConstants.RECENT_CASE,
					DatabaseConstants.DEATH_NUM, DatabaseConstants.RECENT_DEATH_NUM, DatabaseConstants.TRASNMISSION_MODE};
			write.writeNext(header);
			for (DataNode dataNode : datalist) {
                String[] data = DataParameters.modelToDataConverter(dataNode);
                write.writeNext(data);
            }
            write.close();
            System.out.println("Data successfully writen");
		} catch (Exception e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
		}
		
		
	}

	private void performQuery(Connection databaseConnection, ArrayList<DataNode> datalist, String sql) throws SQLException {
		Statement statement = databaseConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			DataNode data = new DataNode();
			extractData(resultSet, data);
			datalist.add(data);
		}
	}

	private void extractData(ResultSet resultSet, DataNode data) throws SQLException {
		
		if (resultSet.getString(DatabaseConstants.COUNTRY_NM) != null) {
			data.setCountryName(resultSet.getString(DatabaseConstants.COUNTRY_NM));
		}
		
		if (resultSet.getDate(DatabaseConstants.TIMESTAMP) != null) {
			data.setDataTimeStamp(resultSet.getDate(DatabaseConstants.TIMESTAMP));
		}
		
		if (resultSet.getString(DatabaseConstants.TRASNMISSION_MODE) != null) {
			data.setModeOfTransmission(resultSet.getString(DatabaseConstants.TRASNMISSION_MODE));
		}
		
		if (resultSet.getInt(DatabaseConstants.CONFIRMED_NUM) != 0) {
			data.setNumConfirmed(resultSet.getInt(DatabaseConstants.CONFIRMED_NUM));
		}
		
		if (resultSet.getInt(DatabaseConstants.RECENT_CASE) != 0) {
			data.setNumRecentCase(resultSet.getInt(DatabaseConstants.RECENT_CASE));
		}
		
		if (resultSet.getInt(DatabaseConstants.DEATH_NUM) != 0) {
			data.setNumDeath(resultSet.getInt(DatabaseConstants.DEATH_NUM));
		}
		
		if (resultSet.getInt(DatabaseConstants.RECENT_DEATH_NUM) != 0) {
			data.setRecentDeath(resultSet.getInt(DatabaseConstants.RECENT_DEATH_NUM));
		}
		
	}

	@Override
	public void getMultipleCountryData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeNewData(Connection databaseConnection, ArrayList<DataNode> dataList) {
		String sql = "INSERT INTO \"covidData\".\"CaseData\"(\"CountryNm\", \"ConfirmedNm\", \"RecentCaseNum\", \"DeathNum\", \"RecentDeathNum\", \"TransmissionMode\", \"TimeStamp\")\r\n" + 
				"VALUES\r\n" + 
				"(?, ?, ?, ?, ?, ?, ?);";
		
		try {
			PreparedStatement pstmt = databaseConnection.prepareStatement(sql);
			
			int count = 0;
			for (int i = 0; i < dataList.size(); i++) {
				pstmt.setString(1, dataList.get(i).getCountryName());
				pstmt.setInt(2, dataList.get(i).getNumConfirmed());
				pstmt.setInt(3, dataList.get(i).getNumRecentCase());
				pstmt.setInt(4, dataList.get(i).getNumDeath());
				pstmt.setInt(5, dataList.get(i).getRecentDeath());
				pstmt.setString(6, dataList.get(i).getModeOfTransmission());
				pstmt.setDate(7, DataParameters.convertStrToDate(dataList.get(i).getDataTimeStamp()));
				
				pstmt.addBatch();
				count++;
				if (count % 30 == 0 || count == dataList.size()) {
					System.out.println("Added " + count + " rows into table");
                    pstmt.executeBatch();
                }
			}
			
			
		} catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
	}
	
}
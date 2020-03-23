package dataReaders;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import interfaces.DataProcessor;
import model.DataNode;
import utils.DataFilter;
import utils.DataParameters;

public class CSVDataReader implements DataProcessor {

	@Override
	public ArrayList<DataNode> readAllData(DataFilter filter, String dataPath) throws CsvValidationException, IOException {
		
		ArrayList<String> countriesOfInterest = filter.getCountryFilter();
		ArrayList<DataNode> dataList = new ArrayList<>();
		
		try {
            File[] dataFiles = new File(dataPath).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    String[] split = file.getName().split("-");
                    return split[0].equals("tabula");
                }
            });

            assert dataFiles != null;
            for (File dataFile : dataFiles) {
                readFileData(countriesOfInterest, dataList, dataFile);
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
		return dataList;
    }

	private void readFileData(ArrayList<String> countriesOfInterest, ArrayList<DataNode> dataList, File dataFile)
			throws FileNotFoundException, IOException, CsvValidationException {
		FileReader fileReader = new FileReader(dataFile);
		CSVReader csvReader = new CSVReader(fileReader);
		String dataTimeStamp = DataParameters.fileNameParser(dataFile.getName());
		String[] nextRecord;
		System.out.println(dataTimeStamp);
		while ((nextRecord = csvReader.readNext()) != null) {
		    if (!nextRecord[5].equals("") && countriesOfInterest.contains(nextRecord[0])) {
		        DataNode dataNode = new DataNode();
		        dataNode.setCountryName(nextRecord[0]);
		        dataNode.setNumConfirmed(Integer.parseInt(nextRecord[1]));
		        dataNode.setNumRecentCase(Integer.parseInt(nextRecord[2]));
		        dataNode.setNumDeath(Integer.parseInt(nextRecord[3]));
		        dataNode.setRecentDeath(Integer.parseInt(nextRecord[4]));
		        dataNode.setModeOfTransmission(nextRecord[5]);
		        dataNode.setDataTimeStamp(dataTimeStamp);
		        dataList.add(dataNode);
		    }
		}
	}

	@Override
	public ArrayList<DataNode> readSpecificFileData(File file, DataFilter filter) {
		ArrayList<DataNode> result = new ArrayList<>();
		ArrayList<String> countriesOfInterest = filter.getCountryFilter();
		try {
			readFileData(countriesOfInterest, result, file);
		} catch (CsvValidationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void writeData(ArrayList<DataNode> dataList, File destinationFile) {
        try {
            FileWriter outputFile = new FileWriter(destinationFile);
            CSVWriter write = new CSVWriter(outputFile);
            String[] header = {"CountryNm", "ConfirmedNum", "RecentCaseNum", "DeathNum", "RecentDeathNum", "TransmissionMode", "TimeStamp"};
            write.writeNext(header);

            for (DataNode dataNode : dataList) {
                String[] data = DataParameters.modelToDataConverter(dataNode);
                write.writeNext(data);
            }
            write.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}

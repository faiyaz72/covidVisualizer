import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import model.DataNode;
import utils.DataParameters;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<DataNode> dataList = new ArrayList<>();
        readData(dataList);

        System.out.println("Enter Country Name to generate CSV data");
        Scanner input = new Scanner(System.in);
        String country = input.nextLine();

        writeCountryData(dataList, country);

//        writeAllData(dataList);

    }

    private static void writeAllData(ArrayList<DataNode> dataList) {
        File file = new File("../data/sqlData.csv");

        try {
            FileWriter outputFile = new FileWriter(file);
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

    private static void writeCountryData(ArrayList<DataNode> dataList, String countryNm) {
        File file = new File("../data/" + countryNm + ".csv");

        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter write = new CSVWriter(outputFile);
            String[] header = {"CountryNm", "ConfirmedNum", "RecentCaseNum", "DeathNum", "RecentDeathNum", "TransmissionMode", "TimeStamp"};
            write.writeNext(header);

            for (DataNode dataNode : dataList) {
                if (dataNode.getCountryName().equals(countryNm)) {
                    String[] data = DataParameters.modelToDataConverter(dataNode);
                    write.writeNext(data);
                }
            }
            write.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readData(ArrayList<DataNode> dataList) {
        try {
            ArrayList<String> countriesOfInterest = DataParameters.getCountriesOfInterests();

            String dataPath = DataParameters.getDataPath();
            File[] dataFiles = new File(dataPath).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    String[] split = file.getName().split("-");
                    return split[0].equals("tabula");
                }
            });

            assert dataFiles != null;
            for (File dataFile : dataFiles) {

                FileReader fileReader = new FileReader(dataFile);
                CSVReader csvReader = new CSVReader(fileReader);
                String dataTimeStamp = DataParameters.fileNameParser(dataFile.getName());
                String[] nextRecord;
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
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }
}

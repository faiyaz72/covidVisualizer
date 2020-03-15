import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.DataNode;
import utils.DataParameters;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<DataNode> dataList = new ArrayList<>();

        readData(dataList);

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

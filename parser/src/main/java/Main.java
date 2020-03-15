import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.DataNode;
import utils.DataParameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<DataNode> dataList = new ArrayList<>();

        try {
            File file = new File("../data/tabula-20200309-sitrep-49-covid-19.csv");
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);
            String dataTimeStamp = DataParameters.fileNameParser(file.getName());
            ArrayList<String> countriesOfInterest = DataParameters.getCountriesOfInterests();



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

            System.out.println(dataList.size());

        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }

    }


}

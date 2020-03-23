package interfaces;

import java.io.File;
import java.util.ArrayList;

import model.DataNode;
import utils.DataFilter;

public interface DataProcessor {
	
	public ArrayList<DataNode> readAllData(DataFilter filter, String dataPath) throws Exception;
	
	public ArrayList<DataNode> readSpecificFileData(File file, DataFilter filter);
	
	public void writeData(ArrayList<DataNode> dataList, File destinationFile);
	
}

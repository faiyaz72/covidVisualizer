package model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DataNode {

    private String countryName;

    private int numConfirmed;

    private int numRecentCase;

    private String modeOfTransmission;

    private int numDeath;

    private int recentDeath;

    private String dataTimeStamp;


    public String getCountryName() {
        return countryName;
    }

    public int getNumConfirmed() {
        return numConfirmed;
    }

    public int getNumRecentCase() {
        return numRecentCase;
    }

    public void setNumRecentCase(int numRecentCase) {
        this.numRecentCase = numRecentCase;
    }

    public String getModeOfTransmission() {
        return modeOfTransmission;
    }

    public void setModeOfTransmission(String modeOfTransmission) {
        this.modeOfTransmission = modeOfTransmission;
    }

    public int getNumDeath() {
        return numDeath;
    }

    public void setNumDeath(int numDeath) {
        this.numDeath = numDeath;
    }

    public int getRecentDeath() {
        return recentDeath;
    }

    public void setRecentDeath(int recentDeath) {
        this.recentDeath = recentDeath;
    }

    public void setNumConfirmed(int numConfirmed) {
        this.numConfirmed = numConfirmed;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDataTimeStamp() {
        return dataTimeStamp;
    }

    public void setDataTimeStamp(String dataTimeStamp) {
        this.dataTimeStamp = dataTimeStamp;
    }

	public void setDataTimeStamp(Date date) {
		this.dataTimeStamp = date.toString();
		
	}
}

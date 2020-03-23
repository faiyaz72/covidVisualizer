package utils;

import java.util.ArrayList;

public class DataFilter {
	
	
	private ArrayList<String> countryFilter = new ArrayList<>();
	
	
	

	public ArrayList<String> getCountryFilter() {
		return countryFilter;
	}

	public void setCountryFilter(ArrayList<String> countryFilter) {
		this.countryFilter = countryFilter;
	}
	
	public void addCountry(String countryNm) {
		countryFilter.add(countryNm);
	}
	
	public void removeCountry(String countryNm) {
		countryFilter.remove(countryNm);
	}
	
	public int getFilterLength() {
		return countryFilter.size();
	}
	
}

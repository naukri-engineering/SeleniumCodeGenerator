package csvReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CSVReader {

	String path;

	public CSVReader(String path) {
		this.path = path;
	}


	/**
	 * To get data from CSV file if row number and column heading is provided.
	 * @param colHeading String 
	 * @param rowno int
	 * @return String which contains the data for the particular row and column heading
	 */


	public String getCellData(String colHeading, int rowno) {

		if(rowno <=0)
			return "";

		if(colHeading.trim().equalsIgnoreCase(""))
			return "";

		String splitBy = ",";		 
		List<HashMap> data = new ArrayList<HashMap>();	
		String line="";
		BufferedReader brCSV=null;
		try {
			brCSV = new BufferedReader(new FileReader(path));
			line = brCSV.readLine();

			String[] headings = line.split(splitBy);
			int i=0;

			while ((line = brCSV.readLine()) != null) {
				String[] testdata = line.split(splitBy);
				HashMap<String, String> hm = new HashMap<String, String>();
				for(int headcount=0; headcount<headings.length; headcount++){

					hm.put(headings[headcount].trim(), testdata[headcount]);

				}

				data.add(hm);
				i=i+1;
				brCSV.close();

			}} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	       



		return (String) data.get(rowno-2).get(colHeading.toString());

	}

	/**
	 * To get row count from the CSV file.	
	 * @return int which contains the number of rows for particular CSV file 
	 */

	public int getRowCount() {

		BufferedReader brCSV;
		int i=0;
		try {
			brCSV = new BufferedReader(new FileReader(path));
			String line ="";
			while ((line = brCSV.readLine()) != null) {
				i=i+1;
			}
			brCSV.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 

		return i;


	}

}

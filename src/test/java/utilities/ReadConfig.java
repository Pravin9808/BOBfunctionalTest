package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Properties;

public class ReadConfig {
	Properties pro;
	public ReadConfig() {
		File src=new File("./src/test/java/Resources/config.properties");
		try {
			FileInputStream fis = new FileInputStream(src);
			pro =new Properties();          //initiating pro object
			pro.load(fis);                  // load is method to load complete file in runtime
		}
		catch(Exception e){
			System.out.println("Exception is"+ e.getMessage());
		}
	}
	
	public String getApplicationUrl() {
		String url=pro.getProperty("baseURL");
		return url;
	}
	
	public ArrayList getPLCalData() {
		ArrayList<String> caldata= new ArrayList<String>() ;
		caldata.add(pro.getProperty("loan_amt_pl"));
		caldata.add(pro.getProperty("int_pl"));
		caldata.add(pro.getProperty("tenure_pl"));
		
		return caldata;
		
	}
	
	
	

	public String getChromePath() {
		String path=pro.getProperty("chromepath");
		// TODO Auto-generated method stub
		return path;
	}

}

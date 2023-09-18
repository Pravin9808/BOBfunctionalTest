package testCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import BOBLinkStatus.BOBLinkStatus.BaseClass;

public class test extends BaseClass {
	
	
	@Test

	public void test1() throws InterruptedException {
		
		
			driver.findElement(By.xpath("//div[@class='privacy-warning acceptonclose']/div[@class='close']")).click();
			// long startTime= System.currentTimeMillis()
			List<WebElement> bobnavBar= driver.findElements(By.xpath("//ul[@class='bob-middle-first-link bob-left-link']/li[@class=' global-nav-l2']/a"));
			Actions actions = new Actions(driver);
			
			for(int i=0; i<bobnavBar.size(); i++) {
				Thread.sleep(5000);
				//actions.contextClick(bobnavBar..get(i)).click();
				bobnavBar.get(i).sendKeys(Keys.chord(Keys.CONTROL,Keys.ENTER));
				Set<String> windows=driver.getWindowHandles();
				Iterator<String> it= windows.iterator();
				
				String parentId=it.next();
				String childId=it.next();
				driver.switchTo().window(childId);
				driver.close();
				driver.switchTo().window(parentId);
				
				
				System.out.println(bobnavBar.get(i).getText());
				
			}
	}}
		// TODO Auto-generated method stub
		/*
		FileInputStream fs = new FileInputStream( ".//InputFiles//Territory.xlsx");
		//Creating a workbook 		
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet1 = workbook.getSheetAt(0);
		int rowCount = sheet1.getLastRowNum()-sheet1.getFirstRowNum();
		System.out.println(rowCount);
		 
		 	//Create a loop over all the rows of excel file to read it
			  
			    for (int i = 0; i < rowCount+1; i++) {
			        XSSFRow currentrow = sheet1.getRow(i);
			        HashMap<String, Integer> links = new HashMap<String, Integer>();

			        for (int j = 0; j < currentrow.getLastCellNum(); j++) {
			        	 if(currentrow.getCell(j)!= null) {
			            //Print Excel data in console
			        	String value1= currentrow.getCell(j).getStringCellValue();
			        	String[] sheetNam =value1.split("//");
			        	//String[] sheetName=sheetNam[1].split("com.");
			        	System.out.println(sheetNam[1]);
			        	 }
			        	 
			        	 
			        	 
			        }
			        
			        
			       
		
		driver.get("https://www.bankofbaroda.in/");
    	Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@class='privacy-warning acceptonclose']/div[@class='close']")).click();
		driver.findElement(By.xpath("//a[@class='search-popup']")).click();
		driver.findElement(By.xpath("//input[@placeholder='Looking for something specific?']")).sendKeys("loan");
	    Thread.sleep(5000);
		List<WebElement> options=driver.findElements(By.xpath("//ul[@class='search-result-list']/li/h4"));
		System.out.println(options.size());
		ArrayList<String> ar=new ArrayList<String>();
		
		for (int j=0;j<options.size();j++) {
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", options.get(j) );
			String text= options.get(j).getText();		
			ar.add(text);
			}
		    System.out.println(ar);	
			   
	}
}
			     }*/
	



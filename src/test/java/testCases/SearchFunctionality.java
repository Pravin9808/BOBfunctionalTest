package testCases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import BOBLinkStatus.BOBLinkStatus.BaseClass;

public class SearchFunctionality extends BaseClass{
	
	@Test
	public void search() throws InterruptedException, IOException {
		
		
		Thread.sleep(5000);
		FileInputStream inpFiles = new FileInputStream(".//InputFiles//Territory.xlsx");
		// Creating a workbook
		XSSFWorkbook inpDataWkb = new XSSFWorkbook(inpFiles);
		XSSFSheet inpSheet = inpDataWkb.getSheetAt(0);
		int inpRowCount = inpSheet.getLastRowNum()-inpSheet.getFirstRowNum();
		
		FileInputStream searchLinks = new FileInputStream(".//DataFiles//Search.xls");
        HSSFWorkbook outPutWkb = new HSSFWorkbook(searchLinks);
       
		
		for (int i = 0; i <inpRowCount+1; i++) {
			XSSFRow currentrow = inpSheet.getRow(i);	
			
				DataFormatter formatter = new DataFormatter();
				String urlName = formatter.formatCellValue(currentrow.getCell(0));			        			        	
	        	String searchData = formatter.formatCellValue(currentrow.getCell(1));
	        	
	        	driver.get(urlName);
	        	Thread.sleep(1000);
	        	try {
				driver.findElement(By.xpath("//div[@class='privacy-warning acceptonclose']/div[@class='close']|//a[@class='AllowAll cookie-btn']")).click();
	        	}
	        	catch(Exception e) {
	        		e.getMessage();
	        	}
				driver.findElement(By.xpath("//a[@class='search-popup']")).click();
				driver.findElement(By.xpath("//input[@placeholder='Looking for something specific?']")).sendKeys(searchData);
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
				    
				    String[] sheetNam = urlName.split("//");
					String[] sheetNam2 = sheetNam[1].split("/");
						
					 for (int k = 0;k < outPutWkb.getNumberOfSheets(); k++) {
							// System.out.println(wkb.getSheetAt(i).getSheetName());
							if (outPutWkb.getSheetAt(k).getSheetName().equals(sheetNam2[0])) {
								outPutWkb.removeSheetAt(k);
						}
							
					 }
					
			        HSSFSheet optSheet = outPutWkb.createSheet(sheetNam2[0]);
			        int rowno = 0;
			        for (int j=0;j<ar.size();j++) {
			    	   HSSFRow row = optSheet.createRow(rowno++);
			    	   String data=ar.get(j);
			    	   row.createCell(0).setCellValue(data);        
	    	     }  
			      
				
				FileOutputStream fos = new FileOutputStream(".//DataFiles//Search.xls");
				outPutWkb.write(fos);
				
			}
	}
}

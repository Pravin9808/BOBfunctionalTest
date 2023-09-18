package testCases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import BOBLinkStatus.BOBLinkStatus.BaseClass;



public class BOBPageLinkStatus extends BaseClass  {
	
	@Test(priority=0)
	public void personalBanking() throws InterruptedException, IOException {
		driver.findElement(By.xpath("//div[@class='privacy-warning acceptonclose']/div[@class='close']")).click();
		// long startTime= System.currentTimeMillis()
		List<WebElement> bobnavBar= driver.findElements(By.xpath("//ul[@class='bob-middle-first-link bob-left-link']/li[@class=' global-nav-l2']/a"));
		Actions actions = new Actions(driver);
		
		for(int i=0; i<bobnavBar.size(); i++) {
			bobnavBar.get(i).sendKeys(Keys.chord(Keys.CONTROL,Keys.ENTER));
			Set<String> windows=driver.getWindowHandles();
			Iterator<String> it= windows.iterator();
			
			String parentId=it.next();
			String childId=it.next();
			driver.switchTo().window(childId);
			List<WebElement> atags = driver.findElements(By.tagName("a"));
			//List<WebElement> imgtags= driver.findElements(By.tagName("img"));
			// ArrayList<String> ar= new ArrayList();
					HashMap<String, Integer> links = new HashMap<String, Integer>();
					for (int j = 0; j < atags.size(); j++) {
						//Thread.sleep(5000);
						String alinkName = atags.get(j).getAttribute("href");
						String atagsName = atags.get(j).getText();
						//atags.get(j).click();
						//driver.switchTo().activeElement();
						
						try {
							if (alinkName.contains("/")) {
								//Thread.sleep(7000);
									HttpURLConnection conn=(HttpURLConnection) new URL(alinkName).openConnection();
									conn.setConnectTimeout(15000);
									conn.setRequestMethod("GET");
									//Thread.sleep(7000);
									conn.connect();				
									int rescode= conn.getResponseCode();
									System.out.println(rescode);
									System.out.println(alinkName);
									
									try {
										links.put(alinkName, rescode);
									} catch (Exception e) {
										e.getMessage();
									}
						/*
						if (rescode==404) {
							System.out.println(link.getText());
							System.out.println(url);
						}*/
								}
							else {
									continue;
								}
						}
							catch(Exception e) {
								e.getMessage();
							}
						}
					
					
					FileInputStream bussinessLinks=new FileInputStream(".//DataFiles//BOBDomesticLinks.xls");
					
					HSSFWorkbook wkb = new HSSFWorkbook(bussinessLinks);  
					for(int m=0;m<wkb.getNumberOfSheets();m++){
			            //System.out.println(wkb.getSheetAt(i).getSheetName());
			            if(wkb.getSheetAt(m).getSheetName().equals("PersonalBankingLinks")){
			                wkb.removeSheetAt(m);
			            }
			        } 
				
			//ystem.out.println(links);
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("PersonalBankingLinks");
				int rowno = 0;

				for (Map.Entry entry : links.entrySet()) {
					
					HSSFRow row = sheet.createRow(rowno++);
					row.createCell(0).setCellValue((String) entry.getKey());
					row.createCell(1).setCellValue((Integer)entry.getValue());
					
				}
				FileOutputStream fos = new FileOutputStream(".//DataFiles//BOBDomesticLinks.xls");
				workbook.write(fos);
				driver.close();
				driver.switchTo().window(parentId);

			}
		
		
		
	}
	
	
	@Test(priority=1)
	public void bussinessBanking() throws InterruptedException, IOException {
		driver.get("https://www.bankofbaroda.in/business-banking");
		//driver.findElement(By.xpath("//div[@class='privacy-warning acceptonclose']/div[@class='close']")).click();
		// long startTime= System.currentTimeMillis()
		List<WebElement> atags = driver.findElements(By.tagName("a"));
			//List<WebElement> imgtags= driver.findElements(By.tagName("img"));
			
					HashMap<String, Integer> links = new HashMap<String, Integer>();
					for (int i = 0; i < atags.size(); i++) {
						
						String alinkName = atags.get(i).getAttribute("href");
						String atagsName = atags.get(i).getText();
						//atags.get(j).click();
						//driver.switchTo().activeElement();
						
						try {
							if (alinkName.contains("/")) {
								//Thread.sleep(7000);
									HttpURLConnection conn=(HttpURLConnection) new URL(alinkName).openConnection();
									conn.setConnectTimeout(15000);
									conn.setRequestMethod("GET");
									//Thread.sleep(7000);
									conn.connect();				
									int rescode= conn.getResponseCode();
									System.out.println(rescode);
									System.out.println(alinkName);
									
									try {
										links.put(alinkName, rescode);
									} catch (Exception e) {
										e.getMessage();
									}
						/*
						if (rescode==404) {
							System.out.println(link.getText());
							System.out.println(url);
						}}*/
								}
							else {
									continue;
								}
						}
							catch(Exception e) {
								e.getMessage();
							}
						
				
					//System.out.println(links);
					FileInputStream bussinessLinks=new FileInputStream(".//DataFiles//BOBDomesticLinks.xls");
					
					HSSFWorkbook wkb = new HSSFWorkbook(bussinessLinks);  
					for(int m=0;m<wkb.getNumberOfSheets();m++){
			            //System.out.println(wkb.getSheetAt(m).getSheetName());
			            if(wkb.getSheetAt(m).getSheetName().equals("BussinessLinks")){
			                wkb.removeSheetAt(m);
			            }
			        } 
					HSSFSheet sheet =wkb.createSheet("BussinessLinks");
					
					int rowno = 0;

					for (Map.Entry entry : links.entrySet()) {
						
						HSSFRow row = sheet.createRow(rowno++);
						row.createCell(0).setCellValue((String) entry.getKey());
						row.createCell(1).setCellValue((Integer)entry.getValue());
						
					}
					FileOutputStream fos = new FileOutputStream(".//DataFiles//BOBDomesticLinks.xls");
					wkb.write(fos);
		
			}
	}
	
}
package testCases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import BOBLinkStatus.BOBLinkStatus.BaseClass;

public class LodhaLink extends BaseClass {
	@Test
	public void bussinessBanking() throws InterruptedException, IOException {
		driver.get("https://lodha.ia-beta.in/");
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
					/*
					FileInputStream bussinessLinks=new FileInputStream(".//DataFiles//BOBDomesticLinks.xls");
					
					HSSFWorkbook wkb = new HSSFWorkbook(bussinessLinks);  
					for(int m=0;m<wkb.getNumberOfSheets();m++){
			            //System.out.println(wkb.getSheetAt(m).getSheetName());
			            if(wkb.getSheetAt(m).getSheetName().equals("BussinessLinks")){
			                wkb.removeSheetAt(m);
			            }
			        } */
					HSSFWorkbook wkb = new HSSFWorkbook();  
					HSSFSheet sheet =wkb.createSheet("BussinessLinks");
					
					int rowno = 0;

					for (Map.Entry entry : links.entrySet()) {
						
						HSSFRow row = sheet.createRow(rowno++);
						row.createCell(0).setCellValue((String) entry.getKey());
						row.createCell(1).setCellValue((Integer)entry.getValue());
						
					}
					FileOutputStream fos = new FileOutputStream(".//DataFiles//LodhaActiveLinks.xls");
					wkb.write(fos);
		
			}
	}
}

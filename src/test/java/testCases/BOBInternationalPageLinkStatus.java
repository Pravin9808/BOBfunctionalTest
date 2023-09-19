package testCases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import BOBLinkStatus.BOBLinkStatus.BaseClass;

public class BOBInternationalPageLinkStatus extends BaseClass {

	@Test
	public void personalBanking() throws InterruptedException, IOException {
		driver.findElement(By.xpath("//div[@class='privacy-warning acceptonclose']/div[@class='close']")).click();
		// long startTime= System.currentTimeMillis()
		
		FileInputStream inpFiles = new FileInputStream(System.getProperty("user.dir")+"//InputFiles//Territory.xlsx");
		XSSFWorkbook inpDataWkb = new XSSFWorkbook(inpFiles);
		XSSFSheet inpSheet= inpDataWkb.getSheetAt(0);
		
		int rowCount = inpSheet.getPhysicalNumberOfRows()-inpSheet.getFirstRowNum();
	
		// Create a loop over all the rows of excel file to read it

		for (int i = 0; i < rowCount + 1; i++) {
			XSSFRow currentrow = inpSheet.getRow(i);

					// Print Excel data in console
					String value1 = currentrow.getCell(0).getStringCellValue();
					String[] sheetNam = value1.split("//");
					String[] sheetNam2 = sheetNam[1].split("/");
					//System.out.println(sheetNam2[0]);

					driver.get(value1);
					Thread.sleep(5000);

					// long startTime= System.currentTimeMillis()
					List<WebElement> atags = driver.findElements(By.tagName("a"));
					// List<WebElement> imgtags= driver.findElements(By.tagName("img"));

					HashMap<String, Integer> links = new HashMap<String, Integer>();

					for (int k = 0; k < atags.size(); k++) {

						String alinkName = atags.get(k).getAttribute("href");
						// String atagsName = atags.get(k).getText();
						// atags.get(j).click();
						// driver.switchTo().activeElement();

						try {
							if (alinkName.contains("/")) {

								HttpURLConnection conn = (HttpURLConnection) new URL(alinkName).openConnection();
							    conn.setConnectTimeout(15000);
								conn.setRequestMethod("GET");
								// Thread.sleep(7000);
								conn.connect();
								int rescode = conn.getResponseCode();
								//System.out.println(rescode);
								//System.out.println(alinkName);

								try {
									links.put(alinkName, rescode);
								} catch (Exception e) {
									e.getMessage();
								}
								/*
								 * if (rescode==404) { System.out.println(link.getText());
								 * System.out.println(url); }
								 */
							} else {
								continue;
							}
						} catch (Exception e) {
							e.getMessage();
						}
					}

					// System.out.println(links);
					

					FileInputStream bobInActiveLinks = new FileInputStream(".//DataFiles//BOBInActiveLinks.xls");
					
					HSSFWorkbook wkb = new HSSFWorkbook(bobInActiveLinks);
					
					
					for (int m = 0; m < wkb.getNumberOfSheets(); m++) {
						// System.out.println(wkb.getSheetAt(i).getSheetName());
						if (wkb.getSheetAt(m).getSheetName().equals(sheetNam2[0])) {
							wkb.removeSheetAt(m);
						}
					}

					HSSFSheet sheet = wkb.createSheet(sheetNam2[0]);
					int rowno = 0;
					for (Map.Entry entry : links.entrySet()) {

						HSSFRow row = sheet.createRow(rowno++);
						row.createCell(0).setCellValue((String) entry.getKey());
						row.createCell(1).setCellValue((Integer) entry.getValue());

					}

					FileOutputStream fos = new FileOutputStream(".//DataFiles//BOBInActiveLinks.xls");
					wkb.write(fos);
				}
			}
		}

	

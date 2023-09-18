package tridentTestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class LodhaLink extends BaseClass {

	@Test(priority = 0)
	public void homePageLinkStatus() throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		// List<WebElement>
		// container=driver.findElements(By.xpath("//div[@class='siteMap-container
		// d-flex']"));
		driver.get("https://lodha.ia-beta.in/sitemap");
		List<WebElement> container = driver.findElements(By.xpath("//div[@class='siteMap-container d-flex']//div/ul"));

		List<WebElement> subcontainer = driver.findElements(By.xpath(
				"//div[@class='siteMap-container d-flex']//div[@class='siteMap-list-block pb-3' or 'siteMap-list-block sitemap-block-border pb-3']//ul[@class='siteMap-leval-2' or 'siteMap-level-1 pt-3' ]/li/a"));
		for (int j = 38; j < subcontainer.size(); j++) {
			
			Actions actions = new Actions(driver);

			if(subcontainer.get(j).getAttribute("href").contains("/")) {
				System.out.println(driver.getTitle());
				
				actions.moveToElement(subcontainer.get(j)).build().perform();
				subcontainer.get(j).sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
				Set<String> windows = driver.getWindowHandles();
				Iterator<String> it = windows.iterator();

				String parentId = it.next();
				Thread.sleep(7000);
				String childId = it.next();
				Thread.sleep(7000);
				driver.switchTo().window(childId);
				Thread.sleep(7000);
				// String title=driver.getCurrentUrl();
				HashMap<String, Integer> links = new HashMap<String, Integer>();
				List<WebElement> atags = driver.findElements(By.tagName("a"));

				for (int k = 0; k < atags.size(); k++) {
					// Thread.sleep(5000);
					String alinkName = atags.get(k).getAttribute("href");
					String atagsName = atags.get(k).getText();
					try {
						if (alinkName.contains("/")) {
							HttpURLConnection conn = (HttpURLConnection) new URL(alinkName).openConnection();
							conn.setConnectTimeout(15000);
							conn.setRequestMethod("GET");
							conn.connect();
							int rescode = conn.getResponseCode();
							System.out.println(rescode);
							System.out.println(alinkName);

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

				List<WebElement> imgtags = driver.findElements(By.tagName("img"));

				for (int l = 0; l < imgtags.size(); l++) {
					// Thread.sleep(5000);
					String imglinkName = imgtags.get(l).getAttribute("src");
					// System.out.println(imgtags.get(i).getText());
					String imgName = imgtags.get(l).getText(); // ar.add(imgName);
					try {
						if (imglinkName.contains("/")) {

							HttpURLConnection conn = (HttpURLConnection) new URL(imglinkName).openConnection();
							conn.setConnectTimeout(15000);
							conn.setRequestMethod("GET");
							conn.connect();
							int rescode = conn.getResponseCode();
							// System.out.println(rescode);
							// System.out.println(imglinkName);

							try {
								links.put(imglinkName, rescode);
							} catch (Exception e) {
								e.getMessage();
							}

						} else {
							continue;
						}
					} catch (Exception e) {
						e.getMessage();
					}
				}
				
				//FileInputStream lodhalink = new FileInputStream(".//DataFiles//LodhaLinkStatus.xls");
				
				HSSFWorkbook wkb = new HSSFWorkbook();
				HSSFSheet sheet = wkb.createSheet("about1");
				int rowno = 0;

				for (Map.Entry entry : links.entrySet()) {
					HSSFRow row = sheet.createRow(rowno++);
					row.createCell(0).setCellValue((String) entry.getKey());
					row.createCell(1).setCellValue((Integer) entry.getValue());

				}

				FileOutputStream fos = new FileOutputStream(".//DataFiles//LodhaLinkStatus.xls");
				wkb.write(fos);
				driver.close();
				Thread.sleep(5000);
				driver.switchTo().window(parentId);
			}
				else  {
					// TODO Auto-generated catch block
					continue;
				
				}
			} 
			

		}
	}



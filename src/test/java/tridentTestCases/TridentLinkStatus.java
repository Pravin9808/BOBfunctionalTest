package tridentTestCases;

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

public class TridentLinkStatus extends BaseClass {

	@Test(priority = 0)
	public void homePageLinkStatus() throws InterruptedException, IOException {
		// driver.findElement(By.xpath("//div[@class='privacy-warning
		// acceptonclose']/div[@class='close']")).click();
		// long startTime= System.currentTimeMillis()
		Actions actions = new Actions(driver);
		driver.findElement(By.xpath("//div[@id='nav-icon']")).click();
		actions.moveToElement(driver.findElement(By.xpath("//a[normalize-space()='Hotels']"))).build().perform();
		List<WebElement> navIcon = driver.findElements(By.xpath("//div[@class='menu-level-1']/ul/li/a"));
		System.out.println(navIcon.size());

		for (int i = 0; i < navIcon.size(); i++) {
			actions.moveToElement(driver.findElement(By.xpath("//a[normalize-space()='Hotels']"))).build().perform();
			navIcon.get(i).sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
			Set<String> windows = driver.getWindowHandles();
			Iterator<String> it = windows.iterator();

			String parentId = it.next();
			String childId = it.next();
			driver.switchTo().window(childId);
			String title = driver.getCurrentUrl();
			String[] sheetNam = title.split("iabeta.in/");
			String[] sheetNam2 = sheetNam[1].split("/");

			List<WebElement> atags = driver.findElements(By.tagName("a"));
			// Actions actions = new Actions(driver);
			HashMap<String, String> links = new HashMap<String, String>();
			//HashMap<String, Integer> links = new HashMap<String, Integer>();
			for (int j = 0; j < atags.size(); j++) {
				// Thread.sleep(5000);
				String alinkName = atags.get(j).getAttribute("href");
				String atagsName = atags.get(j).getText();
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
						
							links.put(alinkName, atagsName);
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

			for (int j = 0; j < imgtags.size(); j++) {
				// Thread.sleep(5000);
				String imglinkName = imgtags.get(j).getAttribute("src");
				// System.out.println(imgtags.get(i).getText());
				String imgName = imgtags.get(j).getText(); // ar.add(imgName);
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
							links.put(imglinkName, imgName);
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

			driver.close();
			driver.switchTo().window(parentId);
			/*FileInputStream tridentLink = new FileInputStream(".//DataFiles//TridentLinkSta.xls");

			HSSFWorkbook wkb = new HSSFWorkbook(tridentLink);

			for (int m = 0; m < wkb.getNumberOfSheets(); m++) {
				System.out.println(wkb.getSheetAt(m).getSheetName());
				if (wkb.getSheetAt(m).getSheetName().equals(sheetNam2[0])) {
					wkb.removeSheetAt(m);
				} else {
					continue;
				}
			}
*/
			HSSFWorkbook wkb = new HSSFWorkbook();
		
			HSSFSheet sheet = wkb.createSheet(sheetNam2[0]);
			int rowno = 0;

			for (Map.Entry entry : links.entrySet()) {
				HSSFRow row = sheet.createRow(rowno++);
				row.createCell(0).setCellValue((String) entry.getKey());
				row.createCell(1).setCellValue((String) entry.getValue());

			}
			FileOutputStream fos = new FileOutputStream(".//DataFiles//TridentLinkSta.xls");
			wkb.write(fos);

		}

	}

}

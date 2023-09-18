package tridentTestCases;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import BOBLinkStatus.BOBLinkStatus.BaseClass;

public class test3 extends BaseClass{

	@Test
	public void tags() throws InterruptedException, IOException{
		// TODO Auto-generated method stub
		//driver.get("http://tridentrevamp.iabeta.in/");
		Actions actions = new Actions(driver);
		driver.findElement(By.xpath("//div[@id='nav-icon']")).click();
		actions.moveToElement(driver.findElement(By.xpath("//a[normalize-space()='Hotels']"))).build().perform();
		List<WebElement> navIcon= driver.findElements(By.xpath("//div[@class='menu-level-1']/ul/li/a"));
		System.out.println(navIcon.size());
		
		for(int i=0; i<navIcon.size(); i++) {
			actions.moveToElement(driver.findElement(By.xpath("//a[normalize-space()='Hotels']"))).build().perform();
			navIcon.get(i).sendKeys(Keys.chord(Keys.CONTROL,Keys.ENTER));
			Set<String> windows=driver.getWindowHandles();
			Iterator<String> it= windows.iterator();
			
			String parentId=it.next();
			String childId=it.next();
			driver.switchTo().window(childId);
			String title=driver.getTitle();
			String[] sheetNam = title.split("Hotels in");
			
			System.out.println(sheetNam[1]);
			
			List<WebElement> atags = driver.findElements(By.tagName("a"));
		    //Actions actions = new Actions(driver);
			HashMap<String, Integer> links = new HashMap<String, Integer>();
			for (int j = 0; j < atags.size(); j++) {
				//Thread.sleep(5000);
				String alinkName = atags.get(j).getAttribute("href");
				String atagsName = atags.get(j).getText();
			try {
				if (alinkName.contains("/")) {
						HttpURLConnection conn=(HttpURLConnection) new URL(alinkName).openConnection();
						conn.setConnectTimeout(15000);
						conn.setRequestMethod("GET");
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
			driver.close();
		
			driver.switchTo().window(parentId);
			Thread.sleep(5000);
		}
		
}}

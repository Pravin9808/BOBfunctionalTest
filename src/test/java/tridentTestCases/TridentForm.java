package tridentTestCases;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import BOBLinkStatus.BOBLinkStatus.BaseClass;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TridentForm extends BaseClass {
	@Test(priority=0)
	public void homePageLinkStatus() throws InterruptedException, TesseractException, IOException {
		driver.get("http://tridentrevamp.iabeta.in/contact-us/");
		Actions actions = new Actions(driver);
		Thread.sleep(5000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h1[normalize-space()='Contact Us']")));
		Thread.sleep(5000);
		actions.moveToElement(driver.findElement(By.xpath("//div[@data-id='writeUs']"))).perform();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//img[@id='captchaImage']")));
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@data-id='writeUs']")).click();
		Thread.sleep(5000);
		actions.moveToElement(driver.findElement(By.xpath("//img[@id='captchaImage']"))).build().perform();
		File src=driver.findElement(By.xpath("//img[@id='captchaImage']")).getScreenshotAs(OutputType.FILE);
		String path=".//InputFiles//captcha.png";
		
		FileHandler.copy(src, new File(path));
		
		Thread.sleep(3000);
		ITesseract image =new Tesseract();
		image.setDatapath("C:\\Automation\\Tess4J-3.4.8-src\\Tess4J\\tessdata");
		 
		String imr = image.doOCR(new File(path));
		String[] imr2=imr.split(" ");
		System.out.println(imr);				
		driver.findElement(By.xpath("//input[@id='txtCaptchaPassword']")).sendKeys(imr2);
		
	}
	
	
	@Test(priority=1)
	public void tridentPriviledgeSignin() throws InterruptedException, TesseractException {
		driver.get("http://tridentrevamp.iabeta.in/trident-privilege/sign-in/");
		Actions actions = new Actions(driver);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='prof-sbmt-btn']//a[contains(text(),'Enrol Now')]")));
		Thread.sleep(5000);
		actions.moveToElement(driver.findElement(By.xpath("//div[@class='prof-sbmt-btn']//a[contains(text(),'Enrol Now')]")));
		actions.doubleClick(driver.findElement(By.xpath("//div[@class='prof-sbmt-btn']//a[contains(text(),'Enrol Now')]"))).perform();
		Thread.sleep(5000);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inputbox']//span[@id='txt_firstname_err']")).getText(), "Please enter your first name.");
		driver.findElement(By.xpath("//input[@id='txt_firstname']")).sendKeys("test");
		Thread.sleep(5000);
		actions.doubleClick(driver.findElement(By.xpath("//div[@class='prof-sbmt-btn']//a[contains(text(),'Enrol Now')]"))).perform();
		Thread.sleep(5000);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inputbox']//span[@id='txt_lastname_err']")).getText(), "Please enter your last name.");
		driver.findElement(By.xpath("//span[@id='txt_lastname']")).sendKeys("testIA");
		actions.doubleClick(driver.findElement(By.xpath("//div[@class='prof-sbmt-btn']//a[contains(text(),'Enrol Now')]"))).perform();
		Thread.sleep(5000);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inputbox']//span[@id='txt_emailid_err']']")).getText(), "Please enter Email Id.");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='txt_lastname']")).sendKeys("test@ignore.com");
		actions.doubleClick(driver.findElement(By.xpath("//div[@class='prof-sbmt-btn']//a[contains(text(),'Enrol Now')]"))).perform();
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='ddl_country_err']")).getText(), "Please select Country.");
		Thread.sleep(5000);
		Select objSelect = new Select(driver.findElement(By.xpath("//select[@id='ddl_country']")));
		objSelect.selectByIndex(0);		
		
		
	}
	
}



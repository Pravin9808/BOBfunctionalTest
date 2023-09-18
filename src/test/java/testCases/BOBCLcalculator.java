package testCases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
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


public class BOBCLcalculator extends BaseClass {

	
	@Test(priority=0)
	public void axisPlCalAmortisation() throws InterruptedException, IOException, InvalidFormatException  {
	
		FileInputStream inpFiles = new FileInputStream(System.getProperty("user.dir")+"//InputFiles//InputFiles.xlsx");
		XSSFWorkbook inpDataWkb = new XSSFWorkbook(inpFiles);
		XSSFSheet inpSheet= inpDataWkb.getSheetAt(2);
		
		int inpRowCount = inpSheet.getPhysicalNumberOfRows()-inpSheet.getFirstRowNum();
		
		FileInputStream amrtFile=new FileInputStream(".//DataFiles//BOBAmrtSchd.xlsx");
		XSSFWorkbook wkb = new XSSFWorkbook(amrtFile); 
		for(int i=0;i<wkb.getNumberOfSheets();i++){
            //System.out.println(wkb.getSheetAt(i).getSheetName());
            if(wkb.getSheetAt(i).getSheetName().equals("CarLoanCalData")){
                wkb.removeSheetAt(i);
            }
        } 
		//wkb.removeSheetAt(1);
		wkb.createSheet("CarLoanCalData");
		
		
		for (int i = 2; i <=inpRowCount+1; i++) {
				XSSFRow currentrow = inpSheet.getRow(i);
							
				 DataFormatter formatter = new DataFormatter();
					String amount = formatter.formatCellValue(currentrow.getCell(0));			        			        	
		        	String rateOfInterest = formatter.formatCellValue(currentrow.getCell(1));
					String loanTermsInMonths = formatter.formatCellValue(currentrow.getCell(2));
					String loanTermsInYears = formatter.formatCellValue(currentrow.getCell(3));
					
					//String loanTermsInYears = currentrow.getCell(1).getStringCellValue();
					      
					XSSFSheet sheet1 = wkb.getSheet("CarLoanCalData");
					int initialRow=sheet1.getLastRowNum()+2;
					
					
					
					driver.get("https://www.bankofbaroda.in/calculators/car-loan-emi-calculator");
					driver.findElement(By.xpath("//div[@class='privacy-warning acceptonclose']/div[@class='close']")).click();
					driver.manage().deleteAllCookies();
					Thread.sleep(5000);
					
					JavascriptExecutor js = (JavascriptExecutor) driver;
					//js.executeScript("arguments[0].innerText="+ caldata.get(0)+';',driver.findElement(By.xpath("//em[normalize-space()='500000']")));
					//js.executeScript("(arguments[0]).getAttribute('value')="+caldata.get(0)+";", driver.findElement(By.xpath("//input[@id='amt_id']")));
					
					Actions slider=new Actions(driver);
					/*slider.clickAndHold(driver.findElement(By.xpath("//input[@id='amt_id']"))).click().perform();
					 * slider.clickAndHold(driver.findElement(By.xpath("//input[@id='rates_id']"))).moveByOffset(Integer. parseInt(caldata.get(1))-5, 0).build().perform();
					 */
					//slider.dragAndDropBy(driver.findElement(By.xpath("//input[@id='amt_id']")),Integer. parseInt(caldata.get(0)), 0).build().perform();
					//slider.clickAndHold(driver.findElement(By.xpath("//input[@id='month_id']"))).moveByOffset(Integer. parseInt(caldata.get(2))-12, 0).build().perform();
					//js.executeScript("arguments[0].innerText="+ caldata.get(1)+';',driver.findElement(By.xpath("//em[normalize-space()='10.90']")));
					//js.executeScript("arguments[0].innerText="+ caldata.get(2)+';',driver.findElement(By.xpath("//em[normalize-space()='18']")));
					WebElement amountTextBoxElement = driver.findElement(By.xpath("//small[contains(text(),'₹')]"));
					// create an instance of the Actions class and perform double click
					
					slider.doubleClick(amountTextBoxElement).build().perform();
					slider.sendKeys(amount).build().perform();
					
					WebElement rateofInterestTextBoxElement = driver.findElement(By.xpath("//em[normalize-space()='8.70']"));

					slider.doubleClick(rateofInterestTextBoxElement).build().perform();
					rateofInterestTextBoxElement.sendKeys(Keys.BACK_SPACE);
					slider.sendKeys(rateOfInterest).build().perform();
					
					WebElement loanTermsTextBoxElement = driver.findElement(By.xpath("//em[normalize-space()='84']"));
					slider.doubleClick(loanTermsTextBoxElement).build().perform();
					slider.sendKeys(loanTermsInMonths).build().perform();
					
					
					//String intAmt_BOB=driver.findElement(By.xpath("//h6[@id='monthly-hl-emi']")).getText();
					String emi_Amt_BOB=driver.findElement(By.xpath("//h6[@id='monthly-hl-emi']")).getText();
					//System.out.print(intAmt_BOB);
					//System.out.print(emi_Amt_BOB);
					
					
					/*
					
					WebElement amrtScheduleBOBtable=driver.findElement(By.xpath("//table[@id='calculateHomeLoan']"));
					List<WebElement> irows =   driver.findElements(By.xpath("//table[@id='calculateHomeLoan']//tr"));
					List<WebElement> icols =   driver.findElements(By.xpath("//table[@id='calculateHomeLoan']//tbody[1]/tr/td"));
					System.out.println( irows.size());
					int iRowsCount = irows.size();     
					
					for (int j=1;j<=iRowsCount;j++) {
						XSSFRow excelRow = sheet1.createRow(j+initialRow);
						//List<WebElement> icols =   driver.findElements(By.xpath("//table[@id='personalLoanamortizationEligibility']//tbody["+i+"]/tr/td"));
						System.out.println( icols.size());
						for (int k=1; k<=icols.size();k++) {           
								    int offsetCl=17;
									int m=j-1;
									WebElement val= driver.findElement(By.xpath("//table[@id='calculateHomeLoan']//thead["+j+"]//tr//th["+k+"]|//table[@id='calculateHomeLoan']//tbody["+m+"]//tr//td["+k+"]"));
								    //WebElement val= driver.findElement(By.xpath("//table[@id='personalLoanamortizationEligibility']//tbody//tr/td"));
									String valbodyText = val.getText();            
									System.out.print(valbodyText);                                
									XSSFCell excelCell = excelRow.createCell(k+offsetCl);                  
									excelCell.setCellType(CellType.STRING);                 
									excelCell.setCellValue(valbodyText);  
					
							}               
					     
						}  
					*/

					ArrayList<String> bobClData=new ArrayList<String>();
					bobClData.add(amount);
					bobClData.add(rateOfInterest);
					bobClData.add(loanTermsInMonths);		
					bobClData.add(emi_Amt_BOB);
					//bobClData.add(intAmt_BOB);
					
					//3,2;3,3;3,4
					int rowno2=initialRow;
					int rowno3=initialRow+1;
					XSSFRow row2 = sheet1.createRow(rowno2);
					XSSFRow row3=sheet1.createRow(rowno3);
					
					row2.createCell(1).setCellValue("Input Amount");
					row2.createCell(2).setCellValue("InterestRate");
					row2.createCell(3).setCellValue("LoanTenureInMonths/Years");					
					
					row2.createCell(4).setCellValue("EMI Amount");
					row2.createCell(5).setCellValue("Interest Payable Amount");
					
					//row2.createCell(11).setCellValue("Amortisation Axis Bank");
					//row2.createCell(18).setCellValue("Amortisation BOB Bank");
					
					row3.createCell(0).setCellValue("For BOB Bank");
					
					 for (int a=0;a<bobClData.size();a++) {	
						 
			            XSSFCell cell = row3.createCell(a+1);
			             cell.setCellType(CellType.STRING);
			             cell.setCellValue((String) bobClData.get(a)); 
			         }	
					

					try (FileOutputStream fos = new FileOutputStream(".//DataFiles//BOBAmrtSchd.xlsx")) {
						
						//fos.flush();  
						wkb.write(fos);     
						//fos.close();
			        }
					
					
					driver.get("https://www.icicibank.com/calculator/car-loan-emi-calculator");		
					Thread.sleep(5000);
					try {
						Boolean pushElement=driver.findElement(By.xpath("//div[@class='exit-popup")).isDisplayed();
						System.out.println(pushElement);
							if(pushElement==true) {
							driver.findElement(By.xpath("//div[@class='exit-popup']")).click();
							}
						}
							catch (Exception e) {
								e.getMessage();
							}
					
					//driver.findElement(By.xpath("//label[normalize-space()='Loan Amount']"));
					//driver.findElement(By.xpath("//label[normalize-space()='Tenure (months)']"));
					//driver.findElement(By.xpath("//label[normalize-space()='Interest Rate (In Percentage)']"));
					//WebElement calSection=driver.findElement(By.xpath("//h2[normalize-space()='Car Loan EMI Calculator']"));
					//((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",calSection);
					
					driver.findElement(By.xpath("//input[@id='loan-amt-input_cl']")).clear();
					driver.findElement(By.xpath("//input[@id='loan-amt-input_cl']")).sendKeys(amount);
					driver.findElement(By.xpath("//input[@id='interest-rate-input_cl']")).clear();
					driver.findElement(By.xpath("//input[@id='interest-rate-input_cl']")).sendKeys(rateOfInterest);
					driver.findElement(By.xpath("//input[@id='tenure-months-input_cl']")).clear();
					driver.findElement(By.xpath("//input[@id='tenure-months-input_cl']")).sendKeys(loanTermsInMonths);
					System.out.print("-------------ICICI Bank PL Data--------------");
					String emi_amt_icici=driver.findElement(By.xpath("//p[@id='emi_amt_cl']")).getText();
					//String interest_payable =driver.findElement(By.xpath("//p[@id='interest_payable_personal']")).getText();
					ArrayList iciciPlData=new ArrayList();
					//iciciPlData.add(emi_amt);
					//iciciPlData.add(interest_payable);
					
					Thread.sleep(7000);
							
					
					ArrayList<String> iciciClData=new ArrayList<String>();
					iciciClData.add(amount);
					iciciClData.add(rateOfInterest);
					iciciClData.add(loanTermsInMonths);		
					iciciClData.add(emi_amt_icici);
					//iciciClData.add(int_paym_axis);
					int rowno4=rowno3+1;
					XSSFRow row4=sheet1.createRow(rowno4);					
					row4.createCell(0).setCellValue("For Icici Bank");
					
					 for (int c=0;c<iciciClData.size();c++) {	
						 
			            XSSFCell cell = row4.createCell(c+1);
			             cell.setCellType(CellType.STRING);
			             cell.setCellValue((String) iciciClData.get(c)); 
			         }	
					
					  
						try (FileOutputStream fos = new FileOutputStream(".//DataFiles//BOBAmrtSchd.xlsx")) {
						
						//fos.flush();  
						wkb.write(fos);     
						//fos.close();
			           }

				     
		 }
	}
}

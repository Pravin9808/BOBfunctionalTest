package testCases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import utilities.ReadConfig;

public class BOBPLcalculator extends BaseClass {
	ReadConfig readconfig=new ReadConfig();
	
	@Test(priority=0)
	public void axisPlCalAmortisation() throws InterruptedException, IOException, InvalidFormatException  {
	
		FileInputStream inpFiles = new FileInputStream(System.getProperty("user.dir")+"//InputFiles//InputFiles.xlsx");
		XSSFWorkbook inpDataWkb = new XSSFWorkbook(inpFiles);
		XSSFSheet inpSheet= inpDataWkb.getSheetAt(0);
		
		int inpRowCount = inpSheet.getPhysicalNumberOfRows()-inpSheet.getFirstRowNum();
		
		FileInputStream amrtFile=new FileInputStream(".//DataFiles//BOBAmrtSchd.xlsx");
		XSSFWorkbook wkb = new XSSFWorkbook(amrtFile);  
		for(int i=0;i<wkb.getNumberOfSheets();i++){
            //System.out.println(wkb.getSheetAt(i).getSheetName());
            if(wkb.getSheetAt(i).getSheetName().equals("PersonalLoanCalData")){
                wkb.removeSheetAt(i);
            }
        } 
		wkb.createSheet("PersonalLoanCalData");
		
		for (int i = 2; i <=inpRowCount+1; i++) {
				 XSSFRow currentrow = inpSheet.getRow(i);
							
				 DataFormatter formatter = new DataFormatter();
					String amount = formatter.formatCellValue(currentrow.getCell(0));			        			        	
		        	String rateOfInterest = formatter.formatCellValue(currentrow.getCell(1));
					String loanTermsInMonths = formatter.formatCellValue(currentrow.getCell(2));
					//String loanTermsInYears = currentrow.getCell(1).getStringCellValue();
					      
					XSSFSheet sheet1 = wkb.getSheet("PersonalLoanCalData");
					int initialRow=sheet1.getLastRowNum()+2;
					
					
					
					driver.get("https://www.bankofbaroda.in/calculators/personal-loan-emi-calculator");
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
					
					WebElement rateofInterestTextBoxElement = driver.findElement(By.xpath("//em[normalize-space()='10.10']"));

					slider.doubleClick(rateofInterestTextBoxElement).build().perform();
					rateofInterestTextBoxElement.sendKeys(Keys.BACK_SPACE);
					slider.sendKeys(rateOfInterest).build().perform();
					
					WebElement loanTermsTextBoxElement = driver.findElement(By.xpath("//em[normalize-space()='18']"));
					slider.doubleClick(loanTermsTextBoxElement).build().perform();
					slider.sendKeys(loanTermsInMonths).build().perform();
					
					
					String intAmt_BOB=driver.findElement(By.xpath("//strong[@id='spn_interest_amt']")).getText();
					String emi_Amt_BOB=driver.findElement(By.xpath("//h6[@id='monthly-hl-emi']")).getText();
					//System.out.print(intAmt_BOB);
					//System.out.print(emi_Amt_BOB);
					
					
					
					
					WebElement amrtScheduleBOBtable=driver.findElement(By.xpath("//table[@id='personalLoanamortizationEligibility']"));
					List<WebElement> irows =   driver.findElements(By.xpath("//table[@id='personalLoanamortizationEligibility']//tr"));
					List<WebElement> icols =   driver.findElements(By.xpath("//table[@id='personalLoanamortizationEligibility']//tbody[1]/tr/td"));
					System.out.println( irows.size());
					int iRowsCount = irows.size();     
					
					for (int j=1;j<=iRowsCount;j++) {
						XSSFRow excelRow = sheet1.createRow(j+initialRow);
						//List<WebElement> icols =   driver.findElements(By.xpath("//table[@id='personalLoanamortizationEligibility']//tbody["+i+"]/tr/td"));
						System.out.println( icols.size());
						for (int k=1; k<=icols.size();k++) {           
								    int offsetCl=16;
									int m=j-1;
									WebElement val= driver.findElement(By.xpath("//table[@id='personalLoanamortizationEligibility']//thead["+j+"]//tr//th["+k+"]|//table[@id='personalLoanamortizationEligibility']//tbody["+m+"]//tr//td["+k+"]"));
								    //WebElement val= driver.findElement(By.xpath("//table[@id='personalLoanamortizationEligibility']//tbody//tr/td"));
									String valbodyText = val.getText();            
									System.out.print(valbodyText);                                
									XSSFCell excelCell = excelRow.createCell(k+offsetCl);                  
									excelCell.setCellType(CellType.STRING);                 
									excelCell.setCellValue(valbodyText);  
					
							}               
					     
						}  
					

					ArrayList<String> bobPlData=new ArrayList<String>();
					bobPlData.add(amount);
					bobPlData.add(rateOfInterest);
					bobPlData.add(loanTermsInMonths);		
					bobPlData.add(emi_Amt_BOB);
					bobPlData.add(intAmt_BOB);
					
					//3,2;3,3;3,4
					int rowno2=initialRow;
					int rowno3=initialRow+1;
					XSSFRow row2 = sheet1.createRow(rowno2);
					XSSFRow row3=sheet1.getRow(rowno3);
					
					row2.createCell(1).setCellValue("Input Amount");
					row2.createCell(2).setCellValue("InterestRate");
					row2.createCell(3).setCellValue("LoanTenureInMonths");
					
					row2.createCell(4).setCellValue("EMI Amount");
					row2.createCell(5).setCellValue("Interest Payable Amount");
					
					row2.createCell(11).setCellValue("Amortisation Axis Bank");
					row2.createCell(18).setCellValue("Amortisation BOB Bank");
					
					row3.createCell(0).setCellValue("For BOB Bank");
					
					 for (int a=0;a<bobPlData.size();a++) {	
						 
			            XSSFCell cell = row3.createCell(a+1);
			             cell.setCellType(CellType.STRING);
			             cell.setCellValue((String) bobPlData.get(a)); 
			         }	
					

					try (FileOutputStream fos = new FileOutputStream(".//DataFiles//BOBAmrtSchd.xlsx")) {
						
						//fos.flush();  
						wkb.write(fos);     
						//fos.close();
			        }
					
					
					driver.get("https://www.axisbank.com/retail/calculators/personal-loan-emi-calculator");
					try {
						Boolean pushElement=driver.findElement(By.xpath("//div[@id='nvpush_cross']")).isDisplayed();
						System.out.println(pushElement);
							if(pushElement==true) {
							driver.findElement(By.xpath("//div[@id='nvpush_cross']")).click();
							}
						}
							catch (Exception e) {
								e.getMessage();
							}
					
					driver.manage().deleteAllCookies();
					Thread.sleep(6000);
					
					driver.findElement(By.xpath("//input[@id='loan_amount']")).sendKeys(Keys.CONTROL,"a");
					driver.findElement(By.xpath("//input[@id='loan_amount']")).sendKeys(Keys.BACK_SPACE);
				
					driver.findElement(By.xpath("//input[@id='loan_amount']")).sendKeys(amount);
					driver.findElement(By.xpath("//input[@id='interest_rate']")).sendKeys(Keys.CONTROL,"a");
					driver.findElement(By.xpath("//input[@id='interest_rate']")).sendKeys(Keys.BACK_SPACE);
					/*driver.findElement(By.xpath("//input[@id='interest_rate']")).sendKeys(Keys.CONTROL,"a");
					driver.findElement(By.xpath("//input[@id='interest_rate']")).sendKeys(Keys.BACK_SPACE);*/
					
					driver.findElement(By.xpath("//input[@id='interest_rate']")).sendKeys(rateOfInterest);
					driver.findElement(By.xpath("//input[@id='tenure']")).sendKeys(Keys.CONTROL,"a");
					driver.findElement(By.xpath("//input[@id='tenure']")).sendKeys(Keys.BACK_SPACE);
					
					driver.findElement(By.xpath("//input[@id='tenure']")).sendKeys(loanTermsInMonths);
					//driver.findElement(By.xpath("//strong[normalize-space()='Amortization']")).click();
					System.out.println("-----Axis Bank PL & Amortisation Data---------");
					driver.findElement(By.xpath("//div[@class='personal-loan']")).click();
					
					Thread.sleep(5000);
					String emi_axis=driver.findElement(By.xpath("//span[@id='lblEMIAmt']")).getText();
					String int_paym_axis=driver.findElement(By.xpath("//span[@id='intrAmt']")).getText();
					String totalAmt_paybleAxis = driver.findElement(By.xpath("//span[@id='totalPayAmt']")).getText();
					//System.out.println("EMI :"+emi_axis);
					//System.out.println("Interest Amount Payable :"+int_paym_axis);
					driver.findElement(By.xpath("//div[@id='trDynamic']")).click();
					
					List<WebElement> irowsAxis =   driver.findElements(By.xpath("//div[@id='trDynamic']//div[@class='tableRow totalCalc']|//div[@id='trDynamic']//div[@class='tableRow tableHead']"));
					//List<WebElement> irowsAxis =   driver.findElements(By.xpath("//div[@id='trDynamic']//div[@class='tableRow totalCalc']"));
					List<WebElement> icolsAxis =   driver.findElements(By.xpath("//div[@id='trDynamic']//div[@class='tableRow totalCalc'][1]//div[@class='tableCell']"));
					System.out.println( irowsAxis.size());
					System.out.println(icolsAxis.size());
					
					Thread.sleep(7000);
					for (int l=1;l<=irowsAxis.size();l++) {
						
						//List<WebElement> icols =   driver.findElements(By.xpath("//table[@id='personalLoanamortizationEligibility']//tbody["+i+"]/tr/td"));
						XSSFRow excelRow = sheet1.getRow(l+initialRow);//initialRow;//getRow+//
						System.out.println( icolsAxis.size());
						for (int p=1; p<=icols.size();p++) { 
						
							int offSetCl=9;
							if(l==1) {		
									WebElement valAxis= driver.findElement(By.xpath("//div[@id='trDynamic']//div[@class='tableRow tableHead']//div[contains(@class,'tableCell ')]["+p+"]"));
									String  valheadAxis = valAxis.getText();            
									System.out.print(valheadAxis);
									
									XSSFCell excelCell = excelRow.createCell(p+offSetCl,CellType.STRING);              
									excelCell.setCellValue(valheadAxis);  
									}
							else {
								int b=l-1;	
								WebElement valbodyAxis= driver.findElement(By.xpath("//div[@id='trDynamic']//div[@class='tableRow totalCalc']["+b+"]//div[@class='tableCell']["+p+"]"));
								String  valbodyAxisText = valbodyAxis.getText();            
								System.out.print(valbodyAxisText);
								XSSFCell excelCell = excelRow.createCell(p+offSetCl,CellType.STRING);                   
								//excelCell.setCellType(CellType.STRING);                 
								excelCell.setCellValue(valbodyAxisText);  
											
								}
							}               
						  
						} 			
					
					ArrayList<String> axisPlData=new ArrayList<String>();
					axisPlData.add(amount);
					axisPlData.add(rateOfInterest);
					axisPlData.add(loanTermsInMonths);		
					axisPlData.add(emi_axis);
					axisPlData.add(int_paym_axis);
					int rowno4=rowno3+1;
					XSSFRow row4=sheet1.getRow(rowno4);					
					row4.createCell(0).setCellValue("For Axis Bank");
					
					 for (int c=0;c<axisPlData.size();c++) {	
						 
			            XSSFCell cell = row4.createCell(c+1);
			             cell.setCellType(CellType.STRING);
			             cell.setCellValue((String) axisPlData.get(c)); 
			         }	
					
					  
						try (FileOutputStream fos = new FileOutputStream(".//DataFiles//BOBAmrtSchd.xlsx")) {
						
						//fos.flush();  
						wkb.write(fos);     
						//fos.close();
			        }

				     
		 }
	}
}

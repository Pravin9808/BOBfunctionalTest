package testCases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting extends TestListenerAdapter {
	public ExtentSparkReporter htmlReport;
	public ExtentReports ext;
	public ExtentTest logger;
	
	public void onStart(ITestContext testContext) {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());	
		
		String repName="Test-Report"+ timeStamp +".html";
		htmlReport = new ExtentSparkReporter (System.getProperty("user.dir")+"\\test-output\\"+repName);
		
			try {
				htmlReport.loadXMLConfig(System.getProperty("user.dir")+ "/extent-config.xml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		ext=new ExtentReports();
		ext.attachReporter(htmlReport);
		ext.setSystemInfo("HostName", "https://www.bankofbaroda.in/");
		ext.setSystemInfo("Environment","QA");
		ext.setSystemInfo("user","Pravin");
		htmlReport.config().setDocumentTitle("BOBLink And SearchFunctionality Test");
		htmlReport.config().setReportName("Functional Test Report");
		htmlReport.config().setTheme(Theme.DARK);
		
	}
	
	public void onTestSuccess(ITestResult tr) {
		logger= ext.createTest(tr.getName());
		logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
	}
	
	public void onTestFailure(ITestResult tr) {
		logger= ext.createTest(tr.getName());
		logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
		
		String screenshotPath=System.getProperty("user.dir")+"\\ScreenShots\\"+tr.getName()+".png";
		File f=new File(screenshotPath);
		if(f.exists()) {
			try {
			logger.fail("Screenshot is below:"+ logger.addScreenCaptureFromPath(screenshotPath));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void onTestSkipped(ITestResult tr) {
		logger= ext.createTest(tr.getName());
		logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREY));
	}
	
	public void onFinish(ITestContext testContext) {
		ext.flush();
	}

}

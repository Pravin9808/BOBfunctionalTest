package BOBLinkStatus.BOBLinkStatus;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ReadConfig;

public class BaseClass {
	
	ReadConfig readconfig=new ReadConfig();
	public String baseURL=readconfig.getApplicationUrl();
	public  ArrayList<String> caldata=readconfig.getPLCalData();
	public static WebDriver driver;
	
	
	public WebDriver initializeDriver() {
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+".//Driver//chromedriver.exe");
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+".//Driver//chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		//ChromeOptions option = new ChromeOptions();
		//option.addArguments("incognito");
		//driver = new ChromeDriver();
		ChromeOptions opt = new ChromeOptions();
		//opt.addArguments("--headless");
		//opt.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver();
		//WebDriverManager.firefoxdriver().setup();
		//FirefoxOptions opt = new FirefoxOptions();							
		//opt.addArguments("--headless");
		//WebDriver driver = new FirefoxDriver(opt);
			
		driver.manage().deleteAllCookies();		
		driver.get(baseURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(400));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));		  
		driver.manage().deleteAllCookies();
		return driver;
	}
		
	@BeforeClass
	public void  launchApplication() throws InterruptedException {		
		driver=initializeDriver();
		//LandingPage landingpage=new LandingPage(driver);
		//landingpage.addToCartTest();
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
		System.out.println("Teardown successful");

	}

}

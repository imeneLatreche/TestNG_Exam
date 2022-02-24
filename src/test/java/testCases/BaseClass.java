package testCases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utilities.ReadConfig;

public class BaseClass {

	ReadConfig readconfig = new ReadConfig();

	public String baseURL = readconfig.getApplicationURL();
	public static WebDriver driver;

	public static Logger logger;

	// @Parameters("browser")
	@BeforeMethod
	public void setup() throws InterruptedException {

		logger = Logger.getLogger("automation-eaxm");
		PropertyConfigurator.configure("log4j.properties");
		System.setProperty("webdriver.chrome.driver", readconfig.getChromepath());
		driver = new ChromeDriver();
		driver.get(baseURL);
		Thread.sleep(2000);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}
}

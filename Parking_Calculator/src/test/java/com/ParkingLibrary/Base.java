package com.ParkingLibrary;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class Base {
	final static Logger logger = Logger.getLogger(Base.class);

	public static WebDriver driver;
	public static GlobalSeleniumLibrary myRepository;
	private static JavaPropertiesManager readProperty;
	private static String browser;
	private static String demoType;

	@BeforeClass
	public void beforeAllTestStart() {

		

		myRepository = new GlobalSeleniumLibrary(driver);

	}

	@AfterClass
	public void afterAllTestCompleted() {

	}

	@BeforeMethod
	public void beforeEachTestStart() {
		driver = myRepository.startChromeBrowser1();
	}

	@AfterMethod
	public void afterEachTestEnd(ITestResult result) {
		try {
			if (ITestResult.FAILURE == result.getStatus()) {
				myRepository.captureScreenshot(result.getName(), "target/screenshots/");
			}
			Thread.sleep(2 * 1000);

			driver.close(); // close the browser
			driver.quit(); // kills/deletes the driver object

		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

}

package com.ParkingLibrary;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

/***
 * This is a common global selenium libraries and can be used for any selenium
 * projects
 * 
 * @author Frank created: 12/22/2018
 */
public class GlobalSeleniumLibrary {
	final static Logger logger = Logger.getLogger(GlobalSeleniumLibrary.class);
	private WebDriver driver;
	private boolean isDemo = false;

	public boolean getDemo() {
		return isDemo;
	}

	public void setDemo(boolean isDemo) {
		this.isDemo = isDemo;
	}

	/***
	 * This is the Constructor
	 * 
	 * @param _driver
	 */
	public GlobalSeleniumLibrary(WebDriver _driver) {
		driver = _driver;
	}

	/**
	 * This is the method enters text string to a edit WebElement in web-site
	 * 
	 * @param by
	 * @param value
	 */
	public void enterTextField(By by, String value) {
		try {
			WebElement textWebElement = driver.findElement(by);
			highlightElement(textWebElement);
			textWebElement.clear();
			textWebElement.sendKeys(value);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void enterTextField(WebElement element, String value) {
		try {
			highlightElement(element);
			element.clear();
			element.sendKeys(value);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This method starts Chrome browser and maximize it
	 * 
	 * @return WebDriver
	 */
	private WebDriver startChromeBrowser() {
		try {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/browser_drivers/chromedriver.exe");
			driver = new ChromeDriver();
			logger.info("Chrome browser is starting...");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;
	}

	/***
	 * This is a fluent wait, waits dynamically for a WebElement and polls the
	 * source html
	 * 
	 * @param by
	 * @return WebElement
	 */
	public WebElement fluentWait(final By by) {
		WebElement targetElem = null;
		try {
			@SuppressWarnings("deprecation")
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			targetElem = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(by);
				}
			});
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		highlightElement(targetElem);
		return targetElem;
	}

	public void selectDropDown(By by, String visibleTextValue) {
		try {
			WebElement dropdownElement = driver.findElement(by);
			highlightElement(dropdownElement);
			Select dropDown = new Select(dropdownElement);
			dropDown.selectByVisibleText(visibleTextValue);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void selectDropDown(By by, int index) {
		try {
			WebElement dropdownElement = driver.findElement(by);
			highlightElement(dropdownElement);
			Select dropDown = new Select(dropdownElement);
			dropDown.selectByIndex(index);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void selectDropDown(String attributeValue, By by) {
		try {
			WebElement dropdownElement = driver.findElement(by);
			highlightElement(dropdownElement);
			Select dropDown = new Select(dropdownElement);
			dropDown.selectByValue(attributeValue);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void clickButton(By by) {
		try {
			WebElement button = driver.findElement(by);
			highlightElement(button);
			button.click();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This method clicks or un-click any check-box and radio buttons
	 * 
	 * @param by
	 * @param isUserWantsToCheckTheBox
	 */
	public void handleCheckBoxRadioBtn(By by, boolean isUserWantsToCheckTheBox) {
		WebElement elem = driver.findElement(by);
		highlightElement(elem);
		boolean checkboxState = elem.isSelected();

		if (checkboxState == true) // by default checkbox is checked
		{
			if (isUserWantsToCheckTheBox == true) { // Scenario1
													// Do nothing
			} else {// Scenario2
				// elem.click();
				clickHiddenElement(elem);
			}
		} else // by default checkbox is Not checked
		{
			if (isUserWantsToCheckTheBox == true) { // Scenario3
				// elem.click();
				clickHiddenElement(elem);
			} else { // Scenario4
						// Do nothing
			}
		}
	}

	public void clickHiddenElement(WebElement elem) {
		try {
			highlightElement(elem);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", elem);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}

	}

	public void handleCheckBoxRadioBtn(WebElement webElement, boolean isUserWantsToCheckTheBox) {

		boolean checkboxState = webElement.isSelected();

		if (checkboxState == true) // by default checkbox is checked
		{
			if (isUserWantsToCheckTheBox == true) { // Scenario1
													// Do nothing
			} else {// Scenario2
				// webElement.click();
				highlightElement(webElement);
				clickHiddenElement(webElement);
			}
		} else // by default checkbox is Not checked
		{
			if (isUserWantsToCheckTheBox == true) { // Scenario3
				// webElement.click();
				highlightElement(webElement);
				clickHiddenElement(webElement);
			} else { // Scenario4
						// Do nothing
			}
		}

	}

	public void clickHiddenElement(By by) {
		try {
			WebElement hiddenElem = driver.findElement(by);
			highlightElement(hiddenElem);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", hiddenElem);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void scrollToWebElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", element);
			highlightElement(element);

		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void scrollByOffsetVertical(String verticalPixel) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(0," + verticalPixel + ")"); // "scroll(0,200)"
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void scrollByOffsetHorizontal(String horizontalPixel) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(" + horizontalPixel + ",0)");
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public String getCurrentTime() {
		String finalTimeStamp = null;
		Date date = new Date();
		String tempTime = new Timestamp(date.getTime()).toString();
		// logger.info("original time stamp is: [" +tempTime+ "]");
		finalTimeStamp = tempTime.replace(":", "_").replace(" ", "_").replace(".", "_").replace("-", "_");
		// logger.info("updated time stamp is: [" +finalTimeStamp+ "]");
		// tempTime.replace(':', '_').replace(' ', '_').replace('.', '_');
		return finalTimeStamp;
	}

	public WebElement waitForElementVisibility(By by) {
		WebElement element = null;
		element = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(by));
		highlightElement(element);
		return element;
	}

	public WebElement highlightElement(By by) {
		WebElement element = null;
		try {
			if (isDemo == true) {
				for (int i = 0; i < 4; i++) {
					element = driver.findElement(by);
					WrapsDriver wrappedElement = (WrapsDriver) element;
					JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
					customWait(0.4);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
							"color: red; border: 2px solid yellow;");
					customWait(0.4);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return element;
	}

	public WebElement highlightElement(WebElement webElement) {
		WebElement element = webElement;
		try {
			if (isDemo == true) {
				for (int i = 0; i < 4; i++) {
					// element = driver.findElement(by);
					WrapsDriver wrappedElement = (WrapsDriver) element;
					JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
					customWait(0.4);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
							"color: red; border: 2px solid yellow;");
					customWait(0.4);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return element;

	}

	public void customWait(double inSeconds) {
		try {
			Thread.sleep((long) (inSeconds * 1000));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}

	}

	public void clickButton(WebElement webElement) {
		try {
			// WebElement button = driver.findElement(by);
			highlightElement(webElement);
			webElement.click();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public String captureScreenshot(String screenshotFileName, String filePath) {
		String screenshotPath = null;
		String timestamp = getCurrentTime();
		try {
			if (!filePath.isEmpty()) {
				checkDirectory(filePath);
				screenshotPath = filePath + screenshotFileName + timestamp + ".png";
			} else {
				checkDirectory("target/screenshots/");
				screenshotPath = "target/screenshots/" + screenshotFileName + timestamp + ".png";
			}

			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(srcFile, new File(screenshotPath));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		logger.info("Screenshot Captured: " + screenshotPath);
		return screenshotPath;
	}

	private String checkDirectory(String inputPath) {
		File file = new File(inputPath);
		String abPath = file.getAbsolutePath();
		File file2 = new File(abPath);
		if (!file2.exists()) {
			if (file2.mkdirs()) {
				logger.info("folders created...");
			} else {
				logger.info("folders Not created...");
			}

		}
		return abPath;
	}

	public WebDriver startLocalBrowser(String browser) {
		if (browser.contains("Chrome")) {
			driver = startChromeBrowser();
		} else if (browser.contains("IE")) {
			// start IE browser
			driver = startIEBrowser();
		} else if (browser.contains("Firefox")) {
			// start Firefox browser
			logger.info("Starting Firefox browser, but not implemented yet !!! Sorry.");
		} else {
			// other browsers we don't support with this version of library
			logger.info("Ops, Sorry, we don't support the browser: [" + browser + "], please contact Automation Team.");
		}
		return driver;
	}

	private WebDriver startIEBrowser() {
		try {
			System.setProperty("webdriver.ie.driver", "src/test/resources/browser_drivers/IEDriverServer.exe");
			// Ignoring Security Protected Mode using below code
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("ie.ensureCleanSession", true);
			driver = new InternetExplorerDriver(capabilities);
			/// this is setting the zoom to 100% by code
			WebElement body = driver.findElement(By.tagName("body"));
			// body.sendKeys(Keys);

			logger.info("IE browser is starting...");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;
	}

	/*
	 * public static void main(String[] args) { GlobalSeleniumLibrary lib = new
	 * GlobalSeleniumLibrary(null); lib.getCurrentTime(); }
	 */

	public WebDriver startChromeBrowser1() {
		try {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\aboba\\eclipse-workspace\\Week6_United_Automation\\src\\test\\resources\\Browers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	public WebDriver switchToWindow(int index) {

		try {
			Set<String> allBrowsers = driver.getWindowHandles();
			Iterator<String> iterator = allBrowsers.iterator();
			List<String> windowHandles = new ArrayList<>();
			while (iterator.hasNext()) {
				String window = iterator.next();
				windowHandles.add(window);
			}
			// switch to index N
			driver.switchTo().window(windowHandles.get(index));
			// highlightElement(By.tagName("body"));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;

	}
	
	public void selectDropDownVisibleText(By by, String visibleTextValue) {

		try {
			WebElement dropDownElement = driver.findElement(by);
			Select DropDown = new Select(dropDownElement);
			DropDown.selectByVisibleText(visibleTextValue);

		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Error", e);
			assertTrue(false);
		}
	}

}

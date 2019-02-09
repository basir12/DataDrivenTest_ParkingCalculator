package com.Parking_pages;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ParkingLibrary.Base;
import com.ParkingLibrary.ExcelManager;

public class Parking_Calculator extends Base {

	@DataProvider(name = "parkingcalculator")
	private Object[][] parkingcalculator() {
		                                            
		ExcelManager excelReader = new ExcelManager("src\\test\\resources\\Data\\ParkingCalculator_DDT.xls");
		
		Object[][] data;
		data = excelReader.getExcelData("parking");
		return data;

	}

	@BeforeMethod
	public void parking_Calculator() {

		driver.get("http://adam.goucher.ca/parkcalc/");
		String webtitle = driver.getTitle();
		System.out.println("This is Web Title:\t" + webtitle);
		assertEquals(webtitle, "Parking Calculator");
	}

	@ Test (dataProvider = "parkingcalculator")
	public void dataDrivenTest(String lot, String eTime, String eDate, String xTime, String xDate, String expacted ) {

		try {

			myRepository.selectDropDownVisibleText(By.id("Lot"),lot);
			myRepository.enterTextField(By.id("EntryTime"), eTime);
			myRepository.enterTextField(By.id("EntryDate"), eDate);
			myRepository.enterTextField(By.id("ExitTime"), xTime);
			myRepository.clickButton(By.id("ExitTime"));
			myRepository.enterTextField(By.id("ExitDate"), xDate);
			myRepository.clickButton(By.cssSelector("input[name=Submit]"));
			assertEquals(actual, expected);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

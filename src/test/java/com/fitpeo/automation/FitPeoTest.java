package com.fitpeo.automation;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FitPeoTest {

	@Test
	public void fitPeoScenarioTest() throws Exception {

		/*
		 * Navigate to the FitPeo Homepage: Open the web browser and navigate to FitPeo
		 * Homepage.
		 */

		WebDriver driver = new ChromeDriver();
		Actions action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
		driver.manage().window().maximize();
		driver.get("https://fitpeo.com/home");

		/*
		 * Navigate to the Revenue Calculator Page: From the homepage, navigate to the
		 * Revenue Calculator Page.
		 */

		driver.findElement(By.xpath("//div[text()='Revenue Calculator']")).click();

		/*
		 * Scroll Down to the Slider section: Scroll down the page until the revenue
		 * calculator slider is visible.
		 */

		WebElement ele = driver.findElement(By.xpath("//p[text()='CPT-99091']"));
		action.scrollToElement(ele).perform();

		/*
		 * Adjust the Slider: Adjust the slider to set its value to 820. we’ve
		 * highlighted the slider in red color, Once the slider is moved the bottom text
		 * field value should be updated to 820
		 */

		WebElement scrollBar = driver.findElement(By.xpath("//input[@aria-valuemax='2000']"));
		action.moveToElement(scrollBar, 94, 0).click().perform();

		WebElement text = driver.findElement(By.xpath("//input[@type='number']"));
		while (true) {
			String value = text.getAttribute("value");
			int value1 = Integer.parseInt(value);
			if (value1 > 820) {
				action.sendKeys(Keys.ARROW_LEFT).perform();
			} else if (value1 < 820) {
				action.sendKeys(Keys.ARROW_RIGHT).perform();
			}
			if (text.getAttribute("value").equals("820")) {
				break;
			}
		}
		text.click();

		/*
		 * Update the Text Field: Click on the text field associated with the slider.
		 * Enter the value 560 in the text field. Now the slider also should change
		 * accordingly
		 */

		action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).perform();
		text.sendKeys("560");

		/*
		 * Validate Slider Value: Ensure that when the value 560 is entered in the text
		 * field, the slider's position is updated to reflect the value 560.
		 */

		WebElement cursor = driver.findElement(By.xpath("//input[@type='range']"));
		String Actualvalue = cursor.getAttribute("value");
		int value = Integer.parseInt(Actualvalue);
		Assert.assertEquals(value, 560);
		System.out.println("Slider Value is updated to 560");

		WebElement textfield = driver.findElement(By.xpath("//input[@type='number']"));
		textfield.click();
		action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).build().perform();
		textfield.sendKeys("820");

		/*
		 * Select CPT Codes: Scroll down further and select the checkboxes for
		 * CPT-99091, CPT-99453, CPT-99454, and CPT-99474.
		 */

		driver.findElement(By.xpath(
				"//p[text()='CPT-99091']/ancestor::div[contains(@class,'MuiBox-root css-1e')]//input[@type='checkbox']"))
				.click();
		driver.findElement(By.xpath(
				"//p[text()='CPT-99453']/ancestor::div[contains(@class,'MuiBox-root css-1e')]//input[@type='checkbox']"))
				.click();
		driver.findElement(By.xpath(
				"//p[text()='CPT-99454']/ancestor::div[contains(@class,'MuiBox-root css-1e')]//input[@type='checkbox']"))
				.click();
		driver.findElement(By.xpath(
				"//p[text()='CPT-99474']/ancestor::div[contains(@class,'MuiBox-root css-1e')]//input[@type='checkbox']"))
				.click();

		/*
		 * Validate Total Recurring Reimbursement: Verify that the header displaying
		 * Total Recurring Reimbursement for all Patients Per Month: shows
		 * the value $110700.
		 */
		String actualresult = driver.findElement(By.xpath(
				"//div[contains(@class,'MuiBox-root css-m')]//p[contains(@class,'MuiTypography-root MuiTypography-body1')]"))
				.getText();
		Assert.assertEquals(actualresult, "$110700");
		System.out.println("Reimburshment value is verified");
	}
}

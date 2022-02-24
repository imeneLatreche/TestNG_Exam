package testCases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.WelcomePage;

public class TestNGExam extends BaseClass {

	// Question 1: Validate a user is able to add a category and once the category
	// is added it should display.
	@Test
	public void question1() throws InterruptedException {
		logger.info("Test 1 started");
		WelcomePage wpage = new WelcomePage(driver);
		
		// Get the number of categories existing already
		int numberOfInitialcategories = getNumberOfCategories();

		// Creating a unique category name
		String finalCategoryName = createCategoryUnicity("Abdel");
		logger.info("Category name created");
		
		// adding a category with a unique name
		wpage.setCategoryDataField(finalCategoryName);
		wpage.setColor("None");
		wpage.clickAddCategoryButton();
		Thread.sleep(2000);
		logger.info("Category added");

		// Checking if the number of categories increased
		Assert.assertEquals(getNumberOfCategories(), numberOfInitialcategories + 1);
		logger.info("number of categories increased");

		// Checking if last category added name is present
		Assert.assertEquals(getLastCategoryAdded(), finalCategoryName);
		logger.info("category name is added");
	}

	// Question 2: Validate a user is not able to add a duplicated category. If it
	// does then a message will display
	@Test
	public void question2() {
		logger.info("Test 2 started");
		WelcomePage wpage = new WelcomePage(driver);
		
		// Getting the name of one the existing categories
		String lastCategoryAdded = getLastCategoryAdded();
		
		// Trying to add a category that exist
		wpage.setCategoryDataField(lastCategoryAdded);
		wpage.clickAddCategoryButton();
		logger.info("Trying to add an existing category");
		
		String txt = driver.findElement(By.xpath("/html/body")).getText();
		Assert.assertTrue(txt.contains("The category you want to add already exists: " + lastCategoryAdded + "."));
		logger.info("Warning message displayed");
	}

	// Validate the month drop down has all the months (jan, feb, mar ...) in the
	// Due Date dropdown section.
	@Test
	public void question3() {
		logger.info("Test 3 started");
		List<String> monthsList = Arrays.asList(new String[] { "None", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" });

		for (int i = 0; i < 13; i++) {
			Assert.assertEquals(getDropDownMonthsElements().get(i), monthsList.get(i));
		}
		logger.info("all months are present in the dropdown menu");
	}

	/* -------------------------helper methods------------------------------- */
	public String createCategoryUnicity(String categoryName) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(" mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String finalCategoryName = categoryName + dtf.format(now);
		return finalCategoryName;
	}

	public int getNumberOfCategories() {
		List<WebElement> allItems = driver.findElements(By.xpath("/html/body/div[3]/a"));
		int numberOfItems = allItems.size();
		return numberOfItems;
	}

	public List<WebElement> getAllCategories() {
		List<WebElement> allItems = driver.findElements(By.xpath("/html/body/div[3]/a"));
		return allItems;

	}

	public String getLastCategoryAdded() {
		String lastElementAdded = getAllCategories().get(getNumberOfCategories() - 1).getText();
		return lastElementAdded;
	}

	public List<String> getDropDownMonthsElements() {
		List<WebElement> allItems = driver.findElements(By.xpath("//select[@name='due_month']/option"));
		List<String> monthsPresent = new ArrayList<String>();
		for (int i = 0; i < allItems.size(); i++) {
			monthsPresent.add(i,
					driver.findElement(By.xpath("//select[@name='due_month']/option[" + (i + 1) + "]")).getText());
		}
		return monthsPresent;
	}
}

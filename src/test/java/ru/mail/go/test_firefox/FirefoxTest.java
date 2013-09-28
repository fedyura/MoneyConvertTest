package ru.mail.go.test_firefox;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ru.mail.go.base.GoMailHomePage;
import ru.mail.go.base.GoMailSearchResultsPage;

public class FirefoxTest {
  
	private FirefoxDriver driver;
	private GoMailHomePage homePage;
	
	@Test
	public void CheckConverterElements() {
		
		//GoMailHomePage homePage = GoMailHomePage.navigateTo(driver);
		GoMailSearchResultsPage resultsPage = homePage.searchFor("100 рублей в гривнах");
				
		Assert.assertEquals(resultsPage.getConverterTitle(), "Конвертер валют");
		Assert.assertNotEquals(resultsPage.getMoneyInput(), "");
		Assert.assertEquals(resultsPage.getInputCurrency(), "российских рублей");
		Assert.assertNotEquals(resultsPage.getMoneyResult(), "");
		Assert.assertEquals(resultsPage.getResultCurrency(), "украинской гривны");
	}
    
	@Test
	public void CheckShowInputData() {
		
		GoMailSearchResultsPage resultsPage = homePage.searchFor("100 рублей в гривнах");
		
		Assert.assertEquals(resultsPage.getMoneyInput(), "100");
	}
	
	@Test
	public void CheckDifferentCurrency() {
		
		GoMailSearchResultsPage resultsPage = homePage.searchFor("100 евро доллар");
		
		Assert.assertEquals(resultsPage.getMoneyInput(), "100");
		Assert.assertEquals(resultsPage.getInputCurrency(), "евро");
		Assert.assertNotEquals(resultsPage.getMoneyResult(), "");
		Assert.assertEquals(resultsPage.getResultCurrency(), "доллара сша");
		
	}
	
	@BeforeTest
    public void setupSelenium() {
		
		driver = new FirefoxDriver();
		homePage = GoMailHomePage.navigateTo(driver);
    }

    @AfterTest
    public void closeSelenium() {
    	driver.close();
		driver.quit();
    }

}

package ru.mail.go.test_browsers;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.mail.go.base.GoMailHomePage;
import ru.mail.go.base.GoMailSearchResultsPage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GeneralTest {
	
	private WebDriver driver;
	private GoMailHomePage homePage;
	
	@Test
	public void CheckConverterElements() {
		
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
	
	@Test
	public void CheckSameCurrency() {
		
		GoMailSearchResultsPage resultsPage = homePage.searchFor("100 гривен гривны");
		
		Assert.assertEquals(resultsPage.getMoneyInput(), "100");
		Assert.assertEquals(resultsPage.getInputCurrency(), "украинских гривен");
		Assert.assertEquals(resultsPage.getMoneyResult(), "100");
		Assert.assertEquals(resultsPage.getResultCurrency(), "украинской гривны");
	}
	
	@Test
    public void CheckDecimalFractionValue() {
		
		GoMailSearchResultsPage resultsPage = homePage.searchFor("100.5 рублей евро");
		
		Assert.assertEquals(resultsPage.getMoneyInput(), "100.5");
		Assert.assertEquals(resultsPage.getInputCurrency(), "российского рубля");
		Assert.assertNotEquals(resultsPage.getMoneyResult(), "");
		Assert.assertEquals(resultsPage.getResultCurrency(), "евро");
	}
    
	@Test
    public void CheckManyDecimalPlacesValue() {
		
		GoMailSearchResultsPage resultsPage = homePage.searchFor("100.5 рублей евро");
		String moneyResult = new String(resultsPage.getMoneyResult()); 
		
		homePage = GoMailHomePage.navigateTo(driver);
		resultsPage = homePage.searchFor("100.53456 рублей евро");
		
		Assert.assertEquals(resultsPage.getMoneyInput(), "100.5");
		Assert.assertEquals(resultsPage.getInputCurrency(), "российского рубля");
		Assert.assertNotEquals(resultsPage.getMoneyResult(), "");
		Assert.assertEquals(resultsPage.getResultCurrency(), "евро");
		Assert.assertNotEquals(resultsPage.getMoneyResult(), moneyResult);
	}
    
	@Test
    public void CheckZeroInputData() {
		
		GoMailSearchResultsPage resultsPage = homePage.searchFor("0 рублей евро");
				
		Assert.assertEquals(resultsPage.getMoneyInput(), "0");
		Assert.assertEquals(resultsPage.getInputCurrency(), "российских рублей");
		Assert.assertEquals(resultsPage.getMoneyResult(), "0");
		Assert.assertEquals(resultsPage.getResultCurrency(), "евро");		
	}
    
	@Test
    public void CheckLargeInputData() {
		
		GoMailSearchResultsPage resultsPage = homePage.searchFor("400000000000000000000000000000 рублей евро ");
				
		Assert.assertEquals(resultsPage.getMoneyInput(), "400000000000000000000000000000");
		Assert.assertEquals(resultsPage.getInputCurrency(), "российского рубля");
		Assert.assertNotEquals(resultsPage.getMoneyResult(), "0");
		Assert.assertNotEquals(resultsPage.getMoneyResult(), "");
		Assert.assertEquals(resultsPage.getResultCurrency(), "евро");		
	}
    
	@Test
    public void CheckChangeCurrency() {
    	
    	GoMailSearchResultsPage resultsPage = homePage.searchFor("4000 рублей евро ");
    	String moneyInput  = new String(resultsPage.getMoneyInput());
    	String moneyResult = new String(resultsPage.getMoneyResult());
    	
    	resultsPage = homePage.SwapCurrency();
    	
    	Assert.assertEquals(moneyInput, resultsPage.getMoneyInput());
    	Assert.assertNotEquals(moneyResult, resultsPage.getMoneyResult());
    	Assert.assertNotEquals(resultsPage.getMoneyResult(), "");
    	Assert.assertNotEquals(resultsPage.getMoneyResult(), "0");
    }
    
    /*@BeforeTest
    public void setupSelenium() {
		
		driver = new FirefoxDriver();
    }*/
    
    @BeforeMethod
    @Parameters({"browser", "hub", "url"})
    public void setUp(String browser, String hub, String url) throws MalformedURLException {
        if (browser.toLowerCase().equals("chrome"))
            this.driver = new RemoteWebDriver(new URL(hub), DesiredCapabilities.chrome());
        else if (browser.toLowerCase().equals("firefox"))
            this.driver = new RemoteWebDriver(new URL(hub), DesiredCapabilities.firefox());
        else
            throw new NotImplementedException(); 
        this.driver.manage().window().maximize();
        this.driver.get(url);
    }

    @AfterMethod
    public void closeSelenium() {
    	
    	driver.close();
		driver.quit();
    }
    
    /*
    @BeforeMethod
    public void OpenPage() {
    	
    	homePage = GoMailHomePage.navigateTo(driver);
    }*/
}

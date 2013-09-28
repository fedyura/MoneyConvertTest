package ru.mail.go.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoMailSearchResultsPage {

	private WebDriver driver;

	public GoMailSearchResultsPage(WebDriver driver) {
		this.driver = driver;
	}

	public String getMoneyInput() {
		
		WebElement query = driver.findElement(By.id("ival"));
		return query.getAttribute("value");
	}
	
    public String getInputCurrency() {
		
		WebElement query = driver.findElement(By.id("icode"));
		return query.getText();  
	}
	
    public String getMoneyResult() {
		
		WebElement query = driver.findElement(By.id("oval"));
		return query.getText();   
	}
    
    public String getResultCurrency() {
		
		WebElement query = driver.findElement(By.id("ocode"));
		return query.getText();  
	}
    
    public String getConverterTitle() {
		
	    WebElement query = driver.findElement(By.className("smack-converter__title"));
		return query.getText();   
	}

}

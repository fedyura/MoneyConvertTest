package ru.mail.go.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class GoMailHomePage {

	/**
	 * @param args
	 */
	private WebDriver driver;

	/*@FindBy(name="sd")
	private WebElement keywordsField;

	@FindBy(css="#navGoButton input")
	private WebElement goButton;*/

	public GoMailHomePage(WebDriver driver) {
		this.driver = driver;
	}

	public GoMailSearchResultsPage searchFor(String searchTerm) {
		WebElement query = driver.findElement(By.name("q"));
        query.sendKeys(searchTerm);
        WebElement button = driver.findElement(By.className("go-form__submit"));
        button.click();
		return PageFactory.initElements(driver, GoMailSearchResultsPage.class);
	}
	
	public GoMailSearchResultsPage SwapCurrency() {
		
		WebElement button = driver.findElement(By.id("change_conv"));
		button.click();
		return PageFactory.initElements(driver, GoMailSearchResultsPage.class);
	}

	public static GoMailHomePage navigateTo(WebDriver driver) {
		driver.get("http://www.go.mail.ru/");
		return PageFactory.initElements(driver, GoMailHomePage.class);
	}

}

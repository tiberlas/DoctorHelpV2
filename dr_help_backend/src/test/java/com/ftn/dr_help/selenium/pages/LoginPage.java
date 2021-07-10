package com.ftn.dr_help.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

	private WebDriver driver;

	public LoginPage() {
	}
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement getLoginEmailForm() {
		return driver.findElement(By.id("tb_email"));
	}
	
	public WebElement getLoginPasswordForm() {
		return driver.findElement(By.id("tb_password"));
	}
	
	public WebElement getLoginSubmit() {
		return driver.findElement(By.id("tb_login"));
	}
}

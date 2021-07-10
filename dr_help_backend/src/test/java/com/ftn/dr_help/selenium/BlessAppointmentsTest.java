package com.ftn.dr_help.selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.ftn.dr_help.selenium.pages.LoginPage;


public class BlessAppointmentsTest {

	private WebDriver browser;
	
	private LoginPage loginPage;
	
	@Before
    public void setUp() {
        //instantiate chrome browser
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        browser = new ChromeDriver();
        
        //maximize window
        browser.manage().window().maximize();

        //navigate
        browser.navigate().to("http://localhost:3000/login");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
    }
	
	@Test
    public void login() {
		WebElement emailForm = loginPage.getLoginEmailForm();
		emailForm.click();
		emailForm.sendKeys("admin1@maildrop.cc");
		
		WebElement passwordForm = loginPage.getLoginPasswordForm();
		passwordForm.click();
		passwordForm.sendKeys("1234");
		
		WebElement submit = loginPage.getLoginSubmit();
		submit.click();
	}
}

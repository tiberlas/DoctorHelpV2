package com.ftn.dr_help.selenium;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.TestPropertySource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ClinicAdminAproveAppointmentTestTest {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;
	
	@Before
	public void setUp() {
	    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
	 	js = (JavascriptExecutor) driver;
	 	vars = new HashMap<String, Object>();
	 	
	 	driver.manage().window().maximize();
	}
	
	@After
		public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void clinicAdminAproveAppointmentTest() {		
		
		try {
		driver.get("http://localhost:3000/login");
	    driver.findElement(By.id("tb_email")).click();
	    driver.findElement(By.id("tb_email")).sendKeys("admin1@maildrop.cc");
	    driver.findElement(By.id("tb_password")).click();
	    driver.findElement(By.id("tb_password")).sendKeys("1234");
	    driver.findElement(By.id("tb_login")).click();
	    
	    (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Requests")));
	    driver.findElement(By.linkText("Requests")).click();   
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("appointment_id")));
	    driver.findElement(By.id("appointment_id")).click();
	    
//	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("clinic_name")));
//	    driver.findElement(By.id("clinic_name")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("table_row_14")));
	    driver.findElement(By.id("table_row_14")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("search_rooms")));
	    driver.findElement(By.id("search_rooms")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("number")));
	    driver.findElement(By.id("number")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("number")));
	    driver.findElement(By.id("number")).sendKeys("30");
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("submit_select")));
	    driver.findElement(By.id("submit_select")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("all_rooms")));
	    driver.findElement(By.id("all_rooms")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("room_selected2")));
	    driver.findElement(By.id("room_selected2")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("bless_appointment")));
	    driver.findElement(By.id("bless_appointment")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name("hour12")));
	    driver.findElement(By.name("hour12")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name("hour12")));
	    driver.findElement(By.name("hour12")).sendKeys("4");
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name("amPm")));
	    driver.findElement(By.name("amPm")).click();
	    {
	      WebElement dropdown = driver.findElement(By.name("amPm"));
	      dropdown.findElement(By.xpath("//option[. = 'PM']")).click();
	    }
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name("amPm")));
	    driver.findElement(By.name("amPm")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("bless_appointment")));
	    driver.findElement(By.id("bless_appointment")).click();
	    
	    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("close_dialog")));
	    driver.findElement(By.id("close_dialog")).click();
		
		} catch(Exception e) {
			
		}
	    
	}
}

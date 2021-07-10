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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;


public class RequestManualTest {
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
  public void doYourMagic() {
	  
    driver.get("http://localhost:3000/login");
    driver.findElement(By.id("tb_email")).click();
    driver.findElement(By.id("tb_email")).sendKeys("happymeal@maildrop.cc");
    driver.findElement(By.id("tb_password")).sendKeys("whoppa42");
    driver.findElement(By.id("tb_login")).click();
    
    {
      WebElement element = driver.findElement(By.id("tb_login"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    
    (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated(By.id("clinics_nav")));
    driver.findElement(By.id("clinics_nav")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("filter_clinics_button")));
    driver.findElement(By.id("filter_clinics_button")).click();
    {
      WebElement element = driver.findElement(By.id("filter_clinics_button"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.className("select-procedure-type")));
    driver.findElement(By.className("select-procedure-type")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("react-select-2-option-1")));
    driver.findElement(By.id("react-select-2-option-1")).click();
    
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("picker")));
    ((JavascriptExecutor)driver).executeScript ("document.getElementById('picker').removeAttribute('readonly',0);");

    WebElement searchDate = driver.findElement(By.id("picker"));
    searchDate.clear();
    searchDate.sendKeys("04");
    searchDate.sendKeys("29");
    searchDate.sendKeys("2020");
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("close_clinic_modal_button")));
    driver.findElement(By.id("close_clinic_modal_button")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Reserve")));
    driver.findElement(By.linkText("Reserve")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.className("react-select-terms")));
    driver.findElement(By.className("react-select-terms")).click();

    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("react-select-6-option-1")));
    driver.findElement(By.id("react-select-6-option-1")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("confirm_request_button")));
    driver.findElement(By.id("confirm_request_button")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("confirm_reserving_button")));
    driver.findElement(By.id("confirm_reserving_button")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("close_success_button")));
    driver.findElement(By.id("close_success_button")).click();
  }
}
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


public class ReservePredefinedTest {
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
    driver.findElement(By.id("tb_password")).click();
    driver.findElement(By.id("tb_password")).sendKeys("whoppa42");
    driver.findElement(By.id("tb_login")).click();
    
    (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Appointments")));
    
    driver.findElement(By.linkText("Appointments")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("book_predefined_appointment")));
    driver.findElement(By.id("book_predefined_appointment")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("button_reserve")));
    driver.findElement(By.id("button_reserve")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("modal_button_reserve")));
    driver.findElement(By.id("modal_button_reserve")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("button_filter")));
    driver.findElement(By.id("button_filter")).click();
    {
      WebElement element = driver.findElement(By.id("button_filter"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    (new WebDriverWait(driver, 15)).until(ExpectedConditions.visibilityOfElementLocated(By.className("select_type")));
    driver.findElement(By.className("select_type")).click();
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("react-select-5-option-1")));
    driver.findElement(By.id("react-select-5-option-1")).click();
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("filter_close")));
    driver.findElement(By.id("filter_close")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("button_reserve")));
    driver.findElement(By.id("button_reserve")).click();
    
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("modal_button_reserve")));
    driver.findElement(By.id("modal_button_reserve")).click();
  }
}

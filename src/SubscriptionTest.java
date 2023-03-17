import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SubscriptionTest {

    private ChromeDriver driver;
    
    @BeforeClass
    public void setUp() {
        // Set up the WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\selenium-4.3.0\\chromedriver.exe");
        //System.setProperty("webdriver.http.factory", "jdk-http-client");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }
    
    @Test(priority = 1)
    public void testInvalidEmail() {
    	
        // Open the company website
        driver.get("https://aidi.io");
        driver.manage().window().maximize();
		
		//Initializing explicit wait with wait out of 10 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		//Wait for the Book Demo button is clickable	
		wait.until(ExpectedConditions.elementToBeClickable(By.id("Nav-Book-Demo")));
        // Click on the "Book Demo" button
        WebElement bookDemoButton = driver.findElement(By.id("Nav-Book-Demo"));
        bookDemoButton.click();
        
        WebElement frame = driver.findElement(By.id("hs-form-iframe-0"));
        driver.switchTo().frame(frame);

        // Enter an invalid email address in the email input field
        WebElement emailInput = driver.findElement(By.name("email"));
        driver.executeScript("arguments[0].scrollIntoView();", emailInput);
        emailInput.sendKeys("invalid_email");
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='email']/following::ul")));
        WebElement errorMessage = driver.findElement(By.xpath("//*[@name='email']/following::ul"));
        Assert.assertEquals(errorMessage.getText(),"Email must be formatted correctly.");
    }
    
    @Test(priority = 2)
    public void testValidEmail() {
    	
    	// Open the company website
        driver.get("https://aidi.io");
        driver.manage().window().maximize();
		
		//Initializing explicit wait with wait out of 10 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		//Wait for the Book Demo button is clickable	
		wait.until(ExpectedConditions.elementToBeClickable(By.id("Nav-Book-Demo")));
        // Click on the "Book Demo" button
        WebElement bookDemoButton = driver.findElement(By.id("Nav-Book-Demo"));
        bookDemoButton.click();
        
        WebElement frame = driver.findElement(By.id("hs-form-iframe-0"));
        driver.switchTo().frame(frame);

        // Enter a valid email address in the email input field
        WebElement emailInput = driver.findElement(By.name("email"));
        driver.executeScript("arguments[0].scrollIntoView();", emailInput);
        emailInput.sendKeys("abcdef2@gmail.com");
        
        //Select a preferred language
        Select languageInput = new Select(driver.findElement(By.name("hs_language")));
        languageInput.selectByVisibleText("English");
        
        //Submit the form
        WebElement submitBtn = driver.findElement(By.xpath("//*[@type='submit']"));
        submitBtn.click();
        
        //Verify Confirmation Message
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Thanks for subscribing')]")));
        WebElement confirmationMsg = driver.findElement(By.xpath("//*[contains(text(),'Thanks for subscribing')]"));
        Assert.assertEquals(confirmationMsg.getText(),"Thanks for subscribing! Merci de vous être abonné !");
    }
    
    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }

}

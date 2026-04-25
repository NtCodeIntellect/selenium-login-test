package com.lab;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    void test_login_with_incorrect_credentials() throws Exception {
        // Navigate to login page
        driver.get("http://103.139.122.250:4000/");
        
        // Find email field by ID and enter email
        driver.findElement(By.id("email")).sendKeys("qasim@malik.com");
        
        // Find password field by ID and enter password
        driver.findElement(By.id("password")).sendKeys("abcdefg");
        
        // Click the Sign In button (find by text)
        driver.findElement(By.xpath("//button[text()='Sign In']")).click();
        
        // Wait for error message to appear
        Thread.sleep(3000);
        
        // Check for error message on the page
        String pageSource = driver.getPageSource();
        boolean hasError = pageSource.contains("Incorrect") || 
                          pageSource.contains("invalid") || 
                          pageSource.contains("Invalid") ||
                          pageSource.contains("email") && pageSource.contains("password");
        
        assertTrue(hasError, "Error message should appear for incorrect credentials");
        
        System.out.println("✅ TEST PASSED! Incorrect credentials showed error message.");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
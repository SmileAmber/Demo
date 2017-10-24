package org.gradle.demo;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class HelloServletFunctionalTest {
    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver","$WORKSPACE/webdemo/chromedriver");
       // ChromeDriverManager.getInstance().setup();
        
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }

    @Test
    public void sayHello() throws Exception {
        driver.get("http://10.209.22.168:8081/webdemo/");
        Thread.sleep(2000);

        driver.findElement(By.id("say-hello-text-input")).sendKeys("Dolly");
        Thread.sleep(2000);
        driver.findElement(By.id("say-hello-button")).click();
        Thread.sleep(2000);

        assertEquals("Hello Page", driver.getTitle());
        assertEquals("Hello, Dolly!", driver.findElement(By.tagName("h2")).getText());
        Thread.sleep(2000);
    }
}

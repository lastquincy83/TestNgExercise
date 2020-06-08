package com.training.demo;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class GoogleDemo {
    @Test
    public void runTest(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.navigate().to("http://facebook.com");
        // navigate methods
        driver.navigate().back();
        driver.navigate().forward();
    }
}

package com.training.demo;


import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class FacebookDemo {

    WebDriver driver;

    @BeforeSuite
    public void setUp() {

    }

//    @Test()
    public void test1 () {
        driver = new WebDriverSetup().getChromeDriver();
        new NewToursDemoAuto().runTest(driver);
        driver.close();
    }

//    @Test
    public void test2 () {
        driver = new WebDriverSetup().getChromeDriver();
        new GoogleDemo().runTest(driver);
        driver.close();
    }

//    @Test
//    public void test3 () {
//        driver = new WebDriverSetup().getChromeDriver();
//        new AdactinHotel().runTest(driver);
//        driver.close();
//    }

    @SneakyThrows
    @AfterSuite
    public void tearDown() {
        Thread.sleep(5000);
        driver.quit();
    }
}

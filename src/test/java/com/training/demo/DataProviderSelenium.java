package com.training.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class DataProviderSelenium {

    WebDriver driver;
    @Test(dataProvider="LoginDataProvider")//reference dataprovider
    public void login(String name,String password) {    //must match @DataProvider

        driver = new WebDriverSetup().getChromeDriver();

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://adactin.com/HotelApp/index.php");

        WebElement uname = driver.findElement(By.id("username"));
        uname.sendKeys(name);

        WebElement paword = driver.findElement(By.id("password"));
        paword.sendKeys(password);

        driver.findElement(By.id("login")).click();
    }

//    @Test(dataProvider="Numbers")
//    public void doStuff(int number, int number1) {
//        Assert.assertEquals(number, 5);
//    }

    @DataProvider(name="LoginDataProvider")
    public Object[][] getData(){
        Object[][] data= {{"bongani10","VZ540H"},{"username2","password2"},
                {"username3","password3"}};

        return data;  //object reference
    }

//    @DataProvider(name="Numbers")
//    public Object[][] getNumbers() {
//        return new Object[][]{{2,0},{4,0},{5,0},{6,0}};
//    }



    @AfterSuite
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }

}

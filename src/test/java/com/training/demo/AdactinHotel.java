package com.training.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class AdactinHotel {

    WebDriver driver;

    @BeforeSuite
    public void setUp() {
        driver = new WebDriverSetup().getChromeDriver();
    }

    @Test
    public void login(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.navigate().to("http://adactinhotelapp.com");

        assertEquals(driver.getTitle(), "AdactIn.com - Hotel Reservation System");

        driver.findElement(By.name("username")).sendKeys("testingdd1234");
        driver.findElement(By.name("password")).sendKeys("Test1234");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void test102() {
        Select location = new Select(driver.findElement(By.name("location")));
        location.selectByVisibleText("Sydney");

        Select hotels = new Select(driver.findElement(By.name("hotels")));
        hotels.selectByVisibleText("Hotel Creek");

        Select roomType = new Select(driver.findElement(By.name("room_type")));
        roomType.selectByVisibleText("Standard");

        Select numberOfRooms = new Select(driver.findElement(By.name("room_nos")));
        numberOfRooms.selectByVisibleText("1 - One");

        driver.findElement(By.name("datepick_in")).clear();
        driver.findElement(By.name("datepick_in")).sendKeys(getCheckinDate(7));
        driver.findElement(By.name("datepick_out")).clear();
        driver.findElement(By.name("datepick_out")).sendKeys(getCheckinDate(5));

        Select adultsPerRoom = new Select(driver.findElement(By.name("adult_room")));
        adultsPerRoom.selectByVisibleText("2 - Two");

        Select childrenPerRoom = new Select(driver.findElement(By.name("child_room")));
        childrenPerRoom.selectByVisibleText("1 - One");

        driver.findElement(By.name("Submit")).click();

        assertEquals(("Check-In Date shall be before than Check-Out Date"), driver.findElement(By.id("checkin_span")).getText());
        assertEquals(("Check-Out Date shall be after than Check-In Date"), driver.findElement(By.id("checkout_span")).getText());
    }

    @Test
    public void test103() {


        // submit dates and options
        driver.findElement(By.name("datepick_in")).clear();
        driver.findElement(By.name("datepick_in")).sendKeys(getCheckinDate(-5));
        driver.findElement(By.name("datepick_out")).clear();
        driver.findElement(By.name("datepick_out")).sendKeys(getCheckinDate(-3));

        driver.findElement(By.name("Submit")).click();

//        assertEquals("Check-In Date shall be before than Check-Out Date", driver.findElement(By.id("checkin_span")).getText());
        // select option
    }

    @Test
    public void test104() {
        driver.findElement(By.name("radiobutton_0")).click();
        driver.findElement(By.name("continue")).click();

        Select cc_exp_month = new Select(driver.findElement(By.name("cc_exp_month")));
        cc_exp_month.selectByVisibleText("January");

        Select cc_exp_year = new Select(driver.findElement(By.name("cc_exp_year")));
        cc_exp_year.selectByVisibleText("2021");
    }

    private String getCheckinDate(int numberOfDays) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, numberOfDays);
        //Date after adding the days to the given date
        return formatter.format(c.getTime());
    }
}

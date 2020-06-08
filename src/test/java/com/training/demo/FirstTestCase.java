package com.training.demo;

import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

public class FirstTestCase {

    public static WebDriver driver;
    @SneakyThrows
    public static void main (String args[]) {
        driver = new WebDriverSetup().getChromeDriver();

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.navigate().to("http://adactinhotelapp.com");

        FileInputStream fis = new FileInputStream("./reg.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        //number of rows
        int noRows =sheet.getLastRowNum();
        //int noCol = sheet.getRow(0).getLastCellNum();

        for(int i =1;i <= noRows; i++) {
            XSSFRow currentRow = sheet.getRow(i);        //active row

            String Username = currentRow.getCell(0).getStringCellValue();
            String Password = currentRow.getCell(1).getStringCellValue();

            //login info

            driver.findElement(By.id("username")).sendKeys(Username);
            driver.findElement(By.id("password")).sendKeys(Password);

            //launch the app
            driver.findElement(By.id("login")).click();

        }

        fis.close();
        driver.close();
    }

}

package com.training.demo;

import com.google.gson.internal.$Gson$Preconditions;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import sun.misc.VM;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

public class OrangeHrmLive {

    WebDriver driver;
    private String USERNAME;
    private String PASSWORD;
    private String ADMINUSERNAME;
    private String ADMINPASSWORD;

    @BeforeSuite
    public void setUp() {
        driver = new WebDriverSetup().getChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        populateData(1);
        populateData(2);
        driver.navigate().to("https://opensource-demo.orangehrmlive.com/index.php/dashboard");
    }

    @Test(priority = 1)
    public void login() {
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
        driver.findElement(By.id("txtUsername")).sendKeys(ADMINUSERNAME);
        driver.findElement(By.id("txtPassword")).sendKeys(ADMINPASSWORD);
        driver.findElement(By.id("btnLogin")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        Assert.assertTrue(driver.findElement(By.id("menu_admin_viewAdminModule")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"welcome\"]")).isDisplayed());
    }

    @Test(priority = 2)
    public void navigateToAdmin() {
        driver.findElement(By.id("menu_admin_viewAdminModule")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("admin/viewSystemUsers"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"searchBtn\"]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"resetBtn\"]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"btnAdd\"]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"btnDelete\"]")).isDisplayed());
    }

    @Test(priority = 3)
    public void validateAdd() {
        driver.findElement(By.xpath("//*[@id=\"btnAdd\"]")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("saveSystemUser"));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"UserHeading\"]")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"UserHeading\"]")).getText(),  "Add User");
    }

    @SneakyThrows
    @Test(priority = 4)
    public void validateSaveAdd() {
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        Select roleSelect = new Select(driver.findElement(By.xpath("//*[@id=\"systemUser_userType\"]")));
        roleSelect.selectByVisibleText("ESS");
        driver.findElement(By.xpath("//*[@id=\"systemUser_employeeName_empName\"]")).sendKeys("Jasmine Morgan");
        driver.findElement(By.xpath("//*[@id=\"systemUser_userName\"]")).sendKeys(USERNAME);
        driver.findElement(By.xpath("//*[@id=\"systemUser_password\"]")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//*[@id=\"systemUser_confirmPassword\"]")).sendKeys(PASSWORD);
        Select statusSelect = new Select(driver.findElement(By.xpath("//*[@id=\"systemUser_status\"]")));
        Assert.assertEquals(statusSelect.getFirstSelectedOption().getText(), "Enabled");
        driver.findElement(By.xpath("//*[@id=\"btnSave\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnSave\"]")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[text()[contains(.,\""+USERNAME+"\")]]")).isDisplayed());
    }

    @Test(priority = 5)
    public void validateSearchAdd() {
        driver.findElement(By.xpath("//*[@id=\"searchSystemUser_userName\"]")).sendKeys(USERNAME);
        driver.findElement(By.xpath("//*[@id=\"searchBtn\"]")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"resultTable\"]/tbody/tr/td[2]/a")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"resultTable\"]/tbody/tr/td[4]")).getText(), "A Duval");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"resultTable\"]/tbody/tr/td[5]")).getText(), "Enabled");
    }

    @Test(priority = 6)
    public void validateDelete() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"resultTable\"]/tbody/tr/td[1]/input"))).click();
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"btnDelete\"]"))).click();
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"dialogDeleteBtn\"]"))).click();

    }

    @Test(priority = 7)
    public void validateSearchDelete() {
        driver.findElement(By.xpath("//*[@id=\"searchSystemUser_userName\"]")).sendKeys(USERNAME);
        driver.findElement(By.xpath("//*[@id=\"searchBtn\"]")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"resultTable\"]/tbody/tr/td")).getText(), "No Records Found");
    }

    @SneakyThrows
    @Test(priority = 8)
    public void validateLogout() {
        driver.findElement(By.xpath("//*[@id=\"welcome\"]")).click();
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()[contains(.,\"Logout\")]]"))).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"logInPanelHeading\"]")).isDisplayed());
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
    }

    @SneakyThrows
    @AfterSuite
    public void tearDown() {
        driver.quit();
    }

    @SneakyThrows
    private void populateData(int index) {
        FileInputStream fis = new FileInputStream("./reg.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        //number of rows
        int noRows = sheet.getLastRowNum();

        if (noRows >= 1) {
            XSSFRow currentRow = sheet.getRow(index);        //active row

            String username = currentRow.getCell(0).getStringCellValue();
            String password = currentRow.getCell(1).getStringCellValue();

            //login info
            if (index == 1) {
                ADMINUSERNAME = username;
                ADMINPASSWORD = password;
            } else {
                USERNAME = username;
                PASSWORD = password;
            }
        }
        fis.close();
    }

}

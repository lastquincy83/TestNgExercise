package com.training.demo;

import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

public class createSql {

    @SneakyThrows
    public static void main (String args[]) {
        FileInputStream fis = new FileInputStream("./cleaned list of countries.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        FileWriter fileWriter = new FileWriter("countries.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);


        //number of rows
        int noRows = sheet.getLastRowNum();

        if (noRows >= 1) {
            for (int i = 1; i < noRows; i++) {
                XSSFRow currentRow = sheet.getRow(i);        //active row
                printWriter.printf("UPDATE ZA00000P set EXTERNALREFERENCE3 = '%s', LASTUPDATEDBY='CR23380', LASTUPDATETIMESTAMP = INT(CURRENT_DATE), LASTUPDATETIME = INT(CURRENT_TIME)  where PARAMETERTYPE = '%s' and PARAMETERVALUE = '%s';", currentRow.getCell(2).getStringCellValue(), currentRow.getCell(0).getStringCellValue(), currentRow.getCell(1).getRawValue());
                printWriter.print("\n");
                printWriter.print("\n");
            }
        }

        sheet = workbook.getSheet("Sheet2");
        noRows = sheet.getLastRowNum();

        if (noRows >= 1) {
            for (int i = 1; i < noRows; i++) {
                XSSFRow currentRow = sheet.getRow(i);        //active row
                printWriter.printf("UPDATE ZA00000P set EXTERNALREFERENCE3 = '%s', LASTUPDATEDBY='CR23380', LASTUPDATETIMESTAMP = INT(CURRENT_DATE), LASTUPDATETIME = INT(CURRENT_TIME)  where PARAMETERTYPE = '%s' and PARAMETERVALUE = '%s';", currentRow.getCell(2).getStringCellValue(), currentRow.getCell(0).getStringCellValue(), currentRow.getCell(1).getStringCellValue());
                printWriter.print("\n");
                printWriter.print("\n");
            }
        }
//            String username = currentRow.getCell(0).getStringCellValue();
//            String password = currentRow.getCell(1).getStringCellValue();

//            //login info
//            if (index == 1) {
//                ADMINUSERNAME = username;
//                ADMINPASSWORD = password;
//            } else {
//                USERNAME = username;
//                PASSWORD = password;
//            }
//        }
        fis.close();
        printWriter.close();
    }
}

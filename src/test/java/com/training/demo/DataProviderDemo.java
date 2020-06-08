package com.training.demo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderDemo {

    @Test(dataProvider = "LoginDataProvider")//reference dataprovider

    public void login(String name, String password) throws InterruptedException   //must match @DataProvider
    {
        System.out.println(name + " " + password);
        Thread.sleep(5000);
    }

    @DataProvider(name = "LoginDataProvider")
    public Object[][] getData() {
        Object[][] data = {{"username1", "password1"}, {"username2", "password2"},{"username3", "password3"}};
        return data;
    }


}

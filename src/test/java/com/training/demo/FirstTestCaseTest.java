package com.training.demo;

import org.testng.annotations.Test;

public class FirstTestCaseTest {
    @Test(priority = 2)
    public void z() {
        System.out.println("z");
    }

    @Test(priority = 3)
    public void c() {
        System.out.println("c");
    }

    @Test(priority = 1)
    public void f() {
        System.out.println("f");
    }

    @Test(priority = 4)
    public void v() {
        System.out.println("v");
    }
}
package com.example.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        AllureUtils.attachText("Failed test", result.getName());
        AllureUtils.attachScreenshot("Failure screenshot");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        AllureUtils.attachScreenshot("Success screenshot");
    }

    @Override
    public void onStart(ITestContext context) {
        AllureUtils.attachText("Suite", context.getName());
    }
}
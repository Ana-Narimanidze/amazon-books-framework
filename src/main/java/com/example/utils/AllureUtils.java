package com.example.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

import static com.example.core.DriverFactory.getDriver;

public class AllureUtils {

    public static void attachScreenshot(String name) {
        WebDriver driver = getDriver();
        if (driver == null) {
            return;
        }

        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
    }

    public static void attachText(String name, String message) {
        Allure.addAttachment(name, message);
    }
}
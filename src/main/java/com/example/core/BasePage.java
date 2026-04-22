package com.example.core;

import com.example.utils.AllureUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Step("Click element: {locator}")
    protected void click(By locator) {
        pause(1500);
        waitClickable(locator).click();
        pause(2500);
        AllureUtils.attachScreenshot("After click");
    }

    @Step("Type text: {text} into: {locator}")
    protected void type(By locator, String text) {
        WebElement element = waitVisible(locator);
        pause(1000);
        element.clear();
        pause(700);
        element.sendKeys(text);
        pause(1500);
        AllureUtils.attachScreenshot("After typing");
    }

    @Step("Scroll to element: {locator}")
    protected void scrollIntoView(By locator) {
        WebElement element = waitVisible(locator);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        pause(1500);
        AllureUtils.attachScreenshot("After scroll");
    }

    @Step("JS click on element: {locator}")
    protected void jsClick(By locator) {
        WebElement element = waitVisible(locator);
        js.executeScript("arguments[0].click();", element);
        pause(2000);
        AllureUtils.attachScreenshot("After JS click");
    }

    protected void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Step("Get text from: {locator}")
    protected String getText(By locator) {
        pause(1000);
        return waitVisible(locator).getText();
    }

    @Step("Check if element is displayed: {locator}")
    protected boolean isDisplayed(By locator) {
        try {
            return waitVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected String normalizeColor(String color) {
        return color.replaceAll("\\s+", "");
    }

    @Step("Parse price text: {rawPrice}")
    protected double parsePrice(String rawPrice) {
        String normalized = rawPrice
                .replace("€", "")
                .replace("EUR", "")
                .replace("\u00A0", "")
                .replaceAll("[^0-9,\\.]", "")
                .trim();

        if (normalized.contains(",") && normalized.contains(".")) {
            normalized = normalized.replace(".", "").replace(",", ".");
        } else if (normalized.contains(",")) {
            normalized = normalized.replace(",", ".");
        }

        double parsed = Double.parseDouble(normalized);
        System.out.println("Raw price: " + rawPrice + " -> Parsed price: " + parsed);
        return parsed;
    }

    protected List<WebElement> allVisible(By... locators) {
        List<WebElement> visibleElements = new ArrayList<>();

        for (By locator : locators) {
            try {
                for (WebElement element : driver.findElements(locator)) {
                    try {
                        if (element.isDisplayed()) {
                            visibleElements.add(element);
                        }
                    } catch (StaleElementReferenceException ignored) {
                    }
                }

                if (!visibleElements.isEmpty()) {
                    return visibleElements;
                }
            } catch (Exception ignored) {
            }
        }

        return visibleElements;
    }

    protected WebElement firstVisible(By... locators) {
        for (By locator : locators) {
            try {
                for (WebElement element : driver.findElements(locator)) {
                    try {
                        if (element.isDisplayed()) {
                            return element;
                        }
                    } catch (StaleElementReferenceException ignored) {
                    }
                }
            } catch (Exception ignored) {
            }
        }
        throw new NoSuchElementException("No visible element found for provided locators");
    }
}
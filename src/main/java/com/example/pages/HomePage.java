package com.example.pages;

import com.example.core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private final By menuButton = By.id("nav-hamburger-menu");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Amazon Germany home page (EN language)")
    public HomePage open() {
        // Force Amazon to load in English
        driver.get("https://www.amazon.de/?language=en_GB");


        pause(1000);
        WebElement menu = waitVisible(menuButton);
        js.executeScript("arguments[0].scrollIntoView(true);", menu);

        return this;
    }

    @Step("Open left hamburger menu")
    public HamburgerMenuPage openHamburgerMenu() {
        WebElement menu = waitVisible(menuButton);
        js.executeScript("arguments[0].click();", menu);
        return new HamburgerMenuPage(driver);
    }

    @Step("Validate home page is loaded")
    public boolean isLoaded() {
        return isDisplayed(menuButton);
    }
}

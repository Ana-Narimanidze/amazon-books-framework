package com.example.pages;

import com.example.core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {

    private final By subtotalLabel = By.xpath(
            "//*[contains(text(),'Subtotal') or contains(text(),'Zwischensumme')]"
    );

    private final By subtotalAmount = By.xpath(
            "//*[@id='sc-subtotal-amount-activecart' or @id='sc-subtotal-amount-buybox']//span"
    );

    private final By proceedToCheckoutButton = By.name("proceedToRetailCheckout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Validate subtotal label is visible")
    public boolean isSubtotalLabelVisible() {
        System.out.println("Cart page URL: " + driver.getCurrentUrl());
        System.out.println("Cart page Title: " + driver.getTitle());
        return isDisplayed(subtotalLabel);
    }

    @Step("Get subtotal amount")
    public double getSubtotalAmount() {
        return parsePrice(getText(subtotalAmount));
    }

    @Step("Set first cart item quantity to 2")
    public CartPage setFirstItemQuantityToTwo() {
        WebElement quantityButton = firstVisible(
                By.xpath("(//span[contains(@class,'a-dropdown-prompt') and normalize-space()='1'])[1]"),
                By.xpath("(//span[contains(@class,'a-button-text') and normalize-space()='1'])[1]"),
                By.xpath("(//a[contains(@aria-label,'Quantity')])[1]"),
                By.xpath("(//span[contains(@aria-label,'Quantity')])[1]"),
                By.xpath("(//*[contains(@id,'quantity') and contains(@class,'dropdown')])[1]"),
                By.xpath("(//span[contains(text(),'Qty: 1')])[1]"),
                By.xpath("(//span[contains(text(),'Qty:1')])[1]")
        );

        System.out.println("Quantity button HTML: " + quantityButton.getAttribute("outerHTML"));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", quantityButton);
        pause(1000);
        quantityButton.click();
        pause(2000);

        WebElement optionTwo = firstVisible(
                By.xpath("//li[.//span[normalize-space()='2']]"),
                By.xpath("//a[normalize-space()='2']"),
                By.xpath("//span[normalize-space()='2']"),
                By.id("quantity_2"),
                By.xpath("//a[contains(@id,'quantity_2')]"),
                By.xpath("//li[contains(@aria-labelledby,'quantity_2')]")
        );

        System.out.println("Quantity option 2 HTML: " + optionTwo.getAttribute("outerHTML"));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", optionTwo);
        pause(1000);
        optionTwo.click();
        pause(3000);

        return this;
    }

    @Step("Proceed to checkout")
    public SignInPage proceedToCheckout() {
        WebElement button = waitVisible(proceedToCheckoutButton);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", button);
        pause(1500);
        js.executeScript("window.scrollBy(0, -150);");
        pause(1000);
        js.executeScript("arguments[0].click();", button);
        pause(2500);
        return new SignInPage(driver);
    }
}
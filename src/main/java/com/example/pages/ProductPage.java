package com.example.pages;

import com.example.core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {

    private final By cartButton = By.id("sw-gtc");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get current product price")
    public double getProductPrice() {
        String price = firstVisible(
                By.cssSelector("#corePrice_feature_div .a-price .a-offscreen"),
                By.cssSelector("#corePriceDisplay_desktop_feature_div .a-price .a-offscreen"),
                By.cssSelector("#tmmSwatches .a-button-selected .a-color-price"),
                By.cssSelector(".kindle-price .a-color-price"),
                By.xpath("(//span[contains(@class,'a-price')]//span[contains(@class,'a-offscreen')])[1]"),
                By.xpath("(//*[contains(@id,'price')]//span[contains(@class,'a-offscreen')])[1]")
        ).getText();

        System.out.println("Detected product price text: " + price);
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Current Title: " + driver.getTitle());

        return parsePrice(price);
    }

    @Step("Add current book to cart")
    public ProductPage addToCart() {
        String currentTitle = driver.getTitle().toLowerCase();
        String currentUrl = driver.getCurrentUrl().toLowerCase();

        if (currentTitle.contains("kindle store")
                || currentTitle.contains("ebook")
                || currentUrl.contains("ebook")
                || currentUrl.contains("kindle")) {
            throw new IllegalStateException("Selected product is an eBook/Kindle item, not a physical book.");
        }

        WebElement button = firstVisible(
                By.id("add-to-cart-button"),
                By.name("submit.add-to-cart"),
                By.xpath("//input[contains(@aria-labelledby,'submit.add-to-cart') or contains(@name,'submit.add-to-cart')]"),
                By.xpath("//input[@title='In den Einkaufswagen']"),
                By.xpath("//input[@title='Add to Shopping Cart']"),
                By.xpath("//span[contains(@id,'submit.add-to-cart')]//input")
        );

        System.out.println("Using add to cart button: " + button.getAttribute("outerHTML"));
        System.out.println("Page title before add to cart: " + driver.getTitle());

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", button);
        js.executeScript("arguments[0].click();", button);
        return this;
    }

    @Step("Open cart after adding books")
    public CartPage goToCart() {
        WebElement cart = firstVisible(
                cartButton,
                By.id("nav-cart"),
                By.xpath("//a[@id='nav-cart' or contains(@href,'/gp/cart/view.html')]")
        );
        cart.click();
        return new CartPage(driver);
    }

    @Step("Return to Books best sellers page")
    public BooksLandingPage goBackToBooksPage() {
        driver.navigate().back();
        return new BooksLandingPage(driver);
    }
}
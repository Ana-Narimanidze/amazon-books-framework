package com.example.pages;

import com.example.core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HamburgerMenuPage extends BasePage {
    private final By booksEntry = By.xpath("//a[normalize-space()='Books' or normalize-space()='Books & Audible' or normalize-space()='B\u00fccher']");
    private final By booksSubmenuOption = By.xpath("//a[contains(@href,'books') and (normalize-space()='Books' or normalize-space()='B\u00fccher')]");

    public HamburgerMenuPage(WebDriver driver) {
        super(driver);
    }

    @Step("Choose Books from left navigation panel")
    public HamburgerMenuPage clickBooksMainCategory() {
        scrollToTextAndClick("Books");
        return this;
    }

    @Step("Click Books sub-option under Books category")
    public BooksLandingPage clickBooksSubOption() {
        WebElement submenu = firstVisible(
                booksSubmenuOption,
                By.xpath("//ul[contains(@class,'hmenu-visible')]//a[contains(.,'Books') or contains(.,'B\u00fccher')][1]"),
                By.xpath("(//a[contains(@href,'books')])[last()]")
        );
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", submenu);
        js.executeScript("arguments[0].click();", submenu);
        return new BooksLandingPage(driver);
    }

    private void scrollToTextAndClick(String text) {
        List<WebElement> items = allVisible(
                By.cssSelector("ul.hmenu-visible a.hmenu-item"),
                By.cssSelector("a.hmenu-item")
        );
        for (WebElement item : items) {
            String itemText = item.getText().trim();
            if (itemText.equalsIgnoreCase(text) || itemText.equalsIgnoreCase("B\u00fccher") || itemText.equalsIgnoreCase("Books & Audible")) {
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", item);
                js.executeScript("arguments[0].click();", item);
                return;
            }
        }

        WebElement fallback = firstVisible(booksEntry);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", fallback);
        js.executeScript("arguments[0].click();", fallback);
    }
}

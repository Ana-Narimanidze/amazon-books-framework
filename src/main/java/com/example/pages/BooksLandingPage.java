package com.example.pages;

import com.example.core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class BooksLandingPage extends BasePage {

    private final By bestSellersSection =
            By.xpath("//*[contains(normalize-space(),'Best Sellers') or contains(normalize-space(),'Bestseller')]");

    public BooksLandingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Validate Best Sellers section is present in DOM")
    public boolean isBestSellersSectionVisible() {
        scrollIntoView(bestSellersSection);
        return isDisplayed(bestSellersSection);
    }

    @Step("Open bestseller physical book with index {index}")
    public ProductPage openBookByIndex(int index) {
        List<WebElement> rawBooks = allVisible(
                By.cssSelector("div[data-asin] h2 a"),
                By.cssSelector("div[data-asin] h3 a"),
                By.xpath("//a[contains(@href,'/dp/') and .//span][not(contains(@href,'sspa'))]"),
                By.xpath("//*[@id='gridItemRoot']//a[contains(@href,'/dp/')]")
        );

        List<WebElement> filteredBooks = new ArrayList<>();

        for (WebElement book : rawBooks) {
            try {
                String text = book.getText().trim().toLowerCase();
                String href = book.getAttribute("href");

                if (href == null || href.isBlank()) {
                    continue;
                }

                String hrefLower = href.toLowerCase();

                if (hrefLower.contains("sspa")) {
                    continue;
                }

                if (hrefLower.contains("ebook")
                        || hrefLower.contains("kindle")
                        || hrefLower.contains("-ebook")
                        || hrefLower.contains("/ebook/")
                        || hrefLower.contains("audible")) {
                    continue;
                }

                if (text.contains("kindle")
                        || text.contains("ebook")
                        || text.contains("e-book")
                        || text.contains("audible")) {
                    continue;
                }

                filteredBooks.add(book);
            } catch (Exception ignored) {
            }
        }

        if (filteredBooks.size() < index) {
            throw new IllegalStateException(
                    "Not enough physical books found on Books page. Found: " + filteredBooks.size()
            );
        }

        WebElement targetBook = filteredBooks.get(index - 1);
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", targetBook);

        String bookText = targetBook.getText();
        String href = targetBook.getAttribute("href");

        System.out.println("Selected book text: " + bookText);
        System.out.println("Selected book href: " + href);

        driver.get(href);
        return new ProductPage(driver);
    }
}
package com.example.tests;

import com.example.core.DriverFactory;
import com.example.pages.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AmazonBooksTest extends BaseTest {

    @Feature("Amazon Books Purchase Flow")
    @Story("Add first two books to cart and validate checkout error")
    @Description("Navigates through Amazon.de Books, validates Best Sellers, adds two books to the cart, verifies subtotal, and checks invalid login error text and color.")
    @Test(priority = 1, groups = {"smoke", "regression", "cart", "checkout"})
    public void addFirstTwoBooksAndValidateInvalidLoginError() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        Assert.assertTrue(homePage.open().isLoaded(), "Amazon home page did not load.");

        BooksLandingPage booksLandingPage = homePage
                .openHamburgerMenu()
                .clickBooksMainCategory()
                .clickBooksSubOption();

        Assert.assertTrue(booksLandingPage.isBestSellersSectionVisible(),
                "Best Sellers section is not visible in the DOM.");

        ProductPage firstBookPage = booksLandingPage.openBookByIndex(1);
        double firstBookPrice = firstBookPage.getProductPrice();
        firstBookPage.addToCart();

        BooksLandingPage secondBookSelectionPage = firstBookPage.goBackToBooksPage();
        ProductPage secondBookPage = secondBookSelectionPage.openBookByIndex(2);
        double secondBookPrice = secondBookPage.getProductPrice();
        secondBookPage.addToCart();

        CartPage cartPage = secondBookPage.goToCart();
        Assert.assertTrue(cartPage.isSubtotalLabelVisible(), "Cart Subtotal label is not visible.");

        Assert.assertEquals(cartPage.getActiveCartLineItemCount(), 2,
                "The basket should list exactly two book line items.");

        Assert.assertTrue(firstBookPrice > 0 && secondBookPrice > 0,
                "Each product detail page should expose a positive price.");

        double lineTotalsSum = round(cartPage.getSumOfActiveCartLineTotals());
        double actualSubtotal = round(cartPage.getSubtotalAmount());
        Assert.assertEquals(actualSubtotal, lineTotalsSum,
                "Cart subtotal should match the sum of the line totals Amazon shows in the basket "
                        + "(detail-page prices can differ from the offer placed in the cart).");

        SignInPage signInPage = cartPage.proceedToCheckout();
        Assert.assertTrue(signInPage.isLoginPageDisplayed(), "Amazon login page was not displayed.");

        signInPage.enterEmail("ananarimanidze57@gmail.com")
                .clickContinue();

        Assert.assertTrue(signInPage.isPasswordFieldDisplayed(),
                "Password field did not appear after entering email.");

        signInPage.enterPassword("WrongPassword123!")
                .clickSignIn();

        Assert.assertTrue(signInPage.isPasswordErrorDisplayed(),
                "Password error message was not displayed.");

        Assert.assertTrue(signInPage.getPasswordErrorText().contains("Your password is incorrect"),
                "Unexpected error text: " + signInPage.getPasswordErrorText());

        Assert.assertTrue(signInPage.isErrorColorRed(),
                "Error message color is not red. Actual color: " + signInPage.getPasswordErrorColor());
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
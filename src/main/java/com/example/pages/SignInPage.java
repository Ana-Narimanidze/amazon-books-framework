package com.example.pages;

import com.example.core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {

    private final By emailField = By.id("ap_email");
    private final By continueButton = By.id("continue");
    private final By passwordField = By.id("ap_password");
    private final By signInSubmitButton = By.id("signInSubmit");

    private final By errorBox = By.cssSelector(".a-alert.a-alert-error");
    private final By errorText = By.cssSelector(".a-alert.a-alert-error .a-alert-content");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    @Step("Validate Amazon login page is displayed")
    public boolean isLoginPageDisplayed() {
        return isDisplayed(emailField) || isDisplayed(passwordField);
    }

    @Step("Enter email: {email}")
    public SignInPage enterEmail(String email) {
        type(emailField, email);
        return this;
    }

    @Step("Click Continue after email")
    public SignInPage clickContinue() {
        click(continueButton);
        return this;
    }

    @Step("Enter password: {password}")
    public SignInPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    @Step("Click Sign In")
    public SignInPage clickSignIn() {
        click(signInSubmitButton);
        return this;
    }

    @Step("Is password field displayed")
    public boolean isPasswordFieldDisplayed() {
        return isDisplayed(passwordField);
    }

    @Step("Is password error displayed")
    public boolean isPasswordErrorDisplayed() {
        return isDisplayed(errorBox) || isDisplayed(errorText);
    }

    @Step("Get password error text")
    public String getPasswordErrorText() {
        return getText(errorText).trim();
    }

    @Step("Get password error color")
    public String getPasswordErrorColor() {
        String color = driver.findElement(errorBox).getCssValue("border-top-color");
        System.out.println("Detected error border color: " + color);
        return color;
    }

    @Step("Check whether error color is red")
    public boolean isErrorColorRed() {
        String normalized = normalizeColor(getPasswordErrorColor()).toLowerCase();
        System.out.println("Normalized error color: " + normalized);
        return normalized.contains("193,0,21")
                || normalized.contains("204,12,57")
                || normalized.contains("196,0,21");
    }
}
package com.kts.cultural_content.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;

    @FindBy(css = "#username")
    private WebElement usernameInputField;

    @FindBy(css = "#password")
    private WebElement passwordInputField;

    @FindBy(css = "#login-btn")
    private WebElement loginButton;

    @FindBy(css = "#username-error")
    private WebElement usernameErrorMessage;

    @FindBy(css = "#password-error")
    private WebElement passwordErrorMessage;

    @FindBy(css = "#search-btn")
    private WebElement searchButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getUsernameInputField() {
        return usernameInputField;
    }
    

    public void setUsername(String username) {
        this.usernameInputField.clear();
        this.usernameInputField.sendKeys(username);
    }

    public void setEmptyUsername() {
        while (!usernameInputField.getText().equals("")) {
            usernameInputField.sendKeys(Keys.BACK_SPACE);
        }
    }

    public WebElement getPasswordInputField() {
        return passwordInputField;
    }

    public void setPassword(String password) {
        this.passwordInputField.clear();
        this.passwordInputField.sendKeys(password);
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getSearchButton() { return  searchButton;};

    public WebElement getUsernameErrorMessage() {
        return usernameErrorMessage;
    }

    public WebElement getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public void ensureLoginBtnIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(usernameInputField));
    }

    public void ensureSearchBtnIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(searchButton));
    }
}

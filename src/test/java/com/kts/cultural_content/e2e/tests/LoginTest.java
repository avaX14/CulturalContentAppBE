package com.kts.cultural_content.e2e.tests;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.kts.cultural_content.e2e.pages.LoginPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {

    private WebDriver browser;

    private LoginPage loginPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
    }

    @After
    public void shutdownSelenium() {
        browser.quit();
    }

    private void gotoLoginPage() {
        browser.navigate().to("http://localhost:4200/login");
        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
    }

    @Test
    public void testLoginEmptyFields() {
        gotoLoginPage();

        loginPage.getUsernameInputField().click();
        loginPage.getPasswordInputField().click();
        loginPage.getUsernameInputField().click();
        assertTrue(loginPage.getPasswordErrorMessage().isDisplayed());
        assertTrue(loginPage.getUsernameErrorMessage().isDisplayed());
        assertFalse(loginPage.getLoginButton().isEnabled());
    }

    @Test
    public void testLoginOnlyUsername() {
        this.gotoLoginPage();
        loginPage.getUsernameInputField().click();
        loginPage.setUsername("testUsername");
        loginPage.getPasswordInputField().click();
        loginPage.getUsernameInputField().click();
        assertTrue(loginPage.getPasswordErrorMessage().isDisplayed());
        assertFalse(loginPage.getLoginButton().isEnabled());

        try {
            assertFalse(loginPage.getUsernameErrorMessage().isDisplayed());
        } catch(NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testLoginOnlyPassword() {
        this.gotoLoginPage();

        loginPage.getUsernameInputField().click();
        loginPage.getPasswordInputField().click();
        loginPage.setPassword("123456");
        assertTrue(loginPage.getUsernameErrorMessage().isDisplayed());
        assertFalse(loginPage.getLoginButton().isEnabled());

        try {
            assertFalse(loginPage.getPasswordErrorMessage().isDisplayed());
        } catch(NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testLoginBadCredentials() {
        this.gotoLoginPage();

        loginPage.setUsername("badtest");
        loginPage.setPassword("badtest123");
        assertTrue(loginPage.getLoginButton().isEnabled());
        loginPage.getLoginButton().click();
        loginPage.ensureLoginBtnIsDisplayed();
        assertEquals(browser.getCurrentUrl(), "http://localhost:4200/login");
    }

    @Test
    public void testLoginCorrectCredentials() {
        this.gotoLoginPage();

        loginPage.setUsername("MarkoMarkovic12");
        loginPage.setPassword("test1234");
        assertTrue(loginPage.getLoginButton().isEnabled());
        loginPage.getLoginButton().click();

        loginPage.ensureSearchBtnIsDisplayed();

        assertEquals(browser.getCurrentUrl(), "http://localhost:4200");
    }
}

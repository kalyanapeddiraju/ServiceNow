package pages;

import Utils.CommonSeleniumFunctions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static Utils.TestBase.getDriver;

public class LoginPage extends CommonSeleniumFunctions {
    WebDriver driver;

    public LoginPage() {
        driver = getDriver();
    }

    private String _log_pageName = "[Login Page]", _log_indicator = ">> ";
    String loginPage_title = "Authentication";
    public final By userNameFieldLocator = By.id("username"),
            passwordFiledLocator = By.id("password"),
            authenticateButtonLocator = By.cssSelector("button[type='submit']"),
            signoutLocator = By.cssSelector("span[translate='global.menu.account.logout']"),
            mainTitleLocator = By.cssSelector("h1[translate='main.title']"),
            mainLoggedmessageLocator = By.cssSelector("div[class='alert alert-success ng-scope ng-binding']"),
            remembermeLocator = By.id("rememberMe"),
            authenticaionFailMessageLocator = By.cssSelector("div[translate='login.messages.error.authentication']");

    // mainLoggedmessageLocator=By.cssSelector("div[translate='mmain.logged.message']");


    public boolean isCurrent() {
        System.out.println(_log_indicator + _log_pageName + " Checking if page is current...");
        waitForPageLoad();
        return driver.getTitle().equals(loginPage_title);
    }

    public void selectOptionInDropDown(By locator, String optionText) {
        Select dropDown = new Select(driver.findElement(locator));
        dropDown.selectByVisibleText(optionText);
    }

    public void enterUsername(String username) {
        System.out.println(_log_indicator + _log_pageName + " Entering username...");
        setTextFieldValueFor(userNameFieldLocator, username);
    }

    public void enterPassword(String password) {
        Assert.assertTrue("Password Filed in Login Page is not Loaded", checkElementPresent("password"));
        System.out.println(_log_indicator + _log_pageName + " Entering password...");
        setTextFieldValueFor(passwordFiledLocator, password);
    }

    public void clickAuthenticateButton() {
        System.out.println(_log_indicator + _log_pageName + " Hitting login button...");
        clickElement(authenticateButtonLocator);
    }

}

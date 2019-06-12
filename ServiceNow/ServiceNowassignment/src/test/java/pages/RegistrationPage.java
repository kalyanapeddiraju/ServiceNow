package pages;

import Utils.CommonSeleniumFunctions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utils.TestBase.getDriver;

public class RegistrationPage extends CommonSeleniumFunctions {


    WebDriver driver;

    public RegistrationPage() {
        driver = getDriver();
    }

    private String Registration_pageName = "[Registration Page]", _Registration_indicator = ">> ";
    String RegistrationPage_title = "Registration";
    String dynamicLocatorValue;
    public final By loginFieldLocator = By.name("login"),
            emailFieldLocator = By.name("email"),
            newPasswordFiledLocator = By.name("password"),
            newPasswordConfirmationFiledLocator = By.name("confirmPassword"),
            registorButtonLocator = By.cssSelector("button[translate='register.form.button']"),
            loginReqMessageLocator = By.cssSelector("class[translate='register.messages.validate.login.required']"),
            emailReqMessageLocator = By.cssSelector("class[translate='global.messages.validate.email.required']"),
            passwordReqMessageLocator = By.cssSelector("class[translate='global.messages.validate.newpassword.required']"),
            confirmPasswordReqMessageLocator = By.cssSelector("h1[translate='main.title']"),
            dynamicLocator = By.cssSelector("class[translate='" + dynamicLocatorValue + "']");


    public boolean isCurrent() {
        System.out.println(_Registration_indicator + Registration_pageName + " Checking if page is current...");
        return driver.getTitle().equals(RegistrationPage_title);
    }


    public By getLocator(String translateValue) {
        return By.cssSelector("p[translate='" + translateValue + "']");
    }



    public void enterLogin(String username) {
        System.out.println(_Registration_indicator + Registration_pageName + " Entering Login...");
        setTextFieldValueFor(loginFieldLocator, username);
    }
    public void enterEmail(String email) {
        Assert.assertTrue("Email Filed in Registration Page is not Loaded", checkElementPresent("email"));
        System.out.println(_Registration_indicator + Registration_pageName + " Entering email..." + email);
        setTextFieldValueFor(emailFieldLocator, email);
    }

    public void enterPassword(String password) {
        Assert.assertTrue("Password Filed in Registration Page is not Loaded", checkElementPresent("password"));
        System.out.println(_Registration_indicator + Registration_pageName + " Entering New password...");
        setTextFieldValueFor(newPasswordFiledLocator, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        Assert.assertTrue("Confirm Password Filed in Registration Page is not Loaded", checkElementPresent("confirmPassword"));
        System.out.println(_Registration_indicator + Registration_pageName + " Entering Confirm password...");
        setTextFieldValueFor(newPasswordConfirmationFiledLocator, confirmPassword);
    }
    public void clickRegisterButton() {
        System.out.println(_Registration_indicator + Registration_pageName + " Hitting Register button...");
        clickElement(registorButtonLocator);
    }
}

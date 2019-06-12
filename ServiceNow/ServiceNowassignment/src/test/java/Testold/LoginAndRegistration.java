package Testold;

import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;
import Utils.CommonSeleniumFunctions;
import Utils.GenerateReport;
import Utils.GenericFunctions;
import Utils.TestBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import data.TestData;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@Listeners(GenerateReport.class)
public class LoginAndRegistration extends TestBase {
    static WebDriver driver = getDriver();
    final String _test_indicator = "(test)", _log_indicator = ">> ";
    private final String pageUrl = "http://127.0.0.1:8080/#/";
    //String userName = "admin", password = "admin";
    String userName, password, confirmPassword, key;

    HomePage mainPage = new HomePage();
    LoginPage loginPage = new LoginPage();
    CommonSeleniumFunctions commonSeleniumFunctions = new CommonSeleniumFunctions();
    RegistrationPage registrationPage = new RegistrationPage();
    GenericFunctions genericFunctions = new GenericFunctions();
    TestData testData = new TestData();
    By valueToSetTolocator = By.id("");
    String extentReportFile, extentReportImage;
    ExtentReports extent;
    ExtentTest extentTest;

    @Ignore
    @Test
    public void ValidateLoginPageContent() throws InterruptedException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the content /elements in the Login Page.");
        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }
        assertEquals("Title of page is incorrect", "gurukula", commonSeleniumFunctions.getPageTitle());
        assertEquals("Main Title of page is incorrect", "Welcome to Gurukula!", commonSeleniumFunctions.getMainHeaderText(mainPage.maintittleLocator));
        assertEquals("Home Menu is not Present", "Home", commonSeleniumFunctions.getMainHeaderText(mainPage.homeMenuLocator));
        assertEquals("Account Menu is not Present", "Account", commonSeleniumFunctions.getMainHeaderText(mainPage.accountMenuLocator));
        assertEquals("Login Link is not Present", "login", commonSeleniumFunctions.getMainHeaderText(mainPage.loginLinkLocator));
        assertEquals("New Account Registration Link is not Present", "Register a new account", commonSeleniumFunctions.getMainHeaderText(mainPage.newAccountRegistorLocator));
    }
    @Ignore
    @Test
    public void ValidateLoginFunctionality() throws InterruptedException, JSONException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the Login Functionality...");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        mainPage.clickElement(mainPage.loginLinkLocator);

        Assert.assertTrue("Login Page is not Loaded", loginPage.isCurrent());
        assertEquals("Title of page is incorrect", "Authentication", mainPage.getPageTitle());

        userName = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "userName");
        password = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "password");

        Assert.assertTrue("Automatic Login in the Login Page is not Checked", commonSeleniumFunctions.isCheckBoxChecked(loginPage.remembermeLocator));

        Assert.assertTrue("User Name Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("username"));
        loginPage.enterUsername(userName);
        Assert.assertTrue("Password Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("password"));
        loginPage.enterPassword(password);
        loginPage.clickAuthenticateButton();

        commonSeleniumFunctions.waitForPageLoad();

        String ExpectedMessage = "You are logged in as user " + "\"" + userName + "\".";
        assertEquals("Main Title of Login page is incorrect", "Welcome to Gurukula!", commonSeleniumFunctions.getMainHeaderText(loginPage.mainTitleLocator));
        assertEquals("Main logged message is incorrect", ExpectedMessage, commonSeleniumFunctions.getMainHeaderText(loginPage.mainLoggedmessageLocator));


        mainPage.clickElement(mainPage.accountMenuLocator);
        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        mainPage.clickElement(loginPage.signoutLocator);


    }
    @Ignore
    @Test
    public void ValidateNewRegistrationRegisterrLink() throws InterruptedException, JSONException {
        System.out.println("This Test Verify the Regetration link from Main page -->Register >>>> ");
        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        commonSeleniumFunctions.clickElement(mainPage.newAccountRegistorLocator);

        Assert.assertTrue("Registration Page is not loaded  ", registrationPage.isCurrent());
        assertEquals("Title of page is incorrect", "Registration", mainPage.getPageTitle());

        userName = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "userName");
        String email = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "email");
        password = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "password");
        confirmPassword = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "confirmpassword");

        //Login
        Assert.assertTrue("Login Field is not found in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("login"));
        Assert.assertTrue("User Name either Lenght is invalid or not in Lower Case", genericFunctions.isLowerCase(userName));
        registrationPage.enterLogin(userName);
        //Email
        Assert.assertTrue("Email Entered is not valid  >> Email entered is >>>  " + email, genericFunctions.isValid(email));
        Assert.assertTrue("Email Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("email"));
        registrationPage.enterEmail(email);
        // Password
        Assert.assertTrue("Password Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("password"));
        Assert.assertTrue("Password Entered is not matching to desired Length  ", genericFunctions.checkStringLength(password, 5, 50));
        registrationPage.enterPassword(password);
        //Confirm Password
        assertEquals("Password and Confirmed Password not matching", password, confirmPassword);
        Assert.assertTrue("confrim Password Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("confirmPassword"));
        Assert.assertTrue("Confirm Password Entered is not matching to desired Length  ", genericFunctions.checkStringLength(confirmPassword, 5, 50));
        registrationPage.enterConfirmPassword(confirmPassword);
        //Click Registration Button
        Assert.assertTrue("Register Button not Enabled in the Registration Page  ", commonSeleniumFunctions.isElementEnabled(registrationPage.registorButtonLocator));
        registrationPage.clickRegisterButton();
        Assert.assertTrue("Login Page is not Loaded", loginPage.isCurrent());
    }
    @Ignore
    @Test
    public void ValidateNewRegistrationAccountRegister() throws InterruptedException, JSONException {
        System.out.println("This Test Verify the Regetration from Account -->Register >>>> ");

        extentReportFile = System.getProperty("user.dir") + "/extentReportFile.html";
        extentReportImage = System.getProperty("user.dir") + "/extentReportImage.png";

        extent = new ExtentReports(extentReportFile, false);
        extentTest = extent.startTest("Validate New Registratuion By Selecting from Account Menu", "Verify if Browser is Lanuched");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }
        extentTest.log(LogStatus.INFO, "Browser Launched");
        extentTest.log(LogStatus.INFO, "Navigated to >>>" + pageUrl);

        commonSeleniumFunctions.isElementVisible(mainPage.accountMenuLocator);
        commonSeleniumFunctions.clickElement(mainPage.accountMenuLocator);
        commonSeleniumFunctions.clickElement(mainPage.accountRegisterLocator);

        commonSeleniumFunctions.waitForPageLoad();

        Assert.assertTrue("Registration Page is not loaded  ", registrationPage.isCurrent());
        assertEquals("Title of page is incorrect", "Registration", mainPage.getPageTitle());

        userName = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "userName");
        String email = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "email");
        password = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "password");
        confirmPassword = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "confirmpassword");

        //Login
        Assert.assertTrue("Login Field is not found in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("login"));
        Assert.assertTrue("User Name either Lenght is invalid or not in Lower Case", genericFunctions.isLowerCase(userName));
        registrationPage.enterLogin(userName);
        //Email
        Assert.assertTrue("Email Entered is not valid  >> Email entered is >>>  " + email, genericFunctions.isValid(email));
        Assert.assertTrue("Email Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("email"));
        registrationPage.enterEmail(email);
        // Password
        Assert.assertTrue("Password Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("password"));
        Assert.assertTrue("Password Entered is not matching to desired Length  ", genericFunctions.checkStringLength(password, 5, 50));
        registrationPage.enterPassword(password);
        //Confirm Password
        assertEquals("Password and Confirmed Password not matching", password, confirmPassword);
        Assert.assertTrue("confrim Password Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("confirmPassword"));
        Assert.assertTrue("Confirm Password Entered is not matching to desired Length  ", genericFunctions.checkStringLength(confirmPassword, 5, 50));
        registrationPage.enterConfirmPassword(confirmPassword);
        //Click Registration Button
        Assert.assertTrue("Register Button not Enabled in the Registration Page  ", commonSeleniumFunctions.isElementEnabled(registrationPage.registorButtonLocator));
        registrationPage.clickRegisterButton();
        Assert.assertTrue("Login Page is not Loaded", loginPage.isCurrent());


    }
    @Ignore
    @Test
    public void ValidateNewRegistrationNoVlauesEntered() throws InterruptedException, JSONException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the New Registration Functionality with no Values Entered...");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        commonSeleniumFunctions.clickElement(mainPage.newAccountRegistorLocator);

        Assert.assertTrue("Registration Page is not loaded  ", registrationPage.isCurrent());
        assertEquals("Title of page is incorrect", "Registration", mainPage.getPageTitle());

        Assert.assertTrue("Login Filed is not found in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("login"));

        commonSeleniumFunctions.setTextFieldValueFor(registrationPage.loginFieldLocator, String.valueOf(Keys.TAB));

        Assert.assertTrue("Login Required message is not found in the Registration Page  ", commonSeleniumFunctions.isElementVisible(registrationPage.loginReqMessageLocator));
        Assert.assertTrue("Email Reuired message is not found in the Registration Page  ", commonSeleniumFunctions.isElementVisible(registrationPage.emailReqMessageLocator));
        Assert.assertTrue("Password Required message is not found in the Registration Page  ", commonSeleniumFunctions.isElementVisible(registrationPage.passwordReqMessageLocator));
        Assert.assertTrue("Confirm Password Required message is not found in the Registration Page  ", commonSeleniumFunctions.isElementVisible(registrationPage.confirmPasswordReqMessageLocator));


    }
    @Ignore
    @Test
    public void ValidateLogInFromRegistrationPage() throws InterruptedException, JSONException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the New Registration Functionality by Clicking Link from Login Page...");


        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        commonSeleniumFunctions.clickElement(mainPage.newAccountRegistorLocator);

        Assert.assertTrue("Registration Page is not loaded  ", registrationPage.isCurrent());

        assertEquals("Title of page is incorrect", "Registration", mainPage.getPageTitle());

        mainPage.clickElement(mainPage.loginLinkLocator);

        Assert.assertTrue("Login Page is not Loaded", loginPage.isCurrent());
        assertEquals("Title of page is incorrect", "Authentication", mainPage.getPageTitle());

        userName = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "userName");
        password = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "password");


        Assert.assertTrue("User Name Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("username"));
        loginPage.enterUsername(userName);
        Assert.assertTrue("Password Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("password"));
        loginPage.enterPassword(password);
        loginPage.clickAuthenticateButton();

        commonSeleniumFunctions.waitForPageLoad();
        String ExpectedMessage = "You are logged in as user " + "\"" + userName + "\".";
        assertEquals("Main Title of Login page is incorrect", "Welcome to Gurukula!", commonSeleniumFunctions.getMainHeaderText(loginPage.mainTitleLocator));
        assertEquals("Main logged message is incorrect", ExpectedMessage, commonSeleniumFunctions.getMainHeaderText(loginPage.mainLoggedmessageLocator));

        assertEquals("Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(mainPage.entitiesMenuLocator));

        commonSeleniumFunctions.clickElement(mainPage.entitiesMenuLocator);


        assertEquals("Entities/ Branch Menu is not Present in the Home Page", "Branch", commonSeleniumFunctions.getMainHeaderText(mainPage.branchMenuLocator));
        assertEquals("Entities/ Staff Menu is not Present in the Home Page", "Staff", commonSeleniumFunctions.getMainHeaderText(mainPage.staffMenuLocator));

        commonSeleniumFunctions.clickElement(mainPage.accountMenuLocator);

        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        mainPage.clickElement(loginPage.signoutLocator);

    }

    @Test
    public void validateErrorMessageTips() throws InterruptedException, JSONException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the Diffrent Error messges of New Registration Functionality...");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        mainPage.clickElement(mainPage.loginLinkLocator);

        Assert.assertTrue("Login Page is not Loaded", loginPage.isCurrent());
        assertEquals("Title of page is incorrect", "Authentication", mainPage.getPageTitle());

        Assert.assertTrue("User Name Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("username"));
        commonSeleniumFunctions.setTextFieldValueFor(loginPage.userNameFieldLocator, "");
        Assert.assertTrue("Password Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("password"));
        commonSeleniumFunctions.setTextFieldValueFor(loginPage.passwordFiledLocator, "");
        commonSeleniumFunctions.clickElement(loginPage.authenticateButtonLocator);
        commonSeleniumFunctions.waitForPageLoad();
        assertEquals("Authentication Error in Login  page is incorrect", "Authentication failed! Please check your credentials and try again.", commonSeleniumFunctions.getMainHeaderText(loginPage.authenticaionFailMessageLocator));
        commonSeleniumFunctions.clickElement(mainPage.newAccountRegistorLocator);
        ArrayList<String> executionKeys = testData.getExecutionOrder();

        for (int i = 0; i < executionKeys.size(); i++) {
            // getkey
            key = executionKeys.get(i);

            commonSeleniumFunctions.setTextFieldValueFor(locator(key), "addd");
            commonSeleniumFunctions.clearText(locator(key));
            commonSeleniumFunctions.setTextFieldValueFor(locator(key), genericFunctions.convertTestSetString(testData.fieldErrorValues(), key));

            By locator = registrationPage.getLocator(genericFunctions.convertTestSetString(testData.fieldErrorMessageLocators(), key));
            String errorMessage = genericFunctions.convertTestSetString(testData.fieldErrorMessages(), key);
            assertEquals(key.toUpperCase() + "  >>>>  Error Message is incorrect", errorMessage, commonSeleniumFunctions.getMainHeaderText(locator));

            Thread.sleep(2000);
        }
    }




    public By locator(String key) {

        if (key.contains("login")) {
            valueToSetTolocator = registrationPage.loginFieldLocator;
        } else if (key.contains("email")) {
            valueToSetTolocator = registrationPage.emailFieldLocator;
        } else if (key.contains("newpassword")) {
            valueToSetTolocator = registrationPage.newPasswordFiledLocator;
        } else if (key.contains("confirmPassword")) {
            valueToSetTolocator = registrationPage.newPasswordConfirmationFiledLocator;
        }

        return valueToSetTolocator;
    }

   /* @AfterClass
    public void endTest(){
        extent.endTest(extentTest);
        extent.flush();
    }*/

}

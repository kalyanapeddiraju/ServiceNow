package test;

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
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;

import static Utils.GenericFunctions.loadProperties;
import static org.junit.Assert.assertEquals;

@Listeners(GenerateReport.class)
public class LoginAndRegistrationExtentReport extends TestBase {
    private static WebDriver driver = getDriver();
    private final String _test_indicator = "(test)", _log_indicator = ">> ";
  //  private final String pageUrl = "http://127.0.0.1:8080/#/";
    static Properties properties;
    private String userName, password, confirmPassword, key;
    private HomePage mainPage = new HomePage();
    private LoginPage loginPage = new LoginPage();
    private CommonSeleniumFunctions commonSeleniumFunctions = new CommonSeleniumFunctions();
    private RegistrationPage registrationPage = new RegistrationPage();
    private static GenericFunctions genericFunctions = new GenericFunctions();
    private TestData testData = new TestData();
    private By valueToSetToLocator = By.id("");
    private static ExtentReports report;
    static String pageUrl;


    @BeforeSuite
    public void suiteSetup() throws IOException, URISyntaxException, InterruptedException {
        properties = loadProperties ( );

    }


    @BeforeClass
    public static void startTest() throws IOException {
        String FileName="TestReults"+ new GenericFunctions().DateFormatYYYYMMDD();
        report = new ExtentReports(System.getProperty("user.dir") + File.separator + FileName+".html",false);

        properties =genericFunctions.loadProperties ( );

        pageUrl = properties.getProperty("pageUrl");
    }

    @Test
    public void validateLoginPageContent() {
        System.out.println(_log_indicator + _test_indicator + " This test validates the content / elements in the Login Page.");
        ExtentTest test = report.startTest("This test validates the content / elements in the Login Page.");
        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }
        test.log(LogStatus.INFO, "Navigate to Login Page");
        assertEqualsR(test, "Title of page is incorrect", "gurukula", commonSeleniumFunctions.getPageTitle());
        test.log(LogStatus.INFO, "Title Page is Correct  -- Expected is : gurukula  & Actual is : " + commonSeleniumFunctions.getPageTitle());
        assertEqualsR(test, "Main Title of page is incorrect", "Welcome to Gurukula!", commonSeleniumFunctions.getMainHeaderText(mainPage.maintittleLocator));
        test.log(LogStatus.INFO, "Main Title Page is correct  -- Expected is : Welcome to Gurukula!  & Actual is : " + commonSeleniumFunctions.getMainHeaderText(mainPage.maintittleLocator));
        assertEqualsR(test, "Home Menu is not Present", "Home", commonSeleniumFunctions.getMainHeaderText(mainPage.homeMenuLocator));
        test.log(LogStatus.INFO, "Home Button is present");
        assertEqualsR(test, "Account Menu is not Present", "Account", commonSeleniumFunctions.getMainHeaderText(mainPage.accountMenuLocator));
        test.log(LogStatus.INFO, "Account Menu is present");
        assertEqualsR(test, "Login Link is not Present", "login", commonSeleniumFunctions.getMainHeaderText(mainPage.loginLinkLocator));
        test.log(LogStatus.INFO, "Login Link is present");
        assertEqualsR(test, "New Account Registration Link is not Present", "Register a new account", commonSeleniumFunctions.getMainHeaderText(mainPage.newAccountRegistorLocator));
        test.log(LogStatus.INFO, "New Account Registration Link is present");
        test.log(LogStatus.PASS, "Content validation test passed");
        report.endTest(test);
    }

    @Test
    public void ValidateLoginFunctionality() throws JSONException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the Login Functionality...");
        ExtentTest test = report.startTest("This test validates the Login Functionality");
        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        mainPage.clickElement(mainPage.loginLinkLocator);

        assertTrueR(test, "Login Page is not Loaded", loginPage.isCurrent());
        test.log(LogStatus.INFO, "Login Page is Loaded Sucessfully");

        assertEqualsR(test, "Title of page is incorrect", "Authentication", mainPage.getPageTitle());

        userName = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "userName");
        password = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "password");

        assertTrueR(test, "Automatic Login in the Login Page is not Checked", commonSeleniumFunctions.isCheckBoxChecked(loginPage.remembermeLocator));

        assertTrueR(test, "User Name Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("username"));
        loginPage.enterUsername(userName);
        assertTrueR(test, "Password Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("password"));
        loginPage.enterPassword(password);
        test.log(LogStatus.INFO, "Entered User ID >>> "+ userName+ "  and Password >>>  "+ password);
        loginPage.clickAuthenticateButton();

        test.log(LogStatus.INFO, "Authenticate Sucessful");
        commonSeleniumFunctions.waitForPageLoad();

        String ExpectedMessage = "You are logged in as user " + "\"" + userName + "\".";
        assertEqualsR(test, "Main Title of Login page is incorrect", "Welcome to Gurukula!", commonSeleniumFunctions.getMainHeaderText(loginPage.mainTitleLocator));
        assertEqualsR(test, "Main logged message is incorrect", ExpectedMessage, commonSeleniumFunctions.getMainHeaderText(loginPage.mainLoggedmessageLocator));

        mainPage.clickElement(mainPage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        mainPage.clickElement(loginPage.signoutLocator);
        test.log(LogStatus.INFO, "Logout is Sucessful");
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void ValidateNewRegistrationRegisterrLink() throws JSONException {
        System.out.println("This Test Verify the Registration link from Main page --> Register >>>> ");
        ExtentTest test = report.startTest("This Test Verify the Registration link from Main page --> Register >>>> ");
        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        commonSeleniumFunctions.clickElement(mainPage.newAccountRegistorLocator);

        assertTrueR(test, "Registration Page is not loaded  ", registrationPage.isCurrent());
        test.log(LogStatus.INFO, "Registration Page is sucessfully loaded...");
        assertEqualsR(test, "Title of page is incorrect", "Registration", mainPage.getPageTitle());

        userName = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "userName");
        String email = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "email");
        password = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "password");
        confirmPassword = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "confirmpassword");

        //Login
        assertTrueR(test, "Login Field is not found in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("login"));
        assertTrueR(test, "User Name either Lenght is invalid or not in Lower Case", genericFunctions.isLowerCase(userName));
        registrationPage.enterLogin(userName);
        test.log(LogStatus.INFO, "Registration Page >> New Registration with Login >>>  "+userName);
        //Email
        assertTrueR(test, "Email Entered is not valid  >> Email entered is >>>  " + email, genericFunctions.isValid(email));
        assertTrueR(test, "Email Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("email"));
        registrationPage.enterEmail(email);

        test.log(LogStatus.INFO, "Registration Page >> New Registration with Email >>>  "+email);
        // Password
        assertTrueR(test, "Password Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("password"));
        assertTrueR(test, "Password Entered is not matching to desired Length  ", genericFunctions.checkStringLength(password, 5, 50));
        registrationPage.enterPassword(password);
        test.log(LogStatus.INFO, "Registration Page >> New Registration with Password >>>  "+password);
        //Confirm Password
        assertEquals("Password and Confirmed Password not matching", password, confirmPassword);
        assertTrueR(test, "confrim Password Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("confirmPassword"));
        assertTrueR(test, "Confirm Password Entered is not matching to desired Length  ", genericFunctions.checkStringLength(confirmPassword, 5, 50));
        registrationPage.enterConfirmPassword(confirmPassword);
        test.log(LogStatus.INFO, "Registration Page >> New Registration with New Password Confirm >>>  "+confirmPassword);
        //Click Registration Button
        assertTrueR(test, "Register Button not Enabled in the Registration Page  ", commonSeleniumFunctions.isElementEnabled(registrationPage.registorButtonLocator));
        registrationPage.clickRegisterButton();
        assertTrueR(test, "Login Page is not Loaded", loginPage.isCurrent());
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void ValidateNewRegistrationAccountRegister() throws JSONException {
        System.out.println("This Test Verify the Regetration from Account -->Register >>>> ");
        ExtentTest test = report.startTest("This test validates the content / elements in the Login Page.");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }
        test.log(LogStatus.INFO, "Browser Launched");
        test.log(LogStatus.INFO, "Navigated to >>>" + pageUrl);

        commonSeleniumFunctions.isElementVisible(mainPage.accountMenuLocator);
        commonSeleniumFunctions.clickElement(mainPage.accountMenuLocator);
        commonSeleniumFunctions.clickElement(mainPage.accountRegisterLocator);

        commonSeleniumFunctions.waitForPageLoad();

        assertTrueR(test, "Registration Page is not loaded  ", registrationPage.isCurrent());
        test.log(LogStatus.INFO, "Registration Page is sucessfully loaded...");
        assertEqualsR(test, "Title of page is incorrect", "Registration", mainPage.getPageTitle());

        userName = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "userName");
        String email = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "email");
        password = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "password");
        confirmPassword = genericFunctions.convertTestSetString(testData.newUserRegistrationTestData(), "confirmpassword");

        //Login
        assertTrueR(test, "Login Field is not found in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("login"));
        assertTrueR(test, "User Name either Lenght is invalid or not in Lower Case", genericFunctions.isLowerCase(userName));
        registrationPage.enterLogin(userName);
        test.log(LogStatus.INFO, "Registration Page >> Entered New Registration with Login >>>  "+userName);

        //Email
        assertTrueR(test, "Email Entered is not valid  >> Email entered is >>>  " + email, genericFunctions.isValid(email));
        assertTrueR(test, "Email Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("email"));
        registrationPage.enterEmail(email);
        test.log(LogStatus.INFO, "Registration Page >> Entered New Registration with Email >>>  "+email);
        // Password
        assertTrueR(test, "Password Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("password"));
        assertTrueR(test, "Password Entered is not matching to desired Length  ", genericFunctions.checkStringLength(password, 5, 50));
        registrationPage.enterPassword(password);
        test.log(LogStatus.INFO, "Registration Page >> Entered New Registration with Password >>>  "+password);
        //Confirm Password
        assertEquals("Password and Confirmed Password not matching", password, confirmPassword);
        assertTrueR(test, "confrim Password Filed is not present in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("confirmPassword"));
        assertTrueR(test, "Confirm Password Entered is not matching to desired Length  ", genericFunctions.checkStringLength(confirmPassword, 5, 50));
        registrationPage.enterConfirmPassword(confirmPassword);
        test.log(LogStatus.INFO, "Registration Page >> Entered New Registration with New Password Confirm >>>  "+confirmPassword);
        //Click Registration Button
        assertTrueR(test, "Register Button not Enabled in the Registration Page  ", commonSeleniumFunctions.isElementEnabled(registrationPage.registorButtonLocator));
        registrationPage.clickRegisterButton();
        assertTrueR(test, "Login Page is not Loaded", loginPage.isCurrent());
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void ValidateNewRegistrationNoValuesEntered() {
        System.out.println(_log_indicator + _test_indicator + " This test validates the New Registration Functionality with no Values Entered...");
        ExtentTest test = report.startTest("This test validates the New Registration Functionality with no Values Entered");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        commonSeleniumFunctions.clickElement(mainPage.newAccountRegistorLocator);

        assertTrueR(test, "Registration Page is not loaded  ", registrationPage.isCurrent());
        assertEqualsR(test, "Title of page is incorrect", "Registration", mainPage.getPageTitle());
        assertTrueR(test, "Login Filed is not found in the Registration Page  ", commonSeleniumFunctions.checkElementPresent("login"));
        commonSeleniumFunctions.setTextFieldValueFor(registrationPage.loginFieldLocator, String.valueOf(Keys.TAB));
        assertTrueR(test, "Login Required message is not found in the Registration Page  ", commonSeleniumFunctions.isElementVisible(registrationPage.loginReqMessageLocator));
        commonSeleniumFunctions.setTextFieldValueFor(registrationPage.emailFieldLocator, String.valueOf(Keys.TAB));
        assertTrueR(test, "Email Reuired message is not found in the Registration Page  ", commonSeleniumFunctions.isElementVisible(registrationPage.emailReqMessageLocator));
        commonSeleniumFunctions.setTextFieldValueFor(registrationPage.newPasswordFiledLocator, String.valueOf(Keys.TAB));
        assertTrueR(test, "Password Required message is not found in the Registration Page  ", commonSeleniumFunctions.isElementVisible(registrationPage.passwordReqMessageLocator));
        commonSeleniumFunctions.setTextFieldValueFor(registrationPage.newPasswordConfirmationFiledLocator, String.valueOf(Keys.TAB));
        assertTrueR(test, "Confirm Password Required message is not found in the Registration Page  ", commonSeleniumFunctions.isElementVisible(registrationPage.confirmPasswordReqMessageLocator));

        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }


    @Test
    public void ValidateLogInFromRegistrationPage() throws JSONException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the New Registration Functionality by Clicking Link from Login Page...");
        ExtentTest test = report.startTest("This test validates the New Registration Functionality by Clicking Link from Login Page");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        commonSeleniumFunctions.clickElement(mainPage.newAccountRegistorLocator);

        assertTrueR(test, "Registration Page is not loaded  ", registrationPage.isCurrent());
        test.log(LogStatus.INFO, "Registration Page is sucessfully loaded...");
        assertEqualsR(test, "Title of page is incorrect", "Registration", mainPage.getPageTitle());
        mainPage.clickElement(mainPage.loginLinkLocator);
        assertTrueR(test, "Login Page is not Loaded", loginPage.isCurrent());
        assertEqualsR(test, "Title of page is incorrect", "Authentication", mainPage.getPageTitle());

        userName = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "userName");
        password = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "password");

        assertTrueR(test, "User Name Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("username"));
        loginPage.enterUsername(userName);
        test.log(LogStatus.INFO, "Login Page >> Entered Login Successfully : >>>  "+userName);
        assertTrueR(test, "Password Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("password"));
        loginPage.enterPassword(password);
        test.log(LogStatus.INFO, "Login Page >> Entered Password Successfully  : >>>  "+password);
        loginPage.clickAuthenticateButton();
        test.log(LogStatus.INFO, "Authenticate is sucess...");
        commonSeleniumFunctions.waitForPageLoad();
        String ExpectedMessage = "You are logged in as user " + "\"" + userName + "\".";
        assertEqualsR(test, "Main Title of Login page is incorrect", "Welcome to Gurukula!", commonSeleniumFunctions.getMainHeaderText(loginPage.mainTitleLocator));
        assertEqualsR(test, "Main logged message is incorrect", ExpectedMessage, commonSeleniumFunctions.getMainHeaderText(loginPage.mainLoggedmessageLocator));

        assertEqualsR(test, "Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(mainPage.entitiesMenuLocator));

        commonSeleniumFunctions.clickElement(mainPage.entitiesMenuLocator);
        assertEqualsR(test, "Entities/ Branch Menu is not Present in the Home Page", "Branch", commonSeleniumFunctions.getMainHeaderText(mainPage.branchMenuLocator));
        assertEqualsR(test, "Entities/ Staff Menu is not Present in the Home Page", "Staff", commonSeleniumFunctions.getMainHeaderText(mainPage.staffMenuLocator));

        commonSeleniumFunctions.clickElement(mainPage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        mainPage.clickElement(loginPage.signoutLocator);

        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void validateUnAutherizedLogin() throws InterruptedException, JSONException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the Diffrent Error messges of New Registration Functionality...");
        ExtentTest test = report.startTest("This test validates the Diffrent Error messges of New Registration Functionality");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        mainPage.clickElement(mainPage.loginLinkLocator);

        assertTrueR(test, "Login Page is not Loaded", loginPage.isCurrent());
        test.log(LogStatus.INFO, "Login Page is sucessfully loaded...");
        assertEqualsR(test, "Title of page is incorrect", "Authentication", mainPage.getPageTitle());
        test.log(LogStatus.INFO, "Login Titile page is correct...");
        assertTrueR(test, "User Name Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("username"));
        commonSeleniumFunctions.setTextFieldValueFor(loginPage.userNameFieldLocator, "NotValid");
        assertTrueR(test, "Password Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("password"));
        commonSeleniumFunctions.setTextFieldValueFor(loginPage.passwordFiledLocator, "Notvalid");
        test.log(LogStatus.INFO, "Entered Empty Values in User and Password fileds and Clicking Authenticate");
        commonSeleniumFunctions.clickElement(loginPage.authenticateButtonLocator);
        commonSeleniumFunctions.waitForPageLoad();
        assertEqualsR(test, "Authentication Error in Login  page is incorrect", "Authentication failed! Please check your credentials and try again.", commonSeleniumFunctions.getMainHeaderText(loginPage.authenticaionFailMessageLocator));
        test.log(LogStatus.INFO, "Authentication failure Message validation is sucess.. ");

        Thread.sleep(2000);

        test.log(LogStatus.INFO, "All Input Error messages are successfully validated ");

        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void validateErrorMessageTips() throws InterruptedException, JSONException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the Diffrent Error messges of New Registration Functionality...");
        ExtentTest test = report.startTest("This test validates the Diffrent Error messges of New Registration Functionality");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        mainPage.clickElement(mainPage.loginLinkLocator);

        assertTrueR(test, "Login Page is not Loaded", loginPage.isCurrent());
        test.log(LogStatus.INFO, "Login Page is sucessfully loaded...");
        assertEqualsR(test, "Title of page is incorrect", "Authentication", mainPage.getPageTitle());
        test.log(LogStatus.INFO, "Login Titile page is correct...");
        assertTrueR(test, "User Name Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("username"));
        commonSeleniumFunctions.setTextFieldValueFor(loginPage.userNameFieldLocator, "");
        assertTrueR(test, "Password Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("password"));
        commonSeleniumFunctions.setTextFieldValueFor(loginPage.passwordFiledLocator, "");
        test.log(LogStatus.INFO, "Entered Empty Values in User and Password fileds and Clicking Authenticate");
        commonSeleniumFunctions.clickElement(loginPage.authenticateButtonLocator);
        commonSeleniumFunctions.waitForPageLoad();
        assertEqualsR(test, "Authentication Error in Login  page is incorrect", "Authentication failed! Please check your credentials and try again.", commonSeleniumFunctions.getMainHeaderText(loginPage.authenticaionFailMessageLocator));
        test.log(LogStatus.INFO, "Authentication failure Message validation is sucess.. ");
        commonSeleniumFunctions.clickElement(mainPage.newAccountRegistorLocator);
        ArrayList<String> executionKeys = testData.getExecutionOrder();


        test.log(LogStatus.INFO, "All Input Error messages are successfully validated ");

        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    public By locator(String key) {

        if (key.contains("login")) {
            valueToSetToLocator = registrationPage.loginFieldLocator;
        } else if (key.contains("email")) {
            valueToSetToLocator = registrationPage.emailFieldLocator;
        } else if (key.contains("newpassword")) {
            valueToSetToLocator = registrationPage.newPasswordFiledLocator;
        } else if (key.contains("confirmPassword")) {
            valueToSetToLocator = registrationPage.newPasswordConfirmationFiledLocator;
        }

        return valueToSetToLocator;
    }

    @AfterClass
    public static void endTest() {
        report.flush();
    }
}

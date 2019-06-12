package Utils;

import pages.*;
import data.TestData;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Properties;

import static Utils.TestBase.getDriver;
import static org.junit.Assert.assertEquals;

public class ReusableFunctionalities {

    //HomePage mainPage = new HomePage();
    LoginPage loginPage = new LoginPage();
    CommonSeleniumFunctions commonSeleniumFunctions = new CommonSeleniumFunctions();
    HomePage homePage = new HomePage();
    BranchesPage branchesPage = new BranchesPage();
    StaffPage staffPage = new StaffPage();
    static Properties properties;
    RegistrationPage registrationPage = new RegistrationPage();
    static GenericFunctions genericFunctions = new GenericFunctions();
    TestData testData = new TestData();

    String userName, password, confirmPassword, branchName, code, staffName;

    static WebDriver driver = getDriver();
    //static String pageUrl = "http://127.0.0.1:8080/#/";
    static String pageUrl,loginUrl;


    @BeforeClass
    public static void startTest() throws IOException {

        properties =genericFunctions.loadProperties ( );

        pageUrl = properties.getProperty("pageUrl");
    }



    // This is Reusable login function
    public void Login() throws InterruptedException, JSONException, IOException {
        properties =genericFunctions.loadProperties ( );

        pageUrl = properties.getProperty("pageUrl");
        loginUrl=properties.getProperty("loginpageurl");

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }


        if (!driver.getCurrentUrl().equalsIgnoreCase(loginUrl)) {
          //  driver.navigate().to("http://127.0.0.1:8080/#/login");
            driver.navigate().to(loginUrl);
        }

        Assert.assertTrue("Login Page is not Loaded", loginPage.isCurrent());
        assertEquals("Title of page is incorrect", "Authentication", homePage.getPageTitle());

        userName = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "userName");

        Assert.assertTrue("User Name Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("username"));
        loginPage.enterUsername(userName);

        password = genericFunctions.convertTestSetString(testData.adminLoginTestData(), "password");

        Assert.assertTrue("Password Filed in Login Page is not Loaded", commonSeleniumFunctions.checkElementPresent("password"));
        loginPage.enterPassword(password);
        Assert.assertTrue("Automatic Login in the Login Page is not Checked", commonSeleniumFunctions.isCheckBoxChecked(loginPage.remembermeLocator));


        loginPage.clickAuthenticateButton();
        commonSeleniumFunctions.waitForPageLoad();

        String ExpectedMessage = "You are logged in as user " + "\"" + userName + "\".";
        assertEquals("Main Title of Login page is incorrect", "Welcome to Gurukula!", commonSeleniumFunctions.getMainHeaderText(loginPage.mainTitleLocator));
        assertEquals("Main logged message is incorrect", ExpectedMessage, commonSeleniumFunctions.getMainHeaderText(loginPage.mainLoggedmessageLocator));

    }


    // This will create Bracnhes & Staffs data if not exist

    public boolean createBrnchsAndStaffs() throws InterruptedException, JSONException, IOException {
        properties =genericFunctions.loadProperties ( );

        pageUrl = properties.getProperty("pageUrl");
        boolean isCreateBrancesStaffsSucess = false;

        if (!driver.getCurrentUrl().equals(pageUrl)) {
            driver.navigate().to(pageUrl);
        }

        // Test starts here
        System.out.println(">> Creating Branchses...");
        assertEquals("Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(homePage.entitiesMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
        assertEquals("Entities/ Branch Menu is not Present in the Home Page", "Branch", commonSeleniumFunctions.getMainHeaderText(homePage.branchMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.branchMenuLocator);

        commonSeleniumFunctions.waitForPageLoad();
        Assert.assertTrue("Branches Page is not loaded  ", branchesPage.isCurrent());
        assertEquals("Title of page is incorrect", "Branches", commonSeleniumFunctions.getPageTitle());
        Assert.assertTrue("Create new Branch  button in the Branches Page is not loaded  ", commonSeleniumFunctions.isClickable(branchesPage.createButtonlocator));

        // Getting Test data to be created
        JSONArray testDataCount = new JSONArray(testData.newBranchAsArray());

        for (int i = 0; i <= testDataCount.length() - 1; i++) {

            commonSeleniumFunctions.isElementVisible(homePage.entitiesMenuLocator);
            commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
            commonSeleniumFunctions.clickElement(homePage.branchMenuLocator);



            /*if (!driver.getCurrentUrl().equalsIgnoreCase("http://127.0.0.1:8080/#/branch")) {
                driver.navigate().to("http://127.0.0.1:8080/#/branch");
            }*/

            Thread.sleep(5000);
            commonSeleniumFunctions.isElementVisible(branchesPage.createButtonlocator);
            commonSeleniumFunctions.clickElement(branchesPage.createButtonlocator);
            Thread.sleep(2000);
            Assert.assertTrue("Branch Name Field is not found in the Create / Edit Branches Page  ", commonSeleniumFunctions.checkElementPresent("branch.name"));

            branchName = genericFunctions.convertTestSetString(testData.newBranch(), "branchName" + (i + 1));
            Assert.assertTrue("Branches Name either Length / Pattern is not valid  ", genericFunctions.isPatternMatch(branchName, "^[a-zA-Z\\s]*$", 5, 20));
            commonSeleniumFunctions.setTextFieldValueFor(branchesPage.branchNameFiledLocator, branchName);

            code = genericFunctions.convertTestSetString(testData.newBranch(), "code" + (i + 1));

            Assert.assertTrue("Code Name Field is not found in the Create / Edit Branches Page  ", commonSeleniumFunctions.checkElementPresent("branch.code"));
            Assert.assertTrue("Code  either Length / Pattern is not valid  ", genericFunctions.isPatternMatch(code, "^[A-Z0-9]*$", 2, 10));
            commonSeleniumFunctions.setTextFieldValueFor(branchesPage.branchCodeFiledLocator, code);

            System.out.println(">> (util) Branch created is -> " + branchName);
            System.out.println(">> (util) Code created for Branch  >> " + branchName + "  is -> " + code);

            Assert.assertTrue("Save button  in the Create / Edit Page not Enabled  ", commonSeleniumFunctions.isElementEnabled(branchesPage.branchSaveButtonFiledLocator));
            commonSeleniumFunctions.clickElement(branchesPage.branchSaveButtonFiledLocator);
            Thread.sleep(2000);


            // Below steps will create Staffs to the corespoding Branchces
            System.out.println(">> Creating Staffs to the Branch...");

            assertEquals("Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(homePage.entitiesMenuLocator));
            commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
            commonSeleniumFunctions.clickElement(homePage.staffMenuLocator);

            commonSeleniumFunctions.waitForPageLoad();
            Assert.assertTrue("Staff Page is not loaded  ", staffPage.isCurrent());
            assertEquals("Title of page is incorrect", "Staffs", commonSeleniumFunctions.getPageTitle());

            Assert.assertTrue("Create new Staffs  button in the Staffs Page is not loaded  ", commonSeleniumFunctions.isClickable(staffPage.createButtonlocator));


            commonSeleniumFunctions.clickElement(staffPage.createrEdidBranchTittlelocator);

            staffName = genericFunctions.convertTestSetString(testData.newBranch(), "staffName" + (i + 1));
            Assert.assertTrue("Staffs  either Length / Pattern is not valid  ", genericFunctions.isPatternMatch(code, "^[A-Z0-9]*$", 2, 10));
            Assert.assertTrue("Staffs  Namme Filed is not present ", commonSeleniumFunctions.isElementVisible(staffPage.staffNameFiledLocator));
            commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffNameFiledLocator, staffName);


            branchName = genericFunctions.convertTestSetString(testData.newBranch(), "branchName" + (i + 1));
            Assert.assertTrue("Branches Name either Length / Pattern is not valid  ", genericFunctions.isPatternMatch(branchName, "^[a-zA-Z\\s]*$", 5, 20));
            Assert.assertTrue("Staffs Branch Namme DropDown is not present ", commonSeleniumFunctions.isElementVisible(staffPage.branchNameDropDownFiledLocator));
            commonSeleniumFunctions.setTextFieldValueFor(staffPage.branchNameDropDownFiledLocator, branchName);

            Assert.assertTrue("Save button  in the Create / Edit Page not Enabled in Staffs Page   ", commonSeleniumFunctions.isElementEnabled(staffPage.staffSaveButtonFiledLocator));
            staffPage.clickSaveInEditModal();


        }

        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
        commonSeleniumFunctions.clickElement(homePage.branchMenuLocator);
        isCreateBrancesStaffsSucess = true;
        return isCreateBrancesStaffsSucess;
    }


}

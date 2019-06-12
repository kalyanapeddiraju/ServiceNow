package test;

import org.junit.*;
import pages.*;
import Utils.CommonSeleniumFunctions;
import Utils.GenericFunctions;
import Utils.ReusableFunctionalities;
import Utils.TestBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import data.TestData;
import org.json.JSONArray;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class EntitiesWithExtentReport extends TestBase {
    private static WebDriver driver = getDriver();
    private final String _test_indicator = "(test)", _log_indicator = ">> ";
    private ReusableFunctionalities reusableFunctionalities = new ReusableFunctionalities();
    private CommonSeleniumFunctions commonSeleniumFunctions = new CommonSeleniumFunctions();
    private HomePage homePage = new HomePage();
    private LoginPage loginPage = new LoginPage();
    private BranchesPage branchesPage = new BranchesPage();
    private BranchDetailsPage branchDetailsPage = new BranchDetailsPage();
    private StaffsDetailsPage staffsDetailsPage = new StaffsDetailsPage();
    private static GenericFunctions genericFunctions = new GenericFunctions();
    private TestData testData = new TestData();
    private StaffPage staffPage = new StaffPage();
    private String branchName, code, staffName, key;
    //ArrayList rowData;
    private String[][] rowDataBeforeBranches, rowDataAfterBranches, rowDataActiveBranches, rowDataBeforeStaffs, rowDataAfterStaffs, rowDataActiveStaffs;
    private boolean staffDeleteTestsPassed = false;
    private By valueToSetTolocator = By.id("");
    private boolean isCreateBranchesStaffsSuccess = false;
    private String errorMessage;
    private static ExtentReports report;
    static Properties properties;
    static String pageUrl,branchPageUrl,staffPageUrl;

    @BeforeClass
    public static void startTest() throws IOException {
        String FileName="TestReults"+ new GenericFunctions().DateFormatYYYYMMDD();
        //report = new ExtentReports(System.getProperty("user.dir") + File.separator + FileName+".html",false);
        report = new ExtentReports(System.getProperty("user.dir") + File.separator + FileName+".html");
        properties =genericFunctions.loadProperties ( );
        pageUrl = properties.getProperty("pageUrl");
        branchPageUrl=properties.getProperty("branchpageurl");
        staffPageUrl=properties.getProperty("staffpageurl");

    }

   // @Ignore
    @Test
    public void validateEntitiesBranchStaff() throws InterruptedException, JSONException, IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates branch creation features.");
        ExtentTest test = report.startTest("This test validates branch creation features.");
        test.log(LogStatus.INFO, "Test started...");
        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on branch page...");

        reusableFunctionalities.Login();
        test.log(LogStatus.INFO, "Sucessfully Logged into the system...");


        // Test starts here
        System.out.println(_log_indicator + _test_indicator + " Creating Branchses...");
        assertEqualsR(test, "Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(homePage.entitiesMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
        assertEqualsR(test, "Entities/ Branch Menu is not Present in the Home Page", "Branch", commonSeleniumFunctions.getMainHeaderText(homePage.branchMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.branchMenuLocator);

        commonSeleniumFunctions.waitForPageLoad();
        assertTrueR(test, "Branches Page is not loaded  ", branchesPage.isCurrent());
        assertEqualsR(test, "Title of page is incorrect", "Branches", commonSeleniumFunctions.getPageTitle());
        assertTrueR(test, "Create new Branch  button in the Branches Page is not loaded  ", commonSeleniumFunctions.isClickable(branchesPage.createButtonlocator));
        test.log(LogStatus.INFO, "Branch Page Successfully Loaded >>> ");
        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        test.log(LogStatus.INFO, "Creating branch data ");
        test.log(LogStatus.INFO, "Branch count Before Creation >>>   :" +rowDataBeforeBranches);
        // Getting Test data to be created
        JSONArray testDataCount = new JSONArray(testData.newBranchAsArray());

        for (int i = 0; i <= testDataCount.length() - 1; i++) {

            if (!driver.getCurrentUrl().equalsIgnoreCase(branchPageUrl)) {
                driver.navigate().to(branchPageUrl);
            }
            commonSeleniumFunctions.clickElement(branchesPage.createButtonlocator);
            Thread.sleep(2000);
            assertTrueR(test, "Branch Name Field is not found in the Create / Edit Branches Page  ", commonSeleniumFunctions.checkElementPresent("branch.name"));
            branchName = genericFunctions.convertTestSetString(testData.newBranch(), "branchName" + (i + 1));
            assertTrueR(test, "Branches Name either Length / Pattern is not valid  ", genericFunctions.isPatternMatch(branchName, "^[a-zA-Z\\s]*$", 5, 20));
            commonSeleniumFunctions.setTextFieldValueFor(branchesPage.branchNameFiledLocator, branchName);
            code = genericFunctions.convertTestSetString(testData.newBranch(), "code" + (i + 1));
            assertTrueR(test, "Code Name Field is not found in the Create / Edit Branches Page  ", commonSeleniumFunctions.checkElementPresent("branch.code"));
            assertTrueR(test, "Code  either Length / Pattern is not valid  ", genericFunctions.isPatternMatch(code, "^[A-Z0-9]*$", 2, 10));
            commonSeleniumFunctions.setTextFieldValueFor(branchesPage.branchCodeFiledLocator, code);
            Thread.sleep(4000);
            assertTrueR(test, "Save button  in the Create / Edit Page not Enabled  ", commonSeleniumFunctions.isElementEnabled(branchesPage.branchSaveButtonFiledLocator));
            commonSeleniumFunctions.clickElement(branchesPage.branchSaveButtonFiledLocator);
            test.log(LogStatus.INFO, "Branch Created Successfully with  >>>   Branch Name :" +branchName + "--- Code is : " + code);
            System.out.println(_log_indicator + _test_indicator + " Branch created is -> " + branchName);
            System.out.println(_log_indicator + _test_indicator + " Code created for Branch  >> " + branchName + "  is -> " + code);
            Thread.sleep(2000);
            // Getting row count after each save of Branch
            rowDataActiveBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
            // To Check if the Branch is created with value we sent
            String[] lastRow = rowDataActiveBranches[rowDataActiveBranches.length - 1];
            assertEqualsR(test, "Branch Name is mismatch  with value entered", branchName, lastRow[1]);
            assertEqualsR(test, "Code is mismatch with value entered", code, lastRow[2]);
            assertEqualsR(test, "View / Edit / Delete Buttons are present", " View  Edit  Delete", lastRow[3]);
            // Below steps will create Staffs to the corespoding Branchces
            System.out.println(_log_indicator + _test_indicator + " Creating Staffs to the Branch...");
            assertEqualsR(test, "Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(homePage.entitiesMenuLocator));
            commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
            commonSeleniumFunctions.clickElement(homePage.staffMenuLocator);
            test.log(LogStatus.INFO, "Creating Staffs data ");
            test.log(LogStatus.INFO, "Staff count Before Creating >>>   :" +rowDataBeforeStaffs);
            commonSeleniumFunctions.waitForPageLoad();
            assertTrueR(test, "Staff Page is not loaded  ", staffPage.isCurrent());
            assertEqualsR(test, "Title of page is incorrect", "Staffs", commonSeleniumFunctions.getPageTitle());
            assertTrueR(test, "Create new Staffs  button in the Staffs Page is not loaded  ", commonSeleniumFunctions.isClickable(staffPage.createButtonlocator));
            commonSeleniumFunctions.clickElement(staffPage.createrEdidBranchTittlelocator);
            staffName = genericFunctions.convertTestSetString(testData.newBranch(), "staffName" + (i + 1));
            assertTrueR(test, "Staffs  either Length / Pattern is not valid  ", genericFunctions.isPatternMatch(code, "^[A-Z0-9]*$", 2, 10));
            assertTrueR(test, "Staffs  Name Filed is not present ", commonSeleniumFunctions.isElementVisible(staffPage.staffNameFiledLocator));
            commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffNameFiledLocator, staffName);
            branchName = genericFunctions.convertTestSetString(testData.newBranch(), "branchName" + (i + 1));

            //assertTrueR(test, "Branches Name either Length / Pattern is not valid  ", genericFunctions.isPatternMatch(branchName, "^[a-zA-Z\\s]*$", 5, 20));
            assertTrueR(test, "Staff Branch Namme Filed is not present ", commonSeleniumFunctions.isElementVisible(staffPage.branchNameDropDownFiledLocator));
            commonSeleniumFunctions.setTextFieldValueFor(staffPage.branchNameDropDownFiledLocator, branchName);

            test.log(LogStatus.INFO, "Staff Created Created Successfully with  >>>   Staff Name :" +staffName + "--- Branch  is : " + branchName);

            assertTrueR(test, "Save button  in the Create / Edit Page not Enabled in Staffs Page   ", commonSeleniumFunctions.isElementEnabled(staffPage.staffSaveButtonFiledLocator));
            commonSeleniumFunctions.clickElement(staffPage.staffSaveButtonFiledLocator);

            Thread.sleep(2000);
            rowDataActiveStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);

            // To Check if the Staff is created with value we sent
            lastRow = rowDataActiveStaffs[rowDataActiveBranches.length - 1];
            assertEqualsR(test, "Staff Name is mismatch  with value entered", staffName, lastRow[1]);
            assertEqualsR(test, "Code is mismatch with value entered", branchName, lastRow[2]);
            assertEqualsR(test, "View / Edit / Delete Buttons are present", " View  Edit  Delete", lastRow[3]);
        }
        rowDataAfterStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        if (!driver.getCurrentUrl().equalsIgnoreCase(branchPageUrl)) {
            driver.navigate().to(branchPageUrl);
        }
        rowDataAfterBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);

        test.log(LogStatus.INFO, "Branch And Staff Data Created Successfully ");
        test.log(LogStatus.INFO, "Branch count After Creation >>>   :" +rowDataAfterBranches);
        test.log(LogStatus.INFO, "Staffs count After Creation >>>   :" +rowDataAfterStaffs);

        validateTableContent(rowDataBeforeStaffs, rowDataAfterStaffs);
        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
        commonSeleniumFunctions.clickElement(homePage.branchMenuLocator);
        validateTableContent(rowDataBeforeBranches, rowDataAfterBranches);

        homePage.clickElement(homePage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");

        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void validateInteractionsForABranch() throws JSONException, InterruptedException,IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the interactive buttons available for an existing branch (except delete).");
        ExtentTest test = report.startTest("This test validates the interactive buttons available for an existing branch (except delete).");
        test.log(LogStatus.INFO, "Test started...");

        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on branch page...");
        reusableFunctionalities.Login();

        if (!driver.getCurrentUrl().equalsIgnoreCase(branchPageUrl)){
            driver.navigate().to(branchPageUrl);
        }
        assertTrueR(test, "Branch page did not load", branchesPage.isCurrent());
        test.log(LogStatus.INFO, "Branch Page is loaded successfully ");

        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        if (rowDataBeforeBranches == null) {
            System.out.println(_log_indicator + _test_indicator + " No branches exist in the system, creating a new branch to continue...");
            reusableFunctionalities.createBrnchsAndStaffs();
        }

        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        assertTrueR(test, "No branch exists for running this test", rowDataBeforeBranches.length > 0);

        String[][] newValues = testData.editBranchAsArray();
        int newValueIndex = 0;
        // test starts here (we assume we have a branch now
        for (int i = rowDataBeforeBranches.length - newValues.length; i < rowDataBeforeBranches.length; i++) {
            System.out.println(_log_indicator + _test_indicator + " Validating buttons for row " + i);
            String currentBranchId = rowDataBeforeBranches[i][0];
            String currentBranchName = rowDataBeforeBranches[i][1];
            String currentBranchCode = rowDataBeforeBranches[i][2];
            String newValueName = newValues[newValueIndex][0];
            String newValueCode = newValues[newValueIndex][1];
            newValueIndex++;
            // 1. ensure buttons are clickable
            assertTrueR(test, "View button is not clickable for row " + i, branchesPage.isOptionClickableAtIndex(i, branchesPage.branchOptionViewLocator));
            assertTrueR(test, "Edit button is not clickable for row " + i, branchesPage.isOptionClickableAtIndex(i, branchesPage.branchOptionEditLocator));
            test.log(LogStatus.INFO, "Sucessfully validated if view & Edit Buttons are clickable in the Branch Details Page ");
            // 2a. validate button behavior -> view button
            test.log(LogStatus.INFO, "Checking the View Model functionality in the Branch Details page >>> ");
            branchesPage.clickOptionAtIndex(i, branchesPage.branchOptionViewLocator);
            assertTrueR(test, "Branch detail page did not load", branchDetailsPage.isCurrent());
            assertTrueR(test, "Branch detail page heading is incorrect", branchDetailsPage.isHeadingCorrect(Integer.valueOf(currentBranchId)));
            assertTrueR(test, "Branch name is enabled", branchDetailsPage.getBranchNameFieldEnabled());
            assertTrueR(test, "Branch code is enabled", branchDetailsPage.getBranchCodeFieldEnabled());
            assertEqualsR(test, "Branch name is incorrect", currentBranchName, branchDetailsPage.getBranchNameFieldValue());
            assertEqualsR(test, "Branch code is incorrect", currentBranchCode, branchDetailsPage.getBranchCodeFieldValue());
            test.log(LogStatus.INFO, "Validation in the  Branch View model is successful >>> ");

            branchDetailsPage.clickBackButton();
            test.log(LogStatus.INFO, "Validation in the  Branch View model is successful >>> ");
            test.log(LogStatus.INFO, "Checking the Edit model functionality in the  Branch Edit model >>> ");
            Thread.sleep(2000);
            // 2b. validate button behavior -> edit button -> checking form state in modal
            branchesPage.clickOptionAtIndex(i, branchesPage.branchOptionEditLocator);
            assertTrueR(test, "Branch edit modal did not open", branchesPage.isBranchEditModalDisplayed());
            assertFalseR(test, "Branch edit modal has enabled Id field", branchesPage.isBranchEditFieldEnabled(branchesPage.branchEditIdFieldLocator));
            assertTrueR(test, "Branch edit modal has disabled Name field", branchesPage.isBranchEditFieldEnabled(branchesPage.branchEditNameFieldLocator));
            assertTrueR(test, "Branch edit modal has disabled Code field", branchesPage.isBranchEditFieldEnabled(branchesPage.branchEditCodeFieldLocator));
            assertTrueR(test, "Save button is not enabled when no changes are made", branchesPage.isSaveButtonEnabledInModal());
            assertTrueR(test, "Cancel button is not enabled when no changes are made", branchesPage.isCancelButtonEnabledInModal());
            // 2b. validate button behavior -> edit button -> checking auto-populated values
            assertEqualsR(test, "Branch edit modal has incorrect Id field value", currentBranchId, branchesPage.getBranchEditFieldValue(branchesPage.branchEditIdFieldLocator));
            assertEqualsR(test, "Branch edit modal has incorrect Name field value", currentBranchName, branchesPage.getBranchEditFieldValue(branchesPage.branchEditNameFieldLocator));
            assertEqualsR(test, "Branch edit modal has incorrect Code field value", currentBranchCode, branchesPage.getBranchEditFieldValue(branchesPage.branchEditCodeFieldLocator));
            // 2b. validate button behavior -> edit button -> editing field values
            commonSeleniumFunctions.clearText(branchesPage.branchEditNameFieldLocator);
            Thread.sleep(2000);
            assertFalseR(test, "Save button is enabled when name is cleared", branchesPage.isSaveButtonEnabledInModal());
            assertTrueR(test, "Cancel button is not enabled when name is cleared", branchesPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.setTextFieldValueFor(branchesPage.branchEditNameFieldLocator, newValueName);
            assertTrueR(test, "Save button is not enabled when name value is put back in", branchesPage.isSaveButtonEnabledInModal());
            assertTrueR(test, "Cancel button is not enabled when name value is put back in", branchesPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.clearText(branchesPage.branchEditCodeFieldLocator);
            assertFalseR(test, "Save button is enabled when code is cleared", branchesPage.isSaveButtonEnabledInModal());
            assertTrueR(test, "Cancel button is not enabled when code is cleared", branchesPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.setTextFieldValueFor(branchesPage.branchEditCodeFieldLocator, newValueCode);
            assertTrueR(test, "Save button is not enabled when valid changes are made", branchesPage.isSaveButtonEnabledInModal());
            assertTrueR(test, "Cancel button is not enabled when valid changes are made", branchesPage.isCancelButtonEnabledInModal());
            branchesPage.clickSaveInEditModal();
            assertTrueR(test, "Branch edit modal did not close", branchesPage.isBranchEditModalNotDisplayed());
            // 2b. validate button behavior -> edit button -> editing field values -> checking if values have been updated
            assertTrueR(test, "Branch list page did not load", branchesPage.isCurrent());
            String[][] updatedRows = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
            String updatedName = updatedRows[i][1];
            String updatedCode = updatedRows[i][2];
            assertEqualsR(test, "New branch name did not get saved after edit", newValueName, updatedName);
            assertEqualsR(test, "New branch code did not get saved after edit", newValueCode, updatedCode);
            test.log(LogStatus.INFO, "Validation in the  Branch Edit model is successful >>> ");

        }


        homePage.clickElement(homePage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }
    @Test
    public void validateInteractionsForStaffs() throws JSONException, InterruptedException ,IOException{
        System.out.println(_log_indicator + _test_indicator + " This test validates the interactive buttons available for an existing Staffs (except delete).");
        ExtentTest test = report.startTest("This test validates the interactive buttons available for an existing Staffs (except delete).");
        test.log(LogStatus.INFO, "Test started...");

        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on Staff page...");
        reusableFunctionalities.Login();
        if (!driver.getCurrentUrl().equalsIgnoreCase(staffPageUrl)) {
            driver.navigate().to(staffPageUrl);
        }
        assertTrueR(test, "Staff page did not load", staffPage.isCurrent());
        test.log(LogStatus.INFO, "Branch Page is loaded successfully ");

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        if (rowDataBeforeStaffs == null) {
            System.out.println(_log_indicator + _test_indicator + " No Staffs exist in the system, creating a new branch / Staffs to continue...");
            isCreateBranchesStaffsSuccess = reusableFunctionalities.createBrnchsAndStaffs();
        }

        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
        commonSeleniumFunctions.clickElement(homePage.staffMenuLocator);

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        assertTrueR(test, "No Staffs exists for running this test", rowDataBeforeStaffs.length > 0);

        String[][] newValues = testData.editStaffsAsArray();
        int newValueIndex = 0;
        // test starts here (we assume we have a branch now
        for (int i = rowDataBeforeStaffs.length - newValues.length; i < rowDataBeforeStaffs.length; i++) {
            System.out.println(_log_indicator + _test_indicator + " Validating buttons for row " + i);
            String currentStaffId = rowDataBeforeStaffs[i][0];
            String currentStaffsName = rowDataBeforeStaffs[i][1];
            String currentStaffBranchName = rowDataBeforeStaffs[i][2];
            String newValueStaffName = newValues[newValueIndex][0];
            String newValueBranchName = newValues[newValueIndex][1];
            newValueIndex++;
            // 1. ensure buttons are clickable
            assertTrueR(test, "View button is not clickable for row " + i, staffPage.isOptionClickableAtIndex(i, staffPage.staffOptionViewLocator));
            assertTrueR(test, "Edit button is not clickable for row " + i, staffPage.isOptionClickableAtIndex(i, staffPage.staffOptionEditLocator));
            test.log(LogStatus.INFO, "Sucessfully validated if view & Edit Buttons are clickable in the Staffs Details Page ");
            // 2a. validate button behavior -> view button
            test.log(LogStatus.INFO, "Checking the View Model functionality in the Staffs Details page >>> ");
            staffPage.clickOptionAtIndex(i, staffPage.staffOptionViewLocator);
            assertTrueR(test, "Staff detail page did not load", staffsDetailsPage.isCurrent());
            assertTrueR(test, "Staff detail page heading is incorrect", staffsDetailsPage.isHeadingCorrect(Integer.valueOf(currentStaffId)));
            assertTrueR(test, "Staff name is enabled", staffsDetailsPage.getStaffNameFieldEnabled());
            assertTrueR(test, "Staff Branch Name is enabled", staffsDetailsPage.getBranchNameFieldEnabled());
            assertEqualsR(test, "Staff name is incorrect", currentStaffsName, staffsDetailsPage.getStaffNameFieldValue());
            assertEqualsR(test, "Staff Branch Name is incorrect", currentStaffBranchName, staffsDetailsPage.getBranchNameFieldValue());
            staffsDetailsPage.clickBackButton();
            test.log(LogStatus.INFO, "Validation in the  Staffs View model is successful >>> ");
            test.log(LogStatus.INFO, "Checking the Edit model functionality in the  Staffs Edit model >>> ");
            // 2b. validate button behavior -> edit button -> checking form state in modal
            staffPage.clickOptionAtIndex(i, staffPage.staffOptionEditLocator);
            assertTrueR(test, "Staff edit modal did not open", staffPage.isStaffEditModalDisplayed());
            assertFalseR(test, "Staff edit modal has enabled Id field", staffPage.isStaffEditFieldEnabled(staffPage.staffEditIdFieldLocator));
            assertTrueR(test, "Staff edit modal has disabled Name field", staffPage.isStaffEditFieldEnabled(staffPage.staffEditNameFieldLocator));
            assertTrueR(test, "Staff edit modal has disabled Branch Name DropDown ", staffPage.isStaffEditFieldEnabled(staffPage.staffEditBranchNameDropDownLocator));
            assertTrueR(test, "Save button is not enabled when no changes are made", staffPage.isSaveButtonEnabledInModal());
            assertTrueR(test, "Cancel button is not enabled when no changes are made", staffPage.isCancelButtonEnabledInModal());
            // 2b. validate button behavior -> edit button -> checking auto-populated values
            assertEqualsR(test, "Staff edit modal has incorrect Id field value", currentStaffId, staffPage.getStaffEditFieldValue(staffPage.staffEditIdFieldLocator));
            assertEqualsR(test, "Staff edit modal has incorrect Staff Name field value", currentStaffsName, staffPage.getStaffEditFieldValue(staffPage.staffEditNameFieldLocator));
            assertEqualsR(test, "Staff edit modal has incorrect Branch Name DropDown value", currentStaffBranchName, staffPage.getStaffEditDropDownValue(staffPage.staffEditBranchNameDropDownLocator));
            // 2b. validate button behavior -> edit button -> editing field values
            commonSeleniumFunctions.clearText(staffPage.staffEditNameFieldLocator);
            Thread.sleep(2000);
            assertFalseR(test, "Save button is enabled when name is cleared", staffPage.isSaveButtonEnabledInModal());
            assertTrueR(test, "Cancel button is not enabled when name is cleared", staffPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffEditNameFieldLocator, newValueStaffName);
            assertTrueR(test, "Save button is not enabled when Staff name value is put back in", staffPage.isSaveButtonEnabledInModal());
            assertTrueR(test, "Cancel button is not enabled when Staff name value is put back in", staffPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffEditBranchNameDropDownLocator, newValueBranchName);
            assertTrueR(test, "Save button is not enabled when valid changes are made", staffPage.isSaveButtonEnabledInModal());
            assertTrueR(test, "Cancel button is not enabled when valid changes are made", staffPage.isCancelButtonEnabledInModal());
            staffPage.clickSaveInEditModal();
            assertTrueR(test, "Staff edit modal did not close", staffPage.isStaffEditModalNotDisplayed());
            // 2b. validate button behavior -> edit button -> editing field values -> checking if values have been updated
            assertTrueR(test, "Staff list page did not load", staffPage.isCurrent());
            String[][] updatedRows = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
            String updatedStaffName = updatedRows[i][1];
            String updatedStaffBranchName = updatedRows[i][2];
            assertEqualsR(test, "New branch name did not get saved after edit", newValueStaffName, updatedStaffName);
            assertEqualsR(test, "New branch code did not get saved after edit", newValueBranchName, updatedStaffBranchName);
            test.log(LogStatus.INFO, "Validation in the  Staffs Edit model is successful >>> ");

        }

        homePage.clickElement(homePage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }
    @Test
    public void validateBranchStaffsDeleteFunctionality() throws JSONException, InterruptedException ,IOException{
        System.out.println(_log_indicator + _test_indicator + " This test validates the delete button available for an existing branch.");
        ExtentTest test = report.startTest("This test validates the delete button available for an existing branch.");
        test.log(LogStatus.INFO, "Test started...");

        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on Staff page...");
        reusableFunctionalities.Login();
        if (!driver.getCurrentUrl().equalsIgnoreCase(staffPageUrl)) {
            driver.navigate().to(staffPageUrl);
        }
        assertTrueR(test, "Staff page did not load", staffPage.isCurrent());
        test.log(LogStatus.INFO, "Staff Page loaded successfully >>> ");


        test.log(LogStatus.INFO, "Checking Staff Delete Model functionality >>> ");

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        if (rowDataBeforeStaffs == null) {
            isCreateBranchesStaffsSuccess = reusableFunctionalities.createBrnchsAndStaffs();
        } else {
            isCreateBranchesStaffsSuccess = true;
        }

        test.log(LogStatus.INFO, "Staff Count before deletting is >>>   "+ rowDataBeforeStaffs.length);

        if (isCreateBranchesStaffsSuccess) {
            for (int i = 0; i < rowDataBeforeStaffs.length; i++) {
                assertTrueR(test, "Delete option is not enabled for row " + 0, staffPage.isOptionClickableAtIndex(0, staffPage.staffOptionDeleteLocator));
                commonSeleniumFunctions.isElementVisible(staffPage.staffDeleteButtonLocator);
                staffPage.clickOptionAtIndex(0, staffPage.staffOptionDeleteLocator);
                String currentStaffId = rowDataBeforeStaffs[i][0];

                assertEqualsR(test, "Delete Staff Title of page is incorrect", "Confirm delete operation", commonSeleniumFunctions.getMainHeaderText(staffPage.staffDelelteLabelLocator));
                assertEqualsR(test, "Delete Staff Title of page is incorrect", "Are you sure you want to delete Staff " + currentStaffId + "?", commonSeleniumFunctions.getMainHeaderText(staffPage.staffDeleltequestionLocator));
                //assertTrue("Staff Delete questionis incorrect", staffsDetailsPage.isHeadingCorrect(Integer.valueOf(currentStaffId)));
                assertTrueR(test, "Delete button is not enabled in the model", staffPage.isStaffDeleteButtonEnabledInModal());
                assertTrueR(test, "Cancel button is not enabled in the model", staffPage.isStaffDeleteCancelButtonEnabledInModal());
                System.out.println("Deleting Staffs >>>>>   Staff " + currentStaffId);
                commonSeleniumFunctions.clickElement(staffPage.staffDeleteButtonLocator);
                commonSeleniumFunctions.isElementVisible(staffPage.staffTableLocator);
                String[][] updatedRows = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
                assertEqualsR(test, "Number of records did not reduce by 1", rowDataBeforeStaffs.length - (i + 1), updatedRows != null ? updatedRows.length : 0);

            }
            staffDeleteTestsPassed = true;
        } else {

            System.out.println(_log_indicator + _test_indicator + " No Staffs to delete >>>>.");
            staffDeleteTestsPassed = true;
        }
        test.log(LogStatus.INFO, "Staff Delected Successfully");

        if (staffDeleteTestsPassed) {
            if (!driver.getCurrentUrl().equalsIgnoreCase(branchPageUrl)) {
                driver.navigate().to("http://127.0.0.1:8080/#/branch");
            }
            assertTrueR(test, "Branch page did not load", branchesPage.isCurrent());

            test.log(LogStatus.INFO, "Branch Page loaded successfully >>> ");
            test.log(LogStatus.INFO, "Checking Branch Delete Model functionality >>> ");
            rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
            test.log(LogStatus.INFO, "Branch Count before deletting is >>>   "+ rowDataBeforeBranches.length);


            if (rowDataBeforeBranches != null) {
                for (int i = 0; i < rowDataBeforeBranches.length; i++) {
                    assertTrueR(test, "Delete option is not enabled for row " + 0, branchesPage.isOptionClickableAtIndex(0, branchesPage.branchOptionDeleteLocator));
                    commonSeleniumFunctions.isElementVisible(branchesPage.branchDeleteButtonLocator);
                    branchesPage.clickOptionAtIndex(0, branchesPage.branchOptionDeleteLocator);
                    String currentStaffId = rowDataBeforeBranches[i][0];
                    assertEqualsR(test, "Delete Branch Title of page is incorrect", "Confirm delete operation", commonSeleniumFunctions.getMainHeaderText(branchesPage.branchDelelteLabelLocator));
                    assertEqualsR(test, "Delete Branch Title of page is incorrect", "Are you sure you want to delete Branch " + currentStaffId + "?", commonSeleniumFunctions.getMainHeaderText(branchesPage.branchDeleltequestionLocator));
                    assertTrueR(test, "Delete button is not enabled in the model", branchesPage.isBranchDeleteButtonEnabledInModal());
                    assertTrueR(test, "Cancel button is not enabled in the model", branchesPage.isBranchDeleteCancelButtonEnabledInModal());
                    System.out.println("Deleting Branches >>>>>   Branch " + currentStaffId);
                    branchesPage.clickDeleteButton();
                    String[][] updatedRows = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
                    assertEqualsR(test, "Number of records did not reduce by 1", rowDataBeforeBranches.length - (i + 1), updatedRows != null ? updatedRows.length : 0);
                }
            } else {
                System.out.println(_log_indicator + _test_indicator + " Staff deletion test failed. Branch delete test cannot be run.");
            }
        } else {
            System.out.println(_log_indicator + _test_indicator + " No Branches to delete >>>>.");
        }
        test.log(LogStatus.INFO, "Branch Delete Model functionality is successfully >>> ");

        homePage.clickElement(homePage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void validateBranchSearchFunctionality() throws InterruptedException, JSONException,IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the search functionality for branches.");
        ExtentTest test = report.startTest("This test validates the search functionality for branches.");
        test.log(LogStatus.INFO, "Test started...");

        reusableFunctionalities.Login();

        assertEqualsR(test, "Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(homePage.entitiesMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);

        assertEqualsR(test, "Entities/ Branch Menu is not Present in the Home Page", "Branch", commonSeleniumFunctions.getMainHeaderText(homePage.branchMenuLocator));

        commonSeleniumFunctions.clickElement(homePage.branchMenuLocator);
        commonSeleniumFunctions.waitForPageLoad();

        assertTrueR(test, "Branches Page is not loaded  ", branchesPage.isCurrent());
        test.log(LogStatus.INFO, "Brances Page Loaded Successfully ");
        assertEqualsR(test, "Title of page is incorrect", "Branches", commonSeleniumFunctions.getPageTitle());
        assertTrueR(test, "Branches Page is not loaded  ", commonSeleniumFunctions.isClickable(branchesPage.createButtonlocator));

        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);

        if (rowDataBeforeBranches == null) {
            System.out.println("No Branches to search adding Branches....");
            isCreateBranchesStaffsSuccess = reusableFunctionalities.createBrnchsAndStaffs();

        } else {
            isCreateBranchesStaffsSuccess = true;
        }

        test.log(LogStatus.INFO, "Bracnh Model Verifying Seraching functionality ");
        if (isCreateBranchesStaffsSuccess) {
            if (!driver.getCurrentUrl().equalsIgnoreCase("http://localhost:8080/#/branch")) {
                driver.navigate().to("http://127.0.0.1:8080/#/branch");
            }
            assertTrueR(test, "SearchQuery Filed is not found in Brachces Page ", commonSeleniumFunctions.checkElementPresent(branchesPage.brachSeachLocator));

            JSONArray testDataCount = new JSONArray(testData.newBranchAsArray());

            for (int i = 0; i <= testDataCount.length() - 1; i++) {

                branchName = genericFunctions.convertTestSetString(testData.newBranch(), "branchName" + (i + 1));
                code = genericFunctions.convertTestSetString(testData.newBranch(), "code" + (i + 1));

                commonSeleniumFunctions.clearText(branchesPage.brachSeachLocator);

                commonSeleniumFunctions.setTextFieldValueFor(branchesPage.brachSeachLocator, branchName);
                assertTrueR(test, "SearchQuery Button is either not found / Loded in Brachces Page ", commonSeleniumFunctions.checkElementPresent(branchesPage.brachQuerySearchButtonLocator));
                commonSeleniumFunctions.clickElement(branchesPage.brachQuerySearchButtonLocator);

                validateBranchSearchResults(test);

                commonSeleniumFunctions.clearText(branchesPage.brachSeachLocator);
                commonSeleniumFunctions.setTextFieldValueFor(branchesPage.brachSeachLocator, code);
                assertTrueR(test, "SearchQuery Button is either not found / Loded in Brachces Page ", commonSeleniumFunctions.checkElementPresent(branchesPage.brachQuerySearchButtonLocator));
                commonSeleniumFunctions.clickElement(branchesPage.brachQuerySearchButtonLocator);

                validateBranchSearchResults(test);

            }

        }
        test.log(LogStatus.INFO, "Bracnh Model-- Successfully Verified Seraching functionality  By Branch Name & Branch Code");
        homePage.clickElement(homePage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void validateStaffsSearchFunctionality() throws InterruptedException, JSONException, IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the search functionality for Staffs.");
        ExtentTest test = report.startTest("This test validates the search functionality for staffs.");
        test.log(LogStatus.INFO, "Test started...");

        ReusableFunctionalities reusableFunctionalities = new ReusableFunctionalities();
        reusableFunctionalities.Login();

        assertEqualsR(test, "Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(homePage.entitiesMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);

        assertEqualsR(test, "Entities/ Staff Menu is not Present in the Home Page", "Staff", commonSeleniumFunctions.getMainHeaderText(homePage.staffMenuLocator));

        commonSeleniumFunctions.clickElement(homePage.staffMenuLocator);
        commonSeleniumFunctions.waitForPageLoad();
        test.log(LogStatus.INFO, "Staff Model Loaded Successfully");
        assertTrueR(test, "Staffs Page is not loaded  ", staffPage.isCurrent());
        assertEqualsR(test, "Title of page is incorrect", "Staffs", commonSeleniumFunctions.getPageTitle());
        commonSeleniumFunctions.isElementVisible(staffPage.createButtonlocator);
        assertTrueR(test, "Staffs Page is not loaded  ", commonSeleniumFunctions.isClickable(staffPage.createButtonlocator));
        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);

        if (rowDataBeforeStaffs == null) {
            System.out.println("No Staffs to search adding Staffs....");
            isCreateBranchesStaffsSuccess = reusableFunctionalities.createBrnchsAndStaffs();
        } else {
            isCreateBranchesStaffsSuccess = true;
        }

        test.log(LogStatus.INFO, "Staff Model Verifying Seraching functionality ");

        if (isCreateBranchesStaffsSuccess) {
            if (!driver.getCurrentUrl().equalsIgnoreCase(staffPageUrl) ){
                driver.navigate().to(staffPageUrl);
            }

            assertTrueR(test, "SearchQuery Filed is not found in Staffs Page ", commonSeleniumFunctions.checkElementPresent(staffPage.staffSearchFiledLocator));

            String[][] existingData = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);

            for (int i = 0; i <= (existingData.length > 3 ? 3 : existingData.length) - 1; i++) {

                staffName = genericFunctions.convertTestSetString(testData.newBranch(), "staffName" + (i + 1));
                branchName = genericFunctions.convertTestSetString(testData.newBranch(), "branchName" + (i + 1));

                staffName = existingData[i][1];
                branchName = existingData[i][2];

                commonSeleniumFunctions.clearText(staffPage.staffSearchFiledLocator);

                commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffSearchFiledLocator, staffName);
                assertTrueR(test, "SearchQuery Button is either not found / Loded in Staffs Page ", commonSeleniumFunctions.checkElementPresent(staffPage.staffQuerySearchButtonLocator));
                commonSeleniumFunctions.clickElement(staffPage.staffQuerySearchButtonLocator);

                validateStaffSearchResults(staffName, branchName,test);

                commonSeleniumFunctions.clearText(staffPage.staffSearchFiledLocator);
                commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffSearchFiledLocator, branchName);
                assertTrueR(test, "SearchQuery Button is either not found / Loded in Staff Page ", commonSeleniumFunctions.checkElementPresent(staffPage.staffQuerySearchButtonLocator));
                commonSeleniumFunctions.clickElement(staffPage.staffQuerySearchButtonLocator);

                validateStaffSearchResults(staffName, branchName,test);

            }

        }

        test.log(LogStatus.INFO, "Staff Model-- Successfully Verified Seraching functionality  By Staff Name & Branch Name");

        homePage.clickElement(homePage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void validateBranchEditModelErrorMessageTips() throws InterruptedException, JSONException,IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the different error Messages in Branch Edit Model...");
        ExtentTest test = report.startTest("This test validates the different error Messages in Branch Edit Model...");
        test.log(LogStatus.INFO, "Test started...");

        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on branch page...");
        reusableFunctionalities.Login();
        if (!driver.getCurrentUrl().equalsIgnoreCase("http://127.0.0.1:8080/#/branch")) {
            driver.navigate().to("http://127.0.0.1:8080/#/branch");
        }
        assertTrueR(test, "Branch page did not load", branchesPage.isCurrent());

        test.log(LogStatus.INFO, "Bracnh Model Successfully Loaded ");
        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        if (rowDataBeforeBranches == null) {
            System.out.println(_log_indicator + _test_indicator + " No branches exist in the system, creating a new branch to continue...");
            reusableFunctionalities.createBrnchsAndStaffs();
        }

        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        assertTrueR(test, "No branch exists for running this test", rowDataBeforeBranches.length > 0);


        branchesPage.clickOptionAtIndex(1, branchesPage.branchOptionEditLocator);
        assertTrueR(test, "Branch edit modal did not open", branchesPage.isBranchEditModalDisplayed());

        test.log(LogStatus.INFO, "Bracnh Edit Modeil Verifying Input Error Meesage for filed Branch Name and Branch Code ");
        ArrayList<String> executionKeys = testData.getBranchModelExecutionOrder();
        String S = "This field should follow pattern ^[a-zA-Z" + "\\s]*$";

        for (int i = 0; i < executionKeys.size(); i++) {
            key = executionKeys.get(i);

            commonSeleniumFunctions.clearText(locator(key));
            commonSeleniumFunctions.setTextFieldValueFor(locator(key), genericFunctions.convertTestSetString(testData.fieldErrorValuesBranchModel(), key));

            By locator = branchesPage.getLocatorNgValue(genericFunctions.convertTestSetString(testData.fieldLocatorBranchModel(), key));

            if (key.equals("branchNamePattern")) {
                errorMessage = "This field should follow pattern ^[a-zA-Z" + "\\s]*$.";
            } else {
                errorMessage = genericFunctions.convertTestSetString(testData.fieldErrorMessagesBranchModel(), key);
            }
            assertEqualsR(test, key.toUpperCase() + "  >>>>  Error Message is incorrect", errorMessage, commonSeleniumFunctions.getMainHeaderText(locator));

            test.log(LogStatus.INFO, "Bracnh Edit Modeil - Validation  for  "+ key  + "  is success");
            test.log(LogStatus.INFO, "Bracnh Edit Modeil - Validation  for  "+ key  + "  is success /n" +
                    "Actual Messange :   /n" +commonSeleniumFunctions.getMainHeaderText(locator) +
                    " Expected Message :  "+ errorMessage);

            Thread.sleep(2000);
        }
        test.log(LogStatus.PASS, "Staff Edit Model >>> Validationof Input error message is success");

        commonSeleniumFunctions.clickElement(branchDetailsPage.cancelButtonLocator);
        assertTrueR(test, "Branch page did not load", branchesPage.isCurrent());

        homePage.clickElement(homePage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }

    @Test
    public void validateStaffEditModelErrorMessageTips() throws InterruptedException, JSONException ,IOException{
        System.out.println(_log_indicator + _test_indicator + " This test validates the different error Messages in Staff Edit Model...");
        ExtentTest test = report.startTest("This test validates the different error Messages in Staff Edit Model...");
        test.log(LogStatus.INFO, "Test started...");

        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on branch page...");
        reusableFunctionalities.Login();
        if (!driver.getCurrentUrl().equalsIgnoreCase(staffPageUrl)) {
            driver.navigate().to(staffPageUrl);
        }
        assertTrueR(test, "Staff page did not load", staffPage.isCurrent());
        test.log(LogStatus.INFO, "Staff Model Successfully Loaded ");

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        if (rowDataBeforeStaffs == null) {
            System.out.println(_log_indicator + _test_indicator + " No Staff exist in the system, creating a new branch /Staff to continue...");
            reusableFunctionalities.createBrnchsAndStaffs();
        }

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        assertTrueR(test, "No branch /Staff exists for running this test", rowDataBeforeStaffs.length > 0);

        staffPage.clickOptionAtIndex(1, staffPage.staffOptionEditLocator);
        assertTrueR(test, "Staff edit modal did not open", staffPage.isStaffEditModalDisplayed());

        test.log(LogStatus.INFO, "Staff Edit Model Successfully Loaded ");

        ArrayList<String> executionKeys = testData.getStaffhModelExecutionOrder();
        String S = "This field should follow pattern ^[a-zA-Z" + "\\s]*$";

        test.log(LogStatus.INFO, "Staff Edit Model -  Valdating input error messages for Staff Name  ");


        for (int i = 0; i < executionKeys.size(); i++) {
            // getkey
            key = executionKeys.get(i);

            commonSeleniumFunctions.clearText(locator(key));
            commonSeleniumFunctions.setTextFieldValueFor(locator(key), genericFunctions.convertTestSetString(testData.fieldErrorValuesStaffEditModel(), key));

            By locator = staffPage.getLocatorNgValue(genericFunctions.convertTestSetString(testData.fieldLocatorStaffModel(), key));

            if (key.equals("staffNamePattern")) {
                errorMessage = "This field should follow pattern ^[a-zA-Z" + "\\s]*$.";
            } else {
                errorMessage = genericFunctions.convertTestSetString(testData.fieldErrorMessagesStaffModel(), key);
            }
            assertEqualsR(test, key.toUpperCase() + "  >>>>  Error Message is incorrect", errorMessage, commonSeleniumFunctions.getMainHeaderText(locator));

            test.log(LogStatus.INFO, "Search Edit Modeil - Validation  for  "+ key  + "  is success");
            test.log(LogStatus.INFO, "Search Edit Modeil - Validation  for  "+ key  + "  is success /n" +
                    "Actual Messange :   /n" +commonSeleniumFunctions.getMainHeaderText(locator) +
                    " Expected Message :  "+ errorMessage);
            Thread.sleep(2000);
        }
        test.log(LogStatus.PASS, "Staff Edit Model >>> Validationof Input error message is success");

        commonSeleniumFunctions.clickElement(staffsDetailsPage.cancelButtonLocator);
        assertTrueR(test, "Staff page did not load", staffPage.isCurrent());
        Thread.sleep(2000);
        homePage.clickElement(homePage.accountMenuLocator);
        assertTrueR(test, "Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
        test.log(LogStatus.PASS, "All validations passed.");
        report.endTest(test);
    }



    // Reusable function to Verify the search results of Branch
    private void validateBranchSearchResults(ExtentTest test) throws InterruptedException {
        System.out.println(_log_indicator + _test_indicator + " Validating branch table data...");
        rowDataAfterBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        String[] activeRow = rowDataAfterBranches[rowDataAfterBranches.length - 1];
        Assert.assertTrue(rowDataAfterBranches.length > 0);
        assertEqualsR(test,"Branch Name is mismatch  with value entered", branchName, activeRow[1]);
        assertEqualsR(test,"Code is mismatch with value entered", code, activeRow[2]);
        assertEqualsR(test,"View / Edit / Delete Buttons are present", " View  Edit  Delete", activeRow[3]);
    }

    // Reusable function to verify the search results of Staffs
    private void validateStaffSearchResults(String staffName, String staffBranchNameName,ExtentTest test) throws InterruptedException {
        System.out.println(_log_indicator + _test_indicator + " Validating staff table data...");
        rowDataAfterStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        Assert.assertTrue("No data in data set.", rowDataAfterStaffs != null && rowDataAfterStaffs.length > 0);
        String[] activeRow = rowDataAfterStaffs[rowDataAfterStaffs.length - 1];
        assertEqualsR(test,"Staff Name is mismatch  with value entered", staffName, activeRow[1]);
        assertEqualsR(test," Staff Branch Name is mismatch with value entered", staffBranchNameName, activeRow[2]);
        assertEqualsR(test,"View / Edit / Delete Buttons are present", " View  Edit  Delete", activeRow[3]);
    }

    // Reusable function to validate the Table content
    private void validateTableContent(String[][] rowDataBefore, String[][] rowDataAfter) {
        // Below code will check Table count by taking count Before and After
        if (rowDataBefore != null) {
            Assert.assertTrue(rowDataAfter.length > rowDataBefore.length);
        } else {
            Assert.assertTrue(rowDataAfter.length > 0);
        }
        //to check if ID is incremented
        for (int i = 0; i < rowDataAfter.length; i++) {
            if (i < (rowDataAfter.length - 1)) {
                int currentId = Integer.valueOf(rowDataAfter[i][0]);
                int nextId = Integer.valueOf(rowDataAfter[i + 1][0]);
                // Below assert to check if next Id is 1 more than previous Id
                Assert.assertEquals(currentId + 1, nextId);
            }
        }
    }

    public By locator(String key) {

        if (key.contains("branchName")) {
            valueToSetTolocator = branchesPage.branchEditNameFieldLocator;
        } else if (key.contains("branchCode")) {
            valueToSetTolocator = branchesPage.branchCodeFiledLocator;
        } else if (key.contains("staffName")) {
            valueToSetTolocator = staffPage.staffNameFiledLocator;
        }

        return valueToSetTolocator;
    }


    @AfterClass
    public static void endTest() {
//        report.endTest(test);
        report.flush();
    }

}

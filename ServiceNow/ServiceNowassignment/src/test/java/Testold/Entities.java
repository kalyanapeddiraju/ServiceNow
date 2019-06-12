package Testold;

import pages.*;
import Utils.CommonSeleniumFunctions;
import Utils.GenericFunctions;
import Utils.ReusableFunctionalities;
import Utils.TestBase;
import data.TestData;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class Entities extends TestBase {

    static WebDriver driver = getDriver();

    final String _test_indicator = "(test)", _log_indicator = ">> ";
    ReusableFunctionalities reusableFunctionalities = new ReusableFunctionalities();
    CommonSeleniumFunctions commonSeleniumFunctions = new CommonSeleniumFunctions();
    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    BranchesPage branchesPage = new BranchesPage();
    BranchDetailsPage branchDetailsPage = new BranchDetailsPage();
    StaffsDetailsPage staffsDetailsPage = new StaffsDetailsPage();
    GenericFunctions genericFunctions = new GenericFunctions();
    TestData testData = new TestData();
    StaffPage staffPage = new StaffPage();
    ArrayList<String> windowList = new ArrayList<>();
    String branchName, code, staffName,key;
    //ArrayList rowData;
    String[][] rowDataBeforeBranches, rowDataAfterBranches, rowDataActiveBranches, rowDataBeforeStaffs, rowDataAfterStaffs, rowDataActiveStaffs;
    boolean staffDeleteTestsPassed = false;
    By valueToSetTolocator = By.id("");
    boolean isCreateBranchesStaffsSuccess = false;
    String errorMessage;

   // @Ignore
    @Test
    public void validateEntitiesBranch() throws InterruptedException, JSONException, IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates branch creation features.");
        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on branch page...");

        reusableFunctionalities.Login();

        // Test starts here
        System.out.println(_log_indicator + _test_indicator + " Creating Branchses...");
        assertEquals("Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(homePage.entitiesMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
        assertEquals("Entities/ Branch Menu is not Present in the Home Page", "Branch", commonSeleniumFunctions.getMainHeaderText(homePage.branchMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.branchMenuLocator);

        commonSeleniumFunctions.waitForPageLoad();
        Assert.assertTrue("Branches Page is not loaded  ", branchesPage.isCurrent());
        assertEquals("Title of page is incorrect", "Branches", commonSeleniumFunctions.getPageTitle());
        Assert.assertTrue("Create new Branch  button in the Branches Page is not loaded  ", commonSeleniumFunctions.isClickable(branchesPage.createButtonlocator));
        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);

        // Getting Test data to be created
        JSONArray testDataCount = new JSONArray(testData.newBranchAsArray());

        for (int i = 0; i <= testDataCount.length() - 1; i++) {

            if (!driver.getCurrentUrl().equalsIgnoreCase("http://127.0.0.1:8080/#/branch")) {
                driver.navigate().to("http://127.0.0.1:8080/#/branch");
            }

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

            System.out.println(_log_indicator + _test_indicator + " Branch created is -> " + branchName);
            System.out.println(_log_indicator + _test_indicator + " Code created for Branch  >> " + branchName + "  is -> " + code);

            Assert.assertTrue("Save button  in the Create / Edit Page not Enabled  ", commonSeleniumFunctions.isElementEnabled(branchesPage.branchSaveButtonFiledLocator));
            commonSeleniumFunctions.clickElement(branchesPage.branchSaveButtonFiledLocator);
            Thread.sleep(2000);

            // Getting row count after each save of Branch
            rowDataActiveBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);

            // To Check if the Branch is created with value we sent
            String[] lastRow = rowDataActiveBranches[rowDataActiveBranches.length - 1];
            assertEquals("Branch Name is mismatch  with value entered", branchName, lastRow[1]);
            assertEquals("Code is mismatch with value entered", code, lastRow[2]);
            assertEquals("View / Edit / Delete Buttons are present", " View  Edit  Delete", lastRow[3]);


            // Below steps will create Staffs to the corespoding Branchces
            System.out.println(_log_indicator + _test_indicator + " Creating Staffs to the Branch...");

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
            Assert.assertTrue("Staffs  Namme Filed is not present ", commonSeleniumFunctions.isElementVisible(staffPage.branchNameDropDownFiledLocator));
            commonSeleniumFunctions.setTextFieldValueFor(staffPage.branchNameDropDownFiledLocator, branchName);

            Assert.assertTrue("Save button  in the Create / Edit Page not Enabled in Staffs Page   ", commonSeleniumFunctions.isElementEnabled(staffPage.staffSaveButtonFiledLocator));
            commonSeleniumFunctions.clickElement(staffPage.staffSaveButtonFiledLocator);

            Thread.sleep(2000);
            rowDataActiveStaffs = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        }
        rowDataAfterStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        rowDataAfterBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);

        validateTableContent(rowDataBeforeStaffs, rowDataAfterStaffs);


        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);

        commonSeleniumFunctions.clickElement(homePage.branchMenuLocator);

        validateTableContent(rowDataBeforeBranches, rowDataAfterBranches);

        homePage.clickElement(homePage.accountMenuLocator);
        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
    }

    @Ignore
    @Test
    public void validateInteractionsForABranch() throws JSONException, InterruptedException, IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the interactive buttons available for an existing branch (except delete).");
        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on branch page...");
        reusableFunctionalities.Login();
        if (!driver.getCurrentUrl().equalsIgnoreCase("http://127.0.0.1:8080/#/branch")) {
            driver.navigate().to("http://127.0.0.1:8080/#/branch");
        }
        assertTrue("Branch page did not load", branchesPage.isCurrent());

        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        if (rowDataBeforeBranches == null) {
            System.out.println(_log_indicator + _test_indicator + " No branches exist in the system, creating a new branch to continue...");
            reusableFunctionalities.createBrnchsAndStaffs();
        }

        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        assertTrue("No branch exists for running this test", rowDataBeforeBranches.length > 0);

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
            assertTrue("View button is not clickable for row " + i, branchesPage.isOptionClickableAtIndex(i, branchesPage.branchOptionViewLocator));
            assertTrue("Edit button is not clickable for row " + i, branchesPage.isOptionClickableAtIndex(i, branchesPage.branchOptionEditLocator));
            // 2a. validate button behavior -> view button
            branchesPage.clickOptionAtIndex(i, branchesPage.branchOptionViewLocator);
            assertTrue("Branch detail page did not load", branchDetailsPage.isCurrent());
            assertTrue("Branch detail page heading is incorrect", branchDetailsPage.isHeadingCorrect(Integer.valueOf(currentBranchId)));
            assertTrue("Branch name is enabled", branchDetailsPage.getBranchNameFieldEnabled());
            assertTrue("Branch code is enabled", branchDetailsPage.getBranchCodeFieldEnabled());
            assertEquals("Branch name is incorrect", currentBranchName, branchDetailsPage.getBranchNameFieldValue());
            assertEquals("Branch code is incorrect", currentBranchCode, branchDetailsPage.getBranchCodeFieldValue());
            branchDetailsPage.clickBackButton();
            // 2b. validate button behavior -> edit button -> checking form state in modal
            branchesPage.clickOptionAtIndex(i, branchesPage.branchOptionEditLocator);
            assertTrue("Branch edit modal did not open", branchesPage.isBranchEditModalDisplayed());
            assertFalse("Branch edit modal has enabled Id field", branchesPage.isBranchEditFieldEnabled(branchesPage.branchEditIdFieldLocator));
            assertTrue("Branch edit modal has disabled Name field", branchesPage.isBranchEditFieldEnabled(branchesPage.branchEditNameFieldLocator));
            assertTrue("Branch edit modal has disabled Code field", branchesPage.isBranchEditFieldEnabled(branchesPage.branchEditCodeFieldLocator));
            assertTrue("Save button is not enabled when no changes are made", branchesPage.isSaveButtonEnabledInModal());
            assertTrue("Cancel button is not enabled when no changes are made", branchesPage.isCancelButtonEnabledInModal());
            // 2b. validate button behavior -> edit button -> checking auto-populated values
            assertEquals("Branch edit modal has incorrect Id field value", currentBranchId, branchesPage.getBranchEditFieldValue(branchesPage.branchEditIdFieldLocator));
            assertEquals("Branch edit modal has incorrect Name field value", currentBranchName, branchesPage.getBranchEditFieldValue(branchesPage.branchEditNameFieldLocator));
            assertEquals("Branch edit modal has incorrect Code field value", currentBranchCode, branchesPage.getBranchEditFieldValue(branchesPage.branchEditCodeFieldLocator));
            // 2b. validate button behavior -> edit button -> editing field values
            commonSeleniumFunctions.clearText(branchesPage.branchEditNameFieldLocator);
            Thread.sleep(2000);
            assertFalse("Save button is enabled when name is cleared", branchesPage.isSaveButtonEnabledInModal());
            assertTrue("Cancel button is not enabled when name is cleared", branchesPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.setTextFieldValueFor(branchesPage.branchEditNameFieldLocator, newValueName);
            assertTrue("Save button is not enabled when name value is put back in", branchesPage.isSaveButtonEnabledInModal());
            assertTrue("Cancel button is not enabled when name value is put back in", branchesPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.clearText(branchesPage.branchEditCodeFieldLocator);
            assertFalse("Save button is enabled when code is cleared", branchesPage.isSaveButtonEnabledInModal());
            assertTrue("Cancel button is not enabled when code is cleared", branchesPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.setTextFieldValueFor(branchesPage.branchEditCodeFieldLocator, newValueCode);
            assertTrue("Save button is not enabled when valid changes are made", branchesPage.isSaveButtonEnabledInModal());
            assertTrue("Cancel button is not enabled when valid changes are made", branchesPage.isCancelButtonEnabledInModal());
            branchesPage.clickSaveInEditModal();
            assertTrue("Branch edit modal did not close", branchesPage.isBranchEditModalNotDisplayed());
            // 2b. validate button behavior -> edit button -> editing field values -> checking if values have been updated
            assertTrue("Branch list page did not load", branchesPage.isCurrent());
            String[][] updatedRows = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
            String updatedName = updatedRows[i][1];
            String updatedCode = updatedRows[i][2];
            assertEquals("New branch name did not get saved after edit", newValueName, updatedName);
            assertEquals("New branch code did not get saved after edit", newValueCode, updatedCode);
        }


        homePage.clickElement(homePage.accountMenuLocator);
        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
    }

    @Ignore
    @Test
    public void validateInteractionsForStaffs() throws JSONException, InterruptedException, IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the interactive buttons available for an existing Staffs (except delete).");
        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on branch page...");
        reusableFunctionalities.Login();
        if (!driver.getCurrentUrl().equalsIgnoreCase("http://127.0.0.1:8080/#/staff")) {
            driver.navigate().to("http://127.0.0.1:8080/#/staff");
        }
        assertTrue("Staff page did not load", staffPage.isCurrent());

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        if (rowDataBeforeStaffs == null) {
            System.out.println(_log_indicator + _test_indicator + " No Staffs exist in the system, creating a new branch / Staffs to continue...");
            isCreateBranchesStaffsSuccess = reusableFunctionalities.createBrnchsAndStaffs();
        }

        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);
        commonSeleniumFunctions.clickElement(homePage.staffMenuLocator);

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        assertTrue("No Staffs exists for running this test", rowDataBeforeStaffs.length > 0);

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
            assertTrue("View button is not clickable for row " + i, staffPage.isOptionClickableAtIndex(i, staffPage.staffOptionViewLocator));
            assertTrue("Edit button is not clickable for row " + i, staffPage.isOptionClickableAtIndex(i, staffPage.staffOptionEditLocator));
            // 2a. validate button behavior -> view button
            staffPage.clickOptionAtIndex(i, staffPage.staffOptionViewLocator);
            assertTrue("Staff detail page did not load", staffsDetailsPage.isCurrent());
            assertTrue("Staff detail page heading is incorrect", staffsDetailsPage.isHeadingCorrect(Integer.valueOf(currentStaffId)));
            assertTrue("Staff name is enabled", staffsDetailsPage.getStaffNameFieldEnabled());
            assertTrue("Staff Branch Name is enabled", staffsDetailsPage.getBranchNameFieldEnabled());
            assertEquals("Staff name is incorrect", currentStaffsName, staffsDetailsPage.getStaffNameFieldValue());
            assertEquals("Staff Branch Name is incorrect", currentStaffBranchName, staffsDetailsPage.getBranchNameFieldValue());
            staffsDetailsPage.clickBackButton();
            // 2b. validate button behavior -> edit button -> checking form state in modal
            staffPage.clickOptionAtIndex(i, staffPage.staffOptionEditLocator);
            assertTrue("Staff edit modal did not open", staffPage.isStaffEditModalDisplayed());
            assertFalse("Staff edit modal has enabled Id field", staffPage.isStaffEditFieldEnabled(staffPage.staffEditIdFieldLocator));
            assertTrue("Staff edit modal has disabled Name field", staffPage.isStaffEditFieldEnabled(staffPage.staffEditNameFieldLocator));
            assertTrue("Staff edit modal has disabled Branch Name DropDown ", staffPage.isStaffEditFieldEnabled(staffPage.staffEditBranchNameDropDownLocator));
            assertTrue("Save button is not enabled when no changes are made", staffPage.isSaveButtonEnabledInModal());
            assertTrue("Cancel button is not enabled when no changes are made", staffPage.isCancelButtonEnabledInModal());
            // 2b. validate button behavior -> edit button -> checking auto-populated values
            assertEquals("Staff edit modal has incorrect Id field value", currentStaffId, staffPage.getStaffEditFieldValue(staffPage.staffEditIdFieldLocator));
            assertEquals("Staff edit modal has incorrect Staff Name field value", currentStaffsName, staffPage.getStaffEditFieldValue(staffPage.staffEditNameFieldLocator));
            assertEquals("Staff edit modal has incorrect Branch Name DropDown value", currentStaffBranchName, staffPage.getStaffEditDropDownValue(staffPage.staffEditBranchNameDropDownLocator));
            // 2b. validate button behavior -> edit button -> editing field values
            commonSeleniumFunctions.clearText(staffPage.staffEditNameFieldLocator);
            Thread.sleep(2000);
            assertFalse("Save button is enabled when name is cleared", staffPage.isSaveButtonEnabledInModal());
            assertTrue("Cancel button is not enabled when name is cleared", staffPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffEditNameFieldLocator, newValueStaffName);
            assertTrue("Save button is not enabled when Staff name value is put back in", staffPage.isSaveButtonEnabledInModal());
            assertTrue("Cancel button is not enabled when Staff name value is put back in", staffPage.isCancelButtonEnabledInModal());
            commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffEditBranchNameDropDownLocator, newValueBranchName);
            assertTrue("Save button is not enabled when valid changes are made", staffPage.isSaveButtonEnabledInModal());
            assertTrue("Cancel button is not enabled when valid changes are made", staffPage.isCancelButtonEnabledInModal());
            staffPage.clickSaveInEditModal();
            assertTrue("Staff edit modal did not close", staffPage.isStaffEditModalNotDisplayed());
            // 2b. validate button behavior -> edit button -> editing field values -> checking if values have been updated
            assertTrue("Staff list page did not load", staffPage.isCurrent());
            String[][] updatedRows = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
            String updatedStaffName = updatedRows[i][1];
            String updatedStaffBranchName = updatedRows[i][2];
            assertEquals("New branch name did not get saved after edit", newValueStaffName, updatedStaffName);
            assertEquals("New branch code did not get saved after edit", newValueBranchName, updatedStaffBranchName);
        }

        homePage.clickElement(homePage.accountMenuLocator);
        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
    }

    @Ignore
    @Test
    public void validateBranchStaffsDeleteFunctionality() throws JSONException, InterruptedException, IOException {

        System.out.println(_log_indicator + _test_indicator + " This test validates the delete button available for an existing branch.");
        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on Staff page...");
        reusableFunctionalities.Login();
        if (!driver.getCurrentUrl().equalsIgnoreCase("http://localhost:8080/#/staff")) {
            driver.navigate().to("http://127.0.0.1:8080/#/staff");
        }
        assertTrue("Staff page did not load", staffPage.isCurrent());

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        if (rowDataBeforeStaffs == null) {
            isCreateBranchesStaffsSuccess = reusableFunctionalities.createBrnchsAndStaffs();
        } else {
            isCreateBranchesStaffsSuccess = true;
        }

        if (isCreateBranchesStaffsSuccess) {
            for (int i = 0; i < rowDataBeforeStaffs.length; i++) {
                assertTrue("Delete option is not enabled for row " + 0, staffPage.isOptionClickableAtIndex(0, staffPage.staffOptionDeleteLocator));
                commonSeleniumFunctions.isElementVisible(staffPage.staffDeleteButtonLocator);
                staffPage.clickOptionAtIndex(0, staffPage.staffOptionDeleteLocator);
                String currentStaffId = rowDataBeforeStaffs[i][0];

                assertEquals("Delete Staff Title of page is incorrect", "Confirm delete operation", commonSeleniumFunctions.getMainHeaderText(staffPage.staffDelelteLabelLocator));
                assertEquals("Delete Staff Title of page is incorrect", "Are you sure you want to delete Staff " + currentStaffId + "?", commonSeleniumFunctions.getMainHeaderText(staffPage.staffDeleltequestionLocator));
                //assertTrue("Staff Delete questionis incorrect", staffsDetailsPage.isHeadingCorrect(Integer.valueOf(currentStaffId)));
                assertTrue("Delete button is not enabled in the model", staffPage.isStaffDeleteButtonEnabledInModal());
                assertTrue("Cancel button is not enabled in the model", staffPage.isStaffDeleteCancelButtonEnabledInModal());
                System.out.println("Deleting Staffs >>>>>   Staff " + currentStaffId);
                commonSeleniumFunctions.clickElement(staffPage.staffDeleteButtonLocator);
                commonSeleniumFunctions.isElementVisible(staffPage.staffTableLocator);
                String[][] updatedRows = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
                assertEquals("Number of records did not reduce by 1", rowDataBeforeStaffs.length - (i + 1), updatedRows != null ? updatedRows.length : 0);
            }
            staffDeleteTestsPassed = true;
        } else {

            System.out.println(_log_indicator + _test_indicator + " No Staffs to delete >>>>.");
            staffDeleteTestsPassed = true;
        }

        if (staffDeleteTestsPassed) {
            if (!driver.getCurrentUrl().equalsIgnoreCase("http://127.0.0.1:8080:8080/#/branch")) {
                driver.navigate().to("http://127.0.0.1:8080/#/branch");
            }
            assertTrue("Branch page did not load", branchesPage.isCurrent());

            rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);

            if (rowDataBeforeBranches != null) {
                for (int i = 0; i < rowDataBeforeBranches.length; i++) {
                    assertTrue("Delete option is not enabled for row " + 0, branchesPage.isOptionClickableAtIndex(0, branchesPage.branchOptionDeleteLocator));
                    commonSeleniumFunctions.isElementVisible(branchesPage.branchDeleteButtonLocator);
                    branchesPage.clickOptionAtIndex(0, branchesPage.branchOptionDeleteLocator);
                    String currentStaffId = rowDataBeforeBranches[i][0];
                    assertEquals("Delete Branch Title of page is incorrect", "Confirm delete operation", commonSeleniumFunctions.getMainHeaderText(branchesPage.branchDelelteLabelLocator));
                    assertEquals("Delete Branch Title of page is incorrect", "Are you sure you want to delete Branch " + currentStaffId + "?", commonSeleniumFunctions.getMainHeaderText(branchesPage.branchDeleltequestionLocator));
                    assertTrue("Delete button is not enabled in the model", branchesPage.isBranchDeleteButtonEnabledInModal());
                    assertTrue("Cancel button is not enabled in the model", branchesPage.isBranchDeleteCancelButtonEnabledInModal());
                    System.out.println("Deleting Branches >>>>>   Branch " + currentStaffId);
                    branchesPage.clickDeleteButton();
                    String[][] updatedRows = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
                    assertEquals("Number of records did not reduce by 1", rowDataBeforeBranches.length - (i + 1), updatedRows != null ? updatedRows.length : 0);
                }
            } else {
                System.out.println(_log_indicator + _test_indicator + " Staff deletion test failed. Branch delete test cannot be run.");
            }
        } else {
            System.out.println(_log_indicator + _test_indicator + " No Branches to delete >>>>.");
        }

        homePage.clickElement(homePage.accountMenuLocator);
        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
    }

    @Ignore
    @Test
    public void validateBranchSearchFunctionality() throws InterruptedException, JSONException, IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the search functionality for branches.");

        reusableFunctionalities.Login();

        assertEquals("Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(homePage.entitiesMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);

        assertEquals("Entities/ Branch Menu is not Present in the Home Page", "Branch", commonSeleniumFunctions.getMainHeaderText(homePage.branchMenuLocator));

        commonSeleniumFunctions.clickElement(homePage.branchMenuLocator);
        commonSeleniumFunctions.waitForPageLoad();

        Assert.assertTrue("Branches Page is not loaded  ", branchesPage.isCurrent());
        assertEquals("Title of page is incorrect", "Branches", commonSeleniumFunctions.getPageTitle());
        Assert.assertTrue("Branches Page is not loaded  ", commonSeleniumFunctions.isClickable(branchesPage.createButtonlocator));
        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);

        if (rowDataBeforeBranches == null) {
            System.out.println("No Branches to search adding Branches....");
            isCreateBranchesStaffsSuccess = reusableFunctionalities.createBrnchsAndStaffs();

        } else {
            isCreateBranchesStaffsSuccess = true;
        }

        if (isCreateBranchesStaffsSuccess) {
            if (!driver.getCurrentUrl().equalsIgnoreCase("http://localhost:8080/#/branch")) {
                driver.navigate().to("http://127.0.0.1:8080/#/branch");
            }
            Assert.assertTrue("SearchQuery Filed is not found in Brachces Page ", commonSeleniumFunctions.checkElementPresent(branchesPage.brachSeachLocator));

            JSONArray testDataCount = new JSONArray(testData.newBranchAsArray());

            for (int i = 0; i <= testDataCount.length() - 1; i++) {

                branchName = genericFunctions.convertTestSetString(testData.newBranch(), "branchName" + (i + 1));
                code = genericFunctions.convertTestSetString(testData.newBranch(), "code" + (i + 1));

                commonSeleniumFunctions.clearText(branchesPage.brachSeachLocator);

                commonSeleniumFunctions.setTextFieldValueFor(branchesPage.brachSeachLocator, branchName);
                Assert.assertTrue("SearchQuery Button is either not found / Loded in Brachces Page ", commonSeleniumFunctions.checkElementPresent(branchesPage.brachQuerySearchButtonLocator));
                commonSeleniumFunctions.clickElement(branchesPage.brachQuerySearchButtonLocator);

                validateBranchSearchResults();

                commonSeleniumFunctions.clearText(branchesPage.brachSeachLocator);
                commonSeleniumFunctions.setTextFieldValueFor(branchesPage.brachSeachLocator, code);
                Assert.assertTrue("SearchQuery Button is either not found / Loded in Brachces Page ", commonSeleniumFunctions.checkElementPresent(branchesPage.brachQuerySearchButtonLocator));
                commonSeleniumFunctions.clickElement(branchesPage.brachQuerySearchButtonLocator);

                validateBranchSearchResults();

            }

        }

        homePage.clickElement(homePage.accountMenuLocator);
        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
    }

    @Ignore
    @Test
    public void validateStaffsSearchFunctionality() throws InterruptedException, JSONException, IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the search functionality for branches.");

        ReusableFunctionalities reusableFunctionalities = new ReusableFunctionalities();
        reusableFunctionalities.Login();

        assertEquals("Entities Menu is not Present in the Home Page", "Entities", commonSeleniumFunctions.getMainHeaderText(homePage.entitiesMenuLocator));
        commonSeleniumFunctions.clickElement(homePage.entitiesMenuLocator);

        assertEquals("Entities/ Staff Menu is not Present in the Home Page", "Staff", commonSeleniumFunctions.getMainHeaderText(homePage.staffMenuLocator));

        commonSeleniumFunctions.clickElement(homePage.staffMenuLocator);
        commonSeleniumFunctions.waitForPageLoad();

        Assert.assertTrue("Staffs Page is not loaded  ", staffPage.isCurrent());
        assertEquals("Title of page is incorrect", "Staffs", commonSeleniumFunctions.getPageTitle());
        commonSeleniumFunctions.isElementVisible(staffPage.createButtonlocator);
        Assert.assertTrue("Staffs Page is not loaded  ", commonSeleniumFunctions.isClickable(staffPage.createButtonlocator));
        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);

        if (rowDataBeforeStaffs == null) {
            System.out.println("No Staffs to search adding Staffs....");
            isCreateBranchesStaffsSuccess = reusableFunctionalities.createBrnchsAndStaffs();
        } else {
            isCreateBranchesStaffsSuccess = true;
        }

        if (isCreateBranchesStaffsSuccess) {
            if (!driver.getCurrentUrl().equalsIgnoreCase("http://127.0.0.1:8080/#/staff")) {
                driver.navigate().to("http://127.0.0.1:8080/#/staff");
            }

            Assert.assertTrue("SearchQuery Filed is not found in Staffs Page ", commonSeleniumFunctions.checkElementPresent(staffPage.staffSearchFiledLocator));

            String[][] existingData = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);

            for (int i = 0; i <= (existingData.length > 3 ? 3 : existingData.length) - 1; i++) {

                staffName = genericFunctions.convertTestSetString(testData.newBranch(), "staffName" + (i + 1));
                branchName = genericFunctions.convertTestSetString(testData.newBranch(), "branchName" + (i + 1));

                staffName = existingData[i][1];
                branchName = existingData[i][2];

                commonSeleniumFunctions.clearText(staffPage.staffSearchFiledLocator);

                commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffSearchFiledLocator, staffName);
                Assert.assertTrue("SearchQuery Button is either not found / Loded in Staffs Page ", commonSeleniumFunctions.checkElementPresent(staffPage.staffQuerySearchButtonLocator));
                commonSeleniumFunctions.clickElement(staffPage.staffQuerySearchButtonLocator);

                validateStaffSearchResults(staffName, branchName);

                commonSeleniumFunctions.clearText(staffPage.staffSearchFiledLocator);
                commonSeleniumFunctions.setTextFieldValueFor(staffPage.staffSearchFiledLocator, branchName);
                Assert.assertTrue("SearchQuery Button is either not found / Loded in Staff Page ", commonSeleniumFunctions.checkElementPresent(staffPage.staffQuerySearchButtonLocator));
                commonSeleniumFunctions.clickElement(staffPage.staffQuerySearchButtonLocator);

                validateStaffSearchResults(staffName, branchName);

            }

        }
        homePage.clickElement(homePage.accountMenuLocator);
        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
    }

    @Ignore
    @Test
    public void validateBranchEditModelErrorMessageTips() throws InterruptedException, JSONException, IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the different error Messages in Branch Edit Model...");
        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on branch page...");
        reusableFunctionalities.Login();
        if (!driver.getCurrentUrl().equalsIgnoreCase("http://127.0.0.1:8080/#/branch")) {
            driver.navigate().to("http://127.0.0.1:8080/#/branch");
        }
        assertTrue("Branch page did not load", branchesPage.isCurrent());

        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        if (rowDataBeforeBranches == null) {
            System.out.println(_log_indicator + _test_indicator + " No branches exist in the system, creating a new branch to continue...");
            reusableFunctionalities.createBrnchsAndStaffs();
        }

        rowDataBeforeBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        assertTrue("No branch exists for running this test", rowDataBeforeBranches.length > 0);

        branchesPage.clickOptionAtIndex(1, branchesPage.branchOptionEditLocator);
        assertTrue("Branch edit modal did not open", branchesPage.isBranchEditModalDisplayed());


        ArrayList<String> executionKeys = testData.getBranchModelExecutionOrder();
        String S="This field should follow pattern ^[a-zA-Z"+"\\s]*$";

        for (int i = 0; i < executionKeys.size(); i++) {
            // getkey
            key = executionKeys.get(i);

            commonSeleniumFunctions.clearText(locator(key));
            commonSeleniumFunctions.setTextFieldValueFor(locator(key), genericFunctions.convertTestSetString(testData.fieldErrorValuesBranchModel(), key));

            By locator = branchesPage.getLocatorNgValue(genericFunctions.convertTestSetString(testData.fieldLocatorBranchModel(), key));

            if(key.equals("branchNamePattern")){
                errorMessage="This field should follow pattern ^[a-zA-Z"+"\\s]*$.";
            }else {
                errorMessage = genericFunctions.convertTestSetString(testData.fieldErrorMessagesBranchModel(), key);
            }
            assertEquals(key.toUpperCase() + "  >>>>  Error Message is incorrect", errorMessage, commonSeleniumFunctions.getMainHeaderText(locator));

            Thread.sleep(2000);
        }

        commonSeleniumFunctions.clickElement(branchDetailsPage.cancelButtonLocator);
        assertTrue("Branch page did not load", branchesPage.isCurrent());

        homePage.clickElement(homePage.accountMenuLocator);
        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
    }


    @Test
    public void validateStaffEditModelErrorMessageTips() throws InterruptedException, JSONException, IOException {
        System.out.println(_log_indicator + _test_indicator + " This test validates the different error Messages in Staff Edit Model...");
        System.out.println(_log_indicator + _test_indicator + " Ensuring that user is on branch page...");
        reusableFunctionalities.Login();
        if (!driver.getCurrentUrl().equalsIgnoreCase("http://127.0.0.1:8080/#/staff")) {
            driver.navigate().to("http://127.0.0.1:8080/#/staff");
        }
        assertTrue("Staff page did not load", staffPage.isCurrent());

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        if (rowDataBeforeStaffs == null) {
            System.out.println(_log_indicator + _test_indicator + " No Staff exist in the system, creating a new branch /Staff to continue...");
            reusableFunctionalities.createBrnchsAndStaffs();
        }

        rowDataBeforeStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        assertTrue("No branch /Staff exists for running this test", rowDataBeforeStaffs.length > 0);

        staffPage.clickOptionAtIndex(1, staffPage.staffOptionEditLocator);
        assertTrue("Staff edit modal did not open", staffPage.isStaffEditModalDisplayed());


        ArrayList<String> executionKeys = testData.getStaffhModelExecutionOrder();
        String S="This field should follow pattern ^[a-zA-Z"+"\\s]*$";

        for (int i = 0; i < executionKeys.size(); i++) {
            // getkey
            key = executionKeys.get(i);

            commonSeleniumFunctions.clearText(locator(key));
            commonSeleniumFunctions.setTextFieldValueFor(locator(key), genericFunctions.convertTestSetString(testData.fieldErrorValuesStaffEditModel(), key));

            By locator = staffPage.getLocatorNgValue(genericFunctions.convertTestSetString(testData.fieldLocatorStaffModel(), key));

            if(key.equals("staffNamePattern")){
                errorMessage="This field should follow pattern ^[a-zA-Z"+"\\s]*$.";
            }else {
                errorMessage = genericFunctions.convertTestSetString(testData.fieldErrorMessagesStaffModel(), key);
            }
            assertEquals(key.toUpperCase() + "  >>>>  Error Message is incorrect", errorMessage, commonSeleniumFunctions.getMainHeaderText(locator));

            Thread.sleep(2000);
        }

        commonSeleniumFunctions.clickElement(staffsDetailsPage.cancelButtonLocator);
        assertTrue("Staff page did not load", staffPage.isCurrent());
        Thread.sleep(2000);
        homePage.clickElement(homePage.accountMenuLocator);
        Assert.assertTrue("Signout is not found /loaded  Login Page is not Loaded", commonSeleniumFunctions.isClickable(loginPage.signoutLocator));
        homePage.clickElement(loginPage.signoutLocator);
        System.out.println(_log_indicator + _test_indicator + " This test ran without issues.");
    }



    // Reusable function to Verify the search results of Branch
    public void validateBranchSearchResults() throws InterruptedException {
        System.out.println(_log_indicator + _test_indicator + " Validating branch table data...");
        rowDataAfterBranches = commonSeleniumFunctions.getTableCount(branchesPage.brachTableLocator);
        String[] activeRow = rowDataAfterBranches[rowDataAfterBranches.length - 1];
        Assert.assertTrue(rowDataAfterBranches.length > 0);
        assertEquals("Branch Name is mismatch  with value entered", branchName, activeRow[1]);
        assertEquals("Code is mismatch with value entered", code, activeRow[2]);
        assertEquals("View / Edit / Delete Buttons are present", " View  Edit  Delete", activeRow[3]);
    }

    // Reusable function to verify the search results of Staffs
    public void validateStaffSearchResults(String staffName, String staffBranchNameName) throws InterruptedException {
        System.out.println(_log_indicator + _test_indicator + " Validating staff table data...");
        rowDataAfterStaffs = commonSeleniumFunctions.getTableCount(staffPage.staffTableLocator);
        Assert.assertTrue("No data in data set.", rowDataAfterStaffs != null && rowDataAfterStaffs.length > 0);
        String[] activeRow = rowDataAfterStaffs[rowDataAfterStaffs.length - 1];
        assertEquals("Staff Name is mismatch  with value entered", staffName, activeRow[1]);
        assertEquals(" Staff Branch Name is mismatch with value entered", staffBranchNameName, activeRow[2]);
        assertEquals("View / Edit / Delete Buttons are present", " View  Edit  Delete", activeRow[3]);
    }

    //Resulable function to validate the Table content
    public void validateTableContent(String[][] rowDataBefore, String[][] rowDataAfter) {
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
        }else if (key.contains("staffName")) {
            valueToSetTolocator = staffPage.staffNameFiledLocator;
        }

        return valueToSetTolocator;
    }

}
